package ru.yandex.practicum.service;

import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.dto.BookedProductsDto;
import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.exception.NoProductsInShoppingCartException;
import ru.yandex.practicum.exception.NotAuthorizedUserException;
import ru.yandex.practicum.exception.ProductInShoppingCartLowQuantityInWarehouse;
import ru.yandex.practicum.exception.ServiceTemporarilyUnavailable;
import ru.yandex.practicum.feign.WarehouseClient;
import ru.yandex.practicum.mapper.CartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.CartRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final WarehouseClient warehouseClient;

    @Override
    public ShoppingCartDto getShoppingCart(String userName) {
        checkUser(userName);
        log.info("Получение корзины покупок пользователя: {}", userName);
        return CartMapper.toSoppingCartDto(getOrCreateShoppingCart(userName));
    }

    @Override
    @Transactional
    public ShoppingCartDto addProductToCart(String userName, Map<UUID, Integer> products) {
        log.info("Добавляет продукты: {} в корзину пользователя: {}", products, userName);
        ShoppingCart cart = getOrCreateShoppingCart(userName);
        cart.getProducts().putAll(products);

        try {
            checkStock(CartMapper.toSoppingCartDto(cart));
        } catch (FeignException e) {
            log.error("Ошибка при обращении к складу: {}", e.getMessage());
            throw new ProductInShoppingCartLowQuantityInWarehouse(e.getMessage());
        }

        cartRepository.save(cart);
        log.info("Добавили продукты {} в корзину пользователя: {}", products, userName);
        return CartMapper.toSoppingCartDto(cart);
    }

    @Override
    @Transactional
    public void deleteCart(String userName) {
        log.info("Удаляем корзину пользователя {}", userName);
        checkUser(userName);
        cartRepository.deleteByUserName(userName);
    }

    @Override
    @Transactional
    public ShoppingCartDto removeProductInCart(String userName, List<UUID> products) {
        log.info("Удаляем продукты: {} из корзины пользователя: {}", products, userName);
        ShoppingCart cart = getOrCreateShoppingCart(userName);
        Map<UUID, Integer> productsMap = cart.getProducts();

        List<UUID> notFoundProducts = products.stream()
                .filter(product -> !cart.getProducts().containsKey(product))
                .toList();

        if (!notFoundProducts.isEmpty()) {
            throw new NoProductsInShoppingCartException("Данные продукты отсутствуют в корзине: " + notFoundProducts);
        }

        products.forEach(product -> cart.getProducts().remove(product));
        cartRepository.save(cart);
        return CartMapper.toSoppingCartDto(cart);
    }

    @Override
    @Transactional
    public ShoppingCartDto changeQuantity(String userName, ChangeProductQuantityRequest changeProductQuantityRequest) {
        log.info("Изменяем количество продукта: {}, в корзине пользователя {}", changeProductQuantityRequest, userName);
        ShoppingCart cart = getOrCreateShoppingCart(userName);
        Map<UUID, Integer> productsMap = cart.getProducts();
        if (!productsMap.containsKey(changeProductQuantityRequest.getProductId())) {
            throw new NoProductsInShoppingCartException("В корзине отсутствует продукт: " +
                    changeProductQuantityRequest.getProductId());
        }
        productsMap.put(changeProductQuantityRequest.getProductId(),
                changeProductQuantityRequest.getNewQuantity());
        cartRepository.save(cart);
        return CartMapper.toSoppingCartDto(cart);
    }

    private void checkUser(String userName) {
        if (userName == null) {
            throw new NotAuthorizedUserException("Имя пользователя не должно быть пустым");
        }
    }

    @CircuitBreaker(name = "warehouse", fallbackMethod = "fallbackCheckStock")
    private BookedProductsDto checkStock(ShoppingCartDto shoppingCartDto) {
        return warehouseClient.check(shoppingCartDto);
    }

    private BookedProductsDto fallbackCheckStock(ShoppingCartDto shoppingCartDto) {
        throw new ServiceTemporarilyUnavailable("Сервер склада временно недоступен");
    }

    private ShoppingCart getOrCreateShoppingCart(String userName) {
        checkUser(userName);
        return cartRepository.findByUserName(userName)
                .orElseGet(() -> {
                    log.info("Создаём новую корзину для пользователя: {}", userName);
                    ShoppingCart cart = new ShoppingCart();
                    cart.setUserName(userName);
                    cart.setProducts(new HashMap<>());
                    return cart;
                });
    }
}