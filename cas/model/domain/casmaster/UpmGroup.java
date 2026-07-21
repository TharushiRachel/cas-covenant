package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.ApprovableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_UPM_GROUP")
public class UpmGroup extends ApprovableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_UPM_GROUP")
    @SequenceGenerator(name = "SEQ_T_UPM_GROUP", sequenceName = "SEQ_T_UPM_GROUP", allocationSize = 1)
    @Column(name = "UPM_GROUP_ID")
    private Integer upmGroupID;

    @Column(name = "GROUP_CODE")
    private String groupCode;

    @Column(name = "REFERENCE_NAME")
    private String referenceName;

    @Column(name = "WORK_FLOW_LEVEL")
    private Integer workFlowLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getUpmGroupID() {
        return upmGroupID;
    }

    public void setUpmGroupID(Integer upmGroupID) {
        this.upmGroupID = upmGroupID;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getReferenceName() {
        return referenceName;
    }

    public void setReferenceName(String referenceName) {
        this.referenceName = referenceName;
    }

    public Integer getWorkFlowLevel() {
        return workFlowLevel;
    }

    public void setWorkFlowLevel(Integer workFlowLevel) {
        this.workFlowLevel = workFlowLevel;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
