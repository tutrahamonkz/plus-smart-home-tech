package ru.yandex.practicum.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
        log.error(message);
    }
}