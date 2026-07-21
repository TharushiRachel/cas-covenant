package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.AFTopic;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_AF_TOPIC_DATA")
public class AFTopicData extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_TOPIC_DATA")
    @SequenceGenerator(name = "SEQ_T_AF_TOPIC_DATA", sequenceName = "SEQ_T_AF_TOPIC_DATA", allocationSize = 1)
    @Column(name = "TOPIC_DATA_ID")
    private Integer topicDataID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TOPIC_ID")
    private AFTopic topic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @Column(name = "REMARK")
    private String remark;

    @Enumerated(EnumType.STRING)
    @Column(name = "PAGE")
    private DomainConstants.ApplicationFormTopicPage page;

    @Column(name = "TOPIC_CODE")
    private String topicCode;

    @Column(name = "TOPIC_DATA")
    private String topicData;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getTopicDataID() {
        return topicDataID;
    }

    public void setTopicDataID(Integer topicDataID) {
        this.topicDataID = topicDataID;
    }

    public AFTopic getTopic() {
        return topic;
    }

    public void setTopic(AFTopic topic) {
        this.topic = topic;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTopicData() {
        return topicData;
    }

    public void setTopicData(String topicData) {
        this.topicData = topicData;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.ApplicationFormTopicPage getPage() {
        return page;
    }

    public void setPage(DomainConstants.ApplicationFormTopicPage page) {
        this.page = page;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }
}
