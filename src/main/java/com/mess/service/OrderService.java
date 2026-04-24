package com.mess.service;

import com.mess.dto.OrderRequest;
import com.mess.dto.OrderResponse;
import com.mess.entity.*;
import com.mess.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepo;
    private final OrderHistoryRepository historyRepo;
    private final UserRepository userRepo;
    private final MessRepository messRepo;
    private final MenuRepository menuRepo;

    @Transactional
    public OrderResponse placeOrder(OrderRequest req) {
        Menu combo = menuRepo.findById(req.getComboId())
                .orElseThrow(() -> new RuntimeException("Combo not found"));
        double total = combo.getPrice() * req.getQuantity();
        String otp = String.format("%04d", new Random().nextInt(10000));

        Order order = Order.builder()
                .userId(req.getUserId())
                .messId(req.getMessId())
                .comboId(req.getComboId())
                .quantity(req.getQuantity())
                .totalPrice(total)
                .status("ACTIVE")
                .otp(otp)
                .createdAt(LocalDateTime.now())
                .build();
        order = orderRepo.save(order);
        return toResponse(order);
    }

    @Transactional
    public OrderResponse verifyOtp(Long orderId, String otp) {
        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));
        if (!"ACTIVE".equals(order.getStatus())) {
            throw new RuntimeException("Order is not active");
        }
        if (!order.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }
        order.setStatus("COMPLETED");
        orderRepo.save(order);

        OrderHistory h = OrderHistory.builder()
                .orderId(order.getId())
                .status("COMPLETED")
                .completedAt(LocalDateTime.now())
                .build();
        historyRepo.save(h);
        return toResponse(order);
    }

    public List<OrderResponse> userActive(Long userId) {
        return orderRepo.findByUserIdAndStatus(userId, "ACTIVE")
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<OrderResponse> userHistory(Long userId) {
        return orderRepo.findByUserIdAndStatus(userId, "COMPLETED")
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<OrderResponse> messActive(Long messId) {
        return orderRepo.findByMessIdAndStatus(messId, "ACTIVE")
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    public List<OrderResponse> messHistory(Long messId) {
        return orderRepo.findByMessIdAndStatus(messId, "COMPLETED")
                .stream().map(this::toResponse).collect(Collectors.toList());
    }

    private OrderResponse toResponse(Order order) {
        User user = userRepo.findById(order.getUserId()).orElse(null);
        Mess mess = messRepo.findById(order.getMessId()).orElse(null);
        Menu combo = menuRepo.findById(order.getComboId()).orElse(null);
        return OrderResponse.builder()
                .orderId(order.getId())
                .userId(order.getUserId())
                .userName(user != null ? user.getName() : null)
                .userMobile(user != null ? user.getMobileNo() : null)
                .userAddress(user != null ? user.getAddress() : null)
                .messId(order.getMessId())
                .messName(mess != null ? mess.getName() : null)
                .comboId(order.getComboId())
                .comboName(combo != null ? combo.getComboName() : null)
                .quantity(order.getQuantity())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .otp(order.getOtp())
                .createdAt(order.getCreatedAt())
                .build();
    }
}
