package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.*;

import java.util.Map;
import java.util.UUID;

@FeignClient(name = "warehouse",
        fallbackFactory = WarehouseClientFallbackFactory.class,
        path = "/api/v1/warehouse")
public interface WarehouseClient {

    @PutMapping()
    void addProduct(@RequestBody NewProductInWarehouseRequest product);

    @PostMapping("check")
    BookedProductsDto check(@RequestBody ShoppingCartDto shoppingCartDto);

    @PostMapping("add")
    void addProductQuantity(@Validated @RequestBody AddProductToWarehouseRequest productQuantity);

    @GetMapping("address")
    AddressDto getAddress();

    @PostMapping("/shipped")
    void shipped(@RequestBody ShippedToDeliveryRequest shippedToDeliveryRequest);

    @PostMapping("/return")
    void returnProduct(@RequestBody Map<UUID, Integer> returnMap);

    @PostMapping("/assembly")
    BookedProductsDto assemblyProduct(@RequestBody AssemblyProductsForOrderRequest request);
}
