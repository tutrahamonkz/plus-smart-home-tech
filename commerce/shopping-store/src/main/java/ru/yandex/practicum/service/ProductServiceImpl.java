package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductState;
import ru.yandex.practicum.dto.QuantityState;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Page<ProductDto> getProductsByCategory(ProductCategory category, Pageable pageable) {
        return productRepository.findAllByProductCategory(category, pageable)
                .map(ProductMapper::toProductDto);
    }

    @Override
    public ProductDto createProduct(ProductDto productDto) {
        return ProductMapper.toProductDto(productRepository.save(ProductMapper.toProduct(productDto)));
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto) {
        Product product = findProductById(productDto.getProductId());
        return ProductMapper.toProductDto(productRepository.save(ProductMapper.updateToProduct(productDto, product)));
    }

    @Override
    public Boolean removeProductFromStore(UUID productId) {
        Product product = findProductById(productId);
        product.setProductState(ProductState.DEACTIVATE);
        Product savedProduct = productRepository.save(product);
        return savedProduct.getProductState() == ProductState.DEACTIVATE;
    }

    @Override
    public Boolean quantityState(UUID productId, QuantityState quantity) {
        Product product = findProductById(productId);
        product.setQuantityState(quantity);
        productRepository.save(product);
        return true;
    }

    @Override
    public ProductDto getProduct(UUID productId) {
        return ProductMapper.toProductDto(findProductById(productId));
    }

    private Product findProductById(UUID productId) {
        return productRepository.findById(productId);
    }
}