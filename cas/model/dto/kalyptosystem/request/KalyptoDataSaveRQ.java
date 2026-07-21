package com.itechro.cas.model.dto.kalyptosystem.request;

import lombok.Data;

@Data
public class KalyptoDataSaveRQ {

    private String parameterId;

    private String valueNumberic;

    private String valuePercentage;

    private String valueText;

    private String periodId;

    private String parameterName;

    private String customerId;

    private String facilityId;

    private Integer isAddedNew;
}
