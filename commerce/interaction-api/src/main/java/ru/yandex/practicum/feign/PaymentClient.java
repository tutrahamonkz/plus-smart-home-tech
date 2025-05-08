package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.PaymentDto;

import java.util.UUID;

@FeignClient(name = "payment",
        path = "/api/v1/payment",
        fallbackFactory = PaymentClientFallbackFactory.class)
public interface PaymentClient {

    @PostMapping
    ResponseEntity<PaymentDto> createPayment(@RequestBody OrderDto order);

    @PostMapping("/totalCost")
    ResponseEntity<Double> calculateTotalCost(@RequestBody OrderDto order);

    @PostMapping("/refund")
    void refund(@RequestBody UUID paymentId);

    @PostMapping("/productCost")
    ResponseEntity<Double> calculateProductCost(@RequestBody OrderDto order);

    @PostMapping("/failed")
    void failed(@RequestBody UUID paymentId);
}
