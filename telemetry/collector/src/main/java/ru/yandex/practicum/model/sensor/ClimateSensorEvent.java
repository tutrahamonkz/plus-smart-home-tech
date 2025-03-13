package ru.yandex.practicum.model.sensor;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ClimateSensorEvent extends SensorEvent {

    private Integer temperatureC;
    private Integer humidity;
    private Integer co2Level;

    @Override
    public SensorEventType getType() {
        return SensorEventType.CLIMATE_SENSOR_EVENT;
    }
}