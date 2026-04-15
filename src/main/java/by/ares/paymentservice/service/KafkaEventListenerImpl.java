package by.ares.paymentservice.service;

import by.ares.paymentservice.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaEventListenerImpl implements EventListener<Status> {

    private final KafkaMessengerService<String, Status> kafkaMessengerService;

    @Override
    public void perform(Status status) {
        kafkaMessengerService.send(status);
    }

}
