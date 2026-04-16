package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.dto.request.OrderStatusRequest;
import by.ares.paymentservice.service.EventListener;
import by.ares.paymentservice.service.KafkaMessengerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OnPerformPaymentListenerService implements EventListener<OrderStatusRequest> {

    private final KafkaMessengerService<String, OrderStatusRequest> kafkaMessengerService;

    @Override
    public void invoke(OrderStatusRequest orderStatusRequest) {
        kafkaMessengerService.send(orderStatusRequest);
    }

}
