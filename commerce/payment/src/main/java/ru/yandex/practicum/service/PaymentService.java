package ru.yandex.practicum.service;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.util.UUID;

public interface PaymentService {
    ResponseEntity<PaymentDto> createPayment(OrderDto order);

    ResponseEntity<Double> calculateTotalCost(OrderDto order);

    void refund(UUID paymentId);

    ResponseEntity<Double> calculateProductCost(OrderDto order);

    void failed(UUID paymentId);
}
