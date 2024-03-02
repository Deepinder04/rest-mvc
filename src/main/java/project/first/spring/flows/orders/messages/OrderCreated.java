package project.first.spring.flows.orders.messages;

import java.util.UUID;

public record OrderCreated(UUID id, String item) {
}
