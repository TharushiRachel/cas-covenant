package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.service.lead.LeadService;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 *
 *
 * @author tharushi
 */

@Data
public class CovAppCommentDTO {

    private Integer commentId;

    private Integer covAppId;

    private String userComment;

    private String createdUserDisplayName;

    private DomainConstants.CoveringApprovalStatus currentStatus;

    private String createdUserId;

    private AppsConstants.Status status;

    private String createdUser;

    private String createdUserDivCode;

    private String assignUserId;

    private String assignUser;

    private String assignUserDisplayName;

    private String assignUserDivCode;

    private AppsConstants.YesNo isPublic;

    private AppsConstants.YesNo isDivisionOnly;

    private AppsConstants.YesNo isUsersOnly;

    private String actionMessage;

    private String assignDepartmentCode;

    private String createdUserUpmCode;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    public CovAppCommentDTO() {
    }

    public CovAppCommentDTO(CovAppComment comment) {
        this.commentId = comment.getCommentId();
        this.covAppId = comment.getCoveringApproval().getCovAppId();
        this.userComment = comment.getUserComment();
        this.createdUserDisplayName = comment.getCreatedUserDisplayName();
        this.currentStatus = comment.getCurrentStatus();
        this.createdUserId = comment.getCreatedUserId();
        this.status = comment.getStatus();
        this.createdUser = comment.getCreatedUser();
        this.createdUserDivCode = comment.getCreatedUserDivCode();
        this.assignUserId = comment.getAssignUserId();
        this.assignUser = comment.getAssignUser();
        this.assignUserDisplayName = comment.getAssignUserDisplayName();
        this.assignUserDivCode = comment.getAssignUserDivCode();
        this.isPublic = comment.getIsPublic();
        this.isDivisionOnly = comment.getIsDivisionOnly();
        this.isUsersOnly = comment.getIsUsersOnly();
        this.actionMessage = comment.getActionMessage();
        this.assignDepartmentCode = comment.getAssignDepartmentCode();
        this.createdUserUpmCode = comment.getCreatedUserUpmCode();
        this.createdBy = comment.getCreatedBy();
        this.createdDate = comment.getCreatedDate();
        this.modifiedBy = comment.getModifiedBy();
        this.modifiedDate = comment.getModifiedDate();
    }
}
