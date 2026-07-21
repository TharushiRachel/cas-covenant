package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.hibernate.envers.AuditOverride;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_FP_CREDIT_RISK_COMMENT")
@Audited
@AuditOverride(forClass = UserTrackableEntity.class)
public class FPCreditRiskComment extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_CREDIT_RISK_COMMENT")
    @SequenceGenerator(name = "SEQ_T_FP_CREDIT_RISK_COMMENT", sequenceName = "SEQ_T_FP_CREDIT_RISK_COMMENT", allocationSize = 1)
    @Column(name = "FP_CREDIT_RISK_COMMENT_ID")
    private Integer fpCreditRiskCommentID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @Column(name = "UPM_PRIVILEGE_CODE")
    private String UPMPrivilegeCode;

    @Column(name = "FP_CR_COMMENT")
    private String creditRiskComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "CREATED_USER_NAME")
    private String createdUserName;

    @Column(name = "MODIFIED_USER_NAME")
    private String modifiedUserName;

    @Column(name = "UPM_ID")
    private Integer upmID;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_LOCKED")
    private AppsConstants.YesNo isLocked;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_VALID_COMMENT")
    private AppsConstants.YesNo isValidComment;

//    @Column(name = "ORIGINAL_COMMENT")
//    private String originalComment;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "fpCreditRiskComment")
    @NotAudited
    private Set<FPCreditRiskReply> fpCreditRiskReplies;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "FP_CREDIT_RISK_COMMENT_ID")
//    private FPCreditRiskCommentHistory fpCreditRiskCommentHistory;

    public Integer getFpCreditRiskCommentID() {
        return fpCreditRiskCommentID;
    }

    public void setFpCreditRiskCommentID(Integer fpCreditRiskCommentID) {
        this.fpCreditRiskCommentID = fpCreditRiskCommentID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
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

//    public String getOriginalComment() {
//        return originalComment;
//    }
//
//    public void setOriginalComment(String originalComment) {
//        this.originalComment = originalComment;
//    }

    public Set<FPCreditRiskReply> getFpCreditRiskReplies() {
        if (fpCreditRiskReplies == null) {
            this.fpCreditRiskReplies = new HashSet<>();
        }
        return fpCreditRiskReplies;
    }

    public Set<FPCreditRiskReply> getActiveFpCreditRiskReplies() {
        return this.getFpCreditRiskReplies().stream().filter(fpCreditRiskReply ->
                fpCreditRiskReply.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toSet());
    }

    public void setFpCreditRiskReplies(Set<FPCreditRiskReply> fpCreditRiskReplies) {
        this.fpCreditRiskReplies = fpCreditRiskReplies;
    }

    public FPCreditRiskReply getFPCreditRiskReplyByID(Integer fpCreditRiskReplyID) {
        List<FPCreditRiskReply> sortedList = this.getFpCreditRiskReplies().stream().filter(fpCreditRiskReply ->
                        fpCreditRiskReplyID.equals(fpCreditRiskReply.getFpCreditRiskReplyID()))
                .sorted(Comparator.comparingLong(FPCreditRiskReply::getVersion)
                        .reversed()
                        .thenComparing(FPCreditRiskReply::getCreatedDate)
                        .reversed())
                .collect(Collectors.toList());

        return sortedList.stream().findFirst().get();
    }

    public void addFPCreditRiskReply(FPCreditRiskReply fpCreditRiskReply) {
        fpCreditRiskReply.setFpCreditRiskComment(this);
        this.getFpCreditRiskReplies().add(fpCreditRiskReply);
    }

    public Optional<FPCreditRiskReply> getActiveFpCreditRiskReply() {
        List<FPCreditRiskReply> sortedList = this.getFpCreditRiskReplies()
                .stream()
                .filter(fpCreditRiskReply -> fpCreditRiskReply.getStatus() == AppsConstants.Status.ACT)
                .sorted(Comparator.comparingLong(FPCreditRiskReply::getVersion)
                        .reversed()
                        .thenComparing(FPCreditRiskReply::getCreatedDate)
                        .reversed())
                .collect(Collectors.toList());

        return sortedList.stream().findFirst();
    }

}
