package com.itechro.cas.model.dto.finacle.response;

import lombok.Data;

import java.util.List;

@Data
public class InsurenceDetailsFromFinacleRS {


    private String collateralType;
    private List<InsuranceDetails> insuranceDetails;
    private String insuranceValuationExpireDates;
    private String createdDate;
}
