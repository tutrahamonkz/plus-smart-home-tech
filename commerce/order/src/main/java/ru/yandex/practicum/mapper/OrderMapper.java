package ru.yandex.practicum.mapper;

import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.model.Order;

import java.util.List;

public class OrderMapper {

    public static OrderDto mapToOrderDto(Order order) {
        return OrderDto.builder()
                .orderId(order.getOrderId())
                .shoppingCartId(order.getShoppingCartId())
                .products(order.getProducts())
                .paymentId(order.getPaymentId())
                .deliveryId(order.getDeliveryId())
                .state(order.getState())
                .deliveryWeight(order.getDeliveryWeight())
                .deliveryVolume(order.getDeliveryVolume())
                .fragile(order.getFragile())
                .totalPrice(order.getTotalPrice())
                .deliveryPrice(order.getDeliveryPrice())
                .productPrice(order.getProductPrice())
                .build();
    }

    public static List<OrderDto> mapToOrderDtoList(List<Order> orders) {
        return orders.stream().map(OrderMapper::mapToOrderDto).toList();
    }

    public static Order mapToOrder(CreateNewOrderRequest createNewOrderRequest) {
        Order order = new Order();

        order.setShoppingCartId(createNewOrderRequest.getShoppingCart().getShoppingCartId());
        order.setProducts(createNewOrderRequest.getShoppingCart().getProducts());

        return order;
    }
}
