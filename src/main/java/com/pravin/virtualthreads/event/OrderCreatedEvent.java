package com.pravin.virtualthreads.event;

public record OrderCreatedEvent(Long orderId,
                                String customerName,
                                String productName,
                                Integer quantity) {
}
