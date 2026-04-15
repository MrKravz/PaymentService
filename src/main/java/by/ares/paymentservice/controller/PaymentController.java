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

    @GetMapping
    public ResponseEntity<Page<PaymentDto>> findByStatus(@PageableDefault Pageable pageable,
                                                         @RequestParam Status status) {
        return ResponseEntity.ok(paymentService.findByStatus(pageable, status));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Page<PaymentDto>> findByUserId(@PageableDefault Pageable pageable,
                                                         @PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.findByUserId(pageable, userId));
    }

    @GetMapping
    public ResponseEntity<PaymentDto> findByOrderId(@PathVariable Long orderId) { // TODO refactor controller
        return ResponseEntity.ok(paymentService.findByOrderId(orderId));
    }

    @GetMapping
    public ResponseEntity<Long> totalSum(@ModelAttribute DateRangeRequest dateRangeRequest) { // TODO refactor controller
        return ResponseEntity.ok(paymentService.totalSum(dateRangeRequest));
    }

    @GetMapping
    public ResponseEntity<Long> totalSum(@ModelAttribute DateRangeRequest dateRangeRequest,
                                         @PathVariable Long userId) {
        return ResponseEntity.ok(paymentService.totalSum(dateRangeRequest, userId));
    }

    @PostMapping
    public ResponseEntity<PaymentDto> save(@Valid @RequestBody PaymentRequest paymentRequest) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(paymentService.save(paymentRequest));
    }



}
