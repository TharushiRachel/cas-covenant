package com.itechro.cas.model.domain.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_BCC_CUSTOMER_CRIB_DETAIL")
public class BccCustomerCribDetail extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BCC_CUSTOMER_CRIB_DETAIL")
    @SequenceGenerator(name = "SEQ_T_BCC_CUSTOMER_CRIB_DETAIL", sequenceName = "SEQ_T_BCC_CUSTOMER_CRIB_DETAIL", allocationSize = 1)
    @Column(name = "BCC_CUSTOMER_CRIB_DETAIL_ID")
    private Integer bccCustomerCribDetailsID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCC_PAPER_ID")
    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    @Enumerated(EnumType.STRING)
    @Column(name = "CRIB_STATUS")
    private DomainConstants.CribStatusType cribStatus;

    @Column(name = "REMARK")
    private String remark;

    @Column(name = "BORROWER")
    private String borrower;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRIB_ISSUE_DATE")
    private Date cribIssueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "REPORT_DATE")
    private Date reportDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getBccCustomerCribDetailsID() {
        return bccCustomerCribDetailsID;
    }

    public void setBccCustomerCribDetailsID(Integer bccCustomerCribDetailsID) {
        this.bccCustomerCribDetailsID = bccCustomerCribDetailsID;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public void setBoardCreditCommitteePaper(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        this.boardCreditCommitteePaper = boardCreditCommitteePaper;
    }

    public DomainConstants.CribStatusType getCribStatus() {
        return cribStatus;
    }

    public void setCribStatus(DomainConstants.CribStatusType cribStatus) {
        this.cribStatus = cribStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCribIssueDate() {
        return cribIssueDate;
    }

    public void setCribIssueDate(Date cribIssueDate) {
        this.cribIssueDate = cribIssueDate;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }
}
