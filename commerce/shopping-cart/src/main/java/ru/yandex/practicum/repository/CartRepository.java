package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.ShoppingCart;

import java.util.Optional;

public interface CartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserName(String userName);

    void deleteByUserName(String userName);
}
