package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.model.Payment;

public class PaymentMapper {

    public static Payment mapToPayment(OrderDto order) {
        Payment payment = new Payment();
        payment.setTotalPayment(order.getTotalPrice());
        payment.setDeliveryTotal(order.getDeliveryPrice());
        payment.setFeeTotal(order.getProductPrice());

        return payment;
    }

    public static PaymentDto maptoPaymentDto(Payment payment) {
        return PaymentDto.builder()
                .paymentId(payment.getPaymentId())
                .totalPayment(payment.getTotalPayment())
                .deliveryTotal(payment.getDeliveryTotal())
                .feeTotal(payment.getFeeTotal())
                .build();
    }
}
