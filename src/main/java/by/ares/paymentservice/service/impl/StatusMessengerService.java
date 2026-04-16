package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.dto.request.OrderStatusRequest;
import by.ares.paymentservice.service.KafkaMessengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class StatusMessengerService implements KafkaMessengerService<String, OrderStatusRequest> {

    private final KafkaTemplate<String, OrderStatusRequest> kafkaTemplate;

    @Value("${TOPIC_NAME:}")
    private String topicName;

    public CompletableFuture<SendResult<String, OrderStatusRequest>> send(OrderStatusRequest status) {
        return kafkaTemplate.send(topicName, status);
    }

}
