package ru.yandex.practicum.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.kafka.KafkaAvroProducer;
import ru.yandex.practicum.mapping.HubMapper;
import ru.yandex.practicum.model.hub.HubEvent;

@Slf4j
@Service
@AllArgsConstructor
public class HubService {
    private final KafkaAvroProducer producer;
    private static final String TOPIC_HUB = "telemetry.hubs.v1";

    public void send(HubEvent event){
        log.info("Отправляем данные от хаба {}", event.toString());

        producer.send(TOPIC_HUB, HubMapper.INSTANCE.mapHubToAvro(event));
    }
}