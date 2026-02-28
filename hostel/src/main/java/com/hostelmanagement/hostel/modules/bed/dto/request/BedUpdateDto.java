package com.hostelmanagement.hostel.modules.bed.dto.request;

import lombok.Data;

@Data
public class BedUpdateDto {

    private Long id;
    private String bedName;
    private Long hostelId;

    private Double monthlyFee;
    private Double maintenanceFee;
}
