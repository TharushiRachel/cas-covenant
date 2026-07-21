package com.itechro.cas.model.dto.applicationform;

import java.io.Serializable;

public class DraftApplicationFormRQ extends ApplicationFormDTO implements Serializable {

    private String remark;
    private Integer leadID;
    private String fsType;

    public DraftApplicationFormRQ() {
    }

    public Integer getLeadID() {
        return leadID;
    }

    public void setLeadID(Integer leadID) {
        this.leadID = leadID;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getFsType() {
        return fsType;
    }

    public void setFsType(String fsType) {
        this.fsType = fsType;
    }

    @Override
    public String toString() {
        return "DraftApplicationFormRQ{" +
                "remark='" + remark + '\'' +
                ", leadID=" + leadID +
                ", fsType=" + fsType +
                '}';
    }
}
