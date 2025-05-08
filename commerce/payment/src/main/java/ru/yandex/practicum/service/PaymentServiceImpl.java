package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private static final Double NDS = 10.0;

    private final PaymentRepository paymentRepository;
    private final StoreClient storeClient;
    private final OrderClient orderClient;

    @Override
    @Transactional
    public ResponseEntity<PaymentDto> createPayment(OrderDto order) {
        log.info("Create payment for order: {}", order);
        Payment payment = PaymentMapper.mapToPayment(order);
        payment.setState(PaymentState.PENDING);
        return ResponseEntity.ok(PaymentMapper.maptoPaymentDto(paymentRepository.save(payment)));
    }

    @Override
    public ResponseEntity<Double> calculateTotalCost(OrderDto order) {
        log.info("Calculate total cost for order: {}", order);
        Double totalCost = order.getProductPrice(); // Добавляем стоимость товаров
        totalCost += totalCost * NDS; // Добавляем НДС
        totalCost += order.getDeliveryPrice(); // Добавляем стоимость доставки

        return ResponseEntity.ok(totalCost);
    }

    @Override
    @Transactional
    public void refund(UUID paymentId) {
        log.info("Refund payment: {}", paymentId);
        Payment payment = findPaymentById(paymentId);
        payment.setState(PaymentState.SUCCESS);
        orderClient.paymentOrder(payment.getOrderId());
        paymentRepository.save(payment);
    }

    @Override
    public ResponseEntity<Double> calculateProductCost(OrderDto order) {
        log.info("Calculate product cost for order: {}", order);
        var productCost = 0.0;
        Map<UUID, Integer> productMap = order.getProducts();
        List<ProductDto> products = productMap.keySet().stream()
                .map(storeClient::getProduct)
                .toList();

        for (ProductDto product : products) {
            productCost += product.getPrice() * productMap.get(product.getProductId());
        }

        return ResponseEntity.ok(productCost);
    }

    @Override
    @Transactional
    public void failed(UUID paymentId) {
        log.info("Failed payment: {}", paymentId);
        Payment payment = findPaymentById(paymentId);
        payment.setState(PaymentState.FAILED);
        orderClient.paymentFailedOrder(payment.getOrderId());
        paymentRepository.save(payment);
    }

    private Payment findPaymentById(UUID paymentId) {
        return paymentRepository.findById(paymentId).orElseThrow(() -> new NoOrderFoundException("No payment found"));
    }
}
