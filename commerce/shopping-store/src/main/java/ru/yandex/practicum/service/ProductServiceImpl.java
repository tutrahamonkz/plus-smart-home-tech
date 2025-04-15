package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.ProductCategory;
import ru.yandex.practicum.dto.ProductDto;
import ru.yandex.practicum.dto.ProductState;
import ru.yandex.practicum.dto.UpdateQuantityState;
import ru.yandex.practicum.mapper.ProductMapper;
import ru.yandex.practicum.model.Product;
import ru.yandex.practicum.repository.ProductRepository;

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
        Product product = productRepository.findById(productDto.getProductId());
        return ProductMapper.toProductDto(productRepository.save(ProductMapper.updateToProduct(productDto, product)));
    }

    @Override
    public Boolean removeProductFromStore(String productId) {
        Product product = productRepository.findById(productId);
        product.setProductState(ProductState.DEACTIVATE);
        productRepository.save(product);
        return true;
    }

    @Override
    public Boolean quantityState(UpdateQuantityState quantity) {
        Product product = productRepository.findById(quantity.getProductId());
        product.setQuantityState(quantity.getQuantityState());
        productRepository.save(product);
        return true;
    }

    @Override
    public ProductDto getProduct(String productId) {
        return ProductMapper.toProductDto(productRepository.findById(productId));
    }
}