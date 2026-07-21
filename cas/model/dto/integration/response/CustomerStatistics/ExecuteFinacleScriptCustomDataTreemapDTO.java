package com.itechro.cas.model.dto.integration.response.CustomerStatistics;

import java.io.Serializable;
import java.util.List;

public class ExecuteFinacleScriptCustomDataTreemapDTO implements Serializable {

    private String accNum;

    private String accCrncy;

    private List<ExecuteFinacleScriptCustomDataDTO> tranData;

    public ExecuteFinacleScriptCustomDataTreemapDTO() {

    }

    public ExecuteFinacleScriptCustomDataTreemapDTO(AccTrainData executeFinacleScriptCustomData) {

        this.accNum = executeFinacleScriptCustomData.getAccNum();
        this.accCrncy = executeFinacleScriptCustomData.getAccCrncy();

    }

    public String getAccNum() {
        return accNum;
    }

    public void setAccNum(String accNum) {
        this.accNum = accNum;
    }

    public String getAccCrncy() {
        return accCrncy;
    }

    public void setAccCrncy(String accCrncy) {
        this.accCrncy = accCrncy;
    }

    public List<ExecuteFinacleScriptCustomDataDTO> getTranData() {
        return tranData;
    }

    public void setTranData(List<ExecuteFinacleScriptCustomDataDTO> tranData) {
        this.tranData = tranData;
    }
}
