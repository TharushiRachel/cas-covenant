package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_WORK_FLOW_TEMPLATE_DATA")
public class WorkFlowTemplateData {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WORK_FLOW_TEMPLATE_DATA")
    @SequenceGenerator(name = "SEQ_T_WORK_FLOW_TEMPLATE_DATA", sequenceName = "SEQ_T_WORK_FLOW_TEMPLATE_DATA", allocationSize = 1)
    @Column(name = "WORK_FLOW_TEMPLATE_DATA_ID")
    private Integer workFlowTemplateDataID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "WORK_FLOW_TEMPLATE_ID")
    private WorkFlowTemplate workFlowTemplate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "UPM_GROUP_ID")
    private UpmGroup upmGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NEXT_UPM_GROUP_ID")
    private UpmGroup nextUPMGroup;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PREVIOUS_UPM_GROUP_ID")
    private UpmGroup previousUPMGroup;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

    public Integer getWorkFlowTemplateDataID() {
        return workFlowTemplateDataID;
    }

    public void setWorkFlowTemplateDataID(Integer workFlowTemplateDataID) {
        this.workFlowTemplateDataID = workFlowTemplateDataID;
    }

    public WorkFlowTemplate getWorkFlowTemplate() {
        return workFlowTemplate;
    }

    public void setWorkFlowTemplate(WorkFlowTemplate workFlowTemplate) {
        this.workFlowTemplate = workFlowTemplate;
    }

    public UpmGroup getUpmGroup() {
        return upmGroup;
    }

    public void setUpmGroup(UpmGroup upmGroup) {
        this.upmGroup = upmGroup;
    }

    public UpmGroup getNextUPMGroup() {
        return nextUPMGroup;
    }

    public void setNextUPMGroup(UpmGroup nextUPMGroup) {
        this.nextUPMGroup = nextUPMGroup;
    }

    public UpmGroup getPreviousUPMGroup() {
        return previousUPMGroup;
    }

    public void setPreviousUPMGroup(UpmGroup previousUPMGroup) {
        this.previousUPMGroup = previousUPMGroup;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }
}
