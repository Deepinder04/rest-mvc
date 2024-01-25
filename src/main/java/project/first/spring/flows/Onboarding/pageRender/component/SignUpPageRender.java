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
import project.first.spring.flows.Onboarding.model.SignUpDto;

import static project.first.spring.flows.Onboarding.pageRender.constants.OnboardingConstants.SIGN_UP;

@Component
@RequiredArgsConstructor
public class SignUpPageRender implements IPageRenderer<SignUpDto> {

    private final ConfigDAO configDAO;
    private final JsonHelper jsonHelper;

    @Override
    public SbApiResponse renderPageFromData(SignUpDto data) throws JsonProcessingException {
        return SbApiResponse.buildSuccess(getSignUpScreen());
    }

    private Object getSignUpScreen() throws JsonProcessingException {
        JSONObject signUpScreen = new JSONObject(configDAO.findByConfigTypeAndConfigCategory(ConfigType.CONSTANTS, ConfigCategory.SIGN_UP).getData());

        JSONObject signUp = new JSONObject();
        signUp.put(SIGN_UP, signUpScreen);

        return jsonHelper.getObjectMapper().readTree(signUp.toString());
    }
}
