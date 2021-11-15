package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.example.model.KafkaMessage;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaAvroConsumerService {

    @KafkaListener(topics = "${kafka.topic}", groupId = "${spring.kafka.consumer.group-id}")
    void consume(KafkaMessage data) {
        log.info(data.toString());
    }

}
