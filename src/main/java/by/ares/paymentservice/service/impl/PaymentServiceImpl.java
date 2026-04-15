package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.dto.request.DateRangeRequest;
import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.dto.response.PaymentDto;
import by.ares.paymentservice.exception.PaymentNotFoundException;
import by.ares.paymentservice.mapper.PaymentMapper;
import by.ares.paymentservice.model.Payment;
import by.ares.paymentservice.model.Status;
import by.ares.paymentservice.repository.PaymentRepository;
import by.ares.paymentservice.service.ExternalApiService;
import by.ares.paymentservice.service.KafkaMessengerService;
import by.ares.paymentservice.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.EventListener;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService, EventListener {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ExternalApiService externalApiService;
    private final KafkaMessengerService<String, String> kafkaMessengerService;

    @Override
    public Page<PaymentDto> findByUserId(Pageable pageable, Long userId) {
        return paymentRepository.findAllByUserId(pageable, userId)
                .map(paymentMapper::toDto);
    }

    @Override
    public Page<PaymentDto> findByStatus(Pageable pageable, Status status) {
        return paymentRepository.findAllByStatus(pageable, status)
                .map(paymentMapper::toDto);
    }

    @Override
    public PaymentDto findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId)
                .map(paymentMapper::toDto)
                .orElseThrow(() -> new PaymentNotFoundException("Payment not found by order id"));
    }

    @Override
    public PaymentDto save(PaymentRequest paymentRequest) {
        var payment = paymentMapper.toModel(paymentRequest);
        assignStatus(payment);
        return paymentMapper.toDto(
                paymentRepository.save(payment)
        );
    }

    @Override
    public Long totalSum(DateRangeRequest dateRangeRequest, Long userId) {
        return paymentRepository.totalSum(dateRangeRequest, userId);
    }

    @Override
    public Long totalSum(DateRangeRequest dateRangeRequest) {
        return paymentRepository.totalSum(dateRangeRequest);
    }


}
