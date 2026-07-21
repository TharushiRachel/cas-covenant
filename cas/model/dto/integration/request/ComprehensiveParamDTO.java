package com.itechro.cas.model.dto.integration.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ComprehensiveParamDTO implements Serializable {

    @JsonProperty("ReportId")
    private String ReportId;

    @JsonProperty("SubjectType")
    private String SubjectType;

    @JsonProperty("ResponseType")
    private String ResponseType;

    @JsonProperty("ReasonCode")
    private String ReasonCode;

    @JsonProperty("ReportId")
    public String getReportId() {
        return ReportId;
    }

    @JsonProperty("ReportId")
    public void setReportId(String reportId) {
        ReportId = reportId;
    }

    @JsonProperty("SubjectType")
    public String getSubjectType() {
        return SubjectType;
    }

    @JsonProperty("SubjectType")
    public void setSubjectType(String subjectType) {
        SubjectType = subjectType;
    }

    @JsonProperty("ResponseType")
    public String getResponseType() {
        return ResponseType;
    }

    @JsonProperty("ResponseType")
    public void setResponseType(String responseType) {
        ResponseType = responseType;
    }

    @JsonProperty("ReasonCode")
    public String getReasonCode() {
        return ReasonCode;
    }

    @JsonProperty("ReasonCode")
    public void setReasonCode(String reasonCode) {
        ReasonCode = reasonCode;
    }

    @Override
    public String toString() {
        return "ComprehensiveParamDTO{" +
                "ReportId='" + ReportId + '\'' +
                ", SubjectType='" + SubjectType + '\'' +
                ", ResponseType='" + ResponseType + '\'' +
                ", ReasonCode='" + ReasonCode + '\'' +
                '}';
    }
}
