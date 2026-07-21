package com.itechro.cas.model.dto.sme;

import lombok.Data;

@Data
public class SmeCustomerTurnoverRqDTO {

    private String requestId;
    private String custId;
    private Integer facilityPaperId;
}
