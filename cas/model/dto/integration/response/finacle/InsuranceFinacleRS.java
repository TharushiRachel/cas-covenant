package com.itechro.cas.model.dto.integration.response.finacle;

import lombok.Data;

@Data
public class InsuranceFinacleRS {

    private String status;
    private String requestId;
    private InsuranceSuccessResponse successResponse;
}
