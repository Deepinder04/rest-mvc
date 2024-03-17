package project.first.spring.processData.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import project.first.spring.Utilities.response.SbApiResponse;
import project.first.spring.processData.model.enums.Rules;
import project.first.spring.processData.model.pojos.InputData;
import project.first.spring.processData.model.pojos.ProcessedData;
import project.first.spring.processData.service.RuleBasedDataProcessingService;

import java.util.List;

import static project.first.spring.Utilities.Constants.RULE_PROCESS_PATH;

@Slf4j
@RestController
@RequestMapping(RULE_PROCESS_PATH)
@RequiredArgsConstructor
public class RuleBasedDataProcessingController {

    private final RuleBasedDataProcessingService dataProcessingService;

    @PostMapping("/process-string-list")
    public SbApiResponse processListOfString(@Valid @RequestBody InputData inputData){
        log.info("List to process - {}", inputData);
        List<ProcessedData> solutions = dataProcessingService.getSolutions(inputData.getInput());
        return SbApiResponse.buildSuccess(solutions);
    }
}
