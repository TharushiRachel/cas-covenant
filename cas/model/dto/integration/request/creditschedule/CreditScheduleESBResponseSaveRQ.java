package com.itechro.cas.model.dto.integration.request.creditschedule;

import com.itechro.cas.commons.constants.AppsConstants;

import java.util.Date;

public class CreditScheduleESBResponseSaveRQ {
    private Integer facilityPaperId;
    private Integer facilityId;
    private String responseStatus;
    private String response;
    private Date createdDate;
    private String createdBy;
    private Long version;
    private AppsConstants.Status status;

    public Integer getFacilityPaperId() {
        return facilityPaperId;
    }

    public void setFacilityPaperId(Integer facilityPaperId) {
        this.facilityPaperId = facilityPaperId;
    }

    public Integer getFacilityId() {
        return facilityId;
    }

    public void setFacilityId(Integer facilityId) {
        this.facilityId = facilityId;
    }

    public String getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(String responseStatus) {
        this.responseStatus = responseStatus;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CreditScheduleESBResponseSaveRQ{" +
                "facilityPaperId=" + facilityPaperId +
                ", facilityId=" + facilityId +
                ", responseStatus='" + responseStatus + '\'' +
                ", response='" + response + '\'' +
                ", createdDate=" + createdDate +
                ", createdBy='" + createdBy + '\'' +
                ", version=" + version +
                ", status=" + status +
                '}';
    }
}
