package by.ares.paymentservice.service;

public interface SecurityValidationService {
    void validateAccess(Long id, Long userId, String role);
}

