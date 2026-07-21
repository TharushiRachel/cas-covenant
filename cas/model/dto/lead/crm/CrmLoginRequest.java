package com.itechro.cas.model.dto.lead.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CrmLoginRequest {

    @JsonProperty("userName")
    private String userName;

    @JsonProperty("password")
    private String password;
}
