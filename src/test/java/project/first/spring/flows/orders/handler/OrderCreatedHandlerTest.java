package project.first.spring.flows.orders.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.first.spring.flows.orders.messages.OrderCreated;
import project.first.spring.flows.orders.services.DispatchService;
import project.first.spring.utils.TestEventData;

import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.mockito.Mockito.*;


class OrderCreatedHandlerTest {

    private OrderCreatedHandler handler;
    private DispatchService dispatchServiceMock;

    @BeforeEach
    void setUp() {
        dispatchServiceMock = mock(DispatchService.class);
        handler = new OrderCreatedHandler(dispatchServiceMock);
    }

    @Test
    void listenSuccess() throws ExecutionException, InterruptedException {
        OrderCreated orderCreatedMessage = TestEventData.buildOrderCreatedData(UUID.randomUUID(), UUID.randomUUID().toString());
        handler.listen(orderCreatedMessage);
        verify(dispatchServiceMock, times(1)).process(orderCreatedMessage);
    }

    @Test
    void listenExecutionException() throws ExecutionException, InterruptedException {
        OrderCreated orderCreatedMessage = TestEventData.buildOrderCreatedData(UUID.randomUUID(), UUID.randomUUID().toString());
        handler.listen(orderCreatedMessage);
        doThrow(ExecutionException.class).when(dispatchServiceMock).process(any(OrderCreated.class));

        verify(dispatchServiceMock, times(1)).process(orderCreatedMessage);
    }

    @Test
    void listenInterruptedException() throws ExecutionException, InterruptedException {
        OrderCreated orderCreatedMessage = TestEventData.buildOrderCreatedData(UUID.randomUUID(), UUID.randomUUID().toString());
        handler.listen(orderCreatedMessage);
        doThrow(InterruptedException.class).when(dispatchServiceMock).process(any(OrderCreated.class));

        verify(dispatchServiceMock, times(1)).process(orderCreatedMessage);
    }
}