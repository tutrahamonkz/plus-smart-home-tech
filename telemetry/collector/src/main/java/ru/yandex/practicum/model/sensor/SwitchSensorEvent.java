package ru.yandex.practicum.model.sensor;

import lombok.Getter;

@Getter
public class SwitchSensorEvent extends SensorEvent {

    private Boolean state;

    @Override
    public SensorEventType getType() {
        return SensorEventType.SWITCH_SENSOR_EVENT;
    }
}
