package ru.yandex.practicum.model.sensor;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MotionSensorEvent extends SensorEvent {

    private Integer linkQuality;
    private Boolean motion;
    private Integer voltage;

    @Override
    public SensorEventType getType() {
        return SensorEventType.MOTION_SENSOR_EVENT;
    }
}