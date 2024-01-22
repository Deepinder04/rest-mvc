package project.first.spring.Utilities.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import project.first.spring.Utilities.response.SbApiResponse;

public interface IPageRenderer<T> {
    SbApiResponse renderPageFromData(T data) throws JsonProcessingException;
}
