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
    "name",
    "style_class"
})
public class Row {

    @JsonProperty("parameter_id")
    private String parameterId;
    @JsonProperty("name")
    private String name;
    @JsonProperty("style_class")
    private String styleClass;
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

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("style_class")
    public String getStyleClass() {
        return styleClass;
    }

    @JsonProperty("style_class")
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
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
