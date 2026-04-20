package by.ares.paymentservice.integration.controller;

import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.model.Payment;
import by.ares.paymentservice.repository.PaymentRepository;
import by.ares.paymentservice.service.SecurityValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import static by.ares.paymentservice.util.TestConstants.*;
import static by.ares.paymentservice.util.TestModelBuilder.buildPayment;
import static by.ares.paymentservice.util.TestModelBuilder.buildPaymentRequest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PaymentControllerTest extends AbstractIntegrationTest {

    @MockitoBean
    private SecurityValidationService securityValidationService;
    @Autowired
    public PaymentRepository paymentRepository;

    private Payment payment;

    @BeforeEach
    void init() {
        payment = saveTestItem();
    }

    private Payment saveTestItem() {
        return paymentRepository.save(buildPayment());
    }

    @Test
    void findPaymentByStatus_shouldReturnPage() throws Exception {
        saveTestItem();
        saveTestItem();
        mockMvc.perform(get("/payments/search/by-status")
                        .param("status", "SUCCESS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3));
    }

    @Test
    void findPaymentByUserId_shouldReturnPage() throws Exception {
        saveTestItem();
        saveTestItem();
        mockMvc.perform(get("/payments/search/by-user")
                        .param("userId", USER_ID.toString())
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3));
    }

    @Test
    void findPaymentByOrderId_shouldReturnPage() throws Exception {
        saveTestItem();
        saveTestItem();
        mockMvc.perform(get("/payments/search/by-order")
                        .param("orderId", ORDER_ID.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content.length()").value(3));
    }

    @Test
    void savePayment_shouldReturnPayment() throws Exception {
        PaymentRequest request = buildPaymentRequest();
        stubFindUserById();
        mockMvc.perform(post("/payments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.orderId").value(ORDER_ID))
                .andExpect(jsonPath("$.userId").value(USER_ID))
                .andExpect(jsonPath("$.status").value(PAYMENT_STATUS.toString()));
    }

    @Test
    void findTotalPaymentsSum_shouldReturnTotal() throws Exception {
        mockMvc.perform(get("/payments/statistics")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", START_DATE.toString())
                        .param("end", END_DATE.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void findTotalPaymentsSumByUserId_shouldReturnTotal() throws Exception {
        mockMvc.perform(get("/payments/statistics/users/{userId}", USER_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("start", START_DATE.toString())
                        .param("end", END_DATE.toString())
                        .header("X-User-Id", 1L)
                        .header("X-User-Role", "ADMIN"))
                .andExpect(status().isOk());
    }

}