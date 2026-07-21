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
    "parameter_id",
    "value_numeric",
    "value_percentage",
    "value_text"
})
public class Param {

    @JsonProperty("parameter_id")
    private String parameterId;
    @JsonProperty("value_numeric")
    private String valueNumeric;
    @JsonProperty("value_percentage")
    private String valuePercentage;
    @JsonProperty("value_text")
    private String valueText;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("parameter_id")
    public String getParameterId() {
        return parameterId;
    }

    @JsonProperty("parameter_id")
    public void setParameterId(String parameterId) {
        this.parameterId = parameterId;
    }

    @JsonProperty("value_numeric")
    public String getValueNumeric() {
        return valueNumeric;
    }

    @JsonProperty("value_numeric")
    public void setValueNumeric(String valueNumeric) {
        this.valueNumeric = valueNumeric;
    }

    @JsonProperty("value_percentage")
    public String getValuePercentage() {
        return valuePercentage;
    }

    @JsonProperty("value_percentage")
    public void setValuePercentage(String valuePercentage) {
        this.valuePercentage = valuePercentage;
    }

    @JsonProperty("value_text")
    public String getValueText() {
        return valueText;
    }

    @JsonProperty("value_text")
    public void setValueText(String valueText) {
        this.valueText = valueText;
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
