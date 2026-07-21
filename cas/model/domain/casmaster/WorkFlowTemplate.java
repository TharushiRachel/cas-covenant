package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
        @Table(name = "T_WORK_FLOW_TEMPLATE")
public class WorkFlowTemplate extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WORK_FLOW_TEMPLATE")
    @SequenceGenerator(name = "SEQ_T_WORK_FLOW_TEMPLATE", sequenceName = "SEQ_T_WORK_FLOW_TEMPLATE", allocationSize = 1)
    @Column(name = "WORK_FLOW_TEMPLATE_ID")
    private Integer workFlowTemplateID;

    @Column(name = "WORK_FLOW_TEMPLATE_NAME")
    private String workFlowTemplateName;

    @Column(name = "CODE")
    private String code;

    @Column(name = "DESCRIPTION")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "workFlowTemplate")
    private Set<WorkFlowTemplateData> workFlowTemplateDataSet;

    public Integer getWorkFlowTemplateID() {
        return workFlowTemplateID;
    }

    public void setWorkFlowTemplateID(Integer workFlowTemplateID) {
        this.workFlowTemplateID = workFlowTemplateID;
    }

    public String getWorkFlowTemplateName() {
        return workFlowTemplateName;
    }

    public void setWorkFlowTemplateName(String workFlowTemplateName) {
        this.workFlowTemplateName = workFlowTemplateName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Set<WorkFlowTemplateData> getWorkFlowTemplateDataSet() {
        if (workFlowTemplateDataSet == null) {
            workFlowTemplateDataSet = new HashSet<>();
        }
        return workFlowTemplateDataSet;
    }

    public void setWorkFlowTemplateDataSet(Set<WorkFlowTemplateData> workFlowTemplateDataSet) {
        this.workFlowTemplateDataSet = workFlowTemplateDataSet;
    }


    public WorkFlowTemplateData getWorkFlowTemplateDataByID(Integer workFlowTemplateID) {
        return this.getWorkFlowTemplateDataSet().stream().
                filter(workFlowTemplateData  -> {
                    return workFlowTemplateID.equals(workFlowTemplateData.getWorkFlowTemplateDataID());
                }).findFirst().orElse(null);
    }


    public void addWorkFlowTemplateData(WorkFlowTemplateData templateData) {
        templateData.setWorkFlowTemplate(this);
        this.getWorkFlowTemplateDataSet().add(templateData);
    }
}
