package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.domain.casmaster.WorkFlowTemplateData;

import java.io.Serializable;

public class WorkFlowTemplateDataDTO implements Serializable {

    private Integer workFlowTemplateDataID;

    private Integer workFlowTemplateID;

    private Integer upmGroupID;

    private UpmGroupDTO upmGroupDTO;

    private UpmGroupDTO nextUPMGroupDTO;

    private Integer nextUPMGroupID;

    private UpmGroupDTO previousUPMGroupDTO;

    private Integer previousUPMGroupID;

    private Integer displayOrder;

    private boolean removed;

    public WorkFlowTemplateDataDTO() {
    }

    public WorkFlowTemplateDataDTO(WorkFlowTemplateData workFlowTemplateData) {

        this.workFlowTemplateDataID = workFlowTemplateData.getWorkFlowTemplateDataID();
        this.workFlowTemplateID = workFlowTemplateData.getWorkFlowTemplate().getWorkFlowTemplateID();
        if (workFlowTemplateData.getUpmGroup() != null) {
            this.upmGroupDTO = new UpmGroupDTO(workFlowTemplateData.getUpmGroup());
            this.upmGroupID = workFlowTemplateData.getUpmGroup().getUpmGroupID();
        }
        if (workFlowTemplateData.getNextUPMGroup() != null) {
            this.nextUPMGroupDTO = new UpmGroupDTO(workFlowTemplateData.getNextUPMGroup());
            this.nextUPMGroupID = workFlowTemplateData.getNextUPMGroup().getUpmGroupID();
        }
        if (workFlowTemplateData.getPreviousUPMGroup() != null) {
            this.previousUPMGroupDTO = new UpmGroupDTO(workFlowTemplateData.getPreviousUPMGroup());
            this.previousUPMGroupID = workFlowTemplateData.getPreviousUPMGroup().getUpmGroupID();
        }
        this.displayOrder = workFlowTemplateData.getDisplayOrder();

    }

    public Integer getWorkFlowTemplateDataID() {
        return workFlowTemplateDataID;
    }

    public void setWorkFlowTemplateDataID(Integer workFlowTemplateDataID) {
        this.workFlowTemplateDataID = workFlowTemplateDataID;
    }

    public Integer getWorkFlowTemplateID() {
        return workFlowTemplateID;
    }

    public void setWorkFlowTemplateID(Integer workFlowTemplateID) {
        this.workFlowTemplateID = workFlowTemplateID;
    }

    public UpmGroupDTO getUpmGroupDTO() {
        return upmGroupDTO;
    }

    public void setUpmGroupDTO(UpmGroupDTO upmGroupDTO) {
        this.upmGroupDTO = upmGroupDTO;
    }

    public UpmGroupDTO getNextUPMGroupDTO() {
        return nextUPMGroupDTO;
    }

    public void setNextUPMGroupDTO(UpmGroupDTO nextUPMGroupDTO) {
        this.nextUPMGroupDTO = nextUPMGroupDTO;
    }

    public UpmGroupDTO getPreviousUPMGroupDTO() {
        return previousUPMGroupDTO;
    }

    public void setPreviousUPMGroupDTO(UpmGroupDTO previousUPMGroupDTO) {
        this.previousUPMGroupDTO = previousUPMGroupDTO;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public Integer getUpmGroupID() {
        return upmGroupID;
    }

    public void setUpmGroupID(Integer upmGroupID) {
        this.upmGroupID = upmGroupID;
    }

    public Integer getNextUPMGroupID() {
        return nextUPMGroupID;
    }

    public void setNextUPMGroupID(Integer nextUPMGroupID) {
        this.nextUPMGroupID = nextUPMGroupID;
    }

    public Integer getPreviousUPMGroupID() {
        return previousUPMGroupID;
    }

    public void setPreviousUPMGroupID(Integer previousUPMGroupID) {
        this.previousUPMGroupID = previousUPMGroupID;
    }

    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }
}
