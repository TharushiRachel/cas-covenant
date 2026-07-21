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
    "style_class",
    "css_text"
})
public class KalyptoRatingStyleClass {

    @JsonProperty("style_class")
    private String styleClass;
    @JsonProperty("css_text")
    private String cssText;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("style_class")
    public String getStyleClass() {
        return styleClass;
    }

    @JsonProperty("style_class")
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    @JsonProperty("css_text")
    public String getCssText() {
        return cssText;
    }

    @JsonProperty("css_text")
    public void setCssText(String cssText) {
        this.cssText = cssText;
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
