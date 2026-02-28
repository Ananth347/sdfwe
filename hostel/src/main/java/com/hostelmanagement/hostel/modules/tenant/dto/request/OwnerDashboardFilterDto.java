package com.hostelmanagement.hostel.modules.tenant.dto.request;

import com.hostelmanagement.hostel.modules.bed.BedStatus;
import com.hostelmanagement.hostel.modules.payment.PaymentStatus;
import com.hostelmanagement.hostel.modules.tenant.TenantStatus;
import lombok.Data;

@Data
public class OwnerDashboardFilterDto {

    private Long ownerId;

    private Long hostelId;

    private Long bedId;

    private TenantStatus tenantStatus;

    private BedStatus bedStatus;

    private PaymentStatus paymentStatus;

    private String search;

}
