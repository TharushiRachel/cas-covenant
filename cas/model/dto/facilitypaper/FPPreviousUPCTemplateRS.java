package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class FPPreviousUPCTemplateRS implements Serializable {

    private Integer copyFromFacilityPaperID;

    private Integer upcTemplateID;

    private String fpRefNumber;

    private String templateName;

    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    private String createdBranchCode;

    private String bankAccountNumber;

    private String lastActionDateStr;

    private String assignUserDisplayName;

    private String assignDepartmentCode;

    public Integer getCopyFromFacilityPaperID() {
        return copyFromFacilityPaperID;
    }

    public void setCopyFromFacilityPaperID(Integer copyFromFacilityPaperID) {
        this.copyFromFacilityPaperID = copyFromFacilityPaperID;
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
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

    public String getCreatedBranchCode() {
        return createdBranchCode;
    }

    public void setCreatedBranchCode(String createdBranchCode) {
        this.createdBranchCode = createdBranchCode;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getLastActionDateStr() {
        return lastActionDateStr;
    }

    public void setLastActionDateStr(String lastActionDateStr) {
        this.lastActionDateStr = lastActionDateStr;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public String getAssignDepartmentCode() {
        return assignDepartmentCode;
    }

    public void setAssignDepartmentCode(String assignDepartmentCode) {
        this.assignDepartmentCode = assignDepartmentCode;
    }

    @Override
    public String toString() {
        return "FPPreviousUPCTemplateRS{" +
                "copyFromFacilityPaperID=" + copyFromFacilityPaperID +
                ", upcTemplateID=" + upcTemplateID +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", templateName='" + templateName + '\'' +
                ", facilityPaperStatus=" + facilityPaperStatus +
                ", createdBranchCode='" + createdBranchCode + '\'' +
                ", bankAccountNumber='" + bankAccountNumber + '\'' +
                ", lastActionDateStr='" + lastActionDateStr + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignDepartmentCode='" + assignDepartmentCode + '\'' +
                '}';
    }
}
