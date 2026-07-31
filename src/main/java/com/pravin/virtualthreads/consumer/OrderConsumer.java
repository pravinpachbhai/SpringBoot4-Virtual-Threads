package com.pravin.virtualthreads.consumer;
import com.pravin.virtualthreads.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;

@Slf4j
@Component
public class OrderConsumer {

    private final ExecutorService virtualThreadExecutor;

    public OrderConsumer(ExecutorService virtualThreadExecutor){
        this.virtualThreadExecutor = virtualThreadExecutor;
    }

    // OPTION 1
    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void consume(OrderCreatedEvent event) throws InterruptedException {
        log.info("Received order {}", event);
        Thread.sleep(3000);
        log.info("Order {} processed consume", event.orderId());
    }

    // OPTION 2
    @KafkaListener(topics = "order-created", groupId = "order-group")
    public void consume1(OrderCreatedEvent event) {
        virtualThreadExecutor.submit(() -> {
            Thread.sleep(3000);
            log.info("Order {} processed - consume1 ", event.orderId());
            return null;
        });
    }
}