package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.model.domain.casmaster.CACommittee;
import com.itechro.cas.model.domain.casmaster.CACommitteeComment;
import com.itechro.cas.model.domain.casmaster.CACommitteeCommentTemp;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
public class CACommitteeCommentDTO {

    private Integer commentId;

    private Integer committeeId;

    private String comment;

    private String commentStatus;

    private String userDisplayName;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    public CACommitteeCommentDTO() {
    }

    public CACommitteeCommentDTO(CACommitteeComment caCommitteeComment){
        this.commentId = caCommitteeComment.getCommentId();
        if(caCommitteeComment.getCaCommittee() != null)
        {
            this.committeeId = caCommitteeComment.getCaCommittee().getCommitteeId();
        }
        this.comment = caCommitteeComment.getComment();
        this.userDisplayName = caCommitteeComment.getUserDisplayName();
        this.commentStatus = caCommitteeComment.getCommentStatus();
        this.createdBy = caCommitteeComment.getCreatedBy();
        this.createdDate = caCommitteeComment.getCreatedDate();

    }

    public CACommitteeCommentDTO(CACommitteeCommentTemp caCommitteeComment){
        this.commentId = caCommitteeComment.getCommentId();
        if(caCommitteeComment.getCaCommitteeTemp() != null)
        {
            this.committeeId = caCommitteeComment.getCaCommitteeTemp().getCommitteeId();
        }
        this.comment = caCommitteeComment.getComment();
        this.userDisplayName = caCommitteeComment.getUserDisplayName();
        this.commentStatus = caCommitteeComment.getCommentStatus();
        this.createdBy = caCommitteeComment.getCreatedBy();
        this.createdDate = caCommitteeComment.getCreatedDate();

    }
}
