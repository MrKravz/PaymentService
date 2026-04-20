package by.ares.paymentservice.controller;

import by.ares.paymentservice.dto.request.DateRangeRequest;
import by.ares.paymentservice.dto.request.PaymentRequest;
import by.ares.paymentservice.dto.response.PaymentDto;
import by.ares.paymentservice.model.Status;
import by.ares.paymentservice.service.PaymentService;
import by.ares.paymentservice.service.SecurityValidationService;
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
    private final SecurityValidationService securityValidationService;

    @GetMapping("/search/by-status")
    public ResponseEntity<Page<PaymentDto>> findPaymentByStatus(@PageableDefault Pageable pageable,
                                                                @RequestParam Status status) { // admin
        return ResponseEntity.ok(paymentService.findByStatus(pageable, status));
    }

    @GetMapping("/search/by-user")
    public ResponseEntity<Page<PaymentDto>> findPaymentByUserId(@PageableDefault Pageable pageable,
                                                                @RequestParam(name = "userId") Long id,
                                                                @RequestHeader("X-User-Id") Long userId,
                                                                @RequestHeader("X-User-Role") String role) {
        securityValidationService.validateAccess(id, userId, role);
        return ResponseEntity.ok(paymentService.findByUserId(pageable, id));
    }

    @GetMapping("/search/by-order")
    public ResponseEntity<Page<PaymentDto>> findPaymentByOrderId(@PageableDefault Pageable pageable,
                                                                 @RequestParam Long orderId) { // admin
        return ResponseEntity.ok(paymentService.findByOrderId(pageable, orderId));
    }

    @GetMapping("/statistics")
    public ResponseEntity<Long> findTotalPaymentsSum(@ModelAttribute DateRangeRequest dateRangeRequest) { // admin
        return ResponseEntity.ok(paymentService.totalSum(dateRangeRequest));
    }

    @GetMapping("/statistics/users/{userId}")
    public ResponseEntity<Long> findTotalPaymentsSumByUserId(@ModelAttribute DateRangeRequest dateRangeRequest,
                                                             @PathVariable(name = "userId") Long id,
                                                             @RequestHeader("X-User-Id") Long userId,
                                                             @RequestHeader("X-User-Role") String role) { // user or admin
        securityValidationService.validateAccess(id, userId, role);
        return ResponseEntity.ok(paymentService.totalSum(dateRangeRequest, userId));
    }

    @PostMapping
    public ResponseEntity<PaymentDto> savePayment(@Valid @RequestBody PaymentRequest paymentRequest,
                                                  @RequestHeader("X-User-Id") Long userId,
                                                  @RequestHeader("X-User-Role") String role) { // user or admin
        securityValidationService.validateAccess(paymentRequest.getUserId(), userId, role);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.save(paymentRequest));
    }

}
