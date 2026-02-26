package com.hostelmanagement.hostel.dto;

import lombok.Data;

@Data
public class BedAllocateDto {

    private Long id;
    private String tenantName;
    private String mobile;

    private Double agreedMonthlyFee;
    private Double agreedMaintenanceFee;
}
