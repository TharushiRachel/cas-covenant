package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DirectorDetailUpdateDTO implements Serializable {

    private Integer facilityPaperID;

    private List<FPDirectorDetailDTO> fpDirectorDetailDTOList;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public List<FPDirectorDetailDTO> getFpDirectorDetailDTOList() {
        if (fpDirectorDetailDTOList == null) {
            fpDirectorDetailDTOList = new ArrayList<>();
        }
        return fpDirectorDetailDTOList;
    }

    public void setFpDirectorDetailDTOList(List<FPDirectorDetailDTO> fpDirectorDetailDTOList) {
        this.fpDirectorDetailDTOList = fpDirectorDetailDTOList;
    }

    @Override
    public String toString() {
        return "DirectorDetailUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", fpDirectorDetailDTOList=" + fpDirectorDetailDTOList +
                '}';
    }
}
