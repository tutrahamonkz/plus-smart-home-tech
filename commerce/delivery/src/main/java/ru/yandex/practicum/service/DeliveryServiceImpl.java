package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.DeliveryDto;
import ru.yandex.practicum.dto.DeliveryState;
import ru.yandex.practicum.dto.OrderDto;
import ru.yandex.practicum.dto.ShippedToDeliveryRequest;
import ru.yandex.practicum.exception.NoDeliveryFoundException;
import ru.yandex.practicum.feign.OrderClient;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.mapper.DeliveryMapper;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private static final double BASE_PRICE = 5.0;

    private final DeliveryRepository deliveryRepository;
    private final WarehouseClient warehouseClient;
    private final OrderClient orderClient;

    @Override
    @Transactional
    public ResponseEntity<DeliveryDto> createDelivery(DeliveryDto deliveryDto) {
        return ResponseEntity.ok(DeliveryMapper.mapToDeliveryDto(deliveryRepository.save(DeliveryMapper
                .mapToDelivery(deliveryDto))));
    }

    @Override
    @Transactional
    public void successfulDelivery(UUID orderId) {
        final Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        ShippedToDeliveryRequest request = ShippedToDeliveryRequest.builder()
                .orderId(orderId)
                .deliveryId(delivery.getDeliveryId())
                .build();
        warehouseClient.shipped(request);
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void pickedDelivery(UUID orderId) {
        final Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        orderClient.deliveryOrder(orderId);
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void failedDelivery(UUID orderId) {
        final Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.deliveryFailedOrder(orderId);
        deliveryRepository.save(delivery);
    }

    @Override
    public ResponseEntity<Double> postDelivery(OrderDto orderDto) {
        var result = BASE_PRICE;

        if (orderDto.getDeliveryId() != UUID.fromString("ADDRESS_2")) {
            result += BASE_PRICE * 2;
        }

        if (orderDto.getFragile()) {
            result += result * 0.2;
        }

        result += orderDto.getDeliveryWeight() * 0.3;
        result += orderDto.getDeliveryVolume() * 0.2;

        if (orderDto.getDeliveryId() != UUID.fromString("ADDRESS_3")) {
            result = +result * 0.2;
        }

        return ResponseEntity.ok(result);
    }

    private Delivery findDeliveryById(UUID id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NoDeliveryFoundException("No delivery found with id: " + id));
    }
}
