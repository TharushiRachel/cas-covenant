package com.itechro.cas.model.dto.customer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import java.util.Date;


@Data
public class CustomerCovenantUpdateResponseDTO {
    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String custId;

    private String casReference;

    @JsonProperty("covenant_Code")
    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    private String createdUserDisplayName;
    //private String userName;

    private String createdBy;

    private Date createdDate;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    private AppsConstants.CovenantApplicableType applicableType;
}
