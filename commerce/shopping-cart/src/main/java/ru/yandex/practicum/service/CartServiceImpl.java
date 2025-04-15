package ru.yandex.practicum.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.mapper.CartMapper;
import ru.yandex.practicum.model.ShoppingCart;
import ru.yandex.practicum.repository.CartRepository;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;

    @Override
    public ShoppingCartDto getShoppingCart(String userName) {
        return CartMapper.toSoppingCartDto(cartRepository.findByUserName(userName));
    }

    @Override
    public ShoppingCartDto addProductToCart(String userName, Map<String, Integer> products) {
        ShoppingCart cart = cartRepository.findByUserName(userName);
        cart.getProducts().putAll(products);
        cartRepository.save(cart);
        return CartMapper.toSoppingCartDto(cart);
    }

    @Override
    public void deleteCart(String userName) {
        cartRepository.deleteByUserName(userName);
    }

    @Override
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
    public ShoppingCartDto changeQuantity(String userName, ChangeProductQuantityRequest changeProductQuantityRequest) {
        ShoppingCart cart = cartRepository.findByUserName(userName);
        cart.getProducts().put(changeProductQuantityRequest.getProductId(),
                changeProductQuantityRequest.getNewQuantity());
        cartRepository.save(cart);
        return CartMapper.toSoppingCartDto(cart);
    }
}
