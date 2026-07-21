package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CACommitteeTemp;
import com.itechro.cas.util.CalendarUtil;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CACommitteeDTO implements Serializable {

    private Integer committeeId;

    private String committeeName;

    private String delegatedAuthority;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private Integer parentRecordID;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private String reviewer;

    private AppsConstants.CAPathType currentPath;

    private Integer committeeTypeId;

    private String committeeTypeName;

    private AppsConstants.CACommitteeStatus committeeStatus;

    private List<CALevelDTO> caLevelDTOList;

    private String tableType;

    private List<CACommitteeCommentDTO> caCommitteeCommentDTOS;

    private String comment;

    private String userDisplayName;

    private List<CAUserDTO> deletedLevelUsers;

    public CACommitteeDTO() {
    }

   /* public CACommitteeDTO(CACommittee subCommittee) {
        this.committeeId = subCommittee.getCommitteeId();
        this.committeeName = subCommittee.getCommitteeName();
        this.delegatedAuthority = subCommittee.getDelegatedAuthority();
        this.status = subCommittee.getStatus();
        if(subCommittee.getCommitteeType() != null){
            this.committeeId = subCommittee.getCommitteeType().getCommitteeTypeId();
            this.committeeName = subCommittee.getCommitteeType().getCommitteeTypeName();
        }

        if(subCommittee.getApprovedDate() != null){
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(subCommittee.getApprovedDate());
        }
        this.approvedBy = subCommittee.getApprovedBy();
        this.approveStatus = subCommittee.getApproveStatus();

        if (subCommittee.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(subCommittee.getCreatedDate());
        }
        this.createdBy = subCommittee.getCreatedBy();

        if (subCommittee.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(subCommittee.getModifiedDate());
        }
        this.modifiedBy = subCommittee.getModifiedBy();
    }*/

    public CACommitteeDTO(CACommitteeTemp subCommittee) {
        this.committeeId = subCommittee.getCommitteeId();
        this.committeeName = subCommittee.getCommitteeName();
        this.delegatedAuthority = subCommittee.getDelegatedAuthority();
        this.status = subCommittee.getStatus();
        if(subCommittee.getCommitteeType() != null){
            this.committeeId = subCommittee.getCommitteeType().getCommitteeTypeId();
            this.committeeName = subCommittee.getCommitteeType().getCommitteeTypeName();
        }

        if(subCommittee.getApprovedDate() != null){
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(subCommittee.getApprovedDate());
        }
        this.approvedBy = subCommittee.getApprovedBy();
        this.approveStatus = subCommittee.getApproveStatus();

        if (subCommittee.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(subCommittee.getCreatedDate());
        }
        this.createdBy = subCommittee.getCreatedBy();

        if (subCommittee.getModifiedDate() != null) {
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(subCommittee.getModifiedDate());
        }
        this.modifiedBy = subCommittee.getModifiedBy();
        this.currentPath = subCommittee.getCurrentPath();
    }
}
