package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_FP_CREDIT_RISK_REPLY")
public class FPCreditRiskReply extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_FP_CREDIT_RISK_REPLY")
    @SequenceGenerator(name = "SEQ_T_FP_CREDIT_RISK_REPLY", sequenceName = "SEQ_T_FP_CREDIT_RISK_REPLY", allocationSize = 1)
    @Column(name = "FP_CREDIT_RISK_REPLY_ID")
    private Integer fpCreditRiskReplyID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FP_CREDIT_RISK_COMMENT_ID")
    private FPCreditRiskComment fpCreditRiskComment;

    @Column(name = "REPLY_COMMENT")
    private String replyComment;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "CREATED_USER_NAME")
    private String createdUserName;

    @Column(name = "MODIFIED_USER_NAME")
    private String modifiedUserName;

    @Column(name = "CREATED_DIV_CODE")
    private String createdDivCode;

    public Integer getFpCreditRiskReplyID() {
        return fpCreditRiskReplyID;
    }

    public void setFpCreditRiskReplyID(Integer fpCreditRiskReplyID) {
        this.fpCreditRiskReplyID = fpCreditRiskReplyID;
    }

    public FPCreditRiskComment getFpCreditRiskComment() {
        return fpCreditRiskComment;
    }

    public void setFpCreditRiskComment(FPCreditRiskComment fpCreditRiskComment) {
        this.fpCreditRiskComment = fpCreditRiskComment;
    }

    public String getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(String replyComment) {
        this.replyComment = replyComment;
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

    public String getCreatedDivCode() {
        return createdDivCode;
    }

    public void setCreatedDivCode(String createdDivCode) {
        this.createdDivCode = createdDivCode;
    }
}


