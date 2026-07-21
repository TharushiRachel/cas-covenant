package com.itechro.cas.model.dto.audit;

import com.google.gson.annotations.SerializedName;

public class UserDaContentDTO extends BaseContentDTO {

    @SerializedName("USER ID")
    private Integer userDaID;

    @SerializedName("USER NAME")
    private String userName;

    @SerializedName("MAX AMOUNT")
    private String maxAmount;

    @SerializedName("DESCRIPTION")
    private String description;

    @SerializedName("APPROVE STATUS")
    private String approveStatus;

    @SerializedName("APPROVE DATE")
    private String approvedDateStr;

    @SerializedName("STATUS")
    private String status;

    public Integer getUserDaID() {
        return userDaID;
    }

    public void setUserDaID(Integer userDaID) {
        this.userDaID = userDaID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(String maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
