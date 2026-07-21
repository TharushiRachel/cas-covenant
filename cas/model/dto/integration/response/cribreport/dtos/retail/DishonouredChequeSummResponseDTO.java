package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.DishonouredChequeSummResponse;

import java.io.Serializable;

public class DishonouredChequeSummResponseDTO implements Serializable {

    private String noOfDC;

    private String totalAmountOfDC;

    public DishonouredChequeSummResponseDTO() {
    }

    public DishonouredChequeSummResponseDTO(DishonouredChequeSummResponse dishonouredChequeSummResponse) {
        this.noOfDC = dishonouredChequeSummResponse.getNoOfDC();
        this.totalAmountOfDC = dishonouredChequeSummResponse.getTotalAmountOfDC();
    }

    public String getNoOfDC() {
        return noOfDC;
    }

    public void setNoOfDC(String noOfDC) {
        this.noOfDC = noOfDC;
    }

    public String getTotalAmountOfDC() {
        return totalAmountOfDC;
    }

    public void setTotalAmountOfDC(String totalAmountOfDC) {
        this.totalAmountOfDC = totalAmountOfDC;
    }
}
