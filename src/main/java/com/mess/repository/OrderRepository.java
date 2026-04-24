package com.mess.repository;

import com.mess.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserIdAndStatus(Long userId, String status);
    List<Order> findByMessIdAndStatus(Long messId, String status);
    List<Order> findByUserId(Long userId);
    List<Order> findByMessId(Long messId);
}
