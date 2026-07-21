package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

public class FPPreviousUPCTemplateRQ extends PagedParamDTO {

    private Integer customerID;

    private String fpRefNumber;

    private String templateName;

    private String createdBranchCode;

    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    private String createdBranch;

    private String bankAccountNumber;

    private String assignUserDisplayName;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public String getCreatedBranch() {
        return createdBranch;
    }

    public void setCreatedBranch(String createdBranch) {
        this.createdBranch = createdBranch;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getCreatedBranchCode() {
        return createdBranchCode;
    }

    public void setCreatedBranchCode(String createdBranchCode) {
        this.createdBranchCode = createdBranchCode;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    @Override
    public String toString() {
        return "FPPreviousUPCTemplateRQ{" +
                "customerID=" + customerID +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", templateName='" + templateName + '\'' +
                ", createdBranchCode='" + createdBranchCode + '\'' +
                ", facilityPaperStatus=" + facilityPaperStatus +
                ", createdBranch='" + createdBranch + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                '}';
    }
}
