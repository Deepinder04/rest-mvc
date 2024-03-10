package project.first.spring.processData.model.pojos;

import lombok.Data;
import project.first.spring.processData.config.InputDataValidation;

import java.util.List;

@Data
@InputDataValidation
public class InputData {
    private List<String> input;
}
