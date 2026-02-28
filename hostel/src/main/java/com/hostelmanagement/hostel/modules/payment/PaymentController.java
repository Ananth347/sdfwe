package com.hostelmanagement.hostel.modules.payment;

import com.hostelmanagement.hostel.modules.payment.dto.request.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentRequestDto dto) {
        return ResponseEntity.ok(paymentService.createPayment(dto));
    }

    @GetMapping("/tenant/{tenantId}")
    public ResponseEntity<List<Payment>> getTenantPayments(@PathVariable Long tenantId) {
        return ResponseEntity.ok(paymentService.getTenantPayments(tenantId));
    }

    @GetMapping("/pending")
    public ResponseEntity<List<Payment>> getPendingPayments() {
        return ResponseEntity.ok(paymentService.getPendingPayments());
    }

    @GetMapping("/tenant/{tenantId}/{month}")
    public ResponseEntity<Payment> getPaymentByMonth(
            @PathVariable Long tenantId,
            @PathVariable String month) {

        return ResponseEntity.ok(paymentService.getTenantPaymentByMonth(tenantId, month));
    }

}