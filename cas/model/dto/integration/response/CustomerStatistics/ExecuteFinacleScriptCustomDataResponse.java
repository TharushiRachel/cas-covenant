package com.itechro.cas.model.dto.integration.response.CustomerStatistics;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.advancedportfolio.AdvancedPortfolio;

import java.io.Serializable;
import java.util.List;

public class ExecuteFinacleScriptCustomDataResponse implements Serializable {

    @JsonProperty("executeFinacleScript_CustomData")
    private ExecuteFinacleScriptCustomData executeFinacleScriptCustomData;

    @JsonProperty("executeFinacleScript_CustomData")
    public ExecuteFinacleScriptCustomData getExecuteFinacleScriptCustomData() {
        return executeFinacleScriptCustomData;
    }

    @JsonProperty("executeFinacleScript_CustomData")
    public void setExecuteFinacleScriptCustomData(ExecuteFinacleScriptCustomData executeFinacleScriptCustomData) {
        this.executeFinacleScriptCustomData = executeFinacleScriptCustomData;
    }
}
