package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.service.DeliveryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PutMapping
    public ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto) {
        return deliveryService.createDelivery(deliveryDto);
    }

    @PostMapping("/successful")
    public void successfulDelivery(@RequestBody UUID orderId) {
        deliveryService.successfulDelivery(orderId);
    }

    @PostMapping("/picked")
    public void pickedDelivery(@RequestBody UUID orderId) {
        deliveryService.pickedDelivery(orderId);
    }

    @PostMapping("/failed")
    public void failedDelivery(@RequestBody UUID orderId) {
        deliveryService.failedDelivery(orderId);
    }

    @PostMapping("/cost")
    public ResponseEntity<Double> costDelivery(@RequestBody OrderDto orderDto) {
        return deliveryService.costDelivery(orderDto);
    }
}
