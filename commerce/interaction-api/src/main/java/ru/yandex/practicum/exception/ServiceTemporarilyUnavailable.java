package ru.yandex.practicum.exception;

public class ServiceTemporarilyUnavailable extends RuntimeException {
    public ServiceTemporarilyUnavailable(String message) {
        super(message);
    }
}
