package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerAddressResponse implements Serializable {

    @JsonProperty("MAILING_ADDRESS_VER4")
    private List<MailingAddressResponse> mailingAddressResponses;

    @JsonProperty("PERMANENT_ADDRESS_VER4")
    private List<PermanentAddressResponse> permanentAddressResponses;

    public List<MailingAddressResponse> getMailingAddressResponses() {
        return mailingAddressResponses;
    }

    public void setMailingAddressResponses(List<MailingAddressResponse> mailingAddressResponses) {
        this.mailingAddressResponses = mailingAddressResponses;
    }

    public List<PermanentAddressResponse> getPermanentAddressResponses() {
        return permanentAddressResponses;
    }

    public void setPermanentAddressResponses(List<PermanentAddressResponse> permanentAddressResponses) {
        this.permanentAddressResponses = permanentAddressResponses;
    }
}
