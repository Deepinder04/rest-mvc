package project.first.spring.cas.cron;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.first.spring.cas.dao.ConsolidatePortfolioDao;
import project.first.spring.cas.dao.InvestmentLedgerDao;
import project.first.spring.cas.entities.ConsolidatePortfolio;
import project.first.spring.cas.entities.InvestmentLedger;
import project.first.spring.cas.enums.InvestmentTypeEnum;
import project.first.spring.cas.model.portfolio.UserDateWiseMfNav;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PrefillBackDatedInfoService {
    private final ConsolidatePortfolioDao consolidatePortfolioDao;
    private final InvestmentLedgerDao investmentLedgerDao;
    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void prefillModuleLedgersAndConsolidatedCollections(Long startingMemberUid, Long endMemberUid) {
        Pageable pageRequest = PageRequest.of(0, 100);
        Page<ConsolidatePortfolio> consolidatePortfolioPage = null;

        consolidatePortfolioPage = getPage(startingMemberUid, endMemberUid, pageRequest);
        while (!consolidatePortfolioPage.isEmpty()){
            long batchStartTime = System.currentTimeMillis();
            log.info("Batch start time for page number {} {}:", pageRequest.getPageNumber(), batchStartTime);
            List<ConsolidatePortfolio> consolidatePortfolioList = consolidatePortfolioPage.getContent();
            processBatch(consolidatePortfolioList);
            pageRequest = pageRequest.next();
            long batchEndTime = System.currentTimeMillis();
            log.info("Batch end time for page number {} {}:", pageRequest.getPageNumber(), batchEndTime);
            log.info("Batch for page executed in {} {}:", pageRequest.getPageNumber(), batchEndTime - batchStartTime);
            consolidatePortfolioPage = getPage(startingMemberUid, endMemberUid, pageRequest);
        }
    }

    private Page<ConsolidatePortfolio> getPage(Long startingMemberUid, Long endMemberUid, Pageable pageRequest) {
        Page<ConsolidatePortfolio> consolidatePortfolioPage;
        if(Objects.isNull(endMemberUid)){
            consolidatePortfolioPage = consolidatePortfolioDao.findByMemberUidGreaterThanEqualOrderByMemberUid(startingMemberUid, pageRequest);
        } else {
            consolidatePortfolioPage =  consolidatePortfolioDao.findByMemberUidGreaterThanEqualAndMemberUidLessThanEqualOrderByMemberUid(startingMemberUid, endMemberUid, pageRequest);
        }
        return consolidatePortfolioPage;
    }

    void processBatch(List<ConsolidatePortfolio> consolidatePortfolioList){
        consolidatePortfolioList.parallelStream().forEach(portfolio -> {
            log.info("Going to process portfolio of user - {}", portfolio.getMemberUid());
            CompletableFuture[] futures = new CompletableFuture[8];

            if(Objects.nonNull(portfolio.getXtraInvestment()))
                futures[0] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getXtraInvestment(), InvestmentTypeEnum.XTRA, portfolio.getMemberUid()), executorService);

            if(Objects.nonNull(portfolio.getGoldInvestment()))
                futures[1] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getGoldInvestment(), InvestmentTypeEnum.GOLD, portfolio.getMemberUid()), executorService);

            if(Objects.nonNull(portfolio.getMfInvestment()))
                futures[2] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getMfInvestment(), InvestmentTypeEnum.MUTUALFUND, portfolio.getMemberUid()), executorService);

            if(Objects.nonNull(portfolio.getEpfoInvestment()))
                futures[3] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getEpfoInvestment(), InvestmentTypeEnum.EPFO, portfolio.getMemberUid()), executorService);

            if(Objects.nonNull(portfolio.getBankAccountBalance()))
                futures[4] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getBankAccountBalance(), InvestmentTypeEnum.BANK_ACCOUNT, portfolio.getMemberUid()), executorService);

            if(Objects.nonNull(portfolio.getFdInvestment()))
                futures[5] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getFdInvestment(), InvestmentTypeEnum.FD, portfolio.getMemberUid()), executorService);

            if(Objects.nonNull(portfolio.getIddInvestment()))
                futures[6] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getIddInvestment(), InvestmentTypeEnum.IDD, portfolio.getMemberUid()), executorService);

            if(Objects.nonNull(portfolio.getConsolidateInvestment()))
                futures[7] = CompletableFuture.runAsync(() -> processTreeMap(portfolio.getConsolidateInvestment(), InvestmentTypeEnum.CONSOLIDATED, portfolio.getMemberUid()), executorService);

            CompletableFuture.allOf(futures).join();
            log.info("Portfolio processed for user - {}", portfolio.getMemberUid());
        });
    }

    private void processTreeMap(TreeMap<LocalDate, UserDateWiseMfNav> investment, InvestmentTypeEnum module, String memberUid){
        log.info("Going to process ledger data for user - {}, of product - {}", memberUid, module);
        Map<Integer, InvestmentLedger> yearsForWhichLedgersExist = new HashMap<>();

        for (LocalDate date : investment.keySet()) {
            InvestmentLedger ledger;
            if (Objects.isNull(yearsForWhichLedgersExist.get(date.getYear()))) {
                ledger = investmentLedgerDao.findByMemberUidAndYearAndModule(memberUid, date.getYear(), module.name());
                if (Objects.isNull(ledger)) {
                    ledger = InvestmentLedger.builder()
                            .module(module)
                            .memberUid(memberUid)
                            .year(date.getYear())
                            .build();
                }
                yearsForWhichLedgersExist.put(date.getYear(), ledger);
            }
            yearsForWhichLedgersExist.get(date.getYear()).getInvestment().put(date, investment.get(date));
        }
        log.info("Going to save ledger data for user - {}, of product - {}", memberUid, module);
        investmentLedgerDao.saveAll(yearsForWhichLedgersExist.values());
        log.info("Saved ledger data for user - {}, of product - {}", memberUid, module);
    }
}
