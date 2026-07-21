package com.itechro.cas.model.dto.integration.response.creditschedule;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ScheduleResponse {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Fault")
    private List<FaultDTO> fault;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<FaultDTO> getFault() {
        return fault;
    }

    public void setFault(List<FaultDTO> fault) {
        this.fault = fault;
    }
}
