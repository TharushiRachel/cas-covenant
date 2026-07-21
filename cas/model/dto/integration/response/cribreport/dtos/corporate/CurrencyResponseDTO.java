package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CurrencyResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.SummaryResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrencyResponseDTO implements Serializable {

    private String bureauCurrency;

    private String priority;

    private String monthYear;

    private List<SummaryResponseDTO> summaryResponseDTOList;

    public CurrencyResponseDTO() {
    }

    public CurrencyResponseDTO(CurrencyResponse currencyResponse) {
        if (currencyResponse != null) {
            this.bureauCurrency = currencyResponse.getBureauCurrency();
            this.priority = currencyResponse.getPriority();
            this.monthYear = currencyResponse.getMonthYear();

            if (currencyResponse.getSummary() != null) {
                for (SummaryResponse summaryResponse : currencyResponse.getSummary()) {
                    SummaryResponseDTO summaryResponseDTO = new SummaryResponseDTO(summaryResponse);
                    this.getSummaryResponseDTOList().add(summaryResponseDTO);
                }
            }
        }
    }

    public String getBureauCurrency() {
        return bureauCurrency;
    }

    public void setBureauCurrency(String bureauCurrency) {
        this.bureauCurrency = bureauCurrency;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getMonthYear() {
        return monthYear;
    }

    public void setMonthYear(String monthYear) {
        this.monthYear = monthYear;
    }

    public List<SummaryResponseDTO> getSummaryResponseDTOList() {
        if (summaryResponseDTOList == null) {
            this.summaryResponseDTOList = new ArrayList<>();
        }
        return summaryResponseDTOList;
    }

    public void setSummaryResponseDTOList(List<SummaryResponseDTO> summaryResponseDTOList) {
        this.summaryResponseDTOList = summaryResponseDTOList;
    }
}
