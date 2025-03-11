package ru.yandex.practicum.model.sensor;

import lombok.Getter;

@Getter
public class LightSensorEvent extends SensorEvent {

    private Integer linkQuality;
    private Integer luminosity;

    @Override
    public SensorEventType getType() {
        return SensorEventType.LIGHT_SENSOR_EVENT;
    }
}
