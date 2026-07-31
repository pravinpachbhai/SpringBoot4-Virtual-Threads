package com.pravin.virtualthreads.repository;
import com.pravin.virtualthreads.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}