package com.itechro.cas.model.dto.integration.request.finacle;

import lombok.Data;

@Data
public class FinacleGuaranteeVolumesRQ {
    private String refId;
    private String custId;
    private String startDate;
    private String endDate;
}
