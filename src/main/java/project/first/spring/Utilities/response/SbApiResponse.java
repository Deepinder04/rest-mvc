package project.first.spring.Utilities.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.first.spring.Utilities.Utils.MessageApiResponse;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Slf4j
public class SbApiResponse {

    private Object data;

    private Boolean success;

    private MessageApiResponse message;

    public SbApiResponse(Object data, boolean success, MessageApiResponse message) {
        this.data = data;
        this.success = success;
        this.message = message;
    }

    public static SbApiResponse buildFailure(MessageApiResponse message) {
        if (message == null)
            throw new IllegalArgumentException("Invalid arguments");

        Map<String, String> data = new HashMap<>();
        data.put("message", message.getText());

        SbApiResponse sbApiResponse = new SbApiResponse(data, false, message);

        log.info("fdApiResponse: " + sbApiResponse);
        return sbApiResponse;
    }

    public static SbApiResponse buildFailure(MessageApiResponse message, Object data) {
        if (message == null)
            throw new IllegalArgumentException("Invalid arguments");

        SbApiResponse sbApiResponse = new SbApiResponse(data, false, message);

        log.info("fdApiResponse: " + sbApiResponse);
        return sbApiResponse;
    }

    public static SbApiResponse buildSuccess(Object data) {

        SbApiResponse sbApiResponse = new SbApiResponse(data, true,null);
        log.info("fdApiResponse: " + sbApiResponse);
        return sbApiResponse;
    }
    public static SbApiResponse buildSuccess(MessageApiResponse message, Object data) {
        if (message == null)
            throw new IllegalArgumentException("Invalid arguments");

        SbApiResponse sbApiResponse = new SbApiResponse(data, true, message);

        log.info("fdApiResponse: " + sbApiResponse);
        return sbApiResponse;
    }


}