package by.ares.paymentservice.service.impl;

import by.ares.paymentservice.dto.request.DateRangeRequest;
import by.ares.paymentservice.dto.request.OrderStatusRequest;
import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.dto.response.PaymentDto;
import by.ares.paymentservice.mapper.PaymentMapper;
import by.ares.paymentservice.model.Payment;
import by.ares.paymentservice.model.Status;
import by.ares.paymentservice.repository.PaymentRepository;
import by.ares.paymentservice.service.EventManager;
import by.ares.paymentservice.service.ExternalApiService;
import by.ares.paymentservice.service.PaymentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.EventListener;

@Service
public class PaymentServiceImpl implements PaymentService, EventListener {

    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;
    private final ExternalApiService externalApiService;
    private final EventManager<OrderStatusRequest> eventManager;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper,
                              ExternalApiService externalApiService, EventManager<OrderStatusRequest> eventManager,
                              by.ares.paymentservice.service.EventListener<OrderStatusRequest> eventListener) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.externalApiService = externalApiService;
        this.eventManager = eventManager;
        this.eventManager.subscribe(eventListener);
    }


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
    public Page<PaymentDto> findByOrderId(Pageable pageable, Long orderId) {
        return paymentRepository.findAllByOrderId(pageable, orderId)
                .map(paymentMapper::toDto);
    }

    @Override
    public PaymentDto save(PaymentRequest paymentRequest) {
        Status paymentStatus = externalApiService.performPayment(paymentRequest);
        Payment resultPayment = paymentMapper.toModel(paymentRequest);
        resultPayment.setStatus(paymentStatus);
        resultPayment.setTimestamp(LocalDate.now());
        var result = paymentMapper.toDto(paymentRepository.save(resultPayment));
        eventManager.notify(new OrderStatusRequest(paymentRequest.getOrderId(), paymentStatus));
        return result;
    }

    @Override
    public Long totalSum(DateRangeRequest dateRangeRequest, Long userId) {
        return paymentRepository.totalSum(dateRangeRequest.getStart(), dateRangeRequest.getEnd(), userId);
    }

    @Override
    public Long totalSum(DateRangeRequest dateRangeRequest) {
        return paymentRepository.totalSum(dateRangeRequest.getStart(), dateRangeRequest.getEnd());
    }

}
