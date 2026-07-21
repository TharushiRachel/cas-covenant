package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ShareHolderDetailUpdateDTO implements Serializable {

    private Integer facilityPaperID;

    private List<FPShareHolderDetailDTO> fpShareHolderDetailDTOList;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public List<FPShareHolderDetailDTO> getFpShareHolderDetailDTOList() {
        if (fpShareHolderDetailDTOList == null) {
            fpShareHolderDetailDTOList = new ArrayList<>();
        }
        return fpShareHolderDetailDTOList;
    }

    public void setFpShareHolderDetailDTOList(List<FPShareHolderDetailDTO> fpShareHolderDetailDTOList) {
        this.fpShareHolderDetailDTOList = fpShareHolderDetailDTOList;
    }

    @Override
    public String toString() {
        return "ShareHolderDetailUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", fpShareHolderDetailDTOList=" + fpShareHolderDetailDTOList +
                '}';
    }
}
