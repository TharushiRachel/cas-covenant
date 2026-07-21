package com.itechro.cas.model.dto.covenants;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerCovenantSaveResponse {

    private String RequestUUID;

    private String custId;

    private String casReference;

    private List<CustomerCovenantSaveChildDTO> covenantDetails;

    private String createdUserDisplayName;

    private String createdBy;

    private Date createdDate;

    private Integer facilityPaperID;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    private AppsConstants.CovenantApplicableType applicableType;

    public CustomerCovenantSaveResponse(CustomerCovenant customerCovenant) {
        this.RequestUUID = customerCovenant.getRequestUUID();
        this.custId = customerCovenant.getCustomerFinancialID();
        this.casReference = customerCovenant.getFacilityPaper().getFpRefNumber();
        this.createdBy = customerCovenant.getCreatedBy();
        this.createdDate = customerCovenant.getCreatedDate();
        this.createdUserDisplayName = customerCovenant.getCreatedUserDisplayName();
        this.disbursementType = customerCovenant.getDisbursementType();
        this.noFrequencyDueDate = customerCovenant.getNoFrequencyDueDate();
        this.covenant_Code = customerCovenant.getCovenant_Code();
        this.covenant_Description = customerCovenant.getCovenant_Description();
        this.covenant_Frequency = customerCovenant.getCovenant_Frequency();
        this.covenant_Due_Date = customerCovenant.getCovenant_Due_Date();
        this.applicableType = customerCovenant.getApplicableType();
    }

}
