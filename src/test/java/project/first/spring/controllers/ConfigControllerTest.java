package project.first.spring.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import project.first.spring.Utilities.Utils.JsonHelper;
import project.first.spring.Utilities.controllers.ConfigController;
import project.first.spring.Utilities.dao.ConfigDAO;
import project.first.spring.Utilities.entities.Config;
import project.first.spring.Utilities.enums.ConfigCategory;
import project.first.spring.Utilities.enums.ConfigType;
import project.first.spring.config.SpringSecurityConfiguration;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.first.spring.Constants.TestConstants.MOCK_CONFIG_DATA;
import static project.first.spring.Utilities.Constants.CONFIG_INSERT_PATH;
import static project.first.spring.Utilities.Constants.UTILITY_CONTROLLER_PATH;


@Import({SpringSecurityConfiguration.class})
@WebMvcTest(ConfigController.class)
public class ConfigControllerTest {

    @MockBean
    ConfigDAO configDAO;

    @MockBean
    JsonHelper jsonHelper;

    @Autowired
    MockMvc mockMvc;

    @Captor
    ArgumentCaptor<ConfigType> configTypeCaptor;

    @Captor
    ArgumentCaptor<ConfigCategory> configCategoryCaptor;

    @BeforeEach
    void createTestConfig(){
        Config config = Config.builder()
                .configType(ConfigType.CONSTANTS)
                .configCategory(ConfigCategory.HEADER)
                .data(MOCK_CONFIG_DATA)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        given(configDAO.findByConfigTypeAndConfigCategory(any(ConfigType.class), any(ConfigCategory.class))).willReturn(config);
        given(jsonHelper.toJson(any(Object.class))).willReturn(MOCK_CONFIG_DATA);
    }

    //TODO : add basic auth in request once security config is updated, also add a failing test for it

    @Test
    void updateConfig() throws Exception {
        mockMvc.perform(post(UTILITY_CONTROLLER_PATH + CONFIG_INSERT_PATH)
                .queryParam("type", ConfigType.CONSTANTS.name())
                .queryParam("category", ConfigCategory.HEADER.name())
                .accept(MediaType.APPLICATION_JSON)
                .content(MOCK_CONFIG_DATA)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", is(MOCK_CONFIG_DATA)));

        verify(configDAO).findByConfigTypeAndConfigCategory(configTypeCaptor.capture(),configCategoryCaptor.capture());

        assertThat(configTypeCaptor.getValue()).isEqualTo(ConfigType.CONSTANTS);
        assertThat(configCategoryCaptor.getValue()).isEqualTo(ConfigCategory.HEADER);
    }
}
