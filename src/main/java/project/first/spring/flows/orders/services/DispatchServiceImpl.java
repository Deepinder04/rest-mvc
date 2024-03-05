package project.first.spring.flows.orders.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import project.first.spring.Utilities.Constants;
import project.first.spring.flows.orders.messages.OrderCreated;
import project.first.spring.flows.orders.messages.OrderDispatched;

import java.util.concurrent.ExecutionException;

import static project.first.spring.Utilities.Constants.ORDER_DISPATCHED_TOPIC;

@Slf4j
@Service
@RequiredArgsConstructor
public class DispatchServiceImpl implements DispatchService {

    private final KafkaTemplate<String, Object> kafkaProducer;

    @Override
    public void process(OrderCreated payload) throws ExecutionException, InterruptedException {
        log.info("Going to process order with item - {}", payload);
        OrderDispatched orderDispatched = new OrderDispatched(payload.id());
        kafkaProducer.send(ORDER_DISPATCHED_TOPIC, orderDispatched).get();
    }
}
