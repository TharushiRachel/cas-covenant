package com.itechro.cas.model.dto.audit;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.common.PagedParamDTO;

import java.util.ArrayList;
import java.util.List;

public class WebAuditSearchRQ  extends PagedParamDTO {


    private List<DomainConstants.WebAuditMainCategory> auditMainCategoryList;

    private List<DomainConstants.WebAuditSubCategory> auditSubCategoryList;

    private List<DomainConstants.AuditType> auditTypeList;

    private Integer auditTypeID;

    private Integer userID;

    private String userName;

    private String userFirstName;

    private String userLastName;

    private String auditFromDateStr;

    private String auditToDateStr;

    private String content;

    public List<DomainConstants.WebAuditMainCategory> getAuditMainCategoryList() {
        if (auditMainCategoryList == null) {
            auditMainCategoryList = new ArrayList<>();
        }
        return auditMainCategoryList;
    }

    public void setAuditMainCategoryList(List<DomainConstants.WebAuditMainCategory> auditMainCategoryList) {
        this.auditMainCategoryList = auditMainCategoryList;
    }

    public List<DomainConstants.WebAuditSubCategory> getAuditSubCategoryList() {
        if (auditSubCategoryList == null) {
            auditSubCategoryList = new ArrayList<>();
        }
        return auditSubCategoryList;
    }

    public void setAuditSubCategoryList(List<DomainConstants.WebAuditSubCategory> auditSubCategoryList) {
        this.auditSubCategoryList = auditSubCategoryList;
    }

    public List<DomainConstants.AuditType> getAuditTypeList() {
        if (auditTypeList == null) {
            auditTypeList = new ArrayList<>();
        }
        return auditTypeList;
    }

    public void setAuditTypeList(List<DomainConstants.AuditType> auditTypeList) {
        this.auditTypeList = auditTypeList;
    }

    public Integer getAuditTypeID() {
        return auditTypeID;
    }

    public void setAuditTypeID(Integer auditTypeID) {
        this.auditTypeID = auditTypeID;
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

    public String getAuditFromDateStr() {
        return auditFromDateStr;
    }

    public void setAuditFromDateStr(String auditFromDateStr) {
        this.auditFromDateStr = auditFromDateStr;
    }

    public String getAuditToDateStr() {
        return auditToDateStr;
    }

    public void setAuditToDateStr(String auditToDateStr) {
        this.auditToDateStr = auditToDateStr;
    }

    public String getContent() {  return content; }

    public void setContent(String content) { this.content = content; }

    @Override
    public String toString() {
        return "WebAuditSearchRQ{" +
                "auditMainCategoryList=" + auditMainCategoryList +
                ", auditSubCategoryList=" + auditSubCategoryList +
                ", auditTypeList=" + auditTypeList +
                ", auditTypeID=" + auditTypeID +
                ", userID=" + userID +
                ", userName='" + userName + '\'' +
                ", auditFromDateStr='" + auditFromDateStr + '\'' +
                ", auditToDateStr='" + auditToDateStr + '\'' +
                '}';
    }
}
