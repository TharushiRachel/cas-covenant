package com.itechro.cas.model.domain.audit;

import com.itechro.cas.commons.constants.DomainConstants;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "T_WEB_AUDIT")
public class WebAudit  implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_WEB_AUDIT")
    @SequenceGenerator(name = "SEQ_T_WEB_AUDIT", sequenceName = "SEQ_T_WEB_AUDIT", allocationSize = 1)
    @Column(name = "WEB_AUDIT_ID")
    private Integer webAuditID;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUDIT_TYPE")
    private DomainConstants.AuditType auditType;

    @Column(name = "AUDIT_TYPE_ID")
    private Integer AuditTypeID;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUDIT_MAIN_CATEGORY")
    private DomainConstants.WebAuditMainCategory webAuditMainCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "AUDIT_SUB_CATEGORY")
    private DomainConstants.WebAuditSubCategory webAuditSubCategory;

    @Column(name = "PREVIOUS_CONTENT")
    private  String previousContent;

    @Column(name = "UPDATED_CONTENT")
    private  String updateContent;

    @Column(name = "AUDITED_DATE_TIME")
    @Temporal(TemporalType.TIMESTAMP)
    private Date auditedDateTime;

    @Column(name = "USER_ID")
    private Integer userID;

    @Column(name = "USER_NAME")
    private String userName;

    public Integer getWebAuditID() {
        return webAuditID;
    }

    public void setWebAuditID(Integer webAuditID) {
        this.webAuditID = webAuditID;
    }

    public DomainConstants.AuditType getAuditType() {
        return auditType;
    }

    public void setAuditType(DomainConstants.AuditType auditType) {
        this.auditType = auditType;
    }

    public Integer getAuditTypeID() {
        return AuditTypeID;
    }

    public void setAuditTypeID(Integer auditTypeID) {
        AuditTypeID = auditTypeID;
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

    public String getPreviousContent() {
        return previousContent;
    }

    public void setPreviousContent(String previousContent) {
        this.previousContent = previousContent;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    public Date getAuditedDateTime() {
        return auditedDateTime;
    }

    public void setAuditedDateTime(Date auditedDateTime) {
        this.auditedDateTime = auditedDateTime;
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
}
