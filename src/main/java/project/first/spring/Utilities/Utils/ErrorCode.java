package project.first.spring.Utilities.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
    ;

    private String instantDisplayMessage;
    private String laterDisplayMessage;
}
