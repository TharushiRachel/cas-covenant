package com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "cas_stat",
        "cas_chq_return"
})
@JsonIgnoreProperties(ignoreUnknown = true)
public class CasStatResponse implements Serializable {

    @JsonProperty("cas_stat")
    private List<CasStat> casStats;

    @JsonProperty("cas_chq_return")
    private List<CasChqReturn> casChqReturns;

    @JsonProperty("cas_stat")
    public List<CasStat> getCasStats() {
        if (casStats == null) {
            casStats = new ArrayList<>();
        }
        return casStats;
    }

    @JsonProperty("cas_stat")
    public void setCasStats(List<CasStat> casStats) {
        this.casStats = casStats;
    }

    @JsonProperty("cas_chq_return")
    public List<CasChqReturn> getCasChqReturns() {
        if (casChqReturns == null) {
            casChqReturns = new ArrayList<>();
        }
        return casChqReturns;
    }

    @JsonProperty("cas_chq_return")
    public void setCasChqReturns(List<CasChqReturn> casChqReturns) {
        this.casChqReturns = casChqReturns;
    }
}
