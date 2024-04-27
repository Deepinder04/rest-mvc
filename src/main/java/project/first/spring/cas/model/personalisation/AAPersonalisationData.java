package project.first.spring.cas.model.personalisation;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AAPersonalisationData {

    @JsonProperty("salaryTag")
    private boolean salaryTagged;
    @JsonProperty("unifiedABB3M")
    private Double unifiedABB3M;
    @JsonProperty("banks")
    private List<BankData> bankData;
    @JsonProperty("createdAt")
    private long createdAt;
    @JsonProperty("updatedAt")
    private long updatedAt;

    @Data
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class BankData{
        @JsonProperty("bankName")
        private String bankName;
        @JsonProperty("bankConsentStatus")
        private String bankConsentStatus;
        @JsonProperty("dataRefreshedDateLensBank")
        private Long dataRefreshedDate;
        @JsonProperty("firstDataFetchDateLensBank")
        private Long firstDataFetchDate;
        @JsonProperty("bankBalance")
        private String currentBalance;
    }
}
