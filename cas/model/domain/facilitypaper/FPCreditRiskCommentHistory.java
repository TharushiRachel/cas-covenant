//package com.itechro.cas.model.domain.facilitypaper;
//
//
//import com.itechro.cas.commons.constants.AppsConstants;
//import com.itechro.cas.commons.constants.DomainConstants;
//import com.itechro.cas.model.domain.common.UserTrackableEntity;
//
//import javax.persistence.*;
//import java.util.Set;
//
//@Entity
//@Table(name = "T_FP_CREDIT_RISK_COMMENT_DAO")
//public class FPCreditRiskCommentHistory extends UserTrackableEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_CREDIT_RISK_COMMENT")
//    @SequenceGenerator(name = "SEQ_T_FP_CREDIT_RISK_COMMENT", sequenceName = "SEQ_T_FP_CREDIT_RISK_COMMENT", allocationSize = 1)
//    @Column(name = "FP_CREDIT_RISK_COMMENT_ID")
//    private Integer fpCreditRiskCommentID;
//
//    @Column(name = "FACILITY_PAPER_ID")
//    private Integer facilityPaperId;
//
//    @Column(name = "UPM_PRIVILEGE_CODE")
//    private String UPMPrivilegeCode;
//
//    @Column(name = "FP_CR_COMMENT")
//    private String creditRiskComment;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "STATUS")
//    private AppsConstants.Status status;
//
//    @Column(name = "CREATED_USER_NAME")
//    private String createdUserName;
//
//    @Column(name = "MODIFIED_USER_NAME")
//    private String modifiedUserName;
//
//    @Column(name = "UPM_ID")
//    private Integer upmID;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "IS_LOCKED")
//    private AppsConstants.YesNo isLocked;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "IS_VALID_COMMENT")
//    private AppsConstants.YesNo isValidComment;
//
//    @Enumerated(EnumType.STRING)
//    @Column(name = "FACILITY_PAPER_FORM_STATUS")
//    private DomainConstants.FacilityPaperStatus facilityPaperFormStatus;
//
//    @Column(name = "REV")
//    private Integer rev;
//
//    @Column(name = "REVTYPE")
//    private Integer revType;
//
//    public Integer getFpCreditRiskCommentID() {
//        return fpCreditRiskCommentID;
//    }
//
//    public void setFpCreditRiskCommentID(Integer fpCreditRiskCommentID) {
//        this.fpCreditRiskCommentID = fpCreditRiskCommentID;
//    }
//
////    public FacilityPaper getFacilityPaper() {
////        return facilityPaper;
////    }
////
////    public void setFacilityPaper(FacilityPaper facilityPaper) {
////        this.facilityPaper = facilityPaper;
////    }
//
//
//    public Integer getFacilityPaperId() {
//        return facilityPaperId;
//    }
//
//    public void setFacilityPaperId(Integer facilityPaperId) {
//        this.facilityPaperId = facilityPaperId;
//    }
//
//    public String getUPMPrivilegeCode() {
//        return UPMPrivilegeCode;
//    }
//
//    public void setUPMPrivilegeCode(String UPMPrivilegeCode) {
//        this.UPMPrivilegeCode = UPMPrivilegeCode;
//    }
//
//    public String getCreditRiskComment() {
//        return creditRiskComment;
//    }
//
//    public void setCreditRiskComment(String creditRiskComment) {
//        this.creditRiskComment = creditRiskComment;
//    }
//
//    public AppsConstants.Status getStatus() {
//        return status;
//    }
//
//    public void setStatus(AppsConstants.Status status) {
//        this.status = status;
//    }
//
//    public String getCreatedUserName() {
//        return createdUserName;
//    }
//
//    public void setCreatedUserName(String createdUserName) {
//        this.createdUserName = createdUserName;
//    }
//
//    public String getModifiedUserName() {
//        return modifiedUserName;
//    }
//
//    public void setModifiedUserName(String modifiedUserName) {
//        this.modifiedUserName = modifiedUserName;
//    }
//
//    public Integer getUpmID() {
//        return upmID;
//    }
//
//    public void setUpmID(Integer upmID) {
//        this.upmID = upmID;
//    }
//
//    public AppsConstants.YesNo getIsLocked() {
//        return isLocked;
//    }
//
//    public void setIsLocked(AppsConstants.YesNo isLocked) {
//        this.isLocked = isLocked;
//    }
//
//    public AppsConstants.YesNo getIsValidComment() {
//        return isValidComment;
//    }
//
//    public void setIsValidComment(AppsConstants.YesNo isValidComment) {
//        this.isValidComment = isValidComment;
//    }
//
//    public DomainConstants.FacilityPaperStatus getFacilityPaperFormStatus() {
//        return facilityPaperFormStatus;
//    }
//
//    public void setFacilityPaperFormStatus(DomainConstants.FacilityPaperStatus facilityPaperFormStatus) {
//        this.facilityPaperFormStatus = facilityPaperFormStatus;
//    }
//
//    public Integer getRev() {
//        return rev;
//    }
//
//    public void setRev(Integer rev) {
//        this.rev = rev;
//    }
//
//    public Integer getRevType() {
//        return revType;
//    }
//
//    public void setRevType(Integer revType) {
//        this.revType = revType;
//    }
//}
