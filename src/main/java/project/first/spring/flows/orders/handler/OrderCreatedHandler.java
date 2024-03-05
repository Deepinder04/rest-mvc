package project.first.spring.flows.orders.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import project.first.spring.flows.orders.messages.OrderCreated;
import project.first.spring.flows.orders.services.DispatchService;

import java.util.concurrent.ExecutionException;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderCreatedHandler {

    private final DispatchService dispatchService;

    @KafkaListener(
            id = "orderConsumerClient",
            topics = "order.created",
            groupId = "dispatch.order.created.consumer"
    )
    public void listen(OrderCreated payload){
        log.info("Received message with item :  {}", payload);
        try {
            dispatchService.process(payload);
        } catch (ExecutionException e) {
            log.error("Exception in sending event - {} with message - {}",payload , e.getMessage());
        } catch (InterruptedException e) {
            log.error("Send interrupted - {} with message - {}",payload , e.getMessage());
        }
    }
}
