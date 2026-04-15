package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.exception.ExceptionResponse;
import by.ares.paymentservice.exception.ExternalApiException;
import by.ares.paymentservice.exception.ResponseParseException;
import by.ares.paymentservice.model.Status;
import by.ares.paymentservice.service.ExternalApiService;
import by.ares.paymentservice.service.AbstractEventManager;
import by.ares.paymentservice.service.EventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

import static by.ares.paymentservice.util.PaymentServiceConstants.RESPONSE_PARSE_MESSAGE;

@Service
public class ExternalApiServiceImpl extends AbstractEventManager<Status> implements ExternalApiService {

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    @Value("${api.user.uri}")
    private String uri;

    public ExternalApiServiceImpl(RestClient restClient, EventListener<Status> eventListener,
                                  ObjectMapper objectMapper) {
        this.restClient = restClient;
        this.objectMapper = objectMapper;
        subscribe(eventListener);
    }

    @Override
    public Status performPayment() {
        var paymentStatus =  restClient.put()
                .uri(uri)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> {
                    ExceptionResponse error;
                    try (InputStream is = res.getBody()) {
                        if (is == null) {
                            throw new ExternalApiException("Empty response from user-service");
                        }
                        error = objectMapper.readValue(is, ExceptionResponse.class);
                    } catch (IOException e) {
                        throw new ResponseParseException(RESPONSE_PARSE_MESSAGE);
                    }

                    throw new ExternalApiException(error.getMessage());
                })
                .body(Long.class);
        final Status status = paymentStatus % 2 == 0 ? Status.SUCCESS : Status.FAILED;
        notify(status);
        return status;
    }
}
