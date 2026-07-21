package com.itechro.cas.model.dto.finacle.request;

import lombok.Data;

@Data
public class ImportTurnoverDataRQ {

    private String year;
    private String billCurrencyCode;
    private Double billAmount =0.0;
    private Double convertedAmount=0.0;
    private String turnOverType;
    private String createdDate;
    private String foracid;
}

