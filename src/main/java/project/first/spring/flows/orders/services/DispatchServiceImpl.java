package project.first.spring.flows.orders.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.first.spring.flows.orders.messages.OrderCreated;

@Slf4j
@Service
public class DispatchServiceImpl implements DispatchService {

    @Override
    public void process(OrderCreated payload) {
        log.info("Going to process order with item - {}", payload.item());
    }
}
