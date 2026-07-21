
package com.itechro.cas.model.dto.integration.ws.response;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "accPartyName",
    "primaryCustomer",
    "cifId"
})
public class JoiningPartnerList {

    @JsonProperty("accPartyName")
    private String accPartyName;
    @JsonProperty("primaryCustomer")
    private String primaryCustomer;
    @JsonProperty("cifId")
    private String cifId;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("accPartyName")
    public String getAccPartyName() {
        return accPartyName;
    }

    @JsonProperty("accPartyName")
    public void setAccPartyName(String accPartyName) {
        this.accPartyName = accPartyName;
    }

    @JsonProperty("primaryCustomer")
    public String getPrimaryCustomer() {
        return primaryCustomer;
    }

    @JsonProperty("primaryCustomer")
    public void setPrimaryCustomer(String primaryCustomer) {
        this.primaryCustomer = primaryCustomer;
    }

    @JsonProperty("cifId")
    public String getCifId() {
        return cifId;
    }

    @JsonProperty("cifId")
    public void setCifId(String cifId) {
        this.cifId = cifId;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
