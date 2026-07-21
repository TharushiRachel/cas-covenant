package com.itechro.cas.model.dto.das;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.das.DALimit;
import com.itechro.cas.model.domain.das.DALimitTemp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class DALimitDTO {

    private Integer daLimitsId = 0;

    private Integer designationId;

    private Integer columnId;

    private Double riskValue;

    private AppsConstants.Status status;

    private String authorizerDisplayName;

    private String riskRating;


    private String approveStatus;
    private String approvedBy;
    private Date approvedDate;
    private String createdBy;
    private Date createdDate;

    private Date modifiedDate;

    private String modifiedBy;

    private String isCommittee;

    public DALimitDTO() {}


    public DALimitDTO(DALimitTemp daLimitTemp) {
        this.daLimitsId = daLimitTemp.getDaLimitsId();
        this.designationId = daLimitTemp.getDesignationId();
        this.columnId = daLimitTemp.getColumnId();
        this.riskValue = daLimitTemp.getRiskValue();
        this.status = daLimitTemp.getStatus();
        this.authorizerDisplayName = daLimitTemp.getAuthorizerDisplayName();
        this.riskRating = daLimitTemp.getRiskRating();
        this.approveStatus = daLimitTemp.getApproveStatus().getDescription();
        this.approvedBy = daLimitTemp.getApprovedBy();
        this.approvedDate = daLimitTemp.getApprovedDate();
        this.createdBy = daLimitTemp.getCreatedBy();
        this.createdDate = daLimitTemp.getCreatedDate();
        this.modifiedBy = daLimitTemp.getModifiedBy();
        this.modifiedDate = daLimitTemp.getModifiedDate();
        this.isCommittee = daLimitTemp.getIsCommittee();
    }

    public DALimitDTO(DALimit daLimit) {
        this.daLimitsId = daLimit.getDaLimitsId();
        this.designationId = daLimit.getDesignationId();
        this.columnId = daLimit.getColumnId();
        this.riskValue = daLimit.getRiskValue();
        this.status = daLimit.getStatus();
        this.authorizerDisplayName = daLimit.getAuthorizerDisplayName();
        this.riskRating = daLimit.getRiskRating();
        this.approveStatus = daLimit.getApproveStatus().getDescription();
        this.approvedBy = daLimit.getApprovedBy();
        this.approvedDate = daLimit.getApprovedDate();
        this.createdBy = daLimit.getCreatedBy();
        this.createdDate = daLimit.getCreatedDate();
        this.modifiedBy = daLimit.getModifiedBy();
        this.modifiedDate = daLimit.getModifiedDate();
        this.isCommittee = daLimit.getIsCommittee();
    }
}
