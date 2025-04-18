package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductState;
import ru.yandex.practicum.dto.QuantityState;
import ru.yandex.practicum.exception.ProductNotFoundException;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable) {
        log.info("Получаем список продуктов по категории: {}, с пагинацией: {}", category, pageable);
        return productRepository.findAllByProductCategory(category, pageable)
                .map(ProductMapper::toProductDto);
    }

    @Override
    @Transactional
    public ProductDto createProduct(ProductDto productDto) {
        log.info("Добавляем продукт в магазин: {}", productDto);
        return ProductMapper.toProductDto(productRepository.save(ProductMapper.toProduct(productDto)));
    }

    @Override
    @Transactional
    public ProductDto updateProduct(ProductDto productDto) {
        log.info("Обновляем информацию о продукте: {}, в магазине", productDto);
        Product product = findProductById(productDto.getProductId());
        return ProductMapper.toProductDto(productRepository.save(ProductMapper.updateToProduct(productDto, product)));
    }

    @Override
    @Transactional
    public Boolean removeProductFromStore(UUID productId) {
        log.info("Деактивируем товар: {}, в магазине", productId);
        Product savedProduct = updateProductStats(productId, ProductState.DEACTIVATE, null);
        return savedProduct.getProductState() == ProductState.DEACTIVATE;
    }

    @Override
    @Transactional
    public Boolean quantityState(UUID productId, QuantityState quantityState) {
        log.info("Изменяем количество доступного товара: {}, на {}", productId, quantityState.toString());
        Product savedProduct = updateProductStats(productId, null, quantityState);
        return savedProduct.getQuantityState() == quantityState;
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        log.info("Получаем товар: {}", productId);
        return ProductMapper.toProductDto(findProductById(productId));
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException("Не найден продукт: " + productId));
    }

    private Product updateProductStats(UUID productId, ProductState productState, QuantityState quantityState) {
        Product product = findProductById(productId);
        if (productState != null) {
            product.setProductState(productState);
        }
        if (quantityState != null) {
            product.setQuantityState(quantityState);
        }
        return productRepository.save(product);
    }
}