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
public class P2PPersonalisationData {
    @JsonProperty("investorStatusXtra")
    private String investorStatus;
    @JsonProperty("currentPosXtra")
    private double currentPos;
    @JsonProperty("firstBuyTxnDateXtra")
    private long firstBuyTxnDate;
    @JsonProperty("firstSellTxnDateXtra")
    private long firstSellTxnDate;
    @JsonProperty("lastBuyTxnDateXtra")
    private long lastBuyTxnDate;
    @JsonProperty("lastSellTxnDateXtra")
    private long lastSellTxnDate;
    @JsonProperty("buyTxnCountXtra")
    private long buyTxnCount;
    @JsonProperty("sellTxnCountXtra")
    private long sellTxnCount;
    @JsonProperty("createdAt")
    private long createdAt;
    @JsonProperty("updatedAt")
    private long updatedAt;
}
