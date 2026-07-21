package com.itechro.cas.model.domain.common;

import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;
import java.util.Date;

public interface Approvable extends Serializable {

    public DomainConstants.MasterDataApproveStatus getApproveStatus();

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus);

    public Date getApprovedDate();

    public void setApprovedDate(Date approvedDate);

    public String getApprovedBy();

    public void setApprovedBy(String approvedBy);

    public Integer getParentRecordID();

    public void setParentRecordID(Integer parentRecordID);
}
