package project.first.spring.cas.model.portfolio;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.first.spring.cas.enums.InvestmentTypeEnum;
import project.first.spring.cas.model.personalisation.*;

import java.math.BigDecimal;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUserPortfolio {

    private String memberId;
    private String memberUid;
    private BigDecimal currentInvested;
    private BigDecimal currentValue;
    private BigDecimal currentReturn;
    private String date; // date in dd-MM-yyyy format
    private String xtraVerifiedEmail;

    @JsonProperty("product")
    private InvestmentTypeEnum product;

    private BigDecimal goldWeight;

    private AAPersonalisationData aaPersonalisationData;
    private GoldPersonalisationData goldPersonalisationData;
    private MfPersonalisationData mfPersonalisationData;
    private EpfoPersonalisationData epfoPersonalisationData;
    private P2PPersonalisationData p2PPersonalisationData;
    private FdPersonalisationData fdPersonalisationData;
}
