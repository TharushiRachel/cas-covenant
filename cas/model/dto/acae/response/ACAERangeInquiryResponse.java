package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tranDetails"
})
public class ACAERangeInquiryResponse {

    @JsonProperty("tranDetails")
    List<ACAERangeInquiryDTO> acaeRangeInquiryDTOList;
}
