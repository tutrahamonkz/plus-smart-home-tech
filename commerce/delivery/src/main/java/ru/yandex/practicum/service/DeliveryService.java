package ru.yandex.practicum.service;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.util.UUID;

public interface DeliveryService {

    ResponseEntity<DeliveryDto> createDelivery(DeliveryDto deliveryDto);

    void successfulDelivery(UUID orderId);

    void pickedDelivery(UUID orderId);

    void failedDelivery(UUID orderId);

    ResponseEntity<Double> postDelivery(OrderDto orderDto);
}
