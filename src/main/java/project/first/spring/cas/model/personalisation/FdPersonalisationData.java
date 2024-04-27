package project.first.spring.cas.model.personalisation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FdPersonalisationData {
    @JsonProperty("fDInvestor")
    private boolean fdInvestor;
    @JsonProperty("createdAt")
    private long createdAt;
    @JsonProperty("updatedAt")
    private long updatedAt;
}
