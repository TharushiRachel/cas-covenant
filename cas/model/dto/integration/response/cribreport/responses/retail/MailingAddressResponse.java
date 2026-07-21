package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MailingAddressResponse implements Serializable {

    @JsonProperty("SERIAL_NO")
    private String serialNo;

    @JsonProperty("LAST_REPORTED_DATE")
    private String lastReportedDate;

    @JsonProperty("ADDRESS_VALUE")
    private String addressValue;

    @JsonProperty("BLOCK_FLAG")
    private String blockFlag;

    @JsonProperty("SERIAL_NO")
    public String getSerialNo() {
        return serialNo;
    }

    @JsonProperty("SERIAL_NO")
    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    @JsonProperty("LAST_REPORTED_DATE")
    public String getLastReportedDate() {
        return lastReportedDate;
    }

    @JsonProperty("LAST_REPORTED_DATE")
    public void setLastReportedDate(String lastReportedDate) {
        this.lastReportedDate = lastReportedDate;
    }

    @JsonProperty("ADDRESS_VALUE")
    public String getAddressValue() {
        return addressValue;
    }

    @JsonProperty("ADDRESS_VALUE")
    public void setAddressValue(String addressValue) {
        this.addressValue = addressValue;
    }

    @JsonProperty("BLOCK_FLAG")
    public String getBlockFlag() {
        return blockFlag;
    }

    @JsonProperty("BLOCK_FLAG")
    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }
}
