package com.hostelmanagement.hostel.modules.hostel.dto.request;

import com.hostelmanagement.hostel.modules.owner.Owner;
import lombok.Data;

@Data
public class HostelCreateDto {

    private Double defaultMonthlyFee;
    private Double defaultMaintenanceFee;
    private Long ownerId;
    private String name;
    private int totalBeds;
}
