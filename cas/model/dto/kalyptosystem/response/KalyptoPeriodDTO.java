package com.itechro.cas.model.dto.kalyptosystem.response;

import lombok.Data;

@Data
public class KalyptoPeriodDTO {

    private Integer parameterId;

    private String name;

    private String valueNumeric;

    private String valuePercentage;

    private String valueText;

    private String periodId;
}
