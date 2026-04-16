package by.ares.paymentservice.controller;

import by.ares.paymentservice.dto.request.DateRangeRequest;
import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.dto.response.PaymentDto;
import by.ares.paymentservice.model.Status;
import by.ares.paymentservice.service.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/search/by-status")
    public ResponseEntity<Page<PaymentDto>> getPaymentByOrderId(@PageableDefault Pageable pageable,
                                                                @RequestParam Status status) {
        return ResponseEntity.ok(paymentService.findByStatus(pageable, status));
    }

    @GetMapping("/search/by-user")
    public ResponseEntity<Page<PaymentDto>> getPaymentByUserId(@PageableDefault Pageable pageable,
                                                               @RequestParam Long userId) {
        return ResponseEntity.ok(paymentService.findByUserId(pageable, userId));
    }

    @GetMapping("/search/by-order")
    public ResponseEntity<PaymentDto> getPaymentByOrderId(@RequestParam Long orderId) {
        return ResponseEntity.ok(paymentService.findByOrderId(orderId));
    }

    @GetMapping("/statistics/total")
    public ResponseEntity<Long> getTotalPaymentsSum(@ModelAttribute DateRangeRequest dateRangeRequest) {
        return ResponseEntity.ok(paymentService.totalSum(dateRangeRequest));
    }

    @GetMapping("/statistics/total/{userId}")
    public ResponseEntity<Long> getTotalPaymentsSumForUser(
            @ModelAttribute DateRangeRequest dateRangeRequest,
            @PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.totalSum(dateRangeRequest, userId));
    }

    @PostMapping
    public ResponseEntity<PaymentDto> createPayment(@Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.save(paymentRequest));
    }

}
