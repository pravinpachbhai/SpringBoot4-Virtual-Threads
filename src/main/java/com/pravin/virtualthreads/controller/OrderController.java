package com.pravin.virtualthreads.controller;

import com.pravin.virtualthreads.dto.OrderRequest;
import com.pravin.virtualthreads.service.OrderService;
import org.springframework.web.bind.annotation.*;
import com.pravin.virtualthreads.entity.Order;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service){
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return service.createOrder(request);
    }
}