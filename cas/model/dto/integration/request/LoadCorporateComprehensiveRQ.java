package com.itechro.cas.model.dto.integration.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class LoadCorporateComprehensiveRQ implements Serializable {

    @JsonProperty("Param")
    private ComprehensiveParamDTO Param;

    @JsonProperty("Search")
    private CorporateComprehensiveDTO Search;

    @JsonProperty("Param")
    public ComprehensiveParamDTO getParam() {
        if (Param == null) {
            Param = new ComprehensiveParamDTO();
        }
        return Param;
    }

    @JsonProperty("Param")
    public void setParam(ComprehensiveParamDTO param) {
        Param = param;
    }

    @JsonProperty("Search")
    public CorporateComprehensiveDTO getSearch() {
        if (Search == null) {
            Search = new CorporateComprehensiveDTO();
        }
        return Search;
    }

    @JsonProperty("Search")
    public void setSearch(CorporateComprehensiveDTO search) {
        Search = search;
    }

    @Override
    public String toString() {
        return "LoadCorporateComprehensiveRQ{" +
                "Param=" + Param +
                ", Search=" + Search +
                '}';
    }
}
