package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialAddressResponse implements Serializable {

    @JsonProperty("MAILING_ADDRESS_VER4")
    private List<MailingAddressResponse> mailingAddressResponse;

    @JsonProperty("PERMANENT_ADDRESS_VER4")
    private List<PermanentAddressResponse> permanentAddressResponse;

    @JsonProperty("MAILING_ADDRESS_VER4")
    public List<MailingAddressResponse> getMailingAddressResponse() {
        return mailingAddressResponse;
    }

    @JsonProperty("MAILING_ADDRESS_VER4")
    public void setMailingAddressResponse(List<MailingAddressResponse> mailingAddressResponse) {
        this.mailingAddressResponse = mailingAddressResponse;
    }

    @JsonProperty("PERMANENT_ADDRESS_VER4")
    public List<PermanentAddressResponse> getPermanentAddressResponse() {
        return permanentAddressResponse;
    }

    @JsonProperty("PERMANENT_ADDRESS_VER4")
    public void setPermanentAddressResponse(List<PermanentAddressResponse> permanentAddressResponse) {
        this.permanentAddressResponse = permanentAddressResponse;
    }
}
