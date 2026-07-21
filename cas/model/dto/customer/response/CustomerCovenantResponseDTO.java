package com.itechro.cas.model.dto.customer.response;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerCovenantResponseDTO {
    private Integer customerCovenantId;

    private String RequestUUID;

    private String customerFinancialID;

    private String fpRefNumber;

    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    private String createdBy;

    private Date createdDate;

    private String createdUserDisplayName;

    private AppsConstants.CovenantStatus status;

    private String workClass;

    private AppsConstants.CovenantStatusOnDisbursement disbursementType;

    private String noFrequencyDueDate;

    private AppsConstants.YesNo isExists;

    private String complianceStatus;

    private Integer displayOrder;

    private AppsConstants.CovenantApplicableType applicableType;

    public Integer getDisplayOrder() {
        if (displayOrder == null){
            return 0;
        }
        return displayOrder;
    }
}
