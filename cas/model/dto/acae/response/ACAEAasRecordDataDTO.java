package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
@Data
public class ACAEAasRecordDataDTO {
    @JsonProperty("acctNumber")
    private String acctNumber;

    @JsonProperty("schmType")
    private String schmType;

    @JsonProperty("relType")
    private String relType;

}
