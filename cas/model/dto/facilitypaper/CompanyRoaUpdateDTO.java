package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CompanyRoaUpdateDTO implements Serializable {

    private Integer facilityPaperID;

    List<FPCompanyRoaDTO> fpCompanyRoaDTOList;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public List<FPCompanyRoaDTO> getFpCompanyRoaDTOList() {
        if(fpCompanyRoaDTOList==null){
            fpCompanyRoaDTOList = new ArrayList<>();
        }
        return fpCompanyRoaDTOList;
    }

    public void setFpCompanyRoaDTOList(List<FPCompanyRoaDTO> fpCompanyRoaDTOList) {
        this.fpCompanyRoaDTOList = fpCompanyRoaDTOList;
    }

    @Override
    public String toString() {
        return "CompanyRoaUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", fpCompanyRoaDTOList=" + fpCompanyRoaDTOList +
                '}';
    }
}
