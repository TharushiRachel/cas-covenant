package com.itechro.cas.model.dto.lead.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LeadCrmDTO {

    @JsonProperty("ItemId")
    private String ItemId;

    @JsonProperty("ItemType")
    private String ItemType;

    @JsonProperty("ProcessMode")
    private String ProcessMode;

    @JsonProperty("ObjectData")
    private LeadCrmObjectDTO ObjectData;
}
