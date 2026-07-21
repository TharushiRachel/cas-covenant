package com.itechro.cas.model.dto.integration.response.finacle;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
public class LimitNodeDetailsResponse implements Serializable {

    @JsonProperty("limitB2kId")
    private String limitB2kId;

    @JsonProperty("limitPrefix")
    private String limitPrefix;

    @JsonProperty("limitSuffix")
    private String limitSuffix;

    @JsonProperty("limitDesc")
    private String limitDesc;

    @JsonProperty("nodeCcy")
    private String nodeCcy;

    @JsonProperty("nodeSanction")
    private String nodeSanction;

    @JsonProperty("drawingPower")
    private String drawingPower;

    @JsonProperty("liab")
    private String liab;

    @JsonProperty("contingentLiab")
    private String contingentLiab;

    @JsonProperty("numOfAccts")
    private String numOfAccts;

    @JsonProperty("limSanctDate")
    private String limSanctDate;

    @JsonProperty("limExpDate")
    private String limExpDate;

    @JsonProperty("limRvwDate")
    private String limRvwDate;

    @JsonProperty("parentLimitB2kId")
    private String parentLimitB2kId;
}
