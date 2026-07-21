package com.itechro.cas.model.dto.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.bccpaper.BccRiskRatingYear;

import java.io.Serializable;

public class BccRiskRatingYearDTO implements Serializable {

    private Integer bccRiskRatingYearID;

    private Integer boardCreditCommitteePaperID;

    private String riskGrading;

    private Double riskScore;

    private String riskYear;

    private AppsConstants.Status status;

    public BccRiskRatingYearDTO() {
    }

    public BccRiskRatingYearDTO(BccRiskRatingYear bccRiskRatingYear) {

        this.bccRiskRatingYearID = bccRiskRatingYear.getBccRiskRatingYearID();
        this.boardCreditCommitteePaperID = bccRiskRatingYear.getBoardCreditCommitteePaper().getBoardCreditCommitteePaperID();
        this.riskGrading = bccRiskRatingYear.getRiskGrading();
        this.riskScore = bccRiskRatingYear.getRiskScore();
        this.riskYear = bccRiskRatingYear.getRiskYear();
        this.status = bccRiskRatingYear.getStatus();
    }

    public Integer getBccRiskRatingYearID() {
        return bccRiskRatingYearID;
    }

    public void setBccRiskRatingYearID(Integer bccRiskRatingYearID) {
        this.bccRiskRatingYearID = bccRiskRatingYearID;
    }

    public Integer getBoardCreditCommitteePaperID() {
        return boardCreditCommitteePaperID;
    }

    public void setBoardCreditCommitteePaperID(Integer boardCreditCommitteePaperID) {
        this.boardCreditCommitteePaperID = boardCreditCommitteePaperID;
    }

    public String getRiskGrading() {
        return riskGrading;
    }

    public void setRiskGrading(String riskGrading) {
        this.riskGrading = riskGrading;
    }

    public Double getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(Double riskScore) {
        this.riskScore = riskScore;
    }

    public String getRiskYear() {
        return riskYear;
    }

    public void setRiskYear(String riskYear) {
        this.riskYear = riskYear;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "BccRiskRatingYearDTO{" +
                "bccRiskRatingYearID=" + bccRiskRatingYearID +
                ", boardCreditCommitteePaperID=" + boardCreditCommitteePaperID +
                ", riskGrading='" + riskGrading + '\'' +
                ", riskScore=" + riskScore +
                ", riskYear='" + riskYear + '\'' +
                ", status=" + status +
                '}';
    }
}
