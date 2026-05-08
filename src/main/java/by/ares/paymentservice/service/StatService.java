package by.ares.paymentservice.service;

import by.ares.paymentservice.dto.request.DateRangeRequest;

public interface StatService {
    Long totalSum(DateRangeRequest dateRangeRequest, Long userId);
    Long totalSum(DateRangeRequest dateRangeRequest);
}
