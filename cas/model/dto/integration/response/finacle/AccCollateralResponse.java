package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class AccCollateralResponse implements Serializable {

    @JsonProperty("Status")
    private String status;

    @JsonProperty("collateral")
    private List<AccCollateralData> collateral;
}
