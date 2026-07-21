package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.SecuritySummaryTopic;

import java.io.Serializable;

public class SecuritySummaryTopicDTO implements Serializable {

    private Integer securitySummaryTopicID;

    private String securityType;

    private String securityTypeGroup;

    private AppsConstants.Status status;

    public SecuritySummaryTopicDTO() {
    }

    public SecuritySummaryTopicDTO(SecuritySummaryTopic securitySummaryTopic) {
        this.securitySummaryTopicID = securitySummaryTopic.getSecuritySummaryTopicID();
        this.securityType = securitySummaryTopic.getSecurityType();
        this.securityTypeGroup = securitySummaryTopic.getSecurityTypeGroup();
        this.status = securitySummaryTopic.getStatus();
    }

    public Integer getSecuritySummaryTopicID() {
        return securitySummaryTopicID;
    }

    public void setSecuritySummaryTopicID(Integer securitySummaryTopicID) {
        this.securitySummaryTopicID = securitySummaryTopicID;
    }

    public String getSecurityType() {
        return securityType;
    }

    public void setSecurityType(String securityType) {
        this.securityType = securityType;
    }

    public String getSecurityTypeGroup() {
        return securityTypeGroup;
    }

    public void setSecurityTypeGroup(String securityTypeGroup) {
        this.securityTypeGroup = securityTypeGroup;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "SecuritySummaryTopicDTO{" +
                "securitySummaryTopicID=" + securitySummaryTopicID +
                ", securityType='" + securityType + '\'' +
                ", securityTypeGroup='" + securityTypeGroup + '\'' +
                ", status=" + status +
                '}';
    }
}
