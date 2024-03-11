package project.first.spring.processData.controller;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import project.first.spring.Utilities.Utils.JsonHelper;
import project.first.spring.config.SpringSecurityConfiguration;
import project.first.spring.processData.config.InputDataValidator;
import project.first.spring.processData.model.enums.Rules;
import project.first.spring.processData.model.pojos.InputData;
import project.first.spring.processData.model.pojos.ProcessedData;
import project.first.spring.processData.service.RuleBasedDataProcessingService;
import project.first.spring.utils.TestEventData;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.first.spring.Utilities.Constants.RULE_PROCESS_PATH;

@WebMvcTest(RuleBasedDataProcessingController.class)
@Import({JsonHelper.class, SpringSecurityConfiguration.class, InputDataValidator.class})
class RuleBasedDataProcessingControllerTest {

    @MockBean
    RuleBasedDataProcessingService dataProcessingService;
    @Autowired
    JsonHelper jsonHelper;
    @Autowired
    MockMvc mockMvc;
    @Captor
    ArgumentCaptor<List<String>> stringListArgumentCaptor;
    @Captor
    ArgumentCaptor<List<Rules>> ruleListArgumentCaptor;

    @Test
    void processListOfStringSingleRule() throws Exception {
        InputData testInputData = TestEventData.getTestInputData(true);
        String body = jsonHelper.getObjectMapper().writeValueAsString(testInputData);

        List<ProcessedData> processedData = TestEventData.getTestProcessedData(List.of(Rules.ASCENDING), testInputData.getInput().toString());
        given(dataProcessingService.getSolutions(any(), any())).willReturn(processedData);

        mockMvc.perform(post(RULE_PROCESS_PATH + "/process-string-list")
                        .queryParam("rules", Rules.ASCENDING.name())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(1)))
                .andExpect(jsonPath("data[0].message", is(Rules.ASCENDING.getDescription())))
                .andReturn();

        verify(dataProcessingService, times(1)).getSolutions(stringListArgumentCaptor.capture(), ruleListArgumentCaptor.capture());
        assertThat(stringListArgumentCaptor.getValue()).isEqualTo(testInputData.getInput());
        assertThat(ruleListArgumentCaptor.getValue()).isEqualTo(List.of(Rules.ASCENDING));
    }

    @Test
    void processListOfStringMultipleRule() throws Exception {
        InputData testInputData = TestEventData.getTestInputData(true);
        String body = jsonHelper.getObjectMapper().writeValueAsString(testInputData);

        List<ProcessedData> processedData = TestEventData.getTestProcessedData(List.of(Rules.ASCENDING,Rules.DESCENDING, Rules.GROUP_COMMON_STRINGS), testInputData.getInput().toString());
        given(dataProcessingService.getSolutions(any(), any())).willReturn(processedData);

        mockMvc.perform(post(RULE_PROCESS_PATH + "/process-string-list")
                        .queryParam("rules", Rules.ASCENDING.name() + "," + Rules.DESCENDING.name() + "," + Rules.GROUP_COMMON_STRINGS.name())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()", is(3)))
                .andReturn();

        verify(dataProcessingService, times(1)).getSolutions(stringListArgumentCaptor.capture(), ruleListArgumentCaptor.capture());
        assertThat(stringListArgumentCaptor.getValue()).isEqualTo(testInputData.getInput());
        assertThat(ruleListArgumentCaptor.getValue()).isEqualTo(List.of(Rules.ASCENDING, Rules.DESCENDING, Rules.GROUP_COMMON_STRINGS));
    }

    @Test
    void incorrectRule() throws Exception {
        InputData testInputData = TestEventData.getTestInputData(true);
        String body = jsonHelper.getObjectMapper().writeValueAsString(testInputData);

        MvcResult mvcResult = mockMvc.perform(post(RULE_PROCESS_PATH + "/process-string-list")
                        .queryParam("rules", "Incorrect rule")
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(TestEventData.INCORRECT_RULE_TEXT);
        verify(dataProcessingService, times(0)).getSolutions(any(), any());
    }

    @Test
    void notEnoughStringsToCompare() throws Exception {
        InputData testInputData = TestEventData.getTestInputData(false);
        String body = jsonHelper.getObjectMapper().writeValueAsString(testInputData);

        MvcResult mvcResult = mockMvc.perform(post(RULE_PROCESS_PATH + "/process-string-list")
                        .queryParam("rules", Rules.ASCENDING.name())
                        .accept(MediaType.APPLICATION_JSON)
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(TestEventData.ONLY_ONE_OR_NO_STRING_PROVIDED_TEXT);
        verify(dataProcessingService, times(0)).getSolutions(any(), any());
    }
}