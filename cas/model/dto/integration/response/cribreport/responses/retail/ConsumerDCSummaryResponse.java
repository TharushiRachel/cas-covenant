package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerDCSummaryResponse implements Serializable {

    @JsonProperty("DISHONOURED_CHEQUE_SUMM_VER4")
    private DishonouredChequeSummResponse dishonouredChequeSummResponse;

    @JsonProperty("DISHONOURED_CHEQUE_SUMM_VER4")
    public DishonouredChequeSummResponse getDishonouredChequeSummResponse() {
        return dishonouredChequeSummResponse;
    }

    @JsonProperty("DISHONOURED_CHEQUE_SUMM_VER4")
    public void setDishonouredChequeSummResponse(DishonouredChequeSummResponse dishonouredChequeSummResponse) {
        this.dishonouredChequeSummResponse = dishonouredChequeSummResponse;
    }
}
