package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.UserPool;
import com.itechro.cas.util.CalendarUtil;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserPoolDTO implements Serializable {

    private Integer userId;

    private Integer poolId;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private Integer parentRecordID;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    private String userName;

    private String userDisplayName;

    private AppsConstants.Status userStatus;

    private String groupCode;

    private String referenceName;

    public UserPoolDTO() {
    }

    public UserPoolDTO(UserPool userPool) {
        this.userId = userPool.getUserId();
        this.userName = userPool.getUserName();
        this.poolId = userPool.getPoolId();
        this.userDisplayName = userPool.getUserDisplayName();
        this.userStatus = userPool.getUserStatus();
        this.approveStatus = userPool.getApproveStatus();
        this.groupCode = userPool.getGroupCode();
        this.referenceName = userPool.getReferenceName();
        this.parentRecordID = userPool.getParentRecordID();

        if (userPool.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(userPool.getApprovedDate());
        }

        this.approvedBy = userPool.getApprovedBy();
        if(userPool.getCreatedDate()!=null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(userPool.getCreatedDate());
        }
        this.createdBy = userPool.getCreatedBy();
        if(userPool.getModifiedDate()!= null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(userPool.getModifiedDate());
        }
        this.modifiedBy = userPool.getModifiedBy();
    }
}
