package com.itechro.cas.model.domain.facilitypaper.creditschedule;

import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_CREDIT_SCHEDULE_ESB_RESPONSE")
public class CreditScheduleESBResponse extends UserTrackableEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CREDIT_SCHEDULE_RESPONSE")
    @SequenceGenerator(name = "SEQ_T_CREDIT_SCHEDULE_RESPONSE", sequenceName = "SEQ_T_CREDIT_SCHEDULE_RESPONSE", allocationSize = 1)
    @Column(name = "CREDIT_SCHEDULE_RESPONSE_ID")
    private Integer creditScheduleESBResponseId;

    @Column(name = "FACILITY_PAPER_ID")
    private Integer facilityPaperId;

    @Column(name = "FACILITY_ID")
    private Integer facilityId;

    @Column(name = "RESPONSE_STATUS")
    private String responseStatus;

    @Column(name = "RESPONSE")
    private String response;

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

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "CreditScheduleESBResponse{" +
                "creditScheduleESBResponseId=" + creditScheduleESBResponseId +
                ", facilityPaperId=" + facilityPaperId +
                ", facilityId=" + facilityId +
                ", responseStatus='" + responseStatus + '\'' +
                ", response='" + response + '\'' +
                '}';
    }
}
