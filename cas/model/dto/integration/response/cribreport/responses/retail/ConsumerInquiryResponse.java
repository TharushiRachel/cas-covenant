package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerInquiryResponse implements Serializable {


    @JsonProperty("INQUIRY_DETAILS_VER4")
    private List<InquiryDetailsResponse> inquiryDetailsResponses;

    @JsonProperty("INQUIRY_DETAILS_VER4")
    public List<InquiryDetailsResponse> getInquiryDetailsResponses() {
        return inquiryDetailsResponses;
    }

    @JsonProperty("INQUIRY_DETAILS_VER4")
    public void setInquiryDetailsResponses(List<InquiryDetailsResponse> inquiryDetailsResponses) {
        this.inquiryDetailsResponses = inquiryDetailsResponses;
    }
}
