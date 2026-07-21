package com.itechro.cas.model.dto.finacle.response;

import lombok.Data;

@Data
public class InsuranceDetails {

    private String insuranceType;
    private String insurerDetails;
    private String policyNo;
    private String policyAmount;
    private String riskCoverStartDate;
    private String riskCoverEndDate;
    private String premiumAmount;
    private String itemsInsured;
}
