package com.itechro.cas.model.dto.lead.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CrmSaveResponseDTO {

    @JsonProperty("ObjectKey")
    private String ObjectKey;

    @JsonProperty("Result")
    private String Result;

    @JsonProperty("IsSuccess")
    private String IsSuccess;

    @JsonProperty("Message")
    private String Message;
}
