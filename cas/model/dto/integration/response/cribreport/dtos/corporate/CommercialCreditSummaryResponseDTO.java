package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialCreditSummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CurrencyResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialCreditSummaryResponseDTO implements Serializable {

    private List<CurrencyResponseDTO> currencyResponseDTOList;

    public CommercialCreditSummaryResponseDTO() {
    }

    public CommercialCreditSummaryResponseDTO(CommercialCreditSummaryResponse commercialCreditSummaryResponse) {
        if (commercialCreditSummaryResponse != null) {
            if (!commercialCreditSummaryResponse.getCurrencyResponses().isEmpty()) {
                for (CurrencyResponse currencyResponse : commercialCreditSummaryResponse.getCurrencyResponses()) {
                    this.getCurrencyResponseDTOList().add(new CurrencyResponseDTO(currencyResponse));
                }
            }
        }
    }

    public List<CurrencyResponseDTO> getCurrencyResponseDTOList() {
        if (currencyResponseDTOList == null) {
            this.currencyResponseDTOList = new ArrayList<>();
        }
        return currencyResponseDTOList;
    }

    public void setCurrencyResponseDTOList(List<CurrencyResponseDTO> currencyResponseDTOList) {
        this.currencyResponseDTOList = currencyResponseDTOList;
    }
}
