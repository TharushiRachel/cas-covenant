package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

import java.io.Serializable;

public class CreditScheduleESBResponseDTO implements Serializable {

    private Integer creditScheduleESBResponseId;

    private Integer facilityPaperId;

    private Integer facilityId;

    private String responseStatus;

    public Integer getCreditScheduleESBResponseId() {
        return creditScheduleESBResponseId;
    }

    public void setCreditScheduleESBResponseId(Integer creditScheduleESBResponseId) {
        this.creditScheduleESBResponseId = creditScheduleESBResponseId;
    }

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

    @Override
    public String toString() {
        return "CreditScheduleESBResponseDTO{" +
                "creditScheduleESBResponseId=" + creditScheduleESBResponseId +
                ", facilityPaperId=" + facilityPaperId +
                ", facilityId=" + facilityId +
                ", responseStatus='" + responseStatus + '\'' +
                '}';
    }
}
