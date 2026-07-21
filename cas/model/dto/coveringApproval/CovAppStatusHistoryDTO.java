package com.itechro.cas.model.dto.coveringApproval;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.coveringApproval.CovAppStatusHistory;
import lombok.Data;

import java.util.Date;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CovAppStatusHistoryDTO {

    private Integer statusHistoryID;

    private Integer covAppId;

    private DomainConstants.CoveringApprovalStatus currentStatus;

    private String assignUser;

    private String remark;

    private String updateBy;

    private String updatedUserDisplayName;

    private Date updateDate;

    private String actionMessage;

    private String assignUserID;
    private String assignUserDisplayName;

    private String assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String assignUserDivCode;

    private String assignDepartmentCode;

    private DomainConstants.ForwardType forwardType;

    private String referenceName;

    public CovAppStatusHistoryDTO() {
    }

    public CovAppStatusHistoryDTO(CovAppStatusHistory covAppStatusHistory){
        this.statusHistoryID = covAppStatusHistory.getStatusHistoryID();
        this.covAppId = covAppStatusHistory.getCoveringApproval().getCovAppId();
        this.currentStatus = covAppStatusHistory.getCurrentStatus();
        this.assignUser = covAppStatusHistory.getAssignUser();
        this.remark = covAppStatusHistory.getRemark();
        this.updateBy = covAppStatusHistory.getUpdateBy();
        this.updatedUserDisplayName = covAppStatusHistory.getUpdatedUserDisplayName();
        this.updateDate = covAppStatusHistory.getUpdateDate();
        this.actionMessage = covAppStatusHistory.getActionMessage();
        this.assignUserID = covAppStatusHistory.getAssignUserID();
        this.assignUserDisplayName = covAppStatusHistory.getAssignUserDisplayName();
        this.assignUserUpmID = covAppStatusHistory.getAssignUserUpmID();
        this.assignUserUpmGroupCode = covAppStatusHistory.getAssignUserUpmGroupCode();
        this.assignUserDivCode = covAppStatusHistory.getAssignUserDivCode();
        this.assignDepartmentCode = covAppStatusHistory.getAssignDepartmentCode();
        this.forwardType = covAppStatusHistory.getForwardType();
    }
}
