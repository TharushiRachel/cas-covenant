package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "communicationAddr",
        "emplAddr",
        "permenentAddress"
})
public class CustomerAddressDetailsResponse implements Serializable {

    @JsonProperty("communicationAddr")
    private String communicationAddr;

    @JsonProperty("emplAddr")
    private String emplAddr;

    @JsonProperty("permenentAddress")
    private String permenentAddress;

    public String getCommunicationAddr() {
        return communicationAddr;
    }

    public void setCommunicationAddr(String communicationAddr) {
        this.communicationAddr = communicationAddr;
    }

    public String getEmplAddr() {
        return emplAddr;
    }

    public void setEmplAddr(String emplAddr) {
        this.emplAddr = emplAddr;
    }

    public String getPermenentAddress() {
        return permenentAddress;
    }

    public void setPermenentAddress(String permenentAddress) {
        this.permenentAddress = permenentAddress;
    }

}
