package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.ShoppingCart;

public interface CartRepository extends JpaRepository<ShoppingCart, Long> {

    ShoppingCart findByUserName(String userName);

    void deleteByUserName(String userName);
}
