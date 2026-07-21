package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ACAEAasRecordsDTO {

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    @JsonProperty("CasAaasData")
    private List<ACAEAasRecordDataDTO> CasAaasData;
}
