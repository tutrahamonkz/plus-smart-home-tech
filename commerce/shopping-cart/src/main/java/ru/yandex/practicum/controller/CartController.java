package ru.yandex.practicum.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;
import ru.yandex.practicum.service.CartService;

import java.util.List;
import java.util.Map;

@RestController()
@RequestMapping("/api/v1/shopping-cart/")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ShoppingCartDto getShoppingCart(@RequestParam String userName) {
        return cartService.getShoppingCart(userName);
    }

    @PutMapping
    public ShoppingCartDto addProductToCart(@RequestParam String userName,
                                            @RequestBody Map<String, Integer> products) {
        return cartService.addProductToCart(userName, products);
    }

    @DeleteMapping
    public void deleteCart(@RequestParam String userName) {
        cartService.deleteCart(userName);
    }

    @PostMapping("/remove")
    public ShoppingCartDto removeProductInCart(@RequestParam String userName, @RequestBody List<String> products) {
        return cartService.removeProductInCart(userName, products);
    }

    @PostMapping("/change-quantity")
    public ShoppingCartDto changeQuantity(@RequestParam String userName,
                                          @RequestBody ChangeProductQuantityRequest changeProductQuantityRequest) {
        return cartService.changeQuantity(userName, changeProductQuantityRequest);
    }

}
