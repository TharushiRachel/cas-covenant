package com.itechro.cas.model.dto.customer.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.dto.customer.request.CustomerCovenantDetailsReqDTO;
import lombok.Data;

import java.util.List;

@Data
public class CovenantDetailResDTO {
    @JsonProperty("RequestUUID")
    private String RequestUUID;
    private String custId;
    private String casReference;
    @JsonProperty("REL")
    private List<CustomerCovenantDetailsReqDTO> REL;

}
