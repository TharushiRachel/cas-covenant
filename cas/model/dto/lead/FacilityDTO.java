package com.itechro.cas.model.dto.lead;

import lombok.Data;

@Data
public class FacilityDTO {
    private String facilityTemplateId;
    private String facilityTemplateName;
    private String facilityDescription;
    private String leaseAmount;
    private String requestedTenure;
    private String repaymentMode;

    public FacilityDTO(){

    }

}
