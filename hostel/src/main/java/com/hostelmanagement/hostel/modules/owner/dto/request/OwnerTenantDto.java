package com.hostelmanagement.hostel.modules.owner.dto.request;



import lombok.Data;

@Data
public class OwnerTenantDto {

    private Long bedId;

    private String tenantName;

    private String mobile;

    private Double monthlyFee;

    private Double maintenanceFee;

}
