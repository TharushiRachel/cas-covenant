package com.itechro.cas.model.dto.integration.response.AccountStat.responses.advancedportfolio;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class AdvancedPortfolioResponse implements Serializable {

    @JsonProperty("cas_advancedportfolio")
    private List<AdvancedPortfolio> advancedPortfolios;

    @JsonProperty("cas_advancedportfolio")
    public List<AdvancedPortfolio> getAdvancedPortfolios() {
        return advancedPortfolios;
    }

    @JsonProperty("cas_advancedportfolio")
    public void setAdvancedPortfolios(List<AdvancedPortfolio> advancedPortfolios) {
        this.advancedPortfolios = advancedPortfolios;
    }
}
