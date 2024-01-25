package project.first.spring.flows.Onboarding.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.first.spring.Utilities.common.IPageRenderer;
import project.first.spring.Utilities.response.SbApiResponse;
import project.first.spring.flows.Onboarding.model.LoginDto;
import project.first.spring.flows.Onboarding.model.SignUpDto;

import static project.first.spring.flows.Onboarding.pageRender.constants.OnboardingConstants.LOGIN_PATH;
import static project.first.spring.flows.Onboarding.pageRender.constants.OnboardingConstants.SIGN_UP_PATH;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class OnboardingController {

    private final IPageRenderer<LoginDto> loginPageRender;
    private final IPageRenderer<SignUpDto> signUpPageRender;

    @GetMapping(LOGIN_PATH)
    SbApiResponse getLoginPage() throws JsonProcessingException {
        LoginDto loginDto = new LoginDto();
        return loginPageRender.renderPageFromData(loginDto);
    }

    @GetMapping(SIGN_UP_PATH)
    SbApiResponse getSignUpPage() throws JsonProcessingException {
        SignUpDto signUpDto = new SignUpDto();
        return signUpPageRender.renderPageFromData(signUpDto);
    }
}
