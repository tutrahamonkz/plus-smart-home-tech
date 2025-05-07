package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import ru.yandex.practicum.model.Address;
import ru.yandex.practicum.model.Delivery;
import ru.yandex.practicum.repository.AddressRepository;
import ru.yandex.practicum.repository.DeliveryRepository;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

    private static final double BASE_PRICE = 5.0;
    private static final String WAREHOUSE_ADDRESS = "ADDRESS_2";

    private final DeliveryRepository deliveryRepository;
    private final WarehouseClient warehouseClient;
    private final OrderClient orderClient;
    private final AddressRepository addressRepository;

    @Override
    @Transactional
    public ResponseEntity<DeliveryDto> createDelivery(DeliveryDto deliveryDto) {
        log.info("Creating delivery: {}", deliveryDto);
        Delivery delivery = deliveryRepository.save(DeliveryMapper.mapToDelivery(deliveryDto));
        Address fromAddress = DeliveryMapper.mapToAddress(deliveryDto.getFromAddress());
        Address toAddress = DeliveryMapper.mapToAddress(deliveryDto.getToAddress());
        delivery.setFromAddress(addressRepository.save(fromAddress));
        delivery.setToAddress(addressRepository.save(toAddress));

        return ResponseEntity.ok(DeliveryMapper.mapToDeliveryDto(delivery));
    }

    @Override
    @Transactional
    public void successfulDelivery(UUID orderId) {
        log.info("Successful delivery: {}", orderId);
        final Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.DELIVERED);
        orderClient.deliveryOrder(orderId);
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void pickedDelivery(UUID orderId) {
        log.info("Picked delivery: {}", orderId);
        final Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.IN_PROGRESS);
        orderClient.assemblyOrder(orderId);
        warehouseClient.shipped(ShippedToDeliveryRequest.builder()
                .deliveryId(delivery.getDeliveryId())
                .orderId(orderId)
                .build());
        deliveryRepository.save(delivery);
    }

    @Override
    @Transactional
    public void failedDelivery(UUID orderId) {
        log.info("Failed delivery: {}", orderId);
        final Delivery delivery = findDeliveryById(orderId);
        delivery.setDeliveryState(DeliveryState.FAILED);
        orderClient.deliveryFailedOrder(orderId);
        deliveryRepository.save(delivery);
    }

    @Override
    public ResponseEntity<Double> costDelivery(OrderDto orderDto) {
        log.info("Cost delivery: {}", orderDto);
        var result = BASE_PRICE;

        Delivery delivery = findDeliveryById(orderDto.getDeliveryId());

        Address toAddress = delivery.getToAddress();
        Address fromAddress = delivery.getFromAddress();

        // Если адрес склада содержит название ADDRESS_2, то умножаем на 2. Складываем получившийся результат
        // с базовой стоимостью.
        if (fromAddress.getCity().equals(WAREHOUSE_ADDRESS)) {
            result += BASE_PRICE * 2;
        }

        // Если в заказе есть признак хрупкости, умножаем сумму на 0.2 и складываем с полученным на предыдущем шаге
        // итогом.
        if (orderDto.getFragile()) {
            result += result * 0.2;
        }

        // Добавляем к сумме, полученной на предыдущих шагах, вес заказа, умноженный на 0.3.
        result += orderDto.getDeliveryWeight() * 0.3;
        // Складываем с полученным на прошлом шаге итогом объём, умноженный на 0.2.
        result += orderDto.getDeliveryVolume() * 0.2;

        // Если адрес доставки не на одной улице со складом, увеличиваем стоимость доставки в 0.2 раза.
        if (!fromAddress.getStreet().equals(toAddress.getStreet())) {
            result = result * 0.2;
        }

        return ResponseEntity.ok(result);
    }

    private Delivery findDeliveryById(UUID id) {
        return deliveryRepository.findById(id)
                .orElseThrow(() -> new NoDeliveryFoundException("No delivery found with id: " + id));
    }
}
