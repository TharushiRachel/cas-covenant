package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialCreditFacilitiesStatusResponse implements Serializable {

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_VER4")
    private List<CommercialCreditFacilityStatusResponse> commercialCreditFacilityStatusResponses;

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_LABEL")
    private List<CommercialCreditFacilityStatusLabel> commercialCreditFacilityStatusLabels;

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_VER4")
    public List<CommercialCreditFacilityStatusResponse> getCommercialCreditFacilityStatusResponses() {
        return commercialCreditFacilityStatusResponses;
    }

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_VER4")
    public void setCommercialCreditFacilityStatusResponses(List<CommercialCreditFacilityStatusResponse> commercialCreditFacilityStatusResponses) {
        this.commercialCreditFacilityStatusResponses = commercialCreditFacilityStatusResponses;
    }

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_LABEL")
    public List<CommercialCreditFacilityStatusLabel> getCommercialCreditFacilityStatusLabels() {
        return commercialCreditFacilityStatusLabels;
    }

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_LABEL")
    public void setCommercialCreditFacilityStatusLabels(List<CommercialCreditFacilityStatusLabel> commercialCreditFacilityStatusLabels) {
        this.commercialCreditFacilityStatusLabels = commercialCreditFacilityStatusLabels;
    }
}
