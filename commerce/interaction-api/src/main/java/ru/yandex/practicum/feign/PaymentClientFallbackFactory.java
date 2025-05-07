package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;

import java.util.UUID;

@Component
public class PaymentClientFallbackFactory implements FallbackFactory<PaymentClient> {

    @Override
    public PaymentClient create(Throwable cause) {
        return new PaymentClient() {

            @Override
            public ResponseEntity<PaymentDto> createPayment(OrderDto order) {
                throw new ServiceTemporarilyUnavailable("Сервер оплаты недоступен");
            }

            @Override
            public ResponseEntity<Double> calculateTotalCost(OrderDto order) {
                throw new ServiceTemporarilyUnavailable("Сервер оплаты недоступен");
            }

            @Override
            public void refund(UUID paymentId) {
                throw new ServiceTemporarilyUnavailable("Сервер оплаты недоступен");
            }

            @Override
            public ResponseEntity<Double> calculateProductCost(OrderDto order) {
                throw new ServiceTemporarilyUnavailable("Сервер оплаты недоступен");
            }

            @Override
            public void failed(UUID paymentId) {
                throw new ServiceTemporarilyUnavailable("Сервер оплаты недоступен");
            }
        };
    }
}
