package project.first.spring.utils;

import project.first.spring.flows.orders.messages.OrderCreated;
import project.first.spring.flows.orders.messages.OrderDispatched;
import project.first.spring.processData.model.enums.Rules;
import project.first.spring.processData.model.pojos.InputData;
import project.first.spring.processData.model.pojos.ProcessedData;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class TestEventData {

    public static OrderCreated buildOrderCreatedData(UUID id, String payload){
        return new OrderCreated(id, payload);
    }

    public static OrderDispatched getOrderDispatchedData(UUID orderId){
        return new OrderDispatched(orderId);
    }

    public static InputData getTestInputData(boolean validInput){
        return validInput ? InputData.builder().input(List.of("abcd", "dcba", "qwerty")).build() : InputData.builder().input(List.of("abcd")).build();
    }

    public static List<ProcessedData> getTestProcessedData(List<Rules> rules, String input){
       return rules.stream().map(rule -> ProcessedData.builder()
               .message(rule.getDescription())
               .solution(input)
               .build()).collect(Collectors.toList());
    }

    public static String INCORRECT_RULE_TEXT = "Incorrect value entered for key - rules";
    public static String ONLY_ONE_OR_NO_STRING_PROVIDED_TEXT = "Please enter at least 2 strings to make comparisons";
}
