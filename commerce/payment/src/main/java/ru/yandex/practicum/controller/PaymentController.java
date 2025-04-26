package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.service.PaymentService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@RequestBody OrderDto order) {
        return paymentService.createPayment(order);
    }

    @PostMapping("/totalCost")
    public ResponseEntity<Double> calculateTotalCost(@RequestBody OrderDto order) {
        return paymentService.calculateTotalCost(order);
    }

    @PostMapping("/refund")
    public void refund(@RequestBody UUID paymentId) {
        paymentService.refund(paymentId);
    }

    @PostMapping("/productCost")
    public ResponseEntity<Double> calculateProductCost(@RequestBody OrderDto order) {
        return paymentService.calculateProductCost(order);
    }

    @PostMapping("/failed")
    public void failed(@RequestBody UUID paymentId) {
        paymentService.failed(paymentId);
    }
}
