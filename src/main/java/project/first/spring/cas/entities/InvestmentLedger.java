package project.first.spring.cas.entities;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import project.first.spring.cas.enums.InvestmentTypeEnum;
import project.first.spring.cas.model.portfolio.UserDateWiseMfNav;
import project.first.spring.cas.utils.CollectionNames;

import java.time.LocalDate;
import java.util.TreeMap;

@Document(CollectionNames.INVESTMENTS_LEDGER)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class InvestmentLedger extends BaseDocumentEntity{

    private String memberUid;
    private int year;
    private InvestmentTypeEnum module;

    @Builder.Default
    TreeMap<LocalDate, UserDateWiseMfNav> investment = new TreeMap<>();
}
