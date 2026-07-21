package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class GroupExposureFundedLimit implements Serializable {

    @JsonProperty("TotalSanctionLimit")
    private String totalSanctionLimit;

    @JsonProperty("OutstandingAmount")
    private String outstandingAmount;
}
