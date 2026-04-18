package by.ares.paymentservice.unit.service;

import by.ares.paymentservice.dto.request.OrderStatusRequest;
import by.ares.paymentservice.service.impl.StatusMessengerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.concurrent.CompletableFuture;

import static by.ares.paymentservice.util.TestConstants.TOPIC_NAME;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class StatusMessengerServiceTest {

    @Mock
    private KafkaTemplate<String, OrderStatusRequest> kafkaTemplate;
    @InjectMocks
    private StatusMessengerService statusMessengerService;

    private OrderStatusRequest orderStatusRequest;

    @BeforeEach
    void setUp() {
        orderStatusRequest = new OrderStatusRequest();
        ReflectionTestUtils.setField(statusMessengerService, "topicName", TOPIC_NAME);
    }

    @Test
    void testInvoke() {
        when(kafkaTemplate.send(anyString(), any(OrderStatusRequest.class)))
                .thenReturn(mock(CompletableFuture.class));
        var result = statusMessengerService.send(orderStatusRequest);
        assertNotNull(result);
    }


}