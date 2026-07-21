package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class NameResponse implements Serializable {

    @JsonProperty("SNO")
    private String sNo;

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("BLOCK_FLAG")
    private String blockFlag;

    @JsonProperty("SNO")
    public String getsNo() {
        return sNo;
    }

    @JsonProperty("SNO")
    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    @JsonProperty("NAME")
    public String getName() {
        return name;
    }

    @JsonProperty("NAME")
    public void setName(String name) {
        this.name = name;
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
