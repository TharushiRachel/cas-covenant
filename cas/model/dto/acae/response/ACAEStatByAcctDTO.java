package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ACAEStatByAcctDTO {

    @JsonProperty("RequestId")
    private String RequestId;

    @JsonProperty("Status")
    private String Status;

    @JsonProperty("SuccessResponse")
    private List<ACAEStatByAcctDataDTO> SuccessResponse;
}
