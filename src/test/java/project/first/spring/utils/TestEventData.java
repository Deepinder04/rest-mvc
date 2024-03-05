package project.first.spring.utils;

import project.first.spring.flows.orders.messages.OrderCreated;
import project.first.spring.flows.orders.messages.OrderDispatched;

import java.util.UUID;

public class TestEventData {

    public static OrderCreated buildOrderCreatedData(UUID id, String payload){
        return new OrderCreated(id, payload);
    }

    public static OrderDispatched getOrderDispatchedData(UUID orderId){
        return new OrderDispatched(orderId);
    }
}
