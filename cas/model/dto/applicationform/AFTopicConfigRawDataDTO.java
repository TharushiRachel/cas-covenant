package com.itechro.cas.model.dto.applicationform;

import com.opencsv.bean.CsvBindByName;

import java.io.Serializable;

public class AFTopicConfigRawDataDTO implements Serializable {

    @CsvBindByName(column = "Page")
    private String page;

    @CsvBindByName(column = "TopicCode")
    private String topicCode;

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
        return "AFTopicConfigRawDataDTO{" +
                "page='" + page + '\'' +
                ", topicCode='" + topicCode + '\'' +
                '}';
    }
}
