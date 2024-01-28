package project.first.spring.Utilities.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import project.first.spring.Utilities.Utils.MessageApiResponse;
import project.first.spring.Utilities.response.SbApiResponse;
import project.first.spring.flows.Beer.Exceptions.NotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    ResponseEntity handleNotFoundException(){
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity handleBindError(MethodArgumentNotValidException exception){
        List<Map<String, String>> errorList = exception.getFieldErrors().stream().map(fieldError -> {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put(fieldError.getField(), fieldError.getDefaultMessage());
            return errorMap;
        }).toList();

        return ResponseEntity.badRequest().body(errorList);
    }

    @ExceptionHandler
    ResponseEntity handleJPAViolations(TransactionSystemException exception){
        ResponseEntity.BodyBuilder responseEntity = ResponseEntity.badRequest();

        if(exception.getCause().getCause() instanceof ConstraintViolationException violationException){
            List errors = violationException.getConstraintViolations().stream()
                    .map(constraintViolation -> {
                        Map<String, String> errorMap = new HashMap<>();
                        errorMap.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
                        return errorMap;
                    }).toList();
            return responseEntity.body(errors);
        }
        return responseEntity.build();
    }

    @ExceptionHandler(OnboardingException.class)
    public ResponseEntity<SbApiResponse> handleOnboardingExceptions(OnboardingException exception){
        log.error("SbException {}", exception.getMessage(), exception);
        return new ResponseEntity<>(SbApiResponse.buildFailure(MessageApiResponse.build(exception.getErrorCode())), HttpStatus.OK);
    }
}