package project.first.spring.cas.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import project.first.spring.cas.model.portfolio.UserDateWiseMfNav;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.TreeMap;

@Document(collection = "ConsolidatePortfolio")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class ConsolidatePortfolio {
        @Id
        BigInteger id;

        @Indexed
        String memberId;

        @Indexed
        String memberUid;

        @Indexed
        String email;

        @Indexed
        String xtraVerifiedEmail;

        TreeMap<LocalDate, UserDateWiseMfNav> xtraInvestment = new TreeMap<>();

        TreeMap<LocalDate, UserDateWiseMfNav> goldInvestment = new TreeMap<>();

        TreeMap<LocalDate, UserDateWiseMfNav> mfInvestment = new TreeMap<>();

        TreeMap<LocalDate, UserDateWiseMfNav> epfoInvestment = new TreeMap<>();

        TreeMap<LocalDate, UserDateWiseMfNav> bankAccountBalance = new TreeMap<>();

        TreeMap<LocalDate, UserDateWiseMfNav> fdInvestment = new TreeMap<>();

        TreeMap<LocalDate, UserDateWiseMfNav> iddInvestment = new TreeMap<>();

        TreeMap<LocalDate, UserDateWiseMfNav> consolidateInvestment = new TreeMap<>();
}
