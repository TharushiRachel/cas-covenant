package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.model.domain.applicationform.AFTopicConfig;

import java.io.Serializable;

public class AFTTopicConfigDTO implements Serializable {

    private String page;

    private String topicCode;

    public AFTTopicConfigDTO() {
    }

    public AFTTopicConfigDTO(AFTopicConfig afTopicConfig) {
        this.page = afTopicConfig.getPage();
        this.topicCode = afTopicConfig.getTopicCode();
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

    @Override
    public String toString() {
        return "AFTTopicConfigDTO{" +
                "page='" + page + '\'' +
                ", topicCode='" + topicCode + '\'' +
                '}';
    }
}
