package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.AFTopic;
import com.itechro.cas.model.domain.casmaster.AFTopicUpcSection;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AFTopicDTO implements Serializable {

    private Integer topicID;

    private DomainConstants.ApplicationFormTopicPage page;

    private String topic;

    private String topicCode;

    private String topicData;

    private String description;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private List<AFTopicUpcSectionDTO> afTopicUpcSectionDTOList;

    public AFTopicDTO() {
    }

    public AFTopicDTO(AFTopic afTopic) {
        this.topicID = afTopic.getTopicID();
        this.page = afTopic.getPage();
        this.topic = afTopic.getTopic();
        this.topicCode = afTopic.getTopicCode();
        this.topicData = afTopic.getTopicData();
        this.description = afTopic.getDescription();
        this.status = afTopic.getStatus();
        this.approveStatus = afTopic.getApproveStatus();
        for (AFTopicUpcSection afTopicUpcSection : afTopic.getOrderedAfTopicUpcSection()) {
            if (afTopicUpcSection.getStatus() == AppsConstants.Status.ACT) {
                getAfTopicUpcSectionDTOList().add(new AFTopicUpcSectionDTO(afTopicUpcSection));
            }
        }

        if (afTopic.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(afTopic.getApprovedDate());
        }
        this.approvedBy = afTopic.getApprovedBy();
        if (afTopic.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(afTopic.getCreatedDate());
        }
        this.createdBy = afTopic.getCreatedBy();
        if (afTopic.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(afTopic.getModifiedDate());
        }
        this.modifiedBy = afTopic.getModifiedBy();
    }

    public Integer getTopicID() {
        return topicID;
    }

    public void setTopicID(Integer topicID) {
        this.topicID = topicID;
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

    public DomainConstants.ApplicationFormTopicPage getPage() {
        return page;
    }

    public void setPage(DomainConstants.ApplicationFormTopicPage page) {
        this.page = page;
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

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public List<AFTopicUpcSectionDTO> getAfTopicUpcSectionDTOList() {
        if (afTopicUpcSectionDTOList == null) {
            this.afTopicUpcSectionDTOList = new ArrayList<>();
        }
        return afTopicUpcSectionDTOList;
    }

    public void setAfTopicUpcSectionDTOList(List<AFTopicUpcSectionDTO> afTopicUpcSectionDTOList) {
        this.afTopicUpcSectionDTOList = afTopicUpcSectionDTOList;
    }

    @Override
    public String toString() {
        return "AFTopicDTO{" +
                "topicID=" + topicID +
                ", page='" + page + '\'' +
                ", topic='" + topic + '\'' +
                ", topicCode='" + topicCode + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", approveStatus=" + approveStatus +
                ", approvedDateStr='" + approvedDateStr + '\'' +
                ", approvedBy='" + approvedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                '}';
    }
}
