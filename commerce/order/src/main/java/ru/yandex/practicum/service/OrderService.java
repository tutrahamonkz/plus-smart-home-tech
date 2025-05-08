package ru.yandex.practicum.service;

import org.springframework.http.ResponseEntity;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {

    ResponseEntity<List<OrderDto>> findAllOrders(String userName);

    ResponseEntity<OrderDto> createOrder(CreateNewOrderRequest createNewOrderRequest);

    ResponseEntity<OrderDto> returnOrder(ProductReturnRequest productReturnRequest);

    ResponseEntity<OrderDto> paymentOrder(UUID orderId);

    ResponseEntity<OrderDto> paymentFailedOrder(UUID orderId);

    ResponseEntity<OrderDto> deliveryOrder(UUID orderId);

    ResponseEntity<OrderDto> deliveryFailedOrder(UUID orderId);

    ResponseEntity<OrderDto> completedOrder(UUID orderId);

    ResponseEntity<OrderDto> calculateTotalOrder(UUID orderId);

    ResponseEntity<OrderDto> calculateDeliveryOrder(UUID orderId);

    ResponseEntity<OrderDto> assemblyOrder(UUID orderId);

    ResponseEntity<OrderDto> assemblyFailedOrder(UUID orderId);
}
