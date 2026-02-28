package com.hostelmanagement.hostel.modules.bed.dto.request;

import lombok.Data;

@Data
public class BedAllocateDto {

    private Long id;
    private String tenantName;
    private String mobile;
    private Long hostelId;
    private Double agreedMonthlyFee;
    private Double agreedMaintenanceFee;
}
