package com.pravin.virtualthreads.service;
import com.pravin.virtualthreads.entity.Order;
import com.pravin.virtualthreads.dto.OrderRequest;
import com.pravin.virtualthreads.event.OrderCreatedEvent;
import com.pravin.virtualthreads.producer.OrderProducer;
import com.pravin.virtualthreads.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final OrderProducer producer;

    public OrderService(OrderRepository repository, OrderProducer producer){
        this.producer =producer;
        this.repository = repository;
    }


    public Order createOrder(OrderRequest request) {

        Order order = Order.builder()
                .customerName(request.customerName())
                .productName(request.productName())
                .quantity(request.quantity())
                .status("CREATED")
                .build();

        Order saved = repository.save(order);
        // Use Outbox Pattern - save event in DB - (Then you can use @Transactional)
        // This is not safe order will create but kafka may fail.
        producer.publish(
                new OrderCreatedEvent(
                        saved.getId(),
                       saved.getCustomerName(),
                        saved.getProductName(),
                        saved.getQuantity())

        );

        return saved;
    }
}