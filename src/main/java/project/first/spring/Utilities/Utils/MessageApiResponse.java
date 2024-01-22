package project.first.spring.Utilities.Utils;

import lombok.Getter;
import lombok.ToString;
import org.springframework.util.StringUtils;

@Getter
@ToString
public class MessageApiResponse {

    private String code;
    private String text;
    private String ctaText;

    private MessageApiResponse() {
    }

    private MessageApiResponse(String code, String text) {
        this.code = code;
        this.text = text;
    }

    private MessageApiResponse(String code, String text, String ctaText) {
        this.code = code;
        this.text = text;
        this.ctaText = ctaText;
    }

    public static MessageApiResponse build(String code, String text) {
        if (!StringUtils.hasText(text) || !StringUtils.hasText(code))
            throw new IllegalArgumentException("Invalid data");

        return new MessageApiResponse(code, text);
    }

    public static MessageApiResponse build(String code, String text, String ctaText) {
        if (!StringUtils.hasText(text) || !StringUtils.hasText(code))
            throw new IllegalArgumentException("Invalid data");

        return new MessageApiResponse(code, text, ctaText);
    }

    public static MessageApiResponse build(ErrorCode errorCode) {
        return new MessageApiResponse(errorCode.name(), errorCode.getInstantDisplayMessage());
    }

    public static MessageApiResponse build(ErrorCode errorCode, String ctaText) {
        return new MessageApiResponse(errorCode.name(), errorCode.getInstantDisplayMessage(), ctaText);
    }
}