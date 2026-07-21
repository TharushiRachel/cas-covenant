package com.itechro.cas.model.domain.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;

@Entity
@Table(name = "T_BCC_COMPANY_SHAREHOLDER")
public class BccCompanyShareholder extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BCC_COMPANY_SHAREHOLDER")
    @SequenceGenerator(name = "SEQ_T_BCC_COMPANY_SHAREHOLDER", sequenceName = "SEQ_T_BCC_COMPANY_SHAREHOLDER", allocationSize = 1)
    @Column(name = "BCC_COMPANY_SHAREHOLDER_ID")
    private Integer bccCompanyShareHolderID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCC_PAPER_ID")
    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    @Column(name = "COMPANY_SHAREHOLDER_NAME")
    private String companyShareholderName;

    @Column(name = "SHARE_HOLDING")
    private Double shareHolding;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getBccCompanyShareHolderID() {
        return bccCompanyShareHolderID;
    }

    public void setBccCompanyShareHolderID(Integer bccCompanyShareHolderID) {
        this.bccCompanyShareHolderID = bccCompanyShareHolderID;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public void setBoardCreditCommitteePaper(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        this.boardCreditCommitteePaper = boardCreditCommitteePaper;
    }

    public String getCompanyShareholderName() {
        return companyShareholderName;
    }

    public void setCompanyShareholderName(String companyShareholderName) {
        this.companyShareholderName = companyShareholderName;
    }

    public Double getShareHolding() {
        return shareHolding;
    }

    public void setShareHolding(Double shareHolding) {
        this.shareHolding = shareHolding;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BccCompanyShareholder{" +
                "bccCompanyShareHolderID=" + bccCompanyShareHolderID +
                ", boardCreditCommitteePaper=" + boardCreditCommitteePaper +
                ", companyShareholderName='" + companyShareholderName + '\'' +
                ", shareHolding=" + shareHolding +
                ", status=" + status +
                '}';
    }
}
