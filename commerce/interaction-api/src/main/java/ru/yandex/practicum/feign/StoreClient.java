package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.QuantityState;

import java.util.UUID;

@FeignClient(name = "shopping-store")
@RequestMapping("/api/v1/shopping-store/")
public interface StoreClient {

    @GetMapping()
    public Page<ProductDto> getProducts(@RequestBody ProductCategory category, Pageable pageable);

    @PutMapping()
    public ProductDto createProduct(@RequestBody ProductDto product);

    @PostMapping()
    public ProductDto updateProduct(@RequestBody ProductDto product);

    @PostMapping("/removeProductFromStore")
    public Boolean removeProductFromStore(@RequestBody String productId);

    @PostMapping("/quantityState")
    public Boolean quantityState(@RequestParam UUID productId, @RequestParam QuantityState quantity);

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable String productId);
}
