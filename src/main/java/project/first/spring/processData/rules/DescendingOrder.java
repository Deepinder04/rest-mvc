package project.first.spring.processData.rules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.first.spring.processData.config.AppliedRule;
import project.first.spring.processData.model.enums.Rules;
import project.first.spring.processData.model.pojos.ProcessedData;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
@AppliedRule(ruleName = Rules.DESCENDING)
public class DescendingOrder implements IStringListProcessRules {
    @Override
    public ProcessedData process(List<String> problem, String appliedRule) {
        log.info("Inside Descending order rule");

        List<String> input = new ArrayList<>(problem);
        input.sort(Comparator.reverseOrder());

        return ProcessedData.builder()
                .solution(input.toString())
                .message(Rules.valueOf(appliedRule).getDescription())
                .build();
    }
}
