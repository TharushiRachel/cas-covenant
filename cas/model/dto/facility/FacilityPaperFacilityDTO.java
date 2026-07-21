package com.itechro.cas.model.dto.facility;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class FacilityPaperFacilityDTO implements Serializable {

    private static final long serialVersionUID = -460925862757299791L;

    private Integer facilityPaperID;

    private List<FacilityDTO> facilityDTOS;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public List<FacilityDTO> getFacilityDTOS() {
        if(facilityDTOS == null) {
            this.facilityDTOS = new ArrayList<>();
        }
        return facilityDTOS;
    }

    public void setFacilityDTOS(List<FacilityDTO> facilityDTOS) {
        this.facilityDTOS = facilityDTOS;
    }

    @Override
    public String toString() {
        return "FacilityPaperFacilityDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", facilityDTOS=" + facilityDTOS +
                '}';
    }
}
