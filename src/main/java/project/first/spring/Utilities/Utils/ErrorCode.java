package project.first.spring.Utilities.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ONBOARDING_01("User does not exist", "Sign In!"),
    ONBOARDING_02("Email and password do not match!", ""),
    ONBOARDING_03("Email already exists!", "Move to login or signUp with a different email.");

    private String instantDisplayMessage;
    private String laterDisplayMessage;
}
