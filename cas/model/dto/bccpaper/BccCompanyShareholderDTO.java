package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.bccpaper.BccCompanyShareholder;
import com.itechro.cas.model.domain.bccpaper.BoardCreditCommitteePaper;

import javax.persistence.*;
import java.io.Serializable;

public class BccCompanyShareholderDTO implements Serializable {

    private Integer bccCompanyShareHolderID;

    private Integer boardCreditCommitteePaperID;

    private String companyShareholderName;

    private Double shareHolding;


    private AppsConstants.Status status;

    public BccCompanyShareholderDTO() {
    }

    public BccCompanyShareholderDTO(BccCompanyShareholder bccCompanyShareholder) {
        this.bccCompanyShareHolderID = bccCompanyShareholder.getBccCompanyShareHolderID();
        this.boardCreditCommitteePaperID = bccCompanyShareholder.getBoardCreditCommitteePaper().getBoardCreditCommitteePaperID();
        this.companyShareholderName = bccCompanyShareholder.getCompanyShareholderName();
        this.shareHolding = bccCompanyShareholder.getShareHolding();
        this.status = bccCompanyShareholder.getStatus();
    }

    public Integer getBccCompanyShareHolderID() {
        return bccCompanyShareHolderID;
    }

    public void setBccCompanyShareHolderID(Integer bccCompanyShareHolderID) {
        this.bccCompanyShareHolderID = bccCompanyShareHolderID;
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
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
}
