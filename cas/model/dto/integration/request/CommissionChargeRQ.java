package com.itechro.cas.model.dto.integration.request;

import lombok.Data;

import java.io.Serializable;

@Data
public class CommissionChargeRQ implements Serializable {

    private String customerId;

    private String dateFrom;

    private String dateTo;

    private String chargeFrom;

    private String chargeTo;

    private String productId;

    private  String chargeSubType;
}
