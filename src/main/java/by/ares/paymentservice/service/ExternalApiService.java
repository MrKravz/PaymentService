package by.ares.paymentservice.service;

import by.ares.paymentservice.model.Status;

public interface ExternalApiService extends EventManager<Status> {
    Status performPayment();
}
