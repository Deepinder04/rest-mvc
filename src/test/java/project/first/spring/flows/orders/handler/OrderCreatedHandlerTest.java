package project.first.spring.flows.orders.handler;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import project.first.spring.flows.orders.services.DispatchService;

import static org.mockito.Mockito.*;
import static project.first.spring.TestConstants.KAFKA_CONSUMER_PAYLOAD;


class OrderCreatedHandlerTest {

    private OrderCreatedHandler handler;
    private DispatchService dispatchServiceMock;

    @BeforeEach
    void setUp() {
        dispatchServiceMock = mock(DispatchService.class);
        handler = new OrderCreatedHandler(dispatchServiceMock);
    }

    @Test
    void listen() {
        handler.listen(KAFKA_CONSUMER_PAYLOAD);
        verify(dispatchServiceMock, times(1)).process(KAFKA_CONSUMER_PAYLOAD);
    }
}