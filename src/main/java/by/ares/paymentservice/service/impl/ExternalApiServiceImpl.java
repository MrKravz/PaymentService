package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.dto.request.OrderStatusRequest;
import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.exception.ExceptionResponse;
import by.ares.paymentservice.exception.ExternalApiException;
import by.ares.paymentservice.exception.ResponseParseException;
import by.ares.paymentservice.model.Status;
import by.ares.paymentservice.service.EventListener;
import by.ares.paymentservice.service.EventManager;
import by.ares.paymentservice.service.ExternalApiService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import static by.ares.paymentservice.util.PaymentServiceConstants.RESPONSE_PARSE_MESSAGE;

@Service
public class ExternalApiServiceImpl implements ExternalApiService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;
    private final EventManager<OrderStatusRequest> eventManager;

    private String uri;

    public ExternalApiServiceImpl(EventManager<OrderStatusRequest> eventManager,
                                  EventListener<OrderStatusRequest> eventListener,
                                  ObjectMapper objectMapper, RestClient restClient,
                                  @Value("${api.user.uri}") String uri) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        this.eventManager = eventManager;
        this.uri = uri;
        this.eventManager.subscribe(eventListener);
    }

    @Override
    public Status performPayment(PaymentRequest paymentRequest) {
        var paymentStatus = restClient.get()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    ExceptionResponse error;
                    try (InputStream is = res.getBody()) {
                        if (is == null) {
                            throw new ResponseParseException("Empty response from payment service");
                        }
                        error = objectMapper.readValue(is, ExceptionResponse.class);
                    } catch (IOException e) {
                        throw new ResponseParseException(RESPONSE_PARSE_MESSAGE);
                    }
                    throw new ExternalApiException(error.getMessage());
                })
                .body(Long.class);
        final Status status = paymentStatus % 2 == 0 ? Status.SUCCESS : Status.FAILED;
        eventManager.notify(new OrderStatusRequest(paymentRequest.getOrderId(), status));
        return status;
    }
}
