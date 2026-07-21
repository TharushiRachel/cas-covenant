package com.itechro.cas.model.dto.integration.ws.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "foracid",
        "accName",
        "schemeCode",
        "accSince"
})
public class AccountDataWSDTO implements Serializable {

    @JsonProperty("foracid")
    private String foracid;

    @JsonProperty("accName")
    private String accName;

    @JsonProperty("schemeCode")
    private String schemeCode;

    @JsonProperty("accSince")
    private String accSince;

    public String getForacid() {
        return foracid;
    }

    public void setForacid(String foracid) {
        this.foracid = foracid;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getSchemeCode() {
        return schemeCode;
    }

    public void setSchemeCode(String schemeCode) {
        this.schemeCode = schemeCode;
    }

    public String getAccSince() {
        return accSince;
    }

    public void setAccSince(String accSince) {
        this.accSince = accSince;
    }
}
