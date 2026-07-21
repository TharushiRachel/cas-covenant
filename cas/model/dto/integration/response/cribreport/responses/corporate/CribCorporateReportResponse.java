package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CribCorporateReportResponse implements Serializable {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("Details")
    private CorporateDetailsResponse corporateDetailsResponse;

    @JsonProperty("Errors")
    private List<String> errors;

    @JsonProperty("Fault")
    private CorporateFaultResponse corporateFaultResponse;

    @JsonProperty("Status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("Status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("Details")
    public CorporateDetailsResponse getCorporateDetailsResponse() {
        return corporateDetailsResponse;
    }

    @JsonProperty("Details")
    public void setCorporateDetailsResponse(CorporateDetailsResponse corporateDetailsResponse) {
        this.corporateDetailsResponse = corporateDetailsResponse;
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
    public CorporateFaultResponse getCorporateFaultResponse() {
        return corporateFaultResponse;
    }

    @JsonProperty("Fault")
    public void setCorporateFaultResponse(CorporateFaultResponse corporateFaultResponse) {
        this.corporateFaultResponse = corporateFaultResponse;
    }
}
