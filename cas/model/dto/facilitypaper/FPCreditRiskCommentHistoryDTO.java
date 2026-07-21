package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.util.CalendarUtil;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.Date;

public class FPCreditRiskCommentHistoryDTO implements Serializable, Comparable<FPCreditRiskCommentHistoryDTO> {

    private Integer fpCreditRiskCommentID;

    private Integer facilityPaperID;

    private String UPMPrivilegeCode;

    private String creditRiskComment;

    private String commentedTimeStr;

    private String createdUserName;

    private String modifiedUserName;

    private Integer upmID;

    private AppsConstants.YesNo isLocked;

    private AppsConstants.YesNo isValidComment;

    private AppsConstants.Status status;

    private String createdBy;

    private String modifiedBy;

    private String createdDateStr;

    private String modifiedDateStr;

    private Integer rev;

    private Integer revType;

    private DomainConstants.FacilityPaperStatus facilityPaperFormStatus;



    public Integer getFpCreditRiskCommentID() {
        return fpCreditRiskCommentID;
    }

    public void setFpCreditRiskCommentID(Integer fpCreditRiskCommentID) {
        this.fpCreditRiskCommentID = fpCreditRiskCommentID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public String getUPMPrivilegeCode() {
        return UPMPrivilegeCode;
    }

    public void setUPMPrivilegeCode(String UPMPrivilegeCode) {
        this.UPMPrivilegeCode = UPMPrivilegeCode;
    }

    public String getCreditRiskComment() {
        return creditRiskComment;
    }

    public void setCreditRiskComment(String creditRiskComment) {
        this.creditRiskComment = creditRiskComment;
    }

    public String getCommentedTimeStr() {
        return commentedTimeStr;
    }

    public void setCommentedTimeStr(String commentedTimeStr) {
        this.commentedTimeStr = commentedTimeStr;
    }

    public String getCreatedUserName() {
        return createdUserName;
    }

    public void setCreatedUserName(String createdUserName) {
        this.createdUserName = createdUserName;
    }

    public String getModifiedUserName() {
        return modifiedUserName;
    }

    public void setModifiedUserName(String modifiedUserName) {
        this.modifiedUserName = modifiedUserName;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    public AppsConstants.YesNo getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(AppsConstants.YesNo isLocked) {
        this.isLocked = isLocked;
    }

    public AppsConstants.YesNo getIsValidComment() {
        return isValidComment;
    }

    public void setIsValidComment(AppsConstants.YesNo isValidComment) {
        this.isValidComment = isValidComment;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getModifiedDateStr() {
        return modifiedDateStr;
    }

    public void setModifiedDateStr(String modifiedDateStr) {
        this.modifiedDateStr = modifiedDateStr;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperFormStatus() {
        return facilityPaperFormStatus;
    }

    public void setFacilityPaperFormStatus(DomainConstants.FacilityPaperStatus facilityPaperFormStatus) {
        this.facilityPaperFormStatus = facilityPaperFormStatus;
    }

    public Integer getRev() {
        return rev;
    }

    public void setRev(Integer rev) {
        this.rev = rev;
    }

    public Integer getRevType() {
        return revType;
    }

    public void setRevType(Integer revType) {
        this.revType = revType;
    }

    @Override
    public String toString() {
        return "FPCreditRiskCommentHistoryDTO{" +
                "fpCreditRiskCommentID=" + fpCreditRiskCommentID +
                ", facilityPaperID=" + facilityPaperID +
                ", UPMPrivilegeCode='" + UPMPrivilegeCode + '\'' +
                ", creditRiskComment='" + creditRiskComment + '\'' +
                ", commentedTimeStr='" + commentedTimeStr + '\'' +
                ", createdUserName='" + createdUserName + '\'' +
                ", modifiedUserName='" + modifiedUserName + '\'' +
                ", upmID=" + upmID +
                ", isLocked=" + isLocked +
                ", isValidComment=" + isValidComment +
                ", status=" + status +
                ", createdBy='" + createdBy + '\'' +
                ", modifiedBy='" + modifiedBy + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", modifiedDateStr='" + modifiedDateStr + '\'' +
                ", rev=" + rev +
                ", revType=" + revType +
                ", facilityPaperFormStatus=" + facilityPaperFormStatus +
                '}';
    }

    @Override
    public int compareTo(FPCreditRiskCommentHistoryDTO fpCreditRiskCommentHistoryDTO) {
        try {
            Date dateOne = CalendarUtil.getDefaultParsedDateTime(this.createdDateStr);
            Date date = CalendarUtil.getDefaultParsedDateTime(fpCreditRiskCommentHistoryDTO.createdDateStr);
            //int x = dateOne.compareTo(date);
            return dateOne.compareTo(date);
        } catch (Exception e) {
            return 0;
        }
    }
}
