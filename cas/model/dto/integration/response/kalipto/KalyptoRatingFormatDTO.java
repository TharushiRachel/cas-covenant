package com.itechro.cas.model.dto.integration.response.kalipto;

import com.itechro.cas.model.dto.integration.ws.response.kalypto.Format;
import com.itechro.cas.model.dto.integration.ws.response.kalypto.KalyptoRatingFormat;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KalyptoRatingFormatDTO implements Serializable {

    private Format format;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public KalyptoRatingFormatDTO(KalyptoRatingFormat kalyptoRatingFormat){
        this.format = kalyptoRatingFormat.getFormat();
        this.additionalProperties = kalyptoRatingFormat.getAdditionalProperties();
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
