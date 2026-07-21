package com.itechro.cas.model.dto.covenants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
/**
 *
 *
 * @author tharushi
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerCovenantSaveDTO implements Serializable {

    //private Integer customerCovenantId;
    @JsonProperty("RequestUUID")
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

    private AppsConstants.CovenantApplicableType applicableType;

    public CustomerCovenantSaveDTO(CustomerCovenant customerCovenant) {
        this.RequestUUID = customerCovenant.getRequestUUID();
        this.custId = customerCovenant.getCustomerFinancialID();
        this.casReference = customerCovenant.getFacilityPaper().getFpRefNumber();
        this.createdBy = customerCovenant.getCreatedBy();
        this.createdDate = customerCovenant.getCreatedDate();
        this.createdUserDisplayName = customerCovenant.getCreatedUserDisplayName();
        this.disbursementType = customerCovenant.getDisbursementType();
        this.noFrequencyDueDate = customerCovenant.getNoFrequencyDueDate();
        this.applicableType = customerCovenant.getApplicableType();

        List<CustomerCovenantSaveChildDTO> detailsList = new ArrayList<>();
        detailsList.add(new CustomerCovenantSaveChildDTO(customerCovenant));
    }
}
