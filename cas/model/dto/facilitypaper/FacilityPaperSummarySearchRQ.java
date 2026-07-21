package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.model.common.PagedParamDTO;

public class FacilityPaperSummarySearchRQ extends PagedParamDTO {

    private Integer customerID;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    @Override
    public String toString() {
        return "FacilityPaperSummarySearchRQ{" +
                "customerID=" + customerID +
                '}';
    }
}
