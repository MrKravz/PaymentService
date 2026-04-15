package by.ares.paymentservice.service;

import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.dto.response.PaymentDto;
import by.ares.paymentservice.model.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaymentService extends StatService {
    Page<PaymentDto> findByUserId(Pageable pageable, Long userId);
    Page<PaymentDto> findByStatus(Pageable pageable, Status status);
    PaymentDto findByOrderId(Long orderId);
    PaymentDto save(PaymentRequest paymentRequest);
}
