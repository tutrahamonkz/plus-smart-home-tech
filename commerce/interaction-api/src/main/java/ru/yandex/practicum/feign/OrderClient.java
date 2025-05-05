package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "payment",
        path = "/api/v1/order")
public interface OrderClient {

    @GetMapping
    ResponseEntity<List<OrderDto>> findAllOrders(@RequestParam(name = "username") String userName);

    @PutMapping
    ResponseEntity<OrderDto> createOrder(@RequestBody CreateNewOrderRequest createNewOrderRequest);

    @PostMapping("/return")
    ResponseEntity<OrderDto> returnOrder(@RequestBody ProductReturnRequest productReturnRequest);

    @PostMapping("/payment")
    ResponseEntity<OrderDto> paymentOrder(@RequestBody UUID orderId);

    @PostMapping("/payment/failed")
    ResponseEntity<OrderDto> paymentFailedOrder(@RequestBody UUID orderId);

    @PostMapping("/delivery")
    ResponseEntity<OrderDto> deliveryOrder(@RequestBody UUID orderId);

    @PostMapping("/delivery/failed")
    ResponseEntity<OrderDto> deliveryFailedOrder(@RequestBody UUID orderId);

    @PostMapping("/completed")
    ResponseEntity<OrderDto> completedOrder(@RequestBody UUID orderId);

    @PostMapping("/calculate/total")
    ResponseEntity<OrderDto> calculateTotalOrder(@RequestBody UUID orderId);

    @PostMapping("/calculate/delivery")
    ResponseEntity<OrderDto> calculateDeliveryOrder(@RequestBody UUID orderId);

    @PostMapping("/assembly")
    ResponseEntity<OrderDto> assemblyOrder(@RequestBody UUID orderId);

    @PostMapping("/assembly/failed")
    ResponseEntity<OrderDto> assemblyFailedOrder(@RequestBody UUID orderId);
}
