package com.itechro.cas.model.dto.integration.response.creditschedule;

import com.itechro.cas.model.dto.integration.request.creditschedule.CreditScheduleESBRQ;

public class CreditScheduleESBDTO {
    private CreditScheduleESBRQ creditScheduleESBRQ;
    private Integer facilityPaperId;
    private Integer facilityId;

    public CreditScheduleESBRQ getCreditScheduleESBRQ() {
        return creditScheduleESBRQ;
    }

    public void setCreditScheduleESBRQ(CreditScheduleESBRQ creditScheduleESBRQ) {
        this.creditScheduleESBRQ = creditScheduleESBRQ;
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

    @Override
    public String toString() {
        return "CreditScheduleESBDTO{" +
                "creditScheduleESBRQ=" + creditScheduleESBRQ +
                ", facilityPaperId=" + facilityPaperId +
                ", facilityId=" + facilityId +
                '}';
    }
}
