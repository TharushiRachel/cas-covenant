package com.itechro.cas.model.dto.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPurposeOfAdvance;

import java.io.Serializable;

public class FacilityPurposeOfAdvanceDTO implements Serializable {

    private Integer facilityPurposeOfAdvanceID;

    private Integer facilityID;

    private String referenceCode;

    private String referenceDescription;

    private String purposeOfAdvance;

    private AppsConstants.Status status;

    public FacilityPurposeOfAdvanceDTO(){}

    public FacilityPurposeOfAdvanceDTO(FacilityPurposeOfAdvance facilityPurposeOfAdvance){

        this.facilityPurposeOfAdvanceID = facilityPurposeOfAdvance.getFacilityPurposeOfAdvanceID();
        if(facilityPurposeOfAdvance.getFacility() != null) {
            this.facilityID = facilityPurposeOfAdvance.getFacility().getFacilityID();
        }
        this.referenceCode = facilityPurposeOfAdvance.getReferenceCode();
        this.referenceDescription = facilityPurposeOfAdvance.getReferenceDescription();
        this.purposeOfAdvance = facilityPurposeOfAdvance.getPurposeOfAdvance();
    }

    public Integer getFacilityPurposeOfAdvanceID() {
        return facilityPurposeOfAdvanceID;
    }

    public void setFacilityPurposeOfAdvanceID(Integer facilityPurposeOfAdvanceID) {
        this.facilityPurposeOfAdvanceID = facilityPurposeOfAdvanceID;
    }

    public Integer getFacilityID() {
        return facilityID;
    }

    public void setFacilityID(Integer facilityID) {
        this.facilityID = facilityID;
    }

    public String getPurposeOfAdvance() {
        return purposeOfAdvance;
    }

    public void setPurposeOfAdvance(String purposeOfAdvance) {
        this.purposeOfAdvance = purposeOfAdvance;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }

    public String getReferenceDescription() {
        return referenceDescription;
    }

    public void setReferenceDescription(String referenceDescription) {
        this.referenceDescription = referenceDescription;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
