package com.hostelmanagement.hostel.modules.bed.dto.request;


import lombok.Data;

@Data
public class AllocateBedDto {

    private Long bedId;
    private String tenantName;
    private String mobile;

    private Double agreedMonthlyFee;
    private Double agreedMaintenanceFee;
}