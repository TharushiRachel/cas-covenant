package com.itechro.cas.model.dto.integration.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LoadConsumerComprehensiveRQ implements Serializable {

    @JsonProperty("Param")
    private ComprehensiveParamDTO Param;

    @JsonProperty("Search")
    private ConsumerComprehensiveDTO Search;

    @JsonProperty("Param")
    public ComprehensiveParamDTO getParam() {
        return Param;
    }

    @JsonProperty("Param")
    public void setParam(ComprehensiveParamDTO param) {
        Param = param;
    }

    @JsonProperty("Search")
    public ConsumerComprehensiveDTO getSearch() {
        return Search;
    }

    @JsonProperty("Search")
    public void setSearch(ConsumerComprehensiveDTO search) {
        Search = search;
    }

    @Override
    public String toString() {
        return "LoadConsumerComprehensiveRQ{" +
                "Param=" + Param +
                ", Search=" + Search +
                '}';
    }
}
