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
    "period_id",
    "header_text"
})
public class KalyptoRatingPeriod {

    @JsonProperty("period_id")
    private String periodId;
    @JsonProperty("header_text")
    private String headerText;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("period_id")
    public String getPeriodId() {
        return periodId;
    }

    @JsonProperty("period_id")
    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    @JsonProperty("header_text")
    public String getHeaderText() {
        return headerText;
    }

    @JsonProperty("header_text")
    public void setHeaderText(String headerText) {
        this.headerText = headerText;
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
