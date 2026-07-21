package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.lead.LeadAction;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Date;

public class LeadActionDTO implements Serializable {

    private static final long serialVersionUID = 2841844449871675383L;

    private Integer leadActionID;

    private Integer leadID;

    private String assignUserID;

    private String remark;

    private DomainConstants.LeadStatus fromLeadStatus;

    private DomainConstants.LeadStatus toLeadStatus;

    private String actionedBy;

    private String actionedByDisplayName;

    private String action;

    private String actionedTimestampStr;

    private Date actionedTimestamp;

    public LeadActionDTO() {
    }

    public LeadActionDTO(LeadAction leadAction) {
        this.leadActionID = leadAction.getLeadActionID();
        this.leadID = leadAction.getLead().getLeadID();
        this.assignUserID = leadAction.getAssignUserID();
        this.remark = leadAction.getRemark();
        this.fromLeadStatus = leadAction.getFromLeadStatus();
        this.toLeadStatus = leadAction.getToLeadStatus();
        this.actionedBy = leadAction.getActionedBy();
        this.actionedByDisplayName = leadAction.getActionedByDisplayName();
        this.action = leadAction.getAction();
        this.actionedTimestampStr = CalendarUtil.getDefaultFormattedDateTime(leadAction.getActionedTimestamp());
        this.actionedTimestamp = leadAction.getActionedTimestamp();
    }

    public Integer getLeadActionID() {
        return leadActionID;
    }

    public void setLeadActionID(Integer leadActionID) {
        this.leadActionID = leadActionID;
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
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

    public String getActionedTimestampStr() {
        return actionedTimestampStr;
    }

    public void setActionedTimestampStr(String actionedTimestampStr) {
        this.actionedTimestampStr = actionedTimestampStr;
    }

    public Date getActionedTimestamp() {
        return actionedTimestamp;
    }

    public void setActionedTimestamp(Date actionedTimestamp) {
        this.actionedTimestamp = actionedTimestamp;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
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

    @Override
    public String toString() {
        return "LeadActionDTO{" +
                "leadActionID=" + leadActionID +
                ", leadID=" + leadID +
                ", assignUserID='" + assignUserID + '\'' +
                ", remark='" + remark + '\'' +
                ", fromLeadStatus=" + fromLeadStatus +
                ", toLeadStatus=" + toLeadStatus +
                ", actionedBy='" + actionedBy + '\'' +
                ", actionedByDisplayName='" + actionedByDisplayName + '\'' +
                ", action='" + action + '\'' +
                ", actionedTimestampStr='" + actionedTimestampStr + '\'' +
                ", actionedTimestamp=" + actionedTimestamp +
                '}';
    }
}
