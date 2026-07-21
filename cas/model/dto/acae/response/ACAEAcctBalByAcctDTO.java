package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ACAEAcctBalByAcctDTO {

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    @JsonProperty("Status")
    private String Status;

    @JsonProperty("StatRecords")
    private List<ACAEAcctBalByAcctDataDTO> StatRecords;
}
