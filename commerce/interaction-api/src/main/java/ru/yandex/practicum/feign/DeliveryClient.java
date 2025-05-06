package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.OrderDto;

import java.util.UUID;

@FeignClient(name = "delivery",
        path = "/api/v1/delivery")
public interface DeliveryClient {

    @PutMapping
    ResponseEntity<DeliveryDto> createDelivery(@RequestBody DeliveryDto deliveryDto);

    @PostMapping("/successful")
    void successfulDelivery(@RequestBody UUID orderId);

    @PostMapping("/picked")
    void pickedDelivery(@RequestBody UUID orderId);

    @PostMapping("/failed")
    void failedDelivery(@RequestBody UUID orderId);

    @PostMapping("/post")
    ResponseEntity<Double> costDelivery(@RequestBody OrderDto orderDto);
}
