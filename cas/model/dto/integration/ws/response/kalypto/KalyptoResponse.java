package com.itechro.cas.model.dto.integration.ws.response.kalypto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Kalypto.Rating.Eval",
    "Kalypto.Rating.Format",
    "Kalypto.Rating.StyleClass",
    "Kalypto.Rating.Period",
    "Kalypto.Rating.PeriodData"
})
public class KalyptoResponse {

    @JsonProperty("Kalypto.Rating.Eval")
    private KalyptoRatingEval kalyptoRatingEval;
    @JsonProperty("Kalypto.Rating.Format")
    private List<KalyptoRatingFormat> kalyptoRatingFormat = null;
    @JsonProperty("Kalypto.Rating.StyleClass")
    private List<KalyptoRatingStyleClass> kalyptoRatingStyleClass = null;
    @JsonProperty("Kalypto.Rating.Period")
    private List<KalyptoRatingPeriod> kalyptoRatingPeriod = null;
    @JsonProperty("Kalypto.Rating.PeriodData")
    private List<KalyptoRatingPeriodData> kalyptoRatingPeriodData = null;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("Kalypto.Rating.Eval")
    public KalyptoRatingEval getKalyptoRatingEval() {
        return kalyptoRatingEval;
    }

    @JsonProperty("Kalypto.Rating.Eval")
    public void setKalyptoRatingEval(KalyptoRatingEval kalyptoRatingEval) {
        this.kalyptoRatingEval = kalyptoRatingEval;
    }

    @JsonProperty("Kalypto.Rating.Format")
    public List<KalyptoRatingFormat> getKalyptoRatingFormat() {
        return kalyptoRatingFormat;
    }

    @JsonProperty("Kalypto.Rating.Format")
    public void setKalyptoRatingFormat(List<KalyptoRatingFormat> kalyptoRatingFormat) {
        this.kalyptoRatingFormat = kalyptoRatingFormat;
    }

    @JsonProperty("Kalypto.Rating.StyleClass")
    public List<KalyptoRatingStyleClass> getKalyptoRatingStyleClass() {
        return kalyptoRatingStyleClass;
    }

    @JsonProperty("Kalypto.Rating.StyleClass")
    public void setKalyptoRatingStyleClass(List<KalyptoRatingStyleClass> kalyptoRatingStyleClass) {
        this.kalyptoRatingStyleClass = kalyptoRatingStyleClass;
    }

    @JsonProperty("Kalypto.Rating.Period")
    public List<KalyptoRatingPeriod> getKalyptoRatingPeriod() {
        return kalyptoRatingPeriod;
    }

    @JsonProperty("Kalypto.Rating.Period")
    public void setKalyptoRatingPeriod(List<KalyptoRatingPeriod> kalyptoRatingPeriod) {
        this.kalyptoRatingPeriod = kalyptoRatingPeriod;
    }

    @JsonProperty("Kalypto.Rating.PeriodData")
    public List<KalyptoRatingPeriodData> getKalyptoRatingPeriodData() {
        return kalyptoRatingPeriodData;
    }

    @JsonProperty("Kalypto.Rating.PeriodData")
    public void setKalyptoRatingPeriodData(List<KalyptoRatingPeriodData> kalyptoRatingPeriodData) {
        this.kalyptoRatingPeriodData = kalyptoRatingPeriodData;
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
