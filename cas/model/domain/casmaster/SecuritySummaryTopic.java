package com.itechro.cas.model.domain.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_SECURITY_SUMMARY_TOPIC")
public class SecuritySummaryTopic extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_SECURITY_SUMMARY_TOPIC")
    @SequenceGenerator(name = "SEQ_T_SECURITY_SUMMARY_TOPIC", sequenceName = "SEQ_T_SECURITY_SUMMARY_TOPIC", allocationSize = 1)
    @Column(name = "SECURITY_SUMMARY_TOPIC_ID")
    private Integer securitySummaryTopicID;

    @Column(name = "SECURITY_TYPE")
    private String securityType;

    @Column(name = "SECURITY_TYPE_GROUP")
    private String securityTypeGroup;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

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
}
