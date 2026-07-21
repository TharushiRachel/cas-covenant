package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPCompanyRoa;

import java.io.Serializable;

public class FPCompanyRoaDTO implements Serializable {

    private Integer fpCompanyRoaID;

    private Integer facilityPaperID;

    private String description;

    private String comment;

    private AppsConstants.Status status;

    public FPCompanyRoaDTO() {
    }

    public FPCompanyRoaDTO(FPCompanyRoa fpCompanyRoa) {
        this.fpCompanyRoaID = fpCompanyRoa.getFpCompanyRoaID();
        this.facilityPaperID = fpCompanyRoa.getFacilityPaper().getFacilityPaperID();
        this.description = fpCompanyRoa.getDescription();
        this.comment = fpCompanyRoa.getComment();
        this.status = fpCompanyRoa.getStatus();
    }

    public Integer getFpCompanyRoaID() {
        return fpCompanyRoaID;
    }

    public void setFpCompanyRoaID(Integer fpCompanyRoaID) {
        this.fpCompanyRoaID = fpCompanyRoaID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FPCompanyRoaDTO{" +
                "fpCompanyRoaID=" + fpCompanyRoaID +
                ", facilityPaperID=" + facilityPaperID +
                ", description='" + description + '\'' +
                ", comment='" + comment + '\'' +
                ", status=" + status +
                '}';
    }
}
