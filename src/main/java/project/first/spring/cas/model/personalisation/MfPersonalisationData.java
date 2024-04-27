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
public class MfPersonalisationData {
    @JsonProperty("mFInvestorStatus")
    private String investorStatus;
    @JsonProperty("currentPOSMF")
    private double currentPos;
    @JsonProperty("mFActiveSIP")
    private boolean activeSip;
    @JsonProperty("mFImported")
    private boolean mfImported;
    @JsonProperty("mFImportedDataRefreshed")
    private long mfImportedDataRefreshedDate;
    @JsonProperty("firstBuyTxnDateMFLumpsum")
    private long mfLumpsumFirstBuyTxnDate;
    @JsonProperty("firstBuyTxnDateMFSIP")
    private long mfSipFirstBuyTxnDate;
    @JsonProperty("firstSellTxnDateMF")
    private long firstSellTxnDate;
    @JsonProperty("lastBuyTxnDateMFLumpsum")
    private long mfLumpsumLastBuyTxnDate;
    @JsonProperty("lastBuyTxnDateMFSIP")
    private long mfSipLastBuyTxnDate;
    @JsonProperty("lastSellTxnDateMF")
    private long lastSellTxnDate;
    @JsonProperty("createdAt")
    private long createdAt;
    @JsonProperty("updatedAt")
    private long updatedAt;
}
