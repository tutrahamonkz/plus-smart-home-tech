package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.repository.PaymentRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public ResponseEntity<PaymentDto> createPayment(OrderDto order) {
        Payment payment = PaymentMapper.mapToPayment(order);
        return ResponseEntity.ok(PaymentMapper.maptoPaymentDto(paymentRepository.save(payment)));
    }

    @Override
    public ResponseEntity<Double> calculateTotalCost(OrderDto order) {
        return null;
    }

    @Override
    public void refund(UUID paymentId) {

    }

    @Override
    public ResponseEntity<Double> calculateProductCost(OrderDto order) {
        return null;
    }

    @Override
    public void failed(UUID paymentId) {

    }
}
