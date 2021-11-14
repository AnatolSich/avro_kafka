package org.example.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.example.KafkaMessage;
import org.example.Status;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaAvroProducerService {

    @Value("${kafka.topic}")
    private String topic;

    private final KafkaTemplate<String, KafkaMessage> kafkaTemplate;

    @Scheduled(fixedRate = 3_000, initialDelay = 5_000)
    public ListenableFuture<SendResult<String, KafkaMessage>> send() {
        final KafkaMessage kafkaMessage = KafkaMessage.newBuilder()
                .setId(UUID.randomUUID().toString())
                .setPayload("START" + UUID.randomUUID() + "END")
                .setStatus(Math.round(Math.random()) == 1 ? Status.YES : Status.NO).build();

        final ProducerRecord<String, KafkaMessage> record = new ProducerRecord<>(topic, kafkaMessage);

        log.info("Producing record: " + record);

        return kafkaTemplate.send(record);
    }

}
