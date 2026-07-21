package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerEmploymentResponse implements Serializable {

    @JsonProperty("EMPLOYMENT_DETAILS_VER4")
    private List<EmploymentDetailResponse> employmentDetailResponses;

    @JsonProperty("EMPLOYMENT_DETAILS_VER4")
    public List<EmploymentDetailResponse> getEmploymentDetailResponses() {
        return employmentDetailResponses;
    }

    @JsonProperty("EMPLOYMENT_DETAILS_VER4")
    public void setEmploymentDetailResponses(List<EmploymentDetailResponse> employmentDetailResponses) {
        this.employmentDetailResponses = employmentDetailResponses;
    }
}
