package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ApplicationFormTopicRQ implements Serializable {

    private Integer applicationFormID;

    private List<String> pageList;

    private String topicCode;

    private String topic;

    private AppsConstants.Status status;

    public ApplicationFormTopicRQ() {
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public List<String> getPageList() {
        if (pageList == null) {
            this.pageList = new ArrayList<>();
        }
        return pageList;
    }

    public void setPageList(List<String> pageList) {
        this.pageList = pageList;
    }

    public String getTopicCode() {
        return topicCode;
    }

    public void setTopicCode(String topicCode) {
        this.topicCode = topicCode;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }
}
