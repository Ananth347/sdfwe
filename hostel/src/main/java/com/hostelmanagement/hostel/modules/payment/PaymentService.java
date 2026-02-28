package com.hostelmanagement.hostel.modules.payment;

import com.hostelmanagement.hostel.modules.payment.dto.request.PaymentRequestDto;
import com.hostelmanagement.hostel.modules.tenant.Tenant;
import com.hostelmanagement.hostel.modules.tenant.TenantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;
    private final TenantRepository tenantRepository;

    public Payment createPayment(PaymentRequestDto dto) {

        Tenant tenant = tenantRepository.findById(dto.getTenantId())
                .orElseThrow(() -> new RuntimeException("Tenant not found"));

        Payment payment = new Payment();
        payment.setTenant(tenant);
        payment.setAmount(dto.getAmount());
        payment.setMonth(dto.getMonth());
        payment.setStatus(PaymentStatus.PAID);
        payment.setPaidAt(LocalDateTime.now());

        return paymentRepository.save(payment);
    }

    public List<Payment> getTenantPayments(Long tenantId) {
        return paymentRepository.findByTenant_Id(tenantId);
    }

    public List<Payment> getPendingPayments() {
        return paymentRepository.findByStatus(PaymentStatus.PENDING);
    }

    public Payment getTenantPaymentByMonth(Long tenantId, String month) {

        return paymentRepository.findByTenant_IdAndMonth(tenantId, month)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Payment not found"));
    }

}