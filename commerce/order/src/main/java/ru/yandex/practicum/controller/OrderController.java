package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;
import ru.yandex.practicum.service.OrderService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDto>> findAllOrders(@RequestParam(name = "username") String userName) {
        return orderService.findAllOrders(userName);
    }

    @PutMapping
    public ResponseEntity<OrderDto> createOrder(@RequestBody CreateNewOrderRequest createNewOrderRequest) {
        return orderService.createOrder(createNewOrderRequest);
    }

    @PostMapping("/return")
    public ResponseEntity<OrderDto> returnOrder(@RequestBody ProductReturnRequest productReturnRequest) {
        return orderService.returnOrder(productReturnRequest);
    }

    @PostMapping("/payment")
    public ResponseEntity<OrderDto> paymentOrder(@RequestBody UUID orderId) {
        return orderService.paymentOrder(orderId);
    }

    @PostMapping("/payment/failed")
    public ResponseEntity<OrderDto> paymentFailedOrder(@RequestBody UUID orderId) {
        return orderService.paymentFailedOrder(orderId);
    }

    @PostMapping("/delivery")
    public ResponseEntity<OrderDto> deliveryOrder(@RequestBody UUID orderId) {
        return orderService.deliveryOrder(orderId);
    }

    @PostMapping("/delivery/failed")
    public ResponseEntity<OrderDto> deliveryFailedOrder(@RequestBody UUID orderId) {
        return orderService.deliveryFailedOrder(orderId);
    }

    @PostMapping("/completed")
    public ResponseEntity<OrderDto> completedOrder(@RequestBody UUID orderId) {
        return orderService.completedOrder(orderId);
    }

    @PostMapping("/calculate/total")
    public ResponseEntity<OrderDto> calculateTotalOrder(@RequestBody UUID orderId) {
        return orderService.calculateTotalOrder(orderId);
    }

    @PostMapping("/calculate/delivery")
    public ResponseEntity<OrderDto> calculateDeliveryOrder(@RequestBody UUID orderId) {
        return orderService.calculateDeliveryOrder(orderId);
    }

    @PostMapping("/assembly")
    public ResponseEntity<OrderDto> assemblyOrder(@RequestBody UUID orderId) {
        return orderService.assemblyOrder(orderId);
    }

    @PostMapping("/assembly/failed")
    public ResponseEntity<OrderDto> assemblyFailedOrder(@RequestBody UUID orderId) {
        return orderService.assemblyFailedOrder(orderId);
    }
}
