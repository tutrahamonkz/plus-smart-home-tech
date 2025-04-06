package ru.yandex.practicum.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.handler.hub.HubEventHandler;
import ru.yandex.practicum.kafka.telemetry.event.HubEventAvro;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class HubServiceImpl {

    private Map<String, HubEventHandler> handlers;

    public HubServiceImpl(Set<HubEventHandler> handlers) {
        this.handlers = handlers.stream()
                .collect(Collectors.toMap(
                        HubEventHandler::getEventType,
                        Function.identity()
                ));
    }

    public void updateState(HubEventAvro hubEvent) {
        String type = hubEvent.getPayload().getClass().getCanonicalName();
        if (handlers.containsKey(type)) {
            handlers.get(type).handle(hubEvent);
        }
    }
}