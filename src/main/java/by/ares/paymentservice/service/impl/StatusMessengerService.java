package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.model.Status;
import by.ares.paymentservice.service.KafkaMessengerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class StatusMessengerService implements KafkaMessengerService<String, Status> {

    private final KafkaTemplate<String, Status> kafkaTemplate;

    @Value("")
    private String topicName;

    public CompletableFuture<SendResult<String, Status>> send(Status status) {
        return kafkaTemplate.send(topicName, status);
    }

}
