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
public class GoldPersonalisationData {

    @JsonProperty("goldBalance")
    private double goldBalance;
    @JsonProperty("goldSIPActiveDaily")
    private boolean dailyActiveGoldSip;
    @JsonProperty("goldSIPActiveMonthly")
    private boolean monthlyActiveGoldSip;
    @JsonProperty("firstBuyTxnDateGoldLumpsum")
    private long goldLumpsumFirstBuyTxnDate;
    @JsonProperty("firstBuyTxnDateGoldSIP")
    private long goldSipFirstBuyTxnDate;
    @JsonProperty("firstSellTxnDateGold")
    private long firstSellTxnDate;
    @JsonProperty("lastBuyTxnDateGoldLumpsum")
    private long goldLumpsumLastBuyTxnDate;
    @JsonProperty("lastBuyTxnDateGoldSIP")
    private long goldSipLastBuyTxnDate;
    @JsonProperty("lastSellTxnDateGold")
    private long lastSellTxnDate;
    @JsonProperty("createdAt")
    private long createdAt;
    @JsonProperty("updatedAt")
    private long updatedAt;
}
