package project.first.spring.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import project.first.spring.config.SpringSecurityConfiguration;
import project.first.spring.flows.Beer.controllers.BeerController;
import project.first.spring.flows.Beer.model.BeerStyle;
import project.first.spring.flows.Beer.services.BeerService;
import project.first.spring.flows.Beer.services.BeerServiceImpl;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.first.spring.Utilities.Constants.BEER_PATH;

@WebMvcTest(BeerController.class)
@Import(SpringSecurityConfiguration.class)
public class BasicAuthTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerService beerServiceImpl;


    @BeforeEach
    void setUp(){
        beerServiceImpl =new BeerServiceImpl();
    }

    @Test
    void testBasicAuthViaConnectedDb() throws Exception {
        mockMvc.perform(get(BEER_PATH).with(httpBasic("user", "password"))
                        .queryParam("beerStyle", BeerStyle.IPA.name())
                        .queryParam("pageSize", "800"))
                .andExpect(status().isOk());
    }

}
