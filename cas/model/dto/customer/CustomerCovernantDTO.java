package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import lombok.Data;

import java.util.Date;

@Data
public class CustomerCovernantDTO {

    private Integer customerCovenantId;

    private String RequestUUID;

    private String customerFinancialID;

    // private FacilityPaper facilityPaper;
    private String fpRefNumber;

    private String covenant_Code;

    private String covenant_Description;

    private String covenant_Frequency;

    private Date covenant_Due_Date;

    //private List<CustomerCovenantDetailsDTO> covenantDetails;

    private String createdBy;

    private Date createdDate;

    private String createdUserDisplayName;

    private AppsConstants.CovenantStatus status;

    private String workClass;


//    @Data
//    private static class FacilityPaper{
//        private String fpRefNumber;
//    }

    public CustomerCovernantDTO(CustomerCovenant customerCovenant){
        this.customerCovenantId = customerCovenant.getCustomerCovenantId();
        this.covenant_Code = customerCovenant.getCovenant_Code();
        this.covenant_Description = customerCovenant.getCovenant_Description();
        this.covenant_Frequency = customerCovenant.getCovenant_Frequency();
        this.covenant_Due_Date = customerCovenant.getCovenant_Due_Date();
        this.fpRefNumber = customerCovenant.getFacilityPaper().getFpRefNumber();
//        this.customerFinancialID = customerCovenant.getCustomer().getCustomerFinancialID();
        this.customerFinancialID = customerCovenant.getCustomerFinancialID();
        this.RequestUUID = customerCovenant.getRequestUUID();
        this.status = customerCovenant.getStatus();
        this.createdBy = customerCovenant.getCreatedBy();
        this.createdUserDisplayName = customerCovenant.getCreatedUserDisplayName();
        this.createdDate = customerCovenant.getCreatedDate();
    }

}
