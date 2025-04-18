package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import ru.yandex.practicum.dto.*;

@FeignClient(name = "warehouse", fallback = WarehouseClientFallback.class)
//@CircuitBreaker(name = "warehouse")
public interface WarehouseClient {

    @PutMapping("/api/v1/warehouse")
    void addProduct(@RequestBody NewProductInWarehouseRequest product);

    @PostMapping("/api/v1/warehouse/check")
    BookedProductsDto check(@RequestBody ShoppingCartDto shoppingCartDto);

    @PostMapping("/api/v1/warehouse/add")
    void addProductQuantity(@Validated @RequestBody AddProductToWarehouseRequest productQuantity);

    @GetMapping("/api/v1/warehouse/address")
    AddressDto getAddress();
}
