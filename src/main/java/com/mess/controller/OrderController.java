package com.mess.controller;

import com.mess.dto.*;
import com.mess.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> place(@RequestBody OrderRequest req) {
        return ResponseEntity.ok(orderService.placeOrder(req));
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<OrderResponse> verifyOtp(@RequestBody OtpRequest req) {
        return ResponseEntity.ok(orderService.verifyOtp(req.getOrderId(), req.getOtp()));
    }

    @GetMapping("/user/{userId}/active")
    public ResponseEntity<List<OrderResponse>> userActive(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.userActive(userId));
    }

    @GetMapping("/user/{userId}/history")
    public ResponseEntity<List<OrderResponse>> userHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.userHistory(userId));
    }

    @GetMapping("/mess/{messId}/active")
    public ResponseEntity<List<OrderResponse>> messActive(@PathVariable Long messId) {
        return ResponseEntity.ok(orderService.messActive(messId));
    }

    @GetMapping("/mess/{messId}/history")
    public ResponseEntity<List<OrderResponse>> messHistory(@PathVariable Long messId) {
        return ResponseEntity.ok(orderService.messHistory(messId));
    }
}
