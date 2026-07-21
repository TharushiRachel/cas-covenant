package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.advancedportfolio;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.advancedportfolio.AdvancedPortfolio;
import com.itechro.cas.model.dto.integration.response.AccountStat.responses.advancedportfolio.AdvancedPortfolioResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AdvancePortfolioResponseDTO implements Serializable {

    private List<AdvancedPortfolioDTO> advancedPortfolioDTOS;

    private boolean success;

    public AdvancePortfolioResponseDTO() {
    }

    public AdvancePortfolioResponseDTO(AdvancedPortfolioResponse advancedPortfolioResponse) {
        for (AdvancedPortfolio advancedPortfolio : advancedPortfolioResponse.getAdvancedPortfolios()) {
            AdvancedPortfolioDTO advancedPortfolioDTO = new AdvancedPortfolioDTO(advancedPortfolio);
            getAdvancedPortfolioDTOS().add(advancedPortfolioDTO);
        }
    }

    public List<AdvancedPortfolioDTO> getAdvancedPortfolioDTOS() {
        if (advancedPortfolioDTOS == null) {
            this.advancedPortfolioDTOS = new ArrayList<>();
        }
        return advancedPortfolioDTOS;
    }

    public void setAdvancedPortfolioDTOS(List<AdvancedPortfolioDTO> advancedPortfolioDTOS) {
        this.advancedPortfolioDTOS = advancedPortfolioDTOS;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "AdvancePortfolioResponseDTO{" +
                "advancedPortfolioDTOS=" + advancedPortfolioDTOS +
                ", success=" + success +
                '}';
    }
}
