package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPShareHolderDetail;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;

import javax.persistence.*;
import java.io.Serializable;

public class FPShareHolderDetailDTO implements Serializable {
    private Integer fpShareHolderDetailID;

    private Integer facilityPaperID;

    private String shareHolderName;

    private Double shareHolding;

    private AppsConstants.Status status;

    public FPShareHolderDetailDTO() {
    }

    public FPShareHolderDetailDTO(FPShareHolderDetail fpShareHolderDetail) {
        this.fpShareHolderDetailID = fpShareHolderDetail.getFpShareHolderDetailID();
        this.facilityPaperID = fpShareHolderDetail.getFacilityPaper().getFacilityPaperID();
        this.shareHolderName = fpShareHolderDetail.getShareHolderName();
        this.shareHolding = fpShareHolderDetail.getShareHolding();
        this.status = fpShareHolderDetail.getStatus();
    }

    public Integer getFpShareHolderDetailID() {
        return fpShareHolderDetailID;
    }

    public void setFpShareHolderDetailID(Integer fpShareHolderDetailID) {
        this.fpShareHolderDetailID = fpShareHolderDetailID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getShareHolderName() {
        return shareHolderName;
    }

    public void setShareHolderName(String shareHolderName) {
        this.shareHolderName = shareHolderName;
    }

    public Double getShareHolding() {
        return shareHolding;
    }

    public void setShareHolding(Double shareHolding) {
        this.shareHolding = shareHolding;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "FPShareHolderDetailDTO{" +
                "fpShareHolderDetailID=" + fpShareHolderDetailID +
                ", facilityPaperID=" + facilityPaperID +
                ", shareHolderName='" + shareHolderName + '\'' +
                ", shareHolding=" + shareHolding +
                ", status=" + status +
                '}';
    }
}
