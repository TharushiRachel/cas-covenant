package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommentUpdateDTO implements Serializable {

    private Integer facilityPaperID;

    private String displayName;

    private Integer upmID;

    private List<FPCommentDTO> fpCommentDTOList;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public List<FPCommentDTO> getFpCommentDTOList() {
        if(fpCommentDTOList == null){
            fpCommentDTOList = new ArrayList<>();
        }
        return fpCommentDTOList;
    }

    public void setFpCommentDTOList(List<FPCommentDTO> fpCommentDTOList) {
        this.fpCommentDTOList = fpCommentDTOList;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    @Override
    public String toString() {
        return "CommentUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", displayName='" + displayName + '\'' +
                ", upmID=" + upmID +
                ", fpCommentDTOList=" + fpCommentDTOList +
                '}';
    }
}
