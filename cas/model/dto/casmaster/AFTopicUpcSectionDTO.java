package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.AFTopicUpcSection;

import java.io.Serializable;

public class AFTopicUpcSectionDTO implements Serializable {

    private Integer topicUpcSectionID;

    private Integer topicID;

    private Integer upcSectionID;

    private String upcSectionName;

    private Integer upcTemplateID;

    private String upcTemplateName;

    private AppsConstants.Status status;

    public AFTopicUpcSectionDTO() {
    }

    public AFTopicUpcSectionDTO(AFTopicUpcSection afTopicUpcSection) {
        this.topicUpcSectionID = afTopicUpcSection.getTopicUpcSectionID();
        this.topicID = afTopicUpcSection.getTopic().getTopicID();
        this.upcSectionID = afTopicUpcSection.getUpcSectionID();
        this.upcSectionName = afTopicUpcSection.getUpcSectionName();
        this.upcTemplateID = afTopicUpcSection.getUpcTemplateID();
        this.upcTemplateName = afTopicUpcSection.getUpcTemplateName();
        this.status = afTopicUpcSection.getStatus();
    }

    public Integer getTopicUpcSectionID() {
        return topicUpcSectionID;
    }

    public void setTopicUpcSectionID(Integer topicUpcSectionID) {
        this.topicUpcSectionID = topicUpcSectionID;
    }

    public Integer getTopicID() {
        return topicID;
    }

    public void setTopicID(Integer topicID) {
        this.topicID = topicID;
    }

    public Integer getUpcSectionID() {
        return upcSectionID;
    }

    public void setUpcSectionID(Integer upcSectionID) {
        this.upcSectionID = upcSectionID;
    }

    public String getUpcSectionName() {
        return upcSectionName;
    }

    public void setUpcSectionName(String upcSectionName) {
        this.upcSectionName = upcSectionName;
    }

    public Integer getUpcTemplateID() {
        return upcTemplateID;
    }

    public void setUpcTemplateID(Integer upcTemplateID) {
        this.upcTemplateID = upcTemplateID;
    }

    public String getUpcTemplateName() {
        return upcTemplateName;
    }

    public void setUpcTemplateName(String upcTemplateName) {
        this.upcTemplateName = upcTemplateName;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
