package ru.yandex.practicum.handler.hub;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.grpc.telemetry.event.HubEventProto;
import ru.yandex.practicum.kafka.KafkaAvroProducer;
import ru.yandex.practicum.mapping.HubMapperHandle;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScenarioRemovedEventHandler implements HubEventHandler {

    @Value("${hub.topic}")
    private String topic;
    private final KafkaAvroProducer producer;

    @Override
    public HubEventProto.PayloadCase getMessageType() {
        return HubEventProto.PayloadCase.SCENARIO_REMOVED;
    }

    @Override
    public void handle(HubEventProto event) {
        log.info("Received scenario removed event: {}", event);
        //producer.send(topic, HubMapper.INSTANCE.mapHubToAvro(event));
        producer.send(topic, HubMapperHandle.mapScenarioRemoved(event));
    }
}