package ru.yandex.practicum.service;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.fallback.WarehouseClientFallback;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.mapper.CartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.CartRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final WarehouseClient warehouseClient;
    private final WarehouseClientFallback warehouseClientFallback;

    @Override
    public ShoppingCartDto getShoppingCart(String userName) {
        return CartMapper.toSoppingCartDto(cartRepository.findByUserName(userName));
    }

    @Override
    @Transactional
    public ShoppingCartDto addProductToCart(String userName, Map<String, Integer> products) {
        ShoppingCart cart = cartRepository.findByUserName(userName);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setUserName(userName);
            cart.setProducts(new HashMap<>());
        }
        cart.getProducts().putAll(products);
        try {
            checkStock(CartMapper.toSoppingCartDto(cart));
        } catch (FeignException e) {
            throw new ProductInShoppingCartLowQuantityInWarehouse(e.getMessage());
        }

        cartRepository.save(cart);
        return CartMapper.toSoppingCartDto(cart);
    }

    @Override
    @Transactional
    public void deleteCart(String userName) {
        cartRepository.deleteByUserName(userName);
    }

    @Override
    @Transactional
    public ShoppingCartDto removeProductInCart(String userName, List<String> products) {
        ShoppingCart cart = cartRepository.findByUserName(userName);
        Map<String, Integer> productsMap = cart.getProducts();
        for (String product : products) {
            productsMap.remove(product);
        }
        cartRepository.save(cart);
        return CartMapper.toSoppingCartDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCartDto changeQuantity(String userName, ChangeProductQuantityRequest changeProductQuantityRequest) {
        ShoppingCart cart = cartRepository.findByUserName(userName);
        cart.getProducts().put(changeProductQuantityRequest.getProductId(),
                changeProductQuantityRequest.getNewQuantity());
        cartRepository.save(cart);
        return CartMapper.toSoppingCartDto(cart);
    }

    @CircuitBreaker(name = "warehouse", fallbackMethod = "fallbackCheckStock")
    private BookedProductsDto checkStock(ShoppingCartDto shoppingCartDto) {
        return warehouseClient.check(shoppingCartDto);
    }

    private BookedProductsDto fallbackCheckStock(ShoppingCartDto shoppingCartDto) {
        return warehouseClientFallback.check(shoppingCartDto);
    }
}