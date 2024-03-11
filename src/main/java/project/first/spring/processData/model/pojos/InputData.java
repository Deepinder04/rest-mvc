package project.first.spring.processData.model.pojos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import project.first.spring.processData.config.InputDataValidation;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Builder
@InputDataValidation
public class InputData {
    @JsonProperty(value = "input")
    private List<String> input;
}
