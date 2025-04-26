package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.WarehouseProduct;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface WarehouseProductRepository extends JpaRepository<WarehouseProduct, Integer> {

    Boolean existsById(UUID productId);

    WarehouseProduct findById(UUID productId);

    List<WarehouseProduct> findAllById(Set<UUID> ids);
}
