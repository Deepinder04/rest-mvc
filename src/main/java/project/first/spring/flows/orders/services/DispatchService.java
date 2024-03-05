package project.first.spring.flows.orders.services;

import project.first.spring.flows.orders.messages.OrderCreated;

import java.util.concurrent.ExecutionException;

public interface DispatchService {

    void process(OrderCreated payload) throws ExecutionException, InterruptedException;
}
