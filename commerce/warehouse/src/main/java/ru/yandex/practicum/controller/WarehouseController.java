package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.*;
import ru.yandex.practicum.service.WarehouseService;

@RestController
@RequestMapping("/api/v1/warehouse")
@RequiredArgsConstructor
public class WarehouseController {

    private final WarehouseService warehouseService;

    @PutMapping
    public void addProduct(@RequestBody NewProductInWarehouseRequest product) {
        warehouseService.addProduct(product);
    }

    @PostMapping("/check")
    public BookedProductsDto check(@RequestBody ShoppingCartDto shoppingCartDto) {
        return warehouseService.check(shoppingCartDto);
    }

    @PostMapping("/add")
    public void addProductQuantity(@Validated @RequestBody AddProductToWarehouseRequest productQuantity) {
        warehouseService.addProductQuantity(productQuantity);
    }

    @GetMapping("/address")
    public AddressDto getAddress() {
        return warehouseService.getAddress();
    }
}
