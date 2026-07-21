package com.itechro.cas.model.dto.facilitypaper.facilitypaperreview;

import com.itechro.cas.model.common.PagedParamDTO;

import java.util.ArrayList;
import java.util.List;

public class FPReviewerCommentSearchRQ extends PagedParamDTO {

    private Integer facilityPaperID;

    private Integer upmID;

    private List<String> paperReviewStatusList;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    public List<String> getPaperReviewStatusList() {
        if(this.paperReviewStatusList == null){
            this.paperReviewStatusList = new ArrayList<String>();
        }
        return paperReviewStatusList;
    }

    public void setPaperReviewStatusList(List<String> paperReviewStatusList) {
        this.paperReviewStatusList = paperReviewStatusList;
    }
}
