package com.itechro.cas.model.dto.finacle.request;

import lombok.Data;

@Data
public class SaveInsuranceDetailsRQ {

    private String insuranceType;
    private String insurerDetails;
    private String policyNo;
    private String riskCoverStartDate;
    private String riskCoverEndDate;
    private String premiumAmount;
    private String itemsInsured;
}
