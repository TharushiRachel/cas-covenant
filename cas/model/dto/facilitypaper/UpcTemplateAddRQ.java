package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.List;

public class UpcTemplateAddRQ implements Serializable {

    private Integer facilityPaperID;

    private Integer applicationFormID;

    private Integer upcTemplateID;

    private String updatedUserDisplayName;

    private Integer copyFromFacilityPaperID;

    private List<FPUpcSectionDataDTO> addedUpcSectionDTOs;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }

    public List<FPUpcSectionDataDTO> getAddedUpcSectionDTOs() {
        return addedUpcSectionDTOs;
    }

    public void setAddedUpcSectionDTOs(List<FPUpcSectionDataDTO> addedUpcSectionDTOs) {
        this.addedUpcSectionDTOs = addedUpcSectionDTOs;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getUpdatedUserDisplayName() {
        return updatedUserDisplayName;
    }

    public void setUpdatedUserDisplayName(String updatedUserDisplayName) {
        this.updatedUserDisplayName = updatedUserDisplayName;
    }

    public Integer getCopyFromFacilityPaperID() {
        return copyFromFacilityPaperID;
    }

    public void setCopyFromFacilityPaperID(Integer copyFromFacilityPaperID) {
        this.copyFromFacilityPaperID = copyFromFacilityPaperID;
    }

    @Override
    public String toString() {
        return "UpcTemplateAddRQ{" +
                "facilityPaperID=" + facilityPaperID +
                ", applicationFormID=" + applicationFormID +
                ", upcTemplateID=" + upcTemplateID +
                ", updatedUserDisplayName='" + updatedUserDisplayName + '\'' +
                ", copyFromFacilityPaperID=" + copyFromFacilityPaperID +
                ", addedUpcSectionDTOs=" + addedUpcSectionDTOs +
                '}';
    }
}
