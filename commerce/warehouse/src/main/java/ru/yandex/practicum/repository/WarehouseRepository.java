package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Warehouse;

public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {
}
