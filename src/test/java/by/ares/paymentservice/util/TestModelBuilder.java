package by.ares.paymentservice.util;

import by.ares.paymentservice.dto.request.DateRangeRequest;
import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.dto.response.PaymentDto;
import by.ares.paymentservice.model.Payment;

import static by.ares.paymentservice.util.TestConstants.*;

public class TestModelBuilder {

    public static Payment buildPayment() {
        return new Payment()
                .setUserId(USER_ID)
                .setOrderId(ORDER_ID)
                .setStatus(PAYMENT_STATUS)
                .setTimestamp(START_DATE)
                .setPaymentAmount(PAYMENT_AMOUNT);
    }

    public static PaymentRequest buildPaymentRequest() {
        return PaymentRequest.builder()
                .userId(USER_ID)
                .orderId(ORDER_ID)
                .paymentAmount(PAYMENT_AMOUNT)
                .build();
    }

    public static PaymentDto buildPaymentDto() {
        return PaymentDto.builder()
                .userId(USER_ID)
                .orderId(ORDER_ID)
                .status(PAYMENT_STATUS)
                .timestamp(START_DATE)
                .paymentAmount(PAYMENT_AMOUNT)
                .build();
    }

    public static DateRangeRequest buildDateRangeRequest() {
        return DateRangeRequest.builder()
                .start(START_DATE)
                .end(END_DATE)
                .build();
    }

}
