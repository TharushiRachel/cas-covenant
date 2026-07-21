package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.UpcTemplateData;
import com.itechro.cas.model.domain.facilitypaper.FPUpcSectionData;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Objects;

public class FPUpcSectionDataDTO implements Serializable {

    private Integer fpUpcSectionDataID;

    private Integer facilityPaperID;

    private Integer upcSectionID;

    private String upcSectionName;

    private Integer parentSectionID;

    private Integer sectionLevel;

    private Integer displayOrder;

    private String modifiedBy;

    private String modifiedDateStr;

    private String data;

    private String comment;

    private String modifiedUserDisplayName;

    private AppsConstants.Status status;

    public FPUpcSectionDataDTO() {
    }

    public FPUpcSectionDataDTO(FPUpcSectionData fpUpcSectionData) {
        this.fpUpcSectionDataID = fpUpcSectionData.getFpUpcSectionDataID();
        this.facilityPaperID = fpUpcSectionData.getFacilityPaper().getFacilityPaperID();
        this.upcSectionID = fpUpcSectionData.getUpcSection().getUpcSectionID();
        this.upcSectionName = fpUpcSectionData.getUpcSection().getUpcSectionName();
        this.parentSectionID = fpUpcSectionData.getParentSectionID();
        this.sectionLevel = fpUpcSectionData.getSectionLevel();
        this.displayOrder = fpUpcSectionData.getDisplayOrder();
        this.data = fpUpcSectionData.getData();
        this.status = fpUpcSectionData.getStatus();
        this.modifiedBy = fpUpcSectionData.getModifiedBy();
        this.modifiedUserDisplayName = fpUpcSectionData.getModifiedUserDisplayName();
        this.comment = fpUpcSectionData.getComment();
        if (fpUpcSectionData.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(fpUpcSectionData.getModifiedDate());
        }
    }
    public FPUpcSectionDataDTO(UpcTemplateData upcTemplateData, Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
        this.upcSectionID = upcTemplateData.getUpcSection().getUpcSectionID();
        this.upcSectionName = upcTemplateData.getUpcSection().getUpcSectionName();
        this.parentSectionID = upcTemplateData.getParentSectionID();
        this.sectionLevel = upcTemplateData.getSectionLevel();
        this.displayOrder = upcTemplateData.getDisplayOrder();
        this.status = AppsConstants.Status.ACT;
    }

    public Integer getFpUpcSectionDataID() {
        return fpUpcSectionDataID;
    }

    public void setFpUpcSectionDataID(Integer fpUpcSectionDataID) {
        this.fpUpcSectionDataID = fpUpcSectionDataID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getUpcSectionID() {
        return upcSectionID;
    }

    public void setUpcSectionID(Integer upcSectionID) {
        this.upcSectionID = upcSectionID;
    }

    public Integer getParentSectionID() {
        return parentSectionID;
    }

    public void setParentSectionID(Integer parentSectionID) {
        this.parentSectionID = parentSectionID;
    }

    public Integer getSectionLevel() {
        return sectionLevel;
    }

    public void setSectionLevel(Integer sectionLevel) {
        this.sectionLevel = sectionLevel;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getData() {
        if (this.data == null){
            this.data = "";
        }
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getUpcSectionName() {
        return upcSectionName;
    }

    public void setUpcSectionName(String upcSectionName) {
        this.upcSectionName = upcSectionName;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedUserDisplayName() {
        return modifiedUserDisplayName;
    }

    public void setModifiedUserDisplayName(String modifiedUserDisplayName) {
        this.modifiedUserDisplayName = modifiedUserDisplayName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FPUpcSectionDataDTO)) return false;
        FPUpcSectionDataDTO that = (FPUpcSectionDataDTO) o;
        return getUpcSectionID().equals(that.getUpcSectionID()) &&
                Objects.equals(getParentSectionID(), that.getParentSectionID()) &&
                Objects.equals(getSectionLevel(), that.getSectionLevel()) &&
                getDisplayOrder().equals(that.getDisplayOrder());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUpcSectionID(), getParentSectionID(), getSectionLevel(), getDisplayOrder());
    }

    @Override
    public String toString() {
        return "FPUpcSectionDataDTO{" +
                "fpUpcSectionDataID=" + fpUpcSectionDataID +
                ", facilityPaperID=" + facilityPaperID +
                ", upcSectionID=" + upcSectionID +
                ", upcSectionName='" + upcSectionName + '\'' +
                ", parentSectionID=" + parentSectionID +
                ", sectionLevel=" + sectionLevel +
                ", displayOrder=" + displayOrder +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedUserDisplayName='" + modifiedUserDisplayName + '\'' +
                ", status=" + status +
                '}';
    }

    public boolean isSameDataContent(FPUpcSectionData fpUpcSectionData) {
        if (fpUpcSectionData.getData() != null && !fpUpcSectionData.getData().isEmpty()) {
            return this.getData().equals(fpUpcSectionData.getData());
        } else {
            return true;
        }
    }
}
