package project.first.spring.utils;

import project.first.spring.flows.orders.messages.OrderCreated;

import java.util.UUID;

public class TestEventData {

    public static OrderCreated buildOrderCreatedData(UUID id, String payload){
        return new OrderCreated(id, payload);
    }
}
