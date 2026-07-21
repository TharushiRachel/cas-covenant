package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class PrefentialChargeResponse implements Serializable {

    @JsonProperty("status")
    private String status;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("reportDtls")
    private List<PrefentialChargeDTO> reportDtls;
}
