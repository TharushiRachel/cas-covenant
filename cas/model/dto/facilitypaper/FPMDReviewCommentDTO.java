package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.FPMDReviewComment;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.util.CalendarUtil;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

import static com.itechro.cas.util.CalendarUtil.CRM_DATE_FORMAT;

@Data
public class FPMDReviewCommentDTO {

    private Integer fpCommentID;

    private Integer facilityPaperId;

    private String comment;

    private AppsConstants.YesNo isView;

    private String createdDate;

    private String createdBy;

    private Integer createdUserWC;

    private String createdUserDisplayName;

    public FPMDReviewCommentDTO() {
    }

    public FPMDReviewCommentDTO(FPMDReviewComment fpmdReviewComment) {
        this.fpCommentID = fpmdReviewComment.getFpCommentID();
        this.facilityPaperId = fpmdReviewComment.getFacilityPaper().getFacilityPaperID();
        this.comment = fpmdReviewComment.getComment();
        this.isView = fpmdReviewComment.getIsView();
        if (fpmdReviewComment.getCreatedDate() != null) {
            this.createdDate = CalendarUtil.getFormattedDate(fpmdReviewComment.getCreatedDate(), CRM_DATE_FORMAT);
        }
        this.createdBy = fpmdReviewComment.getCreatedBy();
        this.createdUserWC = fpmdReviewComment.getCreatedUserWC();
        this.createdUserDisplayName = fpmdReviewComment.getCreatedUserDisplayName();
    }
}
