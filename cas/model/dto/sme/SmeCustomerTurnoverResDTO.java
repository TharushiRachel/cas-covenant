package com.itechro.cas.model.dto.sme;

import lombok.Data;

@Data
public class SmeCustomerTurnoverResDTO {

    private String status;
    private String requestId;
    private String currencyCode;
    private String turnover;
}
