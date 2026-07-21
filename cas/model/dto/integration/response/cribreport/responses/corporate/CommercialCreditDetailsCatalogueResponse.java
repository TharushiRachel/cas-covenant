package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommercialCreditDetailsCatalogueResponse implements Serializable {

    @JsonProperty("Label")
    private String label;

    @JsonProperty("Values")
    private String values;

    @JsonProperty("SerialNumber")
    private String serialNumber;

    @JsonProperty("Label")
    public String getLabel() {
        return label;
    }

    @JsonProperty("Label")
    public void setLabel(String label) {
        this.label = label;
    }

    @JsonProperty("Values")
    public String getValues() {
        return values;
    }

    @JsonProperty("Values")
    public void setValues(String values) {
        this.values = values;
    }

    @JsonProperty("SerialNumber")
    public String getSerialNumber() {
        return serialNumber;
    }

    @JsonProperty("SerialNumber")
    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
}
