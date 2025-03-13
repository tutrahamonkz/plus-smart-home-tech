package ru.yandex.practicum.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SerializationException extends RuntimeException {
    public SerializationException(String message, Throwable cause) {
        super(message, cause);
        log.error(message);
    }
}