package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.FPStatusHistory;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class FPStatusHistoryDTO implements Serializable {

    private Integer fpStatusHistoryID;

    private Integer facilityPaperID;

    private DomainConstants.FacilityPaperStatus facilityPaperStatus;

    private String authorityLevel;

    private String assignUser;

    private Integer assignUserID;

    private String assignUserDisplayName;

    private Integer assignUserUpmID;

    private String assignUserUpmGroupCode;

    private String assignUserDivCode;

    private String remark;

    private String workflowOrder;

    private String updateBy;

    private String updatedUser;

    private String updateDate;

    private AppsConstants.YesNo isPublic;

    private AppsConstants.YesNo isDivisionOnly;

    private AppsConstants.YesNo isUsersOnly;

    public FPStatusHistoryDTO() {
    }

    public FPStatusHistoryDTO(FPStatusHistory fpStatusHistory) {
        this.fpStatusHistoryID = fpStatusHistory.getFpStatusHistoryID();
        this.facilityPaperID = fpStatusHistory.getFacilityPaper().getFacilityPaperID();
        this.facilityPaperStatus = fpStatusHistory.getFacilityPaperStatus();
        this.authorityLevel = fpStatusHistory.getAuthorityLevel();
        this.assignUser = fpStatusHistory.getAssignUser();
        this.assignUserID = fpStatusHistory.getAssignUserID();
        this.assignUserDisplayName = fpStatusHistory.getAssignUserDisplayName();
        this.assignUserUpmID = fpStatusHistory.getAssignUserUpmID();
        this.assignUserDivCode = fpStatusHistory.getAssignUserDivCode();
        this.assignUserUpmGroupCode = fpStatusHistory.getAssignUserUpmGroupCode();
        this.remark = fpStatusHistory.getRemark();
        this.workflowOrder = fpStatusHistory.getWorkflowOrder();
        this.updateBy = fpStatusHistory.getUpdateBy();
        this.updatedUser = fpStatusHistory.getUpdatedUser();
        if (fpStatusHistory.getUpdateDate() != null) {
            this.updateDate = CalendarUtil.getDefaultFormattedDateTime(fpStatusHistory.getUpdateDate());
        }
        this.isPublic = fpStatusHistory.getIsPublic();
        this.isUsersOnly = fpStatusHistory.getIsUsersOnly();
        this.isDivisionOnly = fpStatusHistory.getIsDivisionOnly();
    }

    public Integer getFpStatusHistoryID() {
        return fpStatusHistoryID;
    }

    public void setFpStatusHistoryID(Integer fpStatusHistoryID) {
        this.fpStatusHistoryID = fpStatusHistoryID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public DomainConstants.FacilityPaperStatus getFacilityPaperStatus() {
        return facilityPaperStatus;
    }

    public void setFacilityPaperStatus(DomainConstants.FacilityPaperStatus facilityPaperStatus) {
        this.facilityPaperStatus = facilityPaperStatus;
    }

    public String getAuthorityLevel() {
        return authorityLevel;
    }

    public void setAuthorityLevel(String authorityLevel) {
        this.authorityLevel = authorityLevel;
    }

    public String getAssignUser() {
        return assignUser;
    }

    public void setAssignUser(String assignUser) {
        this.assignUser = assignUser;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getWorkflowOrder() {
        return workflowOrder;
    }

    public void setWorkflowOrder(String workflowOrder) {
        this.workflowOrder = workflowOrder;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(String updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getAssignUserID() {
        return assignUserID;
    }

    public void setAssignUserID(Integer assignUserID) {
        this.assignUserID = assignUserID;
    }

    public String getAssignUserDisplayName() {
        return assignUserDisplayName;
    }

    public void setAssignUserDisplayName(String assignUserDisplayName) {
        this.assignUserDisplayName = assignUserDisplayName;
    }

    public Integer getAssignUserUpmID() {
        return assignUserUpmID;
    }

    public void setAssignUserUpmID(Integer assignUserUpmID) {
        this.assignUserUpmID = assignUserUpmID;
    }

    public String getAssignUserUpmGroupCode() {
        return assignUserUpmGroupCode;
    }

    public void setAssignUserUpmGroupCode(String assignUserUpmGroupCode) {
        this.assignUserUpmGroupCode = assignUserUpmGroupCode;
    }

    public String getAssignUserDivCode() {
        return assignUserDivCode;
    }

    public void setAssignUserDivCode(String assignUserDivCode) {
        this.assignUserDivCode = assignUserDivCode;
    }

    public String getUpdatedUser() {
        return updatedUser;
    }

    public void setUpdatedUser(String updatedUser) {
        this.updatedUser = updatedUser;
    }

    public AppsConstants.YesNo getIsPublic() {
        return isPublic;
    }

    public void setIsPublic(AppsConstants.YesNo isPublic) {
        this.isPublic = isPublic;
    }

    public AppsConstants.YesNo getIsDivisionOnly() {
        return isDivisionOnly;
    }

    public void setIsDivisionOnly(AppsConstants.YesNo isDivisionOnly) {
        this.isDivisionOnly = isDivisionOnly;
    }

    public AppsConstants.YesNo getIsUsersOnly() {
        return isUsersOnly;
    }

    public void setIsUsersOnly(AppsConstants.YesNo isUsersOnly) {
        this.isUsersOnly = isUsersOnly;
    }

    @Override
    public String toString() {
        return "FPStatusHistoryDTO{" +
                "fpStatusHistoryID=" + fpStatusHistoryID +
                ", facilityPaperID=" + facilityPaperID +
                ", facilityPaperStatus=" + facilityPaperStatus +
                ", assignUser='" + assignUser + '\'' +
                ", assignUserID=" + assignUserID +
                ", assignUserDisplayName='" + assignUserDisplayName + '\'' +
                ", assignUserUpmID=" + assignUserUpmID +
                ", assignUserUpmGroupCode='" + assignUserUpmGroupCode + '\'' +
                ", assignUserDivCode='" + assignUserDivCode + '\'' +
                ", updateBy='" + updateBy + '\'' +
                ", updatedUser='" + updatedUser + '\'' +
                ", updateDate='" + updateDate + '\'' +
                ", isPublic=" + isPublic +
                ", isDivisionOnly=" + isDivisionOnly +
                ", isUsersOnly=" + isUsersOnly +
                '}';
    }
}
