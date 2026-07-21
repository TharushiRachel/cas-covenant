package com.itechro.cas.model.dto.integration.response.kalipto;

import com.itechro.cas.model.dto.integration.ws.response.kalypto.KalyptoRatingPeriod;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KalyptoRatingPeriodDTO implements Serializable {

    private String periodId;

    private String headerText;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public KalyptoRatingPeriodDTO(KalyptoRatingPeriod kalyptoRatingPeriod){
        this.periodId = kalyptoRatingPeriod.getPeriodId();
        this.headerText = kalyptoRatingPeriod.getHeaderText();
        this.additionalProperties = kalyptoRatingPeriod.getAdditionalProperties();
    }

    public String getPeriodId() {
        return periodId;
    }

    public void setPeriodId(String periodId) {
        this.periodId = periodId;
    }

    public String getHeaderText() {
        return headerText;
    }

    public void setHeaderText(String headerText) {
        this.headerText = headerText;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
