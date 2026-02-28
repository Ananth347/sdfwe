package com.hostelmanagement.hostel.modules.payment;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByTenant_Id(Long tenantId);

    List<Payment> findByStatus(PaymentStatus status);

    List<Payment> findByTenant_IdAndMonth(Long tenantId, String month);

}
