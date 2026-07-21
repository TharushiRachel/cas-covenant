package com.itechro.cas.model.dto.integration.ws.response.kalypto;

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
    "counterparty_name",
    "unique_identifier",
    "model",
    "ratingscore"
})
public class KalyptoRatingEval {

    @JsonProperty("counterparty_name")
    private String counterpartyName;
    @JsonProperty("unique_identifier")
    private String uniqueIdentifier;
    @JsonProperty("model")
    private String model;
    @JsonProperty("ratingscore")
    private String ratingscore;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("counterparty_name")
    public String getCounterpartyName() {
        return counterpartyName;
    }

    @JsonProperty("counterparty_name")
    public void setCounterpartyName(String counterpartyName) {
        this.counterpartyName = counterpartyName;
    }

    @JsonProperty("unique_identifier")
    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    @JsonProperty("unique_identifier")
    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    @JsonProperty("model")
    public String getModel() {
        return model;
    }

    @JsonProperty("model")
    public void setModel(String model) {
        this.model = model;
    }

    @JsonProperty("ratingscore")
    public String getRatingscore() {
        return ratingscore;
    }

    @JsonProperty("ratingscore")
    public void setRatingscore(String ratingscore) {
        this.ratingscore = ratingscore;
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
