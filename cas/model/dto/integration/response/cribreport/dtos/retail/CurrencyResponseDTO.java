package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CurrencyResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.SummaryResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CurrencyResponseDTO implements Serializable {

    private String bureauCurrency;

    private String priority;

    private String monthYear;

    private List<SummaryResponseDTO> summaryResponsesDtos;

    public CurrencyResponseDTO() {
    }

    public CurrencyResponseDTO(CurrencyResponse currencyResponse) {
        this.bureauCurrency = currencyResponse.getBureauCurrency();
        this.priority = currencyResponse.getPriority();
        this.monthYear = currencyResponse.getMonthYear();
        if (currencyResponse.getSummaryResponses() != null) {
            for (SummaryResponse summaryResponse : currencyResponse.getSummaryResponses()) {
                SummaryResponseDTO summaryResponseDTO = new SummaryResponseDTO(summaryResponse);
                this.getSummaryResponsesDtos().add(summaryResponseDTO);
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

    public List<SummaryResponseDTO> getSummaryResponsesDtos() {
        if (summaryResponsesDtos == null) {
            this.summaryResponsesDtos = new ArrayList<>();
        }
        return summaryResponsesDtos;
    }

    public void setSummaryResponsesDtos(List<SummaryResponseDTO> summaryResponsesDtos) {
        this.summaryResponsesDtos = summaryResponsesDtos;
    }
}
