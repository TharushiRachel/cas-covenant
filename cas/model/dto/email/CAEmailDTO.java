package com.itechro.cas.model.dto.email;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

import java.io.Serializable;

@Data
public class CAEmailDTO implements Serializable {
    private Integer facilityPaperID;

    private AppsConstants.YesNo isCommittee;

    private String assignDepartmentCode;

    private Boolean isCommitteePaperReturn;

    private Boolean isForwardToCA;

    private String committeePaperReturnUser;

    private String currentAssignUser;

    private Integer currentRegLevelID;

    private Integer currentAltLevelID;

    private String currentPath;

    private String recentComment;

    private String recentCommentedBy;

    private AppsConstants.YesNo isReviewPaper;

    private Integer upcTemplateId;
}
