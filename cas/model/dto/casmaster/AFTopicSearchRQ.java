package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.io.Serializable;
import java.util.List;

public class AFTopicSearchRQ extends PagedParamDTO implements Serializable {

    private Integer afTopicID;

    private String pageOfTopic;

    private String topic;

    private String applicationFormPage;

    private String topicCode;

    private String description;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private List<DomainConstants.MasterDataApproveStatus> approveStatusList;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public Integer getAfTopicID() {
        return afTopicID;
    }

    public void setAfTopicID(Integer afTopicID) {
        this.afTopicID = afTopicID;
    }

    public String getPageOfTopic() {
        return pageOfTopic;
    }

    public void setPageOfTopic(String pageOfTopic) {
        this.pageOfTopic = pageOfTopic;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
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

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public List<DomainConstants.MasterDataApproveStatus> getApproveStatusList() {
        return approveStatusList;
    }

    public void setApproveStatusList(List<DomainConstants.MasterDataApproveStatus> approveStatusList) {
        this.approveStatusList = approveStatusList;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getApplicationFormPage() {
        return applicationFormPage;
    }

    public void setApplicationFormPage(String applicationFormPage) {
        this.applicationFormPage = applicationFormPage;
    }

    @Override
    public String toString() {
        return "AFTopicSearchRQ{" +
                "afTopicID=" + afTopicID +
                ", pageOfTopic='" + pageOfTopic + '\'' +
                ", topic='" + topic + '\'' +
                ", applicationFormPage='" + applicationFormPage + '\'' +
                ", topicCode='" + topicCode + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", approveStatus=" + approveStatus +
                ", approveStatusList=" + approveStatusList +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
