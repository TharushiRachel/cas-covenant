package com.itechro.cas.model.dto.integration.response.kalipto;

import com.itechro.cas.model.dto.integration.ws.response.kalypto.KalyptoRatingStyleClass;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class KalyptoRatingStyleClassDTO implements Serializable {

    private String styleClass;

    private String cssText;

    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    public KalyptoRatingStyleClassDTO(KalyptoRatingStyleClass kalyptoRatingStyleClass){
        this.styleClass = kalyptoRatingStyleClass.getStyleClass();
        this.cssText = kalyptoRatingStyleClass.getCssText();
        this.additionalProperties = kalyptoRatingStyleClass.getAdditionalProperties();
    }

    public String getStyleClass() {
        return styleClass;
    }

    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }

    public String getCssText() {
        return cssText;
    }

    public void setCssText(String cssText) {
        this.cssText = cssText;
    }

    public Map<String, Object> getAdditionalProperties() {
        return additionalProperties;
    }

    public void setAdditionalProperties(Map<String, Object> additionalProperties) {
        this.additionalProperties = additionalProperties;
    }
}
