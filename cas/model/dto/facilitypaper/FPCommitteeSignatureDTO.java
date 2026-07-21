package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPStatusHistory;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPCommitteeSignatureDTO implements Serializable {

    private Integer fpStatusHistoryID;
    private Integer facilityPaperID;
    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    private String assignUser;
    private String assignUserID;
    private String assignUserDisplayName;
    private String assignUserUpmGroupCode;
    private String assignUserDivCode;
    private String assignUserDivType;
    private String assignUserDesignation;
    private String assignUserFunctionalUnits;

    public FPCommitteeSignatureDTO() { }
    public Integer getFpStatusHistoryID() {
        return fpStatusHistoryID;
    }

    public void setFpStatusHistoryID(Integer fpStatusHistoryID) {
        this.fpStatusHistoryID = fpStatusHistoryID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(String assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }
    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
    }

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }


    public String getAssignUserDivType() {
        return assignUserDivType;
    }

    public void setAssignUserDivType(String assignUserDivType) {
        this.assignUserDivType = assignUserDivType;
    }

    public String getAssignUserDesignation() {
        return assignUserDesignation;
    }

    public void setAssignUserDesignation(String assignUserDesignation) {
        this.assignUserDesignation = assignUserDesignation;
    }

    public String getAssignUserFunctionalUnits() {
        return assignUserFunctionalUnits;
    }

    public void setAssignUserFunctionalUnits(String assignUserFunctionalUnits) {
        this.assignUserFunctionalUnits = assignUserFunctionalUnits;
    }

    @Override
    public String toString() {
        return "FPCommitteeSignatureDTO{" +
                "fpStatusHistoryID=" + fpStatusHistoryID +
                ", facilityPaperID=" + facilityPaperID +
                ", facilityPaperStatus=" + facilityPaperStatus +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserID='" + assignUserID + '\'' +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignUserDivCode='" + assignUserDivCode + '\'' +
                ", assignUserDivType='" + assignUserDivType + '\'' +
                ", assignUserDesignation='" + assignUserDesignation + '\'' +
                ", assignUserFunctionalUnits='" + assignUserFunctionalUnits + '\'' +
                '}';
    }
}
