package com.itechro.cas.model.dto.integration.response.CustomerStatistics;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

public class ExecuteFinacleScriptCustomData implements Serializable {

    @JsonProperty("ACCTRAN")
    private List<AccTrainData> accTrain;

    @JsonProperty("ACCTRAN")
    public List<AccTrainData> getAccTrain() {
        return accTrain;
    }

    @JsonProperty("ACCTRAN")
    public void setAccTrain(List<AccTrainData> accTrain) {
        this.accTrain = accTrain;
    }
}
