package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class IdentificationDetailsResponse implements Serializable {

    @JsonProperty("SOURCE_ID")
    private String sourceID;

    @JsonProperty("ID_VALUE")
    private String idValue;

    @JsonProperty("ID_DISPLAY_NAME")
    private String idDisplayName;

    @JsonProperty("RUID")
    private String ruid;

    @JsonProperty("SOURCE_ID")
    public String getSourceID() {
        return sourceID;
    }

    @JsonProperty("SOURCE_ID")
    public void setSourceID(String sourceID) {
        this.sourceID = sourceID;
    }

    @JsonProperty("ID_VALUE")
    public String getIdValue() {
        return idValue;
    }

    @JsonProperty("ID_VALUE")
    public void setIdValue(String idValue) {
        this.idValue = idValue;
    }

    @JsonProperty("ID_DISPLAY_NAME")
    public String getIdDisplayName() {
        return idDisplayName;
    }

    @JsonProperty("ID_DISPLAY_NAME")
    public void setIdDisplayName(String idDisplayName) {
        this.idDisplayName = idDisplayName;
    }

    @JsonProperty("RUID")
    public String getRuid() {
        return ruid;
    }

    @JsonProperty("RUID")
    public void setRuid(String ruid) {
        this.ruid = ruid;
    }


}
