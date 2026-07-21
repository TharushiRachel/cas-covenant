package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CribRetailReportResponse implements Serializable {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Details")
    private DetailsResponse detailsResponse;

    @JsonProperty("Fault")
    private FaultResponse faultResponse;

    @JsonProperty("Errors")
    private List<String> errors;

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("Details")
    public DetailsResponse getDetailsResponse() {
        return detailsResponse;
    }

    @JsonProperty("Details")
    public void setDetailsResponse(DetailsResponse detailsResponse) {
        this.detailsResponse = detailsResponse;
    }

    @JsonProperty("Errors")
    public List<String> getErrors() {
        return errors;
    }

    @JsonProperty("Errors")
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    @JsonProperty("Fault")
    public FaultResponse getFaultResponse() {
        return faultResponse;
    }

    @JsonProperty("Fault")
    public void setFaultResponse(FaultResponse faultResponse) {
        this.faultResponse = faultResponse;
    }
}
