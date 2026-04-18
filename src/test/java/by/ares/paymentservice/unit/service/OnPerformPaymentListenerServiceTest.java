package by.ares.paymentservice.unit.service;

import by.ares.paymentservice.dto.request.OrderStatusRequest;
import by.ares.paymentservice.service.KafkaMessengerService;
import by.ares.paymentservice.service.impl.OnPerformPaymentListenerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OnPerformPaymentListenerServiceTest {

    @Mock
    private KafkaMessengerService<String, OrderStatusRequest> kafkaMessengerService;
    @InjectMocks
    private OnPerformPaymentListenerService onPerformPaymentListenerService;

    private OrderStatusRequest orderStatusRequest;

    @BeforeEach
    void setUp() {
        orderStatusRequest = new OrderStatusRequest();
    }

    @Test
    void testInvoke() {
        when(kafkaMessengerService.send(any(OrderStatusRequest.class))).thenReturn(mock(CompletableFuture.class));
        onPerformPaymentListenerService.invoke(orderStatusRequest);
        verify(kafkaMessengerService).send(orderStatusRequest);
    }

}