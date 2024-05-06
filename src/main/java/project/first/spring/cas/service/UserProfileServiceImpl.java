package project.first.spring.cas.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.first.spring.cas.dao.*;
import project.first.spring.cas.entities.*;
import project.first.spring.cas.enums.InvestmentTypeEnum;
import project.first.spring.cas.model.portfolio.CustomerUserPortfolio;
import project.first.spring.cas.model.portfolio.PortfolioTracker;
import project.first.spring.cas.model.portfolio.UserDateWiseMfNav;
import project.first.spring.cas.utils.CommonUtils;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final ConsolidatedUserProfileDao userProfileDao;
    private final InvestmentLedgerDao investmentLedgerDao;

    @Override
    public void updateConsolidatedUserProfile(CustomerUserPortfolio userPortfolio) {
        PortfolioTracker portfolioTracker = getPortfolioDetailsToPersist(userPortfolio);
        InvestmentTypeEnum product = userPortfolio.getProduct();

        String memberUid = userPortfolio.getMemberUid();
        log.info("update portfolio request for user with memberUid - {}, for the product - {}", memberUid, product);

        Optional<ConsolidatedUserProfile> userProfile = userProfileDao.findByMemberUid(memberUid);
        if (userProfile.isPresent()){
            log.info("user profile exists for user with memberUid - {}",memberUid);
            updateUserProfile(product, portfolioTracker, userProfile.get());
        }
        else {
            log.info("User profile doesn't exist for user with memberUid - {}, so creating it", memberUid);
            ConsolidatedUserProfile newUserProfile = ConsolidatedUserProfile.builder()
                    .memberUid(userPortfolio.getMemberUid())
                    .memberId(userPortfolio.getMemberId())
                    .xtraVerifiedEmail(Objects.nonNull(userPortfolio.getXtraVerifiedEmail()) ? userPortfolio.getXtraVerifiedEmail() : null)
                    .build();

            updateUserProfile(product, portfolioTracker, newUserProfile);
        }
    }

    private PortfolioTracker getPortfolioDetailsToPersist(CustomerUserPortfolio userPortfolio){
        DecimalFormat df = CommonUtils.getDecimalFormatOfPattern("#.##");

        Double currentReturn = userPortfolio.getCurrentReturn().doubleValue();
        String currentReturnDf = df.format(currentReturn);
        String currentValue = df.format(userPortfolio.getCurrentValue().doubleValue());
        String currentInvested = df.format(userPortfolio.getCurrentInvested().doubleValue());

        UserDateWiseMfNav nav = UserDateWiseMfNav.builder()
                .roi(Double.parseDouble(currentReturnDf))
                .currentInvestment(Double.parseDouble(currentInvested))
                .nav(Double.parseDouble(currentValue))
                .build();

        LocalDate date = CommonUtils.getFormattedLocalDate(userPortfolio.getDate(), "yyyy-MM-dd");

        return PortfolioTracker.builder()
                .productPortfolio(nav)
                .date(date).build();
    }

    private void updateUserProfile(InvestmentTypeEnum product, PortfolioTracker portfolioTracker, ConsolidatedUserProfile userProfile){
        PortfolioTracker existingDetails = Objects.nonNull(userProfile.getLatestPortfolio().get(product)) ?
                userProfile.getLatestPortfolio().get(product) : null;
        LocalDate dateInPushFromModule = CommonUtils.getFormattedLocalDate(portfolioTracker.getDate().toString(), "yyyy-MM-dd");

        if(Objects.isNull(existingDetails)){
            log.info("latest portfolio entry not present for user - {}, for product - {}", userProfile.getMemberUid(), product);
            userProfile.getLatestPortfolio().put(product, portfolioTracker);
        }

        LocalDate latestPortfolioDate = userProfile.getLatestPortfolio().get(product).getDate();
        if (dateInPushFromModule.isAfter(latestPortfolioDate)){
            userProfile.getPrecedingPortfolio().put(product, userProfile.getLatestPortfolio().get(product));
            userProfile.getLatestPortfolio().put(product, portfolioTracker);
        } else if (dateInPushFromModule.isEqual(latestPortfolioDate)) {
            userProfile.getLatestPortfolio().put(product, portfolioTracker);
        } else {
            userProfile.getPrecedingPortfolio().put(product, portfolioTracker);
        }
        log.info("Going to save userProfile for user - {}", userProfile.getMemberUid());
        userProfileDao.save(userProfile);

        updateDailyModuleWiseData(product, portfolioTracker, userProfile);
    }

    private void updateDailyModuleWiseData(InvestmentTypeEnum product, PortfolioTracker portfolioTracker, ConsolidatedUserProfile userProfile){
        int year = portfolioTracker.getDate().getYear();
        String memberUid = userProfile.getMemberUid();

        InvestmentLedger investmentLedger = investmentLedgerDao.findByMemberUidAndYearAndModule(userProfile.getMemberUid(), year, product.getValue());
        if(Objects.nonNull(investmentLedger))
            investmentLedger.getInvestment().put(portfolioTracker.getDate(), portfolioTracker.getProductPortfolio());
        else {
            log.info("Creating ledger document for user - {}, for year - {} and product - {}", userProfile.getMemberUid(), year, product);
            investmentLedger = InvestmentLedger.builder()
                    .year(year)
                    .memberUid(memberUid)
                    .module(product)
                    .build();
            investmentLedger.getInvestment().put(portfolioTracker.getDate(), portfolioTracker.getProductPortfolio());
        }
        log.info("Saving daily ledger data for user - {} for product - {}", userProfile.getMemberUid(), product);
        investmentLedgerDao.save(investmentLedger);
        saveConsolidatedDataOfAllModules(portfolioTracker, userProfile);
    }

    private void saveConsolidatedDataOfAllModules(PortfolioTracker portfolioTracker, ConsolidatedUserProfile userProfile){
        int year = portfolioTracker.getDate().getYear();
        DecimalFormat df = CommonUtils.getDecimalFormatOfPattern("#.##");

        Double consolidateNav = 0D;
        Double consolidateCurrentInvestment = 0D;

        log.info("going to calculate consolidated data for user - {}, for the date - {}", userProfile.getMemberUid(), portfolioTracker.getDate());
        for (InvestmentTypeEnum investmentType : InvestmentTypeEnum.values()){
            if(Objects.isNull(userProfile.getLatestPortfolio().get(investmentType)) || InvestmentTypeEnum.consolidated.equals(investmentType))
                continue;

            consolidateNav += userProfile.getLatestPortfolio().get(investmentType).getProductPortfolio().getNav();
            consolidateCurrentInvestment += userProfile.getLatestPortfolio().get(investmentType).getProductPortfolio().getCurrentInvestment();
        }

        Double absoluteChange = consolidateNav - consolidateCurrentInvestment;
        Double roi = consolidateCurrentInvestment.compareTo(0.0) == 0 ? 0.0 : (absoluteChange / consolidateCurrentInvestment) * 100;
        Double consolidatedRoi = roi / 2D;
        String consolidatedRoiDf = df.format(consolidatedRoi);

        UserDateWiseMfNav updatedData = UserDateWiseMfNav.builder()
                .nav(consolidateNav)
                .roi(Double.parseDouble(consolidatedRoiDf))
                .currentInvestment(consolidateCurrentInvestment)
                .build();

        InvestmentLedger consolidatedInvestments = investmentLedgerDao.findByMemberUidAndYearAndModule(userProfile.getMemberUid(),year, InvestmentTypeEnum.consolidated.name());
        if (Objects.nonNull(consolidatedInvestments)){
            consolidatedInvestments.getInvestment().put(portfolioTracker.getDate(), updatedData);
        } else {
            consolidatedInvestments = InvestmentLedger.builder()
                    .year(year)
                    .module(InvestmentTypeEnum.consolidated)
                    .memberUid(userProfile.getMemberUid())
                    .build();
            consolidatedInvestments.getInvestment().put(portfolioTracker.getDate(), updatedData);
        }
        log.info("going to save consolidated data for user - {}", userProfile.getMemberUid());
        investmentLedgerDao.save(consolidatedInvestments);
    }
}
