package project.first.spring.flows.Onboarding.pageRender.component;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import project.first.spring.Utilities.Utils.JsonHelper;
import project.first.spring.Utilities.common.IPageRenderer;
import project.first.spring.Utilities.dao.ConfigDAO;
import project.first.spring.Utilities.enums.ConfigCategory;
import project.first.spring.Utilities.enums.ConfigType;
import project.first.spring.Utilities.response.SbApiResponse;
import project.first.spring.flows.Onboarding.model.LoginDto;

import static project.first.spring.Utilities.Constants.FOOTER;
import static project.first.spring.Utilities.Constants.HEADER;
import static project.first.spring.flows.Onboarding.pageRender.constants.OnboardingConstants.LOGIN;

@Component
@RequiredArgsConstructor
public class LoginPageRender implements IPageRenderer<LoginDto> {

    private final ConfigDAO configDAO;
    private final JsonHelper jsonHelper;

    @Override
    public SbApiResponse renderPageFromData(LoginDto data) throws JsonProcessingException {
        return SbApiResponse.buildSuccess(getLoginScreen());
    }

    private Object getLoginScreen() throws JsonProcessingException {
        JSONObject loginScreen = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.LOGIN).getData());
        JSONObject header = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.HEADER).getData());
        JSONObject footer = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.FOOTER).getData());

        JSONObject login = new JSONObject();
        login.put(HEADER, header);
        login.put(LOGIN, loginScreen);
        login.put(FOOTER, footer);

        return jsonHelper.getObjectMapper().readTree(login.toString());
    }
}
