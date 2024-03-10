package project.first.spring.processData.model.pojos;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
@Builder
public class ProcessedData {

    private String solution;

    public ProcessedData(String solution, String message) {
        this.solution = solution;
        this.message = message;
    }

    private String message;
}
