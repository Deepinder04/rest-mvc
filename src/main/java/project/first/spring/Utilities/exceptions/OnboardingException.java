package project.first.spring.Utilities.exceptions;

import lombok.Data;
import project.first.spring.Utilities.Utils.ErrorCode;

@Data
public class OnboardingException extends RuntimeException{
    private ErrorCode errorCode;

    public OnboardingException(ErrorCode errorCode){
        super(errorCode.getInstantDisplayMessage());
        this.errorCode = errorCode;
    }
}
