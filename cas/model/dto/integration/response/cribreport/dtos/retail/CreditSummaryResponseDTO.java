package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CreditSummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CurrencyResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreditSummaryResponseDTO implements Serializable {

    private List<CurrencyResponseDTO> currencyResponseDTOList;

    public CreditSummaryResponseDTO() {
    }

    public CreditSummaryResponseDTO(CreditSummaryResponse creditSummaryResponse) {
        if (creditSummaryResponse.getCurrencyResponseList() != null) {
            for (CurrencyResponse currencyResponse : creditSummaryResponse.getCurrencyResponseList()) {
                this.getCurrencyResponseDTOList().add(new CurrencyResponseDTO(currencyResponse));
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
