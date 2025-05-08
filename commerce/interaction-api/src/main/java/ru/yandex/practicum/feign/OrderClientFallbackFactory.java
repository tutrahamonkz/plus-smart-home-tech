package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.dto.CreateNewOrderRequest;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ProductReturnRequest;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;

import java.util.List;
import java.util.UUID;

@Component
public class OrderClientFallbackFactory implements FallbackFactory<OrderClient> {

    @Override
    public OrderClient create(Throwable cause) {
        return new OrderClient() {

            @Override
            public ResponseEntity<List<OrderDto>> findAllOrders(String userName) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> createOrder(CreateNewOrderRequest createNewOrderRequest) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> returnOrder(ProductReturnRequest productReturnRequest) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> paymentOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> paymentFailedOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> deliveryOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> deliveryFailedOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> completedOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> calculateTotalOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> calculateDeliveryOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> assemblyOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }

            @Override
            public ResponseEntity<OrderDto> assemblyFailedOrder(UUID orderId) {
                throw new ServiceTemporarilyUnavailable("Сервер заказов недоступен");
            }
        };
    }
}
