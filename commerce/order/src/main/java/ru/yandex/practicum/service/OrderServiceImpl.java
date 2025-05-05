package ru.yandex.practicum.service;

import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.AssemblyProductsForOrderRequest;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.feign.DeliveryClient;
import ru.yandex.practicum.feign.PaymentClient;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final DeliveryClient deliveryClient;
    private final PaymentClient paymentClient;
    private final WarehouseClient warehouseClient;

    @Override
    public ResponseEntity<List<OrderDto>> findAllOrders(String userName) {
        if (userName.isBlank()) {
            throw new NotAuthorizedException("User is not authorized");
        }
        return ResponseEntity.ok(OrderMapper.mapToOrderDtoList(orderRepository.findAllByUserName(userName)));
    }

    @Override
    public ResponseEntity<OrderDto> createOrder(CreateNewOrderRequest createNewOrderRequest) {
        Order order = orderRepository.save(OrderMapper.mapToOrder(createNewOrderRequest));
        AssemblyProductsForOrderRequest assemblyProductsForOrderRequest = AssemblyProductsForOrderRequest.builder()
                .products(createNewOrderRequest.getShoppingCart().getProducts())
                .orderId(order.getOrderId())
                .build();
        warehouseClient.assemblyProduct(assemblyProductsForOrderRequest);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> returnOrder(ProductReturnRequest productReturnRequest) {
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(findOrderById(productReturnRequest.getOrderId())));
    }

    @Override
    public ResponseEntity<OrderDto> paymentOrder(UUID orderId) {
        Order order = findOrderById(orderId);

        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> paymentFailedOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> deliveryOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> deliveryFailedOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> completedOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> calculateTotalOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> calculateDeliveryOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> assemblyOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    @Override
    public ResponseEntity<OrderDto> assemblyFailedOrder(UUID orderId) {
        Order order = findOrderById(orderId);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(order));
    }

    private Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("No order found with id: " + orderId));
    }
}
