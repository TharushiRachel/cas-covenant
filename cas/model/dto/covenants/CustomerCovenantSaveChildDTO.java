package com.itechro.cas.model.dto.covenants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class CustomerCovenantSaveChildDTO {
    @JsonProperty("covenant_Code")
    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    private AppsConstants.CovenantApplicableType applicableType;

    public CustomerCovenantSaveChildDTO(CustomerCovenant customerCovenant) {
        this.covenant_Code = customerCovenant.getCovenant_Code();
        this.covenant_Description = customerCovenant.getCovenant_Description();
        this.covenant_Frequency = customerCovenant.getCovenant_Frequency();
        this.covenant_Due_Date = customerCovenant.getCovenant_Due_Date();
        this.disbursementType = customerCovenant.getDisbursementType();
        this.noFrequencyDueDate = customerCovenant.getNoFrequencyDueDate();
        this.applicableType = customerCovenant.getApplicableType();
    }
}
