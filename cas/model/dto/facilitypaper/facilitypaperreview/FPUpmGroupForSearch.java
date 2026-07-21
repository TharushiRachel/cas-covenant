package com.itechro.cas.model.dto.facilitypaper.facilitypaperreview;

public class FPUpmGroupForSearch {

    private Integer workFlowTemplateID;

    private String upmCode;

    private String referenceName;

    private Integer displayOrder;

    public FPUpmGroupForSearch() {
    }

    public FPUpmGroupForSearch(Integer workFlowTemplateID, String upmCode, Integer displayOrder, String referenceName) {
        this.workFlowTemplateID = workFlowTemplateID;
        this.upmCode = upmCode;
        this.displayOrder = displayOrder;
        this.referenceName = referenceName;
    }

    public FPUpmGroupForSearch(FPUpmGroupForSearch fpUpmGroupForSearch) {
        this.workFlowTemplateID = fpUpmGroupForSearch.getWorkFlowTemplateID();
        this.upmCode = fpUpmGroupForSearch.getUpmCode();
        this.displayOrder = fpUpmGroupForSearch.getDisplayOrder();
        this.referenceName = fpUpmGroupForSearch.getReferenceName();
    }

    public Integer getWorkFlowTemplateID() {
        return workFlowTemplateID;
    }

    public void setWorkFlowTemplateID(Integer workFlowTemplateID) {
        this.workFlowTemplateID = workFlowTemplateID;
    }

    public String getUpmCode() {
        return upmCode;
    }

    public void setUpmCode(String upmCode) {
        this.upmCode = upmCode;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }
}
