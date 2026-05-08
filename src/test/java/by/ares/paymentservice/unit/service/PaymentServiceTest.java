package by.ares.paymentservice.unit.service;

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
import by.ares.paymentservice.service.impl.PaymentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static by.ares.paymentservice.util.TestConstants.*;
import static by.ares.paymentservice.util.TestModelBuilder.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private EventManager<OrderStatusRequest> eventManager;
    @Mock
    private PaymentMapper paymentMapper;
    @Mock
    private ExternalApiService externalApiService;
    @InjectMocks
    private PaymentServiceImpl paymentService;

    private Payment payment;
    private PaymentRequest paymentRequest;
    private PaymentDto paymentDto;
    private DateRangeRequest dateRangeRequest;


    @BeforeEach
    void setUp() {
        payment = buildPayment();
        paymentRequest = buildPaymentRequest();
        paymentDto = buildPaymentDto();
        dateRangeRequest = buildDateRangeRequest();
    }

    @Test
    void findByUserId_shouldReturnMappedPage() {
        when(paymentRepository.findAllByUserId(any(Pageable.class), any(Long.class)))
                .thenReturn(new PageImpl<>(List.of(payment)));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        Page<PaymentDto> result = paymentService.findByUserId(mock(Pageable.class), USER_ID);
        assertEquals(1, result.getTotalElements());
        assertEquals(paymentDto, result.stream().findFirst().get());
    }

    @Test
    void findByOrderId_shouldReturnMappedPage() {
        when(paymentRepository.findAllByOrderId(any(Pageable.class), any(Long.class)))
                .thenReturn(new PageImpl<>(List.of(payment)));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        Page<PaymentDto> result = paymentService.findByOrderId(mock(Pageable.class), ORDER_ID);
        assertEquals(1, result.getTotalElements());
        assertEquals(paymentDto, result.stream().findFirst().get());
    }

    @Test
    void findByStatus_shouldReturnMappedPage() {
        when(paymentRepository.findAllByStatus(any(Pageable.class), any(Status.class)))
                .thenReturn(new PageImpl<>(List.of(payment)));
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        Page<PaymentDto> result = paymentService.findByStatus(mock(Pageable.class), PAYMENT_STATUS);
        assertEquals(1, result.getTotalElements());
        assertEquals(paymentDto, result.stream().findFirst().get());
    }

    @Test
    void save_shouldReturnPaymentDto() {
        when(externalApiService.performPayment(any(PaymentRequest.class))).thenReturn(PAYMENT_STATUS);
        when(paymentMapper.toModel(any(PaymentRequest.class))).thenReturn(payment);
        when(paymentRepository.save(any(Payment.class))).thenReturn(payment);
        when(paymentMapper.toDto(payment)).thenReturn(paymentDto);
        PaymentDto result = paymentService.save(paymentRequest);
        assertEquals(result, paymentDto);
    }

    @Test
    void totalSum_shouldReturnLong() {
        when(paymentRepository.totalSum(any(LocalDate.class), any(LocalDate.class))).thenReturn(TOTAL_RESULT);
        Long result = paymentService.totalSum(dateRangeRequest);
        assertEquals(TOTAL_RESULT, result);
    }

    @Test
    void totalUserSum_shouldReturnLong() {
        when(paymentRepository.totalSum(any(LocalDate.class), any(LocalDate.class), any(Long.class))).thenReturn(TOTAL_RESULT);
        Long result = paymentService.totalSum(dateRangeRequest, USER_ID);
        assertEquals(TOTAL_RESULT, result);
    }

}
