package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CreditDetailsResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.MasterCreditDetailsResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.MonthYearResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MasterCreditDetailsResponseDTO implements Serializable {

    private String relationID;

    private String activeRootID;

    private String serialNumber;

    private List<CreditDetailsResponseDTO> creditDetailsResponsesDtos;

    private List<MonthYearResponseDTO> monthYearResponsesDtos;

    public MasterCreditDetailsResponseDTO() {
    }

    public MasterCreditDetailsResponseDTO(MasterCreditDetailsResponse masterCreditDetailsResponse) {
        this.relationID = masterCreditDetailsResponse.getRelationID();
        this.activeRootID = masterCreditDetailsResponse.getActiveRootID();
        this.serialNumber = masterCreditDetailsResponse.getSerialNumber();
        if (masterCreditDetailsResponse.getCreditDetailsResponses() != null) {
            for (CreditDetailsResponse creditDetailsResponse : masterCreditDetailsResponse.getCreditDetailsResponses()) {
                CreditDetailsResponseDTO creditDetailsResponseDTO = new CreditDetailsResponseDTO(creditDetailsResponse);
                this.getCreditDetailsResponsesDtos().add(creditDetailsResponseDTO);
            }
        }

        if (masterCreditDetailsResponse.getMonthYearResponses() != null) {
            for (MonthYearResponse monthYearResponse : masterCreditDetailsResponse.getMonthYearResponses()) {
                MonthYearResponseDTO monthYearResponseDTO = new MonthYearResponseDTO(monthYearResponse);
                this.getMonthYearResponsesDtos().add(monthYearResponseDTO);
            }
        }

    }

    public String getRelationID() {
        return relationID;
    }

    public void setRelationID(String relationID) {
        this.relationID = relationID;
    }

    public String getActiveRootID() {
        return activeRootID;
    }

    public void setActiveRootID(String activeRootID) {
        this.activeRootID = activeRootID;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public List<CreditDetailsResponseDTO> getCreditDetailsResponsesDtos() {
        if (creditDetailsResponsesDtos == null) {
            creditDetailsResponsesDtos = new ArrayList<>();
        }
        return creditDetailsResponsesDtos;
    }

    public void setCreditDetailsResponsesDtos(List<CreditDetailsResponseDTO> creditDetailsResponsesDtos) {
        this.creditDetailsResponsesDtos = creditDetailsResponsesDtos;
    }

    public List<MonthYearResponseDTO> getMonthYearResponsesDtos() {
        if (monthYearResponsesDtos == null) {
            this.monthYearResponsesDtos = new ArrayList<>();
        }
        return monthYearResponsesDtos;
    }

    public void setMonthYearResponsesDtos(List<MonthYearResponseDTO> monthYearResponsesDtos) {
        this.monthYearResponsesDtos = monthYearResponsesDtos;
    }
}
