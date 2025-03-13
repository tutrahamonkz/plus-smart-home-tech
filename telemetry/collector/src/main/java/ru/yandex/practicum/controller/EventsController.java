package ru.yandex.practicum.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.sensor.SensorEvent;
import ru.yandex.practicum.service.HubService;
import ru.yandex.practicum.service.SensorService;

@RestController
@RequestMapping("/events")
@AllArgsConstructor
public class EventsController {
    private final SensorService sensorService;
    private final HubService hubService;

    @PostMapping("/sensors")
    public void collectSensorEvent(@Valid @RequestBody SensorEvent event) {
        sensorService.send(event);
    }

    @PostMapping("/hubs")
    public void collectHub(@Valid @RequestBody HubEvent event) {
        hubService.send(event);
    }
}