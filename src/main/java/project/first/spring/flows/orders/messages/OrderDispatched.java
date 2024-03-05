package project.first.spring.flows.orders.messages;

import java.util.UUID;

public record OrderDispatched(UUID orderId) {
}
