package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;

import java.io.Serializable;

public class CalculateExposureRQ implements Serializable {

    private Integer facilityPaperID;

    private AppsConstants.YesNo isCommittee;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public AppsConstants.YesNo getIsCooperate() {
        return isCommittee;
    }

    public void setIsCooperate(AppsConstants.YesNo isCooperate) {
        this.isCommittee = isCooperate;
    }

    @Override
    public String toString() {
        return "CalculateExposureRQ{" +
                "facilityPaperID=" + facilityPaperID +
                ", isCommittee=" + isCommittee +
                '}';
    }
}
