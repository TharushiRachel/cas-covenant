package com.itechro.cas.model.dto.audit;

import com.google.gson.Gson;
import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;
import java.util.Date;

public class WebAuditDTO implements Serializable {

    private static final long serialVersionUID = 1240721584736405886L;

    private Integer webAuditID;

    private Integer userID;

    private String userName;

    private String userFirstName;

    private String userLastName;

    private DomainConstants.AuditType auditType;

    private Integer auditTypeID;

    private DomainConstants.WebAuditMainCategory webAuditMainCategory;

    private DomainConstants.WebAuditSubCategory webAuditSubCategory;

    private String auditMainCategory;

    private String auditSubCategory;

    private String auditTypeStr;

    private Date auditedDateTime;

    private String AuditedDateTimeStr;

    private BaseContentDTO updateContentDTO;

    private BaseContentDTO previousContentDTO;

    private String updateContent;

    private String previousContent;

    public WebAuditDTO() {
    }

    public WebAuditDTO(Integer userID,
                       DomainConstants.AuditType auditType,
                       Integer auditTypeID,
                       DomainConstants.WebAuditMainCategory webAuditMainCategory,
                       DomainConstants.WebAuditSubCategory webAuditSubCategory,
                       Date auditedDateTime,
                       BaseContentDTO previousContentDTO,
                       BaseContentDTO updateContentDTO) {

        this.userID = userID;
        this.auditTypeID = auditTypeID;
        this.auditType = auditType;
        this.webAuditMainCategory = webAuditMainCategory;
        this.webAuditSubCategory = webAuditSubCategory;
        this.auditedDateTime = auditedDateTime;
        this.updateContentDTO = updateContentDTO;
        this.previousContentDTO = previousContentDTO;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getWebAuditID() {
        return webAuditID;
    }

    public void setWebAuditID(Integer webAuditID) {
        this.webAuditID = webAuditID;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserFirstName() {
        return userFirstName;
    }

    public void setUserFirstName(String userFirstName) {
        this.userFirstName = userFirstName;
    }

    public String getUserLastName() {
        return userLastName;
    }

    public void setUserLastName(String userLastName) {
        this.userLastName = userLastName;
    }

    public DomainConstants.AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(DomainConstants.AuditType auditType) {
        this.auditType = auditType;
    }

    public Integer getAuditTypeID() {
        return auditTypeID;
    }

    public void setAuditTypeID(Integer auditTypeID) {
        this.auditTypeID = auditTypeID;
    }

    public String getAuditTypeStr() {
        return auditTypeStr;
    }

    public void setAuditTypeStr(String auditTypeStr) {
        this.auditTypeStr = auditTypeStr;
    }

    public DomainConstants.WebAuditMainCategory getWebAuditMainCategory() {
        return webAuditMainCategory;
    }

    public void setWebAuditMainCategory(DomainConstants.WebAuditMainCategory webAuditMainCategory) {
        this.webAuditMainCategory = webAuditMainCategory;
    }

    public DomainConstants.WebAuditSubCategory getWebAuditSubCategory() {
        return webAuditSubCategory;
    }

    public void setWebAuditSubCategory(DomainConstants.WebAuditSubCategory webAuditSubCategory) {
        this.webAuditSubCategory = webAuditSubCategory;
    }

    public String getAuditMainCategory() {
        return auditMainCategory;
    }

    public void setAuditMainCategory(String auditMainCategory) {
        this.auditMainCategory = auditMainCategory;
    }

    public String getAuditSubCategory() {
        return auditSubCategory;
    }

    public void setAuditSubCategory(String auditSubCategory) {
        this.auditSubCategory = auditSubCategory;
    }

    public Date getAuditedDateTime() {
        return auditedDateTime;
    }

    public void setAuditedDateTime(Date auditedDateTime) {
        this.auditedDateTime = auditedDateTime;
    }

    public String getAuditedDateTimeStr() {
        return AuditedDateTimeStr;
    }

    public void setAuditedDateTimeStr(String auditedDateTimeStr) {
        AuditedDateTimeStr = auditedDateTimeStr;
    }

    public BaseContentDTO getUpdateContentDTO() {
        return updateContentDTO;
    }

    public void setUpdateContentDTO(BaseContentDTO updateContentDTO) {
        this.updateContentDTO = updateContentDTO;
    }

    public BaseContentDTO getPreviousContentDTO() {
        return previousContentDTO;
    }

    public void setPreviousContentDTO(BaseContentDTO previousContentDTO) {
        this.previousContentDTO = previousContentDTO;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public String getPreviousContent() {
        return previousContent;
    }

    public void setPreviousContent(String previousContent) {
        this.previousContent = previousContent;
    }

    @Override
    public String toString() {
        return "WebAuditDTO{" +
                "webAuditID=" + webAuditID +
                ", udserID=" + userID +
                ", auditType=" + auditType +
                ", auditTypeID=" + auditTypeID +
                ", webAuditMainCategory=" + webAuditMainCategory +
                ", webAuditSubCategory=" + webAuditSubCategory +
                ", auditedDateTime=" + auditedDateTime +
                '}';
    }

    public String getJsonUpdateContent() {

        Gson gson = new Gson();
        return gson.toJson(updateContentDTO);

    }

   public String getJsonPreviousContent() {
        if (previousContentDTO == null) {
            return null;
        }
        Gson gson = new Gson();
        return gson.toJson(previousContentDTO);

    }

}
