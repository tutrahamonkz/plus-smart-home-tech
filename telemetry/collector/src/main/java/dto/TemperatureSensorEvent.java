package dto;

import lombok.Getter;

@Getter
public class TemperatureSensorEvent extends SensorEvent {

    private Integer temperatureC;
    private Integer temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}
