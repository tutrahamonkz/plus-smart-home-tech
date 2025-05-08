package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;

import java.util.UUID;

@Component
public class DeliveryClientFallbackFactory implements FallbackFactory<DeliveryClient> {

    @Override
    public DeliveryClient create(Throwable cause) {
        return new DeliveryClient() {

            @Override
            public ResponseEntity<DeliveryDto> createDelivery(DeliveryDto deliveryDto) {
                throw new ServiceTemporarilyUnavailable("Сервер доставки недоступен");
            }

            @Override
            public void successfulDelivery(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер доставки недоступен");
            }

            @Override
            public void pickedDelivery(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер доставки недоступен");
            }

            @Override
            public void failedDelivery(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер доставки недоступен");
            }

            @Override
            public ResponseEntity<Double> costDelivery(OrderDto orderDto) {
                throw new ServiceTemporarilyUnavailable("Сервер доставки недоступен");
            }
        };
    }
}
