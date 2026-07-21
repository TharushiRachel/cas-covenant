package com.itechro.cas.model.dto.das;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.das.DALimit;
import com.itechro.cas.model.domain.das.DALimitTemp;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@ToString
public class DALimitsWithApproveStatusDTO {

    private String authStatus;

    private Integer daLimitsId;

    private Integer designationId;

    private Integer columnId;

    private Double riskValue;

    private AppsConstants.Status status;


    private Integer parentRecId;


    private Date createdDate;


    private String createdBy;


    private Date modifiedDate;


    private String modifiedBy;


    private Date approvedDate;


    private String approvedBy;


    private String approveStatus;


    private String authorizerDisplayName;


    private String riskRating;


    private String version;


    public DALimitsWithApproveStatusDTO(String authStatus, Integer daLimitsId, Integer designationId, Integer columnId, Double riskValue,
                                        AppsConstants.Status status, Integer parentRecId, Date createdDate, String createdBy, Date modifiedDate,
                                        String modifiedBy, Date approvedDate, String approvedBy, String approveStatus, String authorizerDisplayName,
                                        String riskRating, String version) {
        this.authStatus = authStatus;
        this.daLimitsId = daLimitsId;
        this.designationId = designationId;
        this.columnId = columnId;
        this.riskValue = riskValue;
        this.status = status;
        this.parentRecId = parentRecId;
        this.createdDate = createdDate;
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate;
        this.modifiedBy = modifiedBy;
        this.approvedDate = approvedDate;
        this.approvedBy = approvedBy;
        this.approveStatus = approveStatus;
        this.authorizerDisplayName = authorizerDisplayName;
        this.riskRating = riskRating;
        this.version = version;
    }


    public DALimitsWithApproveStatusDTO(DALimitTemp daLimitTemp, String authStatus) {
        this.authStatus = authStatus;
        this.daLimitsId = daLimitTemp.getDaLimitsId();
        this.designationId = daLimitTemp.getDesignationId();
        this.columnId = daLimitTemp.getColumnId();
        this.riskValue = daLimitTemp.getRiskValue();
        this.status = daLimitTemp.getStatus();
        this.parentRecId = daLimitTemp.getParentRecordID();
        this.createdDate = daLimitTemp.getCreatedDate();
        this.createdBy = daLimitTemp.getCreatedBy();
        this.modifiedDate = daLimitTemp.getModifiedDate();
        this.modifiedBy = daLimitTemp.getModifiedBy();
        this.approvedDate = daLimitTemp.getApprovedDate();
        this.approvedBy = daLimitTemp.getApprovedBy();
        this.approveStatus = daLimitTemp.getApproveStatus().getDescription();
        this.authorizerDisplayName = daLimitTemp.getAuthorizerDisplayName();
        this.riskRating = daLimitTemp.getRiskRating();
    }




    public DALimitsWithApproveStatusDTO(DALimit daLimitTemp, String authStatus) {
        this.authStatus = authStatus;
        this.daLimitsId = daLimitTemp.getDaLimitsId();
        this.designationId = daLimitTemp.getDesignationId();
        this.columnId = daLimitTemp.getColumnId();
        this.riskValue = daLimitTemp.getRiskValue();
        this.status = daLimitTemp.getStatus();
        this.parentRecId = daLimitTemp.getParentRecordID();
        this.createdDate = daLimitTemp.getCreatedDate();
        this.createdBy = daLimitTemp.getCreatedBy();
        this.modifiedDate = daLimitTemp.getModifiedDate();
        this.modifiedBy = daLimitTemp.getModifiedBy();
        this.approvedDate = daLimitTemp.getApprovedDate();
        this.approvedBy = daLimitTemp.getApprovedBy();
        this.approveStatus = daLimitTemp.getApproveStatus().getDescription();
        this.authorizerDisplayName = daLimitTemp.getAuthorizerDisplayName();
        this.riskRating = daLimitTemp.getRiskRating();
    }
}
