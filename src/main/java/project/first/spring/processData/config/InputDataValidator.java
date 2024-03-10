package project.first.spring.processData.config;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import project.first.spring.processData.model.pojos.InputData;

import java.util.Objects;

@Component
@Slf4j
public class InputDataValidator implements ConstraintValidator<InputDataValidation, InputData> {
    @Override
    public boolean isValid(InputData inputData, ConstraintValidatorContext constraintValidatorContext) {

        if (Objects.isNull(inputData) || Objects.isNull(inputData.getInput()))
            throw new ValidationException("Data to process can't be null");

        if(inputData.getInput().size()<=1)
            throw new ValidationException("Please enter at least 2 strings to make comparisons");

        return true;
    }
}
