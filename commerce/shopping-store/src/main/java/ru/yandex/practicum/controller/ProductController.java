package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.UpdateQuantityState;
import ru.yandex.practicum.service.ProductServiceImpl;

@RestController()
@RequestMapping("/api/v1/shopping-store/")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;

    @GetMapping()
    public Page<ProductDto> getProducts(@RequestBody ProductCategory category, Pageable pageable) {
        return productService.getProductsByCategory(category, pageable);
    }

    @PutMapping()
    public ProductDto createProduct(@RequestBody ProductDto product) {
        return productService.createProduct(product);
    }

    @PostMapping()
    public ProductDto updateProduct(@RequestBody ProductDto product) {
        return productService.updateProduct(product);
    }

    @PostMapping("/removeProductFromStore")
    public Boolean removeProductFromStore(@RequestBody String productId) {
        return productService.removeProductFromStore(productId);
    }

    @PostMapping("/quantityState")
    public Boolean quantityState(@RequestBody UpdateQuantityState quantity) {
        return productService.quantityState(quantity);
    }

    @GetMapping("/{productId}")
    public ProductDto getProduct(@PathVariable String productId) {
        return productService.getProduct(productId);
    }
}
