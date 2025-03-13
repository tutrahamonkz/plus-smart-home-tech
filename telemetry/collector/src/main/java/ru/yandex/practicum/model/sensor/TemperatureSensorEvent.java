package ru.yandex.practicum.model.sensor;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class TemperatureSensorEvent extends SensorEvent {

    private Integer temperatureC;
    private Integer temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}