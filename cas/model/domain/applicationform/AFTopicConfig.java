package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_TOPIC_CONFIG")
public class AFTopicConfig extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_TOPIC_CONFIG")
    @SequenceGenerator(name = "SEQ_T_AF_TOPIC_CONFIG", sequenceName = "SEQ_T_AF_TOPIC_CONFIG", allocationSize = 1)
    @Column(name = "TOPIC_CONFIG_ID")
    private Integer topicDataID;

    @Column(name = "PAGE")
    private String page;

    @Column(name = "TOPIC_CODE")
    private String topicCode;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getTopicDataID() {
        return topicDataID;
    }

    public void setTopicDataID(Integer topicDataID) {
        this.topicDataID = topicDataID;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
