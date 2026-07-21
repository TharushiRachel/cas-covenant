package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MasterCreditDetailsResponse implements Serializable {

    @JsonProperty("relation_id")
    private String relationID;

    @JsonProperty("active_root_id")
    private String activeRootID;

    @JsonProperty("SerialNumber")
    private String serialNumber;

    @JsonProperty("CREDIT_DETAILS_VER4")
    private List<CreditDetailsResponse> creditDetailsResponses;

    @JsonProperty("MONTH_YEAR_VER4")
    private List<MonthYearResponse> monthYearResponses;

    @JsonProperty("relation_id")
    public String getRelationID() {
        return relationID;
    }

    @JsonProperty("relation_id")
    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    @JsonProperty("active_root_id")
    public String getActiveRootID() {
        return activeRootID;
    }

    @JsonProperty("active_root_id")
    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    @JsonProperty("SerialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    @JsonProperty("SerialNumber")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    @JsonProperty("CREDIT_DETAILS_VER4")
    public List<CreditDetailsResponse> getCreditDetailsResponses() {
        return creditDetailsResponses;
    }

    @JsonProperty("CREDIT_DETAILS_VER4")
    public void setCreditDetailsResponses(List<CreditDetailsResponse> creditDetailsResponses) {
        this.creditDetailsResponses = creditDetailsResponses;
    }

    @JsonProperty("MONTH_YEAR_VER4")
    public List<MonthYearResponse> getMonthYearResponses() {
        return monthYearResponses;
    }

    @JsonProperty("MONTH_YEAR_VER4")
    public void setMonthYearResponses(List<MonthYearResponse> monthYearResponses) {
        this.monthYearResponses = monthYearResponses;
    }
}
