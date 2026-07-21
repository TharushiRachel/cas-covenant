package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class LeadFacilityPaperStatusDetailRS implements Serializable {

    private Integer leadID;

    private Integer facilityPaperID;

    private String fpRefNumber;

    private String assignUserDisplayName;

    private String createdDateStr;

    private DomainConstants.FacilityPaperStatus currentFacilityPaperStatus;

    private List<LeadFacilityPaperStatusDetailDTO> leadFacilityPaperStatusDetailDTOS;

    public LeadFacilityPaperStatusDetailRS() {
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getFpRefNumber() {
        return fpRefNumber;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public DomainConstants.FacilityPaperStatus getCurrentFacilityPaperStatus() {
        return currentFacilityPaperStatus;
    }

    public void setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus currentFacilityPaperStatus) {
        this.currentFacilityPaperStatus = currentFacilityPaperStatus;
    }

    public List<LeadFacilityPaperStatusDetailDTO> getLeadFacilityPaperStatusDetailDTOS() {
        if (leadFacilityPaperStatusDetailDTOS == null) {
            this.leadFacilityPaperStatusDetailDTOS = new ArrayList<>();
        }
        return leadFacilityPaperStatusDetailDTOS;
    }

    public void setLeadFacilityPaperStatusDetailDTOS(List<LeadFacilityPaperStatusDetailDTO> leadFacilityPaperStatusDetailDTOS) {
        this.leadFacilityPaperStatusDetailDTOS = leadFacilityPaperStatusDetailDTOS;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    @Override
    public String toString() {
        return "LeadFacilityPaperStatusDetailRS{" +
                "leadID=" + leadID +
                ", facilityPaperID=" + facilityPaperID +
                ", fpRefNumber='" + fpRefNumber + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", currentFacilityPaperStatus=" + currentFacilityPaperStatus +
                ", leadFacilityPaperStatusDetailDTOS=" + leadFacilityPaperStatusDetailDTOS +
                '}';
    }
}
