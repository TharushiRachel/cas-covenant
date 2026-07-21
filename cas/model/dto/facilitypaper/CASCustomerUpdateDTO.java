package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CASCustomerUpdateDTO implements Serializable {

    private Integer facilityPaperID;

    private List<CASCustomerDTO> casCustomerDTOList;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public List<CASCustomerDTO> getCasCustomerDTOList() {
        if (casCustomerDTOList == null) {
            casCustomerDTOList = new ArrayList<>();
        }
        return casCustomerDTOList;
    }

    public void setCasCustomerDTOList(List<CASCustomerDTO> casCustomerDTOList) {
        this.casCustomerDTOList = casCustomerDTOList;
    }

    @Override
    public String toString() {
        return "CASCustomerUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", casCustomerDTOList=" + casCustomerDTOList +
                '}';
    }
}
