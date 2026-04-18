package by.ares.paymentservice.util;

import by.ares.paymentservice.model.Status;

import java.time.LocalDate;

public class TestConstants {
    public static final Long USER_ID = 1L;
    public static final Long ORDER_ID = 2L;
    public static final Long PAYMENT_AMOUNT = 200L;
    public static final Long TOTAL_RESULT = 10000L;
    public static final Status PAYMENT_STATUS = Status.SUCCESS;
    public static final String TOPIC_NAME = "test_topic";
    public static final String EVENT = "event";
    public static final LocalDate START_DATE = LocalDate.of(2026, 01, 01);
    public static final LocalDate END_DATE = LocalDate.of(2026, 01, 01);

}
