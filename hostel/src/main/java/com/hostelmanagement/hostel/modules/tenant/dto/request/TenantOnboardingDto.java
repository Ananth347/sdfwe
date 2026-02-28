package com.hostelmanagement.hostel.modules.tenant.dto.request;

import lombok.Data;

@Data
public class TenantOnboardingDto {

    private Long bedId;

    private String tenantName;

    private String mobile;

    private Double monthlyFee;

    private Double maintenanceFee;

}
