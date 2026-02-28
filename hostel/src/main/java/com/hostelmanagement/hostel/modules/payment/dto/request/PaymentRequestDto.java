package com.hostelmanagement.hostel.modules.payment.dto.request;

import lombok.Data;

@Data
public class PaymentRequestDto {

    private Long tenantId;

    private Double amount;

    private String month;

}
