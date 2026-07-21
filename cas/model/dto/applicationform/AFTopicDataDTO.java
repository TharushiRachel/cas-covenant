package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.AFTopicData;

import java.io.Serializable;

public class AFTopicDataDTO implements Serializable {

    private Integer topicDataID;

    private Integer topicID;

    private Integer applicationFormID;

    private String remark;

    private String topic;

    private DomainConstants.ApplicationFormTopicPage page;

    private String topicCode;

    private String topicData;

    private AppsConstants.YesNo isDefault;

    private AppsConstants.Status status;

    public AFTopicDataDTO() {
    }

    public AFTopicDataDTO(AFTopicData afTopicData) {
        this.topicDataID = afTopicData.getTopicDataID();
        this.topicID = afTopicData.getTopic().getTopicID();
        this.applicationFormID = afTopicData.getApplicationForm().getApplicationFormID();
        this.remark = afTopicData.getRemark();
        this.topic = afTopicData.getTopic().getTopic();
        this.page = afTopicData.getPage();
        this.topicCode = afTopicData.getTopicCode();
        this.topicData = afTopicData.getTopicData();
        this.status = afTopicData.getStatus();
    }

    public Integer getTopicDataID() {
        return topicDataID;
    }

    public void setTopicDataID(Integer topicDataID) {
        this.topicDataID = topicDataID;
    }

    public Integer getTopicID() {
        return topicID;
    }

    public void setTopicID(Integer topicID) {
        this.topicID = topicID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public AppsConstants.YesNo getIsDefault() {
        return isDefault;
    }

    public void setIsDefault(AppsConstants.YesNo isDefault) {
        this.isDefault = isDefault;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    @Override
    public String toString() {
        return "AFTopicDataDTO{" +
                "topicDataID=" + topicDataID +
                ", topicID=" + topicID +
                ", applicationFormID=" + applicationFormID +
                ", remark='" + remark + '\'' +
                ", topic='" + topic + '\'' +
                ", topicCode='" + topicCode + '\'' +
                ", isDefault=" + isDefault +
                ", status=" + status +
                '}';
    }
}
