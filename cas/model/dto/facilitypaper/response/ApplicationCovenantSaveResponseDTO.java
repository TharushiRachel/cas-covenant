package com.itechro.cas.model.dto.facilitypaper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.customer.response.CustomerCovenantSaveResponseDTO;
import lombok.Data;

@Data
public class ApplicationCovenantSaveResponseDTO {

    @JsonProperty("RequestUUID")
    private String RequestUUID;


    private Customer customer;

    private FacilityPaper facilityPaper;

    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private String covenant_Due_Date;

    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    @Data
    private static class FacilityPaper{
        private String fpRefNumber;
    }

    @Data
    private static class Customer{
        private String customerFinancialID;
    }
}
