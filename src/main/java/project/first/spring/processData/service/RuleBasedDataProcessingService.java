package project.first.spring.processData.service;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import project.first.spring.Utilities.dao.ConfigDAO;
import project.first.spring.processData.config.RuleExecutorConfig;
import project.first.spring.processData.model.enums.RuleType;
import project.first.spring.processData.model.enums.Rules;
import project.first.spring.processData.model.pojos.ProcessedData;
import project.first.spring.processData.repository.RuleDAO;
import project.first.spring.processData.rules.IStringListProcessRules;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Slf4j
@Service
@ComponentScan(value = {"project.first.spring.processData.config"})
public class RuleBasedDataProcessingService {

    @Autowired
    private List<IStringListProcessRules> processingRules;
    private ExecutorService executorService;
    private final RuleDAO ruleDAO;
    private final RuleExecutorConfig ruleExecutorConfig;

    public RuleBasedDataProcessingService(RuleDAO ruleDAO, final RuleExecutorConfig ruleExecutorConfig) {
        this.ruleDAO = ruleDAO;
        this.ruleExecutorConfig = ruleExecutorConfig;
    }
    @PostConstruct
    void init() {
        executorService = Executors.newFixedThreadPool(ruleExecutorConfig.threadPoolSize());
    }

    public List<ProcessedData> getSolutions(List<String> inputData){
        List<ProcessedData> solutions = new ArrayList<>();

        //TODO : cache this query
        List<String> rulesToApply = ruleDAO.findNameByRuleTypeAndEnabledTrue(RuleType.STRING_LIST);

        List<CompletableFuture<List<ProcessedData>>> futures = rulesToApply.stream()
                .map(ruleToApply -> CompletableFuture.supplyAsync(() -> {
                    log.info("going to apply rule - {}", ruleToApply);
                    return processingRules.parallelStream()
                            .map(rule -> rule.process(inputData, ruleToApply))
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList());
                }, executorService)).toList();

        List<ProcessedData> processedDataList = futures.stream()
                .map(CompletableFuture::join)
                .flatMap(Collection::stream).toList();

        if (!processedDataList.isEmpty())
            solutions.addAll(processedDataList);

       return solutions;
    }

    @PreDestroy
    public void preDestroy() {
        log.info("shutting down the rule process executor service");
        executorService.shutdown();
    }
}
