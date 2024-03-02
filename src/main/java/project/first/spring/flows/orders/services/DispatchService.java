package project.first.spring.flows.orders.services;

import project.first.spring.flows.orders.messages.OrderCreated;

public interface DispatchService {

    void process(OrderCreated payload);
}
