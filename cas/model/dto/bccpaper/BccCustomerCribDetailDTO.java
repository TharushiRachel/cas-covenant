package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.bccpaper.BccCustomerCribDetail;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class BccCustomerCribDetailDTO implements Serializable {

    private Integer bccCustomerCribDetailsID;

    private Integer boardCreditCommitteePaperID;

    private String borrower;

    private DomainConstants.CribStatusType cribStatus;

    private String remark;

    private String cribIssueDateStr;

    private String reportDateStr;

    private AppsConstants.Status status;

    public BccCustomerCribDetailDTO() {
    }

    public BccCustomerCribDetailDTO(BccCustomerCribDetail bccCustomerCribDetail) {
        this.bccCustomerCribDetailsID = bccCustomerCribDetail.getBccCustomerCribDetailsID();
        this.boardCreditCommitteePaperID = bccCustomerCribDetail.getBoardCreditCommitteePaper().getBoardCreditCommitteePaperID();
        this.cribStatus = bccCustomerCribDetail.getCribStatus();
        this.remark = bccCustomerCribDetail.getRemark();
        this.borrower = bccCustomerCribDetail.getBorrower();
        if (bccCustomerCribDetail.getCribIssueDate() != null) {
            this.cribIssueDateStr = CalendarUtil.getDefaultFormattedDateOnly(bccCustomerCribDetail.getCribIssueDate());
        }
        if (bccCustomerCribDetail.getReportDate() != null) {
            this.reportDateStr = CalendarUtil.getDefaultFormattedDateOnly(bccCustomerCribDetail.getReportDate());
        }
        this.status = bccCustomerCribDetail.getStatus();
    }

    public Integer getBccCustomerCribDetailsID() {
        return bccCustomerCribDetailsID;
    }

    public void setBccCustomerCribDetailsID(Integer bccCustomerCribDetailsID) {
        this.bccCustomerCribDetailsID = bccCustomerCribDetailsID;
    }

    public DomainConstants.CribStatusType getCribStatus() {
        return cribStatus;
    }

    public String getCribStatusValue() {
        if (getCribStatus() != null) {
            return getCribStatus().getDescription();
        } else {
            return "";
        }
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

    public String getCribIssueDateStr() {
        return cribIssueDateStr;
    }

    public void setCribIssueDateStr(String cribIssueDateStr) {
        this.cribIssueDateStr = cribIssueDateStr;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
    }

    public String getReportDateStr() {
        return reportDateStr;
    }

    public void setReportDateStr(String reportDateStr) {
        this.reportDateStr = reportDateStr;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    @Override
    public String toString() {
        return "BccCustomerCribDetailDTO{" +
                "bccCustomerCribDetailsID=" + bccCustomerCribDetailsID +
                ", boardCreditCommitteePaperID=" + boardCreditCommitteePaperID +
                ", borrower='" + borrower + '\'' +
                ", cribStatus=" + cribStatus +
                ", remark='" + remark + '\'' +
                ", cribIssueDateStr='" + cribIssueDateStr + '\'' +
                ", reportDateStr='" + reportDateStr + '\'' +
                ", status=" + status +
                '}';
    }
}
