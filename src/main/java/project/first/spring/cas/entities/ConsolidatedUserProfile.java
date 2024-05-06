package project.first.spring.cas.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import project.first.spring.cas.enums.InvestmentTypeEnum;
import project.first.spring.cas.model.portfolio.PortfolioTracker;
import project.first.spring.cas.utils.CollectionNames;

import java.util.EnumMap;

@Document(CollectionNames.CONSOLIDATED_USER_PROFILE)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ConsolidatedUserProfile extends BaseDocumentEntity{

    private String memberId;
    private String memberUid;
    private String email;
    private String xtraVerifiedEmail;

    @Builder.Default
    private EnumMap<InvestmentTypeEnum, PortfolioTracker> latestPortfolio = new EnumMap<>(InvestmentTypeEnum.class);

    @Builder.Default
    private EnumMap<InvestmentTypeEnum, PortfolioTracker> precedingPortfolio = new EnumMap<>(InvestmentTypeEnum.class);
}
