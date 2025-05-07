package ru.yandex.practicum.service;

import jakarta.ws.rs.NotAuthorizedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.exception.NoOrderFoundException;
import ru.yandex.practicum.feign.DeliveryClient;
import ru.yandex.practicum.feign.PaymentClient;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.mapper.OrderMapper;
import ru.yandex.practicum.model.Order;
import ru.yandex.practicum.repository.OrderRepository;

import java.util.List;
import java.util.UUID;

@Slf4j
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
        log.info("Find all orders by userName: {}", userName);
        if (userName.isBlank()) {
            throw new NotAuthorizedException("User is not authorized");
        }
        return ResponseEntity.ok(OrderMapper.mapToOrderDtoList(orderRepository.findAllByUserName(userName)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> createOrder(CreateNewOrderRequest createNewOrderRequest) {
        log.info("Create new order request: {}", createNewOrderRequest);
        Order order = orderRepository.save(OrderMapper.mapToOrder(createNewOrderRequest));
        order.setState(OrderState.NEW);

        BookedProductsDto bookedProductsDto = warehouseClient.check(createNewOrderRequest.getShoppingCart());

        DeliveryDto newDeliveryDto = DeliveryDto.builder()
                .orderId(order.getOrderId())
                .fromAddress(createNewOrderRequest.getDeliveryAddress())
                .toAddress(warehouseClient.getAddress())
                .deliveryState(DeliveryState.CREATED)
                .build();

        DeliveryDto deliveryDto = deliveryClient.createDelivery(newDeliveryDto).getBody();

        order.setDeliveryId(deliveryDto.getDeliveryId());
        order.setDeliveryWeight(bookedProductsDto.getDeliveryWeight());
        order.setDeliveryVolume(bookedProductsDto.getDeliveryVolume());
        order.setFragile(bookedProductsDto.getFragile());
        order.setDeliveryPrice(deliveryClient.costDelivery(OrderMapper.mapToOrderDto(order)).getBody());

        order.setProductPrice(paymentClient.calculateProductCost(OrderMapper.mapToOrderDto(order)).getBody());
        order.setTotalPrice(paymentClient.calculateTotalCost(OrderMapper.mapToOrderDto(order)).getBody());

        PaymentDto newPaymentDto = paymentClient.createPayment(OrderMapper.mapToOrderDto(order)).getBody();

        order.setPaymentId(newPaymentDto.getPaymentId());

        Order savedOrder = orderRepository.save(order);

        return ResponseEntity.ok(OrderMapper.mapToOrderDto(savedOrder));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> returnOrder(ProductReturnRequest productReturnRequest) {
        log.info("Return order request: {}", productReturnRequest);
        warehouseClient.returnProduct(productReturnRequest.getProducts());
        Order order = findOrderById(productReturnRequest.getOrderId());
        order.setState(OrderState.PRODUCT_RETURNED);
        orderRepository.save(order);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> paymentOrder(UUID orderId) {
        log.info("Payment successful order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setState(OrderState.PAID);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> paymentFailedOrder(UUID orderId) {
        log.info("Payment failed order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setState(OrderState.PAYMENT_FAILED);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> deliveryOrder(UUID orderId) {
        log.info("Delivery successful order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setState(OrderState.DELIVERED);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> deliveryFailedOrder(UUID orderId) {
        log.info("Delivery failed order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setState(OrderState.DELIVERY_FAILED);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> completedOrder(UUID orderId) {
        log.info("Complete order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setState(OrderState.COMPLETED);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> calculateTotalOrder(UUID orderId) {
        log.info("Calculate total order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setTotalPrice(paymentClient.calculateTotalCost(OrderMapper.mapToOrderDto(order)).getBody());
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> calculateDeliveryOrder(UUID orderId) {
        log.info("Calculate delivery order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setDeliveryPrice(deliveryClient.costDelivery(OrderMapper.mapToOrderDto(order)).getBody());
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> assemblyOrder(UUID orderId) {
        log.info("Assembly order: {}", orderId);
        Order order = findOrderById(orderId);
        warehouseClient.assemblyProduct(
                AssemblyProductsForOrderRequest.builder()
                        .orderId(orderId)
                        .products(order.getProducts())
                        .build()
        );
        order.setState(OrderState.ASSEMBLED);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    @Override
    @Transactional
    public ResponseEntity<OrderDto> assemblyFailedOrder(UUID orderId) {
        log.info("Assembly failed order: {}", orderId);
        Order order = findOrderById(orderId);
        order.setState(OrderState.ASSEMBLY_FAILED);
        return ResponseEntity.ok(OrderMapper.mapToOrderDto(orderRepository.save(order)));
    }

    private Order findOrderById(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NoOrderFoundException("No order found with id: " + orderId));
    }
}
