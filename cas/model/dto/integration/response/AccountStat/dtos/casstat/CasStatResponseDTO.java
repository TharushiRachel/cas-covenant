package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.casstat;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat.CasChqReturn;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat.CasStat;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.casstat.CasStatResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CasStatResponseDTO implements Serializable {

    private List<CasStatDTO> casStats;

    private List<CasChqReturnDTO> casChqReturns;

    private boolean success;

    public CasStatResponseDTO() {
    }

    public CasStatResponseDTO(CasStatResponse casStatResponse) {

        for (CasStat casStat : casStatResponse.getCasStats()) {
            CasStatDTO casStatDTO = new CasStatDTO(casStat);
            this.getCasStats().add(casStatDTO);
        }

        for (CasChqReturn casChqReturn : casStatResponse.getCasChqReturns()) {
            CasChqReturnDTO casChqReturnDTO = new CasChqReturnDTO(casChqReturn);
            this.getCasChqReturns().add(casChqReturnDTO);
        }
    }

    public List<CasStatDTO> getCasStats() {
        if (casStats == null) {
            this.casStats = new ArrayList<>();
        }
        return casStats;
    }

    public void setCasStats(List<CasStatDTO> casStats) {
        this.casStats = casStats;
    }

    public List<CasChqReturnDTO> getCasChqReturns() {
        if (casChqReturns == null) {
            this.casChqReturns = new ArrayList<>();
        }
        return casChqReturns;
    }

    public void setCasChqReturns(List<CasChqReturnDTO> casChqReturns) {
        this.casChqReturns = casChqReturns;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "CasStatResponseDTO{" +
                "casStats=" + casStats +
                ", casChqReturns=" + casChqReturns +
                ", success=" + success +
                '}';
    }
}
