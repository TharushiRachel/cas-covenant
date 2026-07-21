package com.itechro.cas.model.dto.facility;

import java.io.Serializable;

public class PurposeOfAdvancedDTO implements Serializable {

    private Integer purposeOfAdvanceID;

    private String referenceCode;

    private String referenceDescription;

    public Integer getPurposeOfAdvanceID() {
        return purposeOfAdvanceID;
    }

    public void setPurposeOfAdvanceID(Integer purposeOfAdvanceID) {
        this.purposeOfAdvanceID = purposeOfAdvanceID;
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
}
