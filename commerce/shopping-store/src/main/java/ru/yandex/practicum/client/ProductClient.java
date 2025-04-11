package ru.yandex.practicum.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.model.ProductDto;

import java.util.List;

@FeignClient(name = "service", path = "/api/v1/shopping-store/")
public class ProductClient {
    @GetMapping()
    public List<ProductDto> getProducts() {
        return null;
    }

    @PutMapping()
    public ProductDto createProduct(@RequestBody Object product) {
        return null;
    }

    @PostMapping()
    public ProductDto updateProduct(@RequestBody Object product) {
        return null;
    }

    @PostMapping("/removeProductFromStore")
    public Boolean removeProductFromStore(@RequestBody String productId) {
        return null;
    }

    @PostMapping("/quantityState")
    public Boolean quantityState(@RequestBody Object quantity) {
        return null;
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable String productId) {
        return null;
    }
}
