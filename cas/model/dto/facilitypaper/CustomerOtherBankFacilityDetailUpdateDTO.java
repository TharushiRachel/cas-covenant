package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerOtherBankFacilityDetailUpdateDTO implements Serializable {

    private Integer facilityPaperID;

    private Integer casCustomerID;

    private List<CASCustomerOtherBankFacilityDTO> casCustomerOtherBankFacilityDTOList;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public List<CASCustomerOtherBankFacilityDTO> getCasCustomerOtherBankFacilityDTOList() {
        if (casCustomerOtherBankFacilityDTOList == null) {
            casCustomerOtherBankFacilityDTOList = new ArrayList<>();
        }
        return casCustomerOtherBankFacilityDTOList;
    }

    public void setCasCustomerOtherBankFacilityDTOList(List<CASCustomerOtherBankFacilityDTO> casCustomerOtherBankFacilityDTOList) {
        this.casCustomerOtherBankFacilityDTOList = casCustomerOtherBankFacilityDTOList;
    }

    @Override
    public String toString() {
        return "CustomerOtherBankFacilityDetailUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", casCustomerID=" + casCustomerID +
                ", casCustomerOtherBankFacilityDTOList=" + casCustomerOtherBankFacilityDTOList +
                '}';
    }
}
