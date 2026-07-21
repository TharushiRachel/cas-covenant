package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerHistorySummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.MonthYearResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MonthYearResponseDTO implements Serializable {

    private String sno;
    private String rowNum;
    private String relationID;
    private String fromMonthYear;
    private String toMonthYear;
    private String activeRootID;
    private String bureauCurrency;
    private String bureauAccStatus;
    private String rank;
    private List<ConsumerHistorySummaryResponseDTO> consumerHistorySummaryResponsesDtos;

    public MonthYearResponseDTO() {
    }


    public MonthYearResponseDTO(MonthYearResponse monthYearResponse) {
        this.sno = monthYearResponse.getSno();
        this.rowNum = monthYearResponse.getRowNum();
        this.relationID = monthYearResponse.getRelationID();
        this.fromMonthYear = monthYearResponse.getFromMonthYear();
        this.toMonthYear = monthYearResponse.getToMonthYear();
        this.activeRootID = monthYearResponse.getActiveRootID();
        this.bureauCurrency = monthYearResponse.getBureauCurrency();
        this.bureauAccStatus = monthYearResponse.getBureauAccStatus();
        this.rank = monthYearResponse.getRank();

        if (monthYearResponse.getConsumerHistorySummaryResponses() != null) {
            for (ConsumerHistorySummaryResponse consumerHistorySummaryResponse : monthYearResponse.getConsumerHistorySummaryResponses()) {
                ConsumerHistorySummaryResponseDTO consumerHistorySummaryResponseDTO = new ConsumerHistorySummaryResponseDTO(consumerHistorySummaryResponse);
                this.getConsumerHistorySummaryResponsesDtos().add(consumerHistorySummaryResponseDTO);
            }
        }
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getRowNum() {
        return rowNum;
    }

    public void setRowNum(String rowNum) {
        this.rowNum = rowNum;
    }

    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    public String getFromMonthYear() {
        return fromMonthYear;
    }

    public void setFromMonthYear(String fromMonthYear) {
        this.fromMonthYear = fromMonthYear;
    }

    public String getToMonthYear() {
        return toMonthYear;
    }

    public void setToMonthYear(String toMonthYear) {
        this.toMonthYear = toMonthYear;
    }

    public String getActiveRootID() {
        return activeRootID;
    }

    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    public String getBureauCurrency() {
        return bureauCurrency;
    }

    public void setBureauCurrency(String bureauCurrency) {
        this.bureauCurrency = bureauCurrency;
    }

    public String getBureauAccStatus() {
        return bureauAccStatus;
    }

    public void setBureauAccStatus(String bureauAccStatus) {
        this.bureauAccStatus = bureauAccStatus;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public List<ConsumerHistorySummaryResponseDTO> getConsumerHistorySummaryResponsesDtos() {
        if (this.consumerHistorySummaryResponsesDtos == null) {
            this.consumerHistorySummaryResponsesDtos = new ArrayList<>();
        }
        return consumerHistorySummaryResponsesDtos;
    }

    public void setConsumerHistorySummaryResponsesDtos(List<ConsumerHistorySummaryResponseDTO> consumerHistorySummaryResponsesDtos) {
        this.consumerHistorySummaryResponsesDtos = consumerHistorySummaryResponsesDtos;
    }
}
