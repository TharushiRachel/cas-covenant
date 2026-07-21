package com.itechro.cas.model.domain.common;

import com.itechro.cas.commons.constants.DomainConstants;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class ApprovableEntity extends UserTrackableEntity implements Approvable {

    @Enumerated(EnumType.STRING)
    @Column(name = "APPROVE_STATUS")
    private DomainConstants.MasterDataApproveStatus approveStatus;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "APPROVED_DATE")
    private Date approvedDate;

    @Column(name = "APPROVED_BY")
    private String approvedBy;

    @Column(name = "PARENT_REC_ID")
    private Integer parentRecordID;

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public Date getApprovedDate() {
        return approvedDate;
    }

    public void setApprovedDate(Date approvedDate) {
        this.approvedDate = approvedDate;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public Integer getParentRecordID() {
        return parentRecordID;
    }

    public void setParentRecordID(Integer parentRecordID) {
        this.parentRecordID = parentRecordID;
    }
}
