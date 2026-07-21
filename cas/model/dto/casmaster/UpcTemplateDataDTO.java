package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.domain.casmaster.UpcTemplateData;

import java.io.Serializable;

public class UpcTemplateDataDTO implements Serializable {

    private Integer upcTemplateDataID;

    private Integer upcTemplateID;

    private Integer upcSectionID;

    private Integer parentSectionID;

    private Integer sectionLevel;

    private Integer displayOrder;

    private boolean removed;

    public UpcTemplateDataDTO(){}

    public UpcTemplateDataDTO(UpcTemplateData upcTemplateData){

        this.upcTemplateDataID = upcTemplateData.getUpcTemplateDataID();
        this.upcTemplateID = upcTemplateData.getUpcTemplate().getUpcTemplateID();
        this.upcSectionID = upcTemplateData.getUpcSection().getUpcSectionID();
        this.displayOrder = upcTemplateData.getDisplayOrder();
        this.parentSectionID = upcTemplateData.getParentSectionID();
        this.sectionLevel = upcTemplateData.getSectionLevel();

    }

    public Integer getUpcTemplateDataID() {
        return upcTemplateDataID;
    }

    public void setUpcTemplateDataID(Integer upcTemplateDataID) {
        this.upcTemplateDataID = upcTemplateDataID;
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
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

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
