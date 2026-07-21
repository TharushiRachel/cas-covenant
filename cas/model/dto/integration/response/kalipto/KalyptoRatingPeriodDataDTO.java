package com.itechro.cas.model.dto.integration.response.kalipto;

import com.itechro.cas.model.dto.integration.ws.response.kalypto.KalyptoRatingPeriodData;
import com.itechro.cas.model.dto.integration.ws.response.kalypto.Period;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KalyptoRatingPeriodDataDTO implements Serializable {

    private Period period;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public KalyptoRatingPeriodDataDTO(KalyptoRatingPeriodData kalyptoRatingPeriodData){
        this.period = kalyptoRatingPeriodData.getPeriod();
        this.additionalProperties = kalyptoRatingPeriodData.getAdditionalProperties();
    }

    public Period getPeriod() {
        return period;
    }

    public void setPeriod(Period period) {
        this.period = period;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
