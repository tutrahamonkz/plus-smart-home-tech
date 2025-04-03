package ru.yandex.practicum.mapping;

import ru.yandex.practicum.kafka.telemetry.event.ScenarioAddedEventAvro;
import ru.yandex.practicum.model.Scenario;

public class ScenarioMapper {

    public static Scenario map(String hubId, ScenarioAddedEventAvro scenarioAddedEventAvro) {
        Scenario scenario = new Scenario();
        scenario.setHubId(hubId);
        scenario.setName(scenarioAddedEventAvro.getName());
        return scenario;
    }
}
