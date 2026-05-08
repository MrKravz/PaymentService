package by.ares.paymentservice.service;

import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.model.Status;

public interface ExternalApiService {
    Status performPayment(PaymentRequest paymentRequest);
}
