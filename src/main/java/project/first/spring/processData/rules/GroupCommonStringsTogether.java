package project.first.spring.processData.rules;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.first.spring.processData.config.AppliedRule;
import project.first.spring.processData.model.enums.Rules;
import project.first.spring.processData.model.pojos.ProcessedData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AppliedRule(ruleName = Rules.GROUP_COMMON_STRINGS)
public class GroupCommonStringsTogether implements IStringListProcessRules {

    @Override
    public ProcessedData process(List<String> problem, String appliedRule) {
        log.info("Inside GroupCommonStringsTogether rule");

        Map<Map<Character, Integer>, List<String>> groupedStrings = new HashMap<>();
        for (String str : problem) {
            Map<Character, Integer> charCount = new HashMap<>();
            for (char c : str.toCharArray()) {
                charCount.put(c, charCount.getOrDefault(c, 0) + 1);
            }
            groupedStrings.computeIfAbsent(charCount, k -> new ArrayList<>()).add(str);
        }

        List<List<String>> resultList = new ArrayList<>(groupedStrings.values());

        return ProcessedData.builder()
                .solution(resultList.toString())
                .message(Rules.valueOf(appliedRule).getDescription())
                .build();
    }
}
