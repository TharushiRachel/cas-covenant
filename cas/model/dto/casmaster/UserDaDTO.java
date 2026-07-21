package com.itechro.cas.model.dto.casmaster;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.UserDa;
import com.itechro.cas.util.CalendarUtil;


import java.io.Serializable;
import java.math.BigDecimal;

public class UserDaDTO implements Serializable {

    private Integer userDaID;

    private String userName;

    private BigDecimal maxAmount;

    private BigDecimal cleanAmount;

    private String description;

    private AppsConstants.Status status;

    private DomainConstants.MasterDataApproveStatus approveStatus;

    private String approvedDateStr;

    private String approvedBy;

    private String createdDateStr;

    private String createdBy;

    private String modifiedDateStr;

    private String modifiedBy;

    public UserDaDTO(){

    }

    public UserDaDTO(UserDa userDa) {
        this.userDaID = userDa.getUserDaID();
        this.userName = userDa.getUserName();
        this.maxAmount = userDa.getMaxAmount();
        this.cleanAmount = userDa.getCleanAmount();
        this.description = userDa.getDescription();
        this.status = userDa.getStatus();
        this.approveStatus = userDa.getApproveStatus();

        if (userDa.getApprovedDate() != null) {
            this.approvedDateStr = CalendarUtil.getDefaultFormattedDateTime(userDa.getApprovedDate());
        }

        this.approvedBy = userDa.getApprovedBy();
        if(userDa.getCreatedDate()!=null){
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(userDa.getCreatedDate());
        }
        this.createdBy = userDa.getCreatedBy();
        if(userDa.getModifiedDate()!= null){
            this.modifiedDateStr = CalendarUtil.getDefaultFormattedDateTime(userDa.getModifiedDate());
        }
        this.modifiedBy = userDa.getModifiedBy();
    }


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

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.MasterDataApproveStatus getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(DomainConstants.MasterDataApproveStatus approveStatus) {
        this.approveStatus = approveStatus;
    }

    public String getApprovedDateStr() {
        return approvedDateStr;
    }

    public void setApprovedDateStr(String approvedDateStr) {
        this.approvedDateStr = approvedDateStr;
    }

    public String getApprovedBy() {
        return approvedBy;
    }

    public void setApprovedBy(String approvedBy) {
        this.approvedBy = approvedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public BigDecimal getCleanAmount() {
        return cleanAmount;
    }

    public void setCleanAmount(BigDecimal cleanAmount) {
        this.cleanAmount = cleanAmount;
    }

    @Override
    public String toString() {
        return "UserDaDTO{" +
                "userDaID=" + userDaID +
                ", userName='" + userName + '\'' +
                ", maxAmount=" + maxAmount +
                ", cleanAmount=" + cleanAmount +
                ", description='" + description + '\'' +
                ", status=" + status +
                '}';
    }
}
