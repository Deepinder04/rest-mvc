package project.first.spring.flows.orders.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.kafka.core.KafkaTemplate;
import project.first.spring.flows.orders.messages.OrderCreated;
import project.first.spring.flows.orders.messages.OrderDispatched;
import project.first.spring.utils.TestEventData;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static project.first.spring.Utilities.Constants.ORDER_DISPATCHED_TOPIC;

class DispatchServiceImplTest {

    KafkaTemplate<String, Object> mockKafkaProducer;
    DispatchServiceImpl dispatchService;

    @BeforeEach
    void setUp() {
        mockKafkaProducer = mock(KafkaTemplate.class);
        dispatchService = new DispatchServiceImpl(mockKafkaProducer);
    }

    @Test
    void processSuccess() throws ExecutionException, InterruptedException {
        given(mockKafkaProducer.send(any(String.class), any(OrderDispatched.class))).willReturn(mock(CompletableFuture.class));

        OrderCreated orderCreated = TestEventData.buildOrderCreatedData(UUID.randomUUID(), UUID.randomUUID().toString());
        dispatchService.process(orderCreated);

        verify(mockKafkaProducer, times(1)).send(eq(ORDER_DISPATCHED_TOPIC), any(OrderDispatched.class));
    }

    @Test
    void processInterruptedException() throws ExecutionException, InterruptedException {
        doThrow(new RuntimeException("Interrupted while fetching response from kafka broker")).when(mockKafkaProducer).send(any(String.class), any(OrderDispatched.class));

        OrderCreated orderCreated = TestEventData.buildOrderCreatedData(UUID.randomUUID(), UUID.randomUUID().toString());

        Exception exception = assertThrows(RuntimeException.class, () -> dispatchService.process(orderCreated));
        verify(mockKafkaProducer, times(1)).send(eq(ORDER_DISPATCHED_TOPIC), any(OrderDispatched.class));
        assertThat(exception.getMessage()).isEqualTo("Interrupted while fetching response from kafka broker");
    }
}