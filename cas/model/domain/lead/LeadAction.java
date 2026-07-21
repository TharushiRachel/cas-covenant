package com.itechro.cas.model.domain.lead;

import com.itechro.cas.commons.constants.DomainConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_LEAD_ACTION")
public class LeadAction implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_LEAD_ACTION")
    @SequenceGenerator(name = "SEQ_T_LEAD_ACTION", sequenceName = "SEQ_T_LEAD_ACTION", allocationSize = 1)
    @Column(name = "LEAD_ACTION_ID")
    private Integer leadActionID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LEAD_ID")
    private Lead lead;

    @Column(name = "ASSIGN_USER_ID")
    private String assignUserID;

    @Column(name = "REMARK")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "FROM_LEAD_STATUS")
    private DomainConstants.LeadStatus fromLeadStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "TO_LEAD_STATUS")
    private DomainConstants.LeadStatus toLeadStatus;

    @Column(name = "ACTIONED_BY")
    private String actionedBy;

    @Column(name = "ACTIONED_BY_DISPLAY_NAME")
    private String actionedByDisplayName;

    @Column(name = "ACTION")
    private String action;

    @Column(name = "ACTIONED_TIMESTAMP")
    private Date actionedTimestamp;

    public Integer getLeadActionID() {
        return leadActionID;
    }

    public void setLeadActionID(Integer leadActionID) {
        this.leadActionID = leadActionID;
    }

    public Lead getLead() {
        return lead;
    }

    public void setLead(Lead lead) {
        this.lead = lead;
    }

    public String getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(String assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DomainConstants.LeadStatus getFromLeadStatus() {
        return fromLeadStatus;
    }

    public void setFromLeadStatus(DomainConstants.LeadStatus fromLeadStatus) {
        this.fromLeadStatus = fromLeadStatus;
    }

    public DomainConstants.LeadStatus getToLeadStatus() {
        return toLeadStatus;
    }

    public void setToLeadStatus(DomainConstants.LeadStatus toLeadStatus) {
        this.toLeadStatus = toLeadStatus;
    }

    public String getActionedBy() {
        return actionedBy;
    }

    public void setActionedBy(String actionedBy) {
        this.actionedBy = actionedBy;
    }

    public Date getActionedTimestamp() {
        return actionedTimestamp;
    }

    public void setActionedTimestamp(Date actionedTimestamp) {
        this.actionedTimestamp = actionedTimestamp;
    }

    public String getActionedByDisplayName() {
        return actionedByDisplayName;
    }

    public void setActionedByDisplayName(String actionedByDisplayName) {
        this.actionedByDisplayName = actionedByDisplayName;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
