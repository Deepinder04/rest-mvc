package project.first.spring.processData.rules;

import project.first.spring.processData.model.pojos.ProcessedData;

import java.util.List;

public interface IStringListProcessRules {

    ProcessedData process(List<String> problem, String appliedRule);
}
