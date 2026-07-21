package com.itechro.cas.model.dto.applicationform;

import java.io.Serializable;
import java.util.List;

public class ApplicationFormFacilitiesDTO implements Serializable {

    private Integer applicationFormID;

    private List<AFFacilityDTO> facilityDTOS;

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public List<AFFacilityDTO> getFacilityDTOS() {
        return facilityDTOS;
    }

    public void setFacilityDTOS(List<AFFacilityDTO> facilityDTOS) {
        this.facilityDTOS = facilityDTOS;
    }

    @Override
    public String toString() {
        return "ApplicationFormFacilitiesDTO{" +
                "applicationFormID=" + applicationFormID +
                ", facilityDTOS=" + facilityDTOS +
                '}';
    }
}
