package ru.yandex.practicum.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.dto.ChangeProductQuantityRequest;
import ru.yandex.practicum.dto.ShoppingCartDto;

import java.util.List;
import java.util.Map;

@FeignClient(name = "shopping-cart")
@RequestMapping("/api/v1/shopping-cart/")
public interface CartClient {

    @GetMapping
    ShoppingCartDto getShoppingCart(@RequestParam String userName);

    @PutMapping
    ShoppingCartDto addProductToCart(@RequestParam String userName, @RequestBody Map<String, Integer> products);

    @DeleteMapping
    void deleteCart(@RequestParam String userName);

    @PostMapping("/remove")
    ShoppingCartDto removeProductInCart(@RequestParam String userName, @RequestBody List<String> products);

    @PostMapping("/change-quantity")
    ShoppingCartDto changeQuantity(@RequestParam String userName,
                                          @RequestBody ChangeProductQuantityRequest changeProductQuantityRequest);
}
