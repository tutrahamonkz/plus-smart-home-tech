package ru.yandex.practicum.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.yandex.practicum.model.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
