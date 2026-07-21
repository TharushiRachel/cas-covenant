package com.itechro.cas.model.dto.kalyptosystem;

import lombok.Data;

@Data
public class kalyptoRefresh {


    private Integer id;
    private Integer parameterId;

    private Integer kalyptoId;

    private Integer valueNumeric;

    private Integer valuePercentage;

    private String valueText;

    private Integer periodId;
}
