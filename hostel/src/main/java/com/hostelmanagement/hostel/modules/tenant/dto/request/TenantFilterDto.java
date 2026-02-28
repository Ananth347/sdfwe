package com.hostelmanagement.hostel.modules.tenant.dto.request;

import com.hostelmanagement.hostel.modules.tenant.TenantStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TenantFilterDto {

    private Long hostelId;

    private Long bedId;

    private TenantStatus status;

    private String tenantName;

    private String mobile;

    private String email;

    private String search; // global search

    private LocalDateTime fromDate;

    private LocalDateTime toDate;

}
