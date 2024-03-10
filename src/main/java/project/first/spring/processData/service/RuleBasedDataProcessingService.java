package project.first.spring.processData.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.first.spring.processData.model.enums.Rules;
import project.first.spring.processData.model.pojos.ProcessedData;
import project.first.spring.processData.rules.IStringListProcessRules;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RuleBasedDataProcessingService {

    @Autowired
    private List<IStringListProcessRules> processingRules;

    public List<ProcessedData> getSolutions(List<String> inputData, List<Rules> rulesToApply){
        List<ProcessedData> solutions = new ArrayList<>();

       for (Rules ruleToApply : rulesToApply){
           log.info("going to apply rule - {}", ruleToApply.name());
           List<ProcessedData> processedDataList = processingRules.stream().map(rule -> rule.process(inputData, ruleToApply.name())).toList();

           processedDataList = processedDataList.stream().filter(Objects::nonNull).collect(Collectors.toList());
           if (!processedDataList.isEmpty())
               solutions.addAll(processedDataList);
       }

       return solutions;
    }
}
