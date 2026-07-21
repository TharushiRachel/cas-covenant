package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CftSupportingDoc;

import java.io.Serializable;

public class CftSupportingDocDTO implements Serializable {

    private Integer cftSupportingDocID;

    private Integer creditFacilityTemplateID;

    private Integer supportingDocID;

    private String documentName;

    private AppsConstants.YesNo mandatory;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private AppsConstants.Status status;

    public CftSupportingDocDTO(){}

    public CftSupportingDocDTO(CftSupportingDoc cftSupportingDoc){

        this.cftSupportingDocID = cftSupportingDoc.getCftSupportingDocID();
        this.creditFacilityTemplateID = cftSupportingDoc.getCreditFacilityTemplate().getCreditFacilityTemplateID();
        this.supportingDocID = cftSupportingDoc.getSupportingDoc().getSupportingDocID();
        this.documentName = cftSupportingDoc.getSupportingDoc().getDocumentName();
        this.mandatory = cftSupportingDoc.getMandatory();
        this.approveStatus = cftSupportingDoc.getApproveStatus();
        this.status = cftSupportingDoc.getStatus();
    }

    public Integer getCftSupportingDocID() {
        return cftSupportingDocID;
    }

    public void setCftSupportingDocID(Integer cftSupportingDocID) {
        this.cftSupportingDocID = cftSupportingDocID;
    }

    public Integer getCreditFacilityTemplateID() {
        return creditFacilityTemplateID;
    }

    public void setCreditFacilityTemplateID(Integer creditFacilityTemplateID) {
        this.creditFacilityTemplateID = creditFacilityTemplateID;
    }

    public Integer getSupportingDocID() {
        return supportingDocID;
    }

    public void setSupportingDocID(Integer supportingDocID) {
        this.supportingDocID = supportingDocID;
    }

    public AppsConstants.YesNo getMandatory() {
        return mandatory;
    }

    public void setMandatory(AppsConstants.YesNo mandatory) {
        this.mandatory = mandatory;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }
}
