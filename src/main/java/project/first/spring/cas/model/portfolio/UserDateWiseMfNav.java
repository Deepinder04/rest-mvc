package project.first.spring.cas.model.portfolio;

import lombok.Builder;
import lombok.Data;
import project.first.spring.cas.model.personalisation.*;

@Builder
@Data
public class UserDateWiseMfNav {

    Double nav;
    Double roi;
    Double currentInvestment;
    Double goldWeight;
    AAPersonalisationData bankAccount;
    MfPersonalisationData mutualfund;
    GoldPersonalisationData gold;
    EpfoPersonalisationData epfo;
    P2PPersonalisationData p2p;
    FdPersonalisationData fd;
}
