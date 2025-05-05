package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.feign.OrderClient;
import ru.yandex.practicum.feign.StoreClient;
import ru.yandex.practicum.mapper.PaymentMapper;
import ru.yandex.practicum.model.Payment;
import ru.yandex.practicum.model.PaymentState;
import ru.yandex.practicum.repository.PaymentRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final StoreClient storeClient;
    private final OrderClient orderClient;

    @Override
    public ResponseEntity<PaymentDto> createPayment(OrderDto order) {
        Payment payment = PaymentMapper.mapToPayment(order);
        payment.setState(PaymentState.PENDING);
        return ResponseEntity.ok(PaymentMapper.maptoPaymentDto(paymentRepository.save(payment)));
    }

    @Override
    public ResponseEntity<Double> calculateTotalCost(OrderDto order) {
        var totalCost = 0.0;
        Double productCost = calculateProductCost(order).getBody();
        totalCost += productCost + productCost * 0.1;
        totalCost += order.getDeliveryPrice();

        return ResponseEntity.ok(totalCost);
    }

    @Override
    public void refund(UUID paymentId) {
        Payment payment = findPaymentById(paymentId);
        payment.setState(PaymentState.SUCCESS);
        orderClient.paymentOrder(payment.getOrderId());
        paymentRepository.save(payment);
    }

    @Override
    public ResponseEntity<Double> calculateProductCost(OrderDto order) {
        var productCost = order.getProducts().keySet().stream()
                .map(storeClient::getProduct)
                .map(ProductDto::getPrice)
                .reduce(0.0, Double::sum);

        return ResponseEntity.ok(productCost);
    }

    @Override
    public void failed(UUID paymentId) {
        Payment payment = findPaymentById(paymentId);
        payment.setState(PaymentState.FAILED);
        orderClient.paymentFailedOrder(payment.getOrderId());
        paymentRepository.save(payment);
    }

    private Payment findPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NoOrderFoundException("No payment found"));
    }
}
