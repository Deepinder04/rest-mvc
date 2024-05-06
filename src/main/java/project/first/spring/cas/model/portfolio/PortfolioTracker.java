package project.first.spring.cas.model.portfolio;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class PortfolioTracker {
    private LocalDate date;
    private UserDateWiseMfNav productPortfolio;
}
