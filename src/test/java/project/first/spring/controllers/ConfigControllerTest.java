package project.first.spring.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import project.first.spring.Utilities.Utils.JsonHelper;
import project.first.spring.Utilities.controllers.ConfigController;
import project.first.spring.Utilities.dao.ConfigDAO;
import project.first.spring.Utilities.entities.Config;
import project.first.spring.Utilities.enums.ConfigType;
import project.first.spring.config.SpringSecurityConfiguration;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import java.time.Instant;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static project.first.spring.Utilities.Constants.UTILITY_CONTROLLER_PATH;

@Import(SpringSecurityConfiguration.class)
@WebMvcTest(ConfigController.class)
public class ConfigControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ConfigDAO configDAO;

    @MockBean
    JsonHelper jsonHelper;

    //TODO: rectify bad request

    public static final SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor jwtRequestPostProcessor =
            jwt().jwt(jwt -> {
                jwt.claims(claims -> {
                            claims.put("scope", "message-read");
                            claims.put("scope", "message-write");
                        })
                        .subject("messaging-client")
                        .notBefore(Instant.now().minusSeconds(5L));
            });

    @Test
    void testDisablingCsrf() throws Exception {
        Config config = Config.builder().build();

        given(configDAO.findByConfigTypeAndConfigCategory(any(), any())).willReturn(config);
        given((configDAO.save(any(Config.class)))).willReturn(config);

        given((jsonHelper.toJson(any()))).willReturn("test string");

        mockMvc.perform(post(UTILITY_CONTROLLER_PATH + "/insert/config")
                        .queryParam("type", String.valueOf(ConfigType.CONSTANTS))
                        .queryParam("category","TEST")
                        .accept(MediaType.APPLICATION_JSON)
                        .content("test")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());

    }
}
