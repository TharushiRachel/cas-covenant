package com.itechro.cas.model.dto.lead.crm;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CrmLoginResponse {

    @JsonProperty("access_token")
    private String access_token;

    @JsonProperty("expires_in")
    private String expires_in;

    @JsonProperty("token_type")
    private String token_type;

    @JsonProperty("refresh_token")
    private String refresh_token;

    @JsonProperty("scope")
    private String scope;
}
