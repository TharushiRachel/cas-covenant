package com.itechro.cas.model.dto.customer;

import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperSummaryDTO;

import java.io.Serializable;
import java.util.List;

public class Customer360DTO extends CustomerDTO implements Serializable {

    private List<FacilityPaperSummaryDTO> facilityPaperSummaryDTOList;
    private String errorMessage;

    private String status;

//    public Customer360DTO(String errorMessage) {
//    }

    public List<FacilityPaperSummaryDTO> getFacilityPaperSummaryDTOList() {
        return facilityPaperSummaryDTOList;
    }

    public void setFacilityPaperSummaryDTOList(List<FacilityPaperSummaryDTO> facilityPaperSummaryDTOList) {
        this.facilityPaperSummaryDTOList = facilityPaperSummaryDTOList;
    }

    public Customer360DTO() {
    }

    public Customer360DTO(Customer customer, List<FacilityPaperSummaryDTO> facilityPaperSummaryDTOList) {
        super(customer);
        this.facilityPaperSummaryDTOList = facilityPaperSummaryDTOList;
    }

    public Customer360DTO(String errorMessage) {
        this.errorMessage = errorMessage;
        this.status = "error";
    }

}
