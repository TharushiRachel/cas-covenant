package com.itechro.cas.model.domain.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_BCC_RISK_RATING_YEAR")
public class BccRiskRatingYear extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_BCC_RISK_RATING_YEAR")
    @SequenceGenerator(name = "SEQ_T_BCC_RISK_RATING_YEAR", sequenceName = "SEQ_T_BCC_RISK_RATING_YEAR", allocationSize = 1)
    @Column(name = "BCC_RISK_RATING_YEAR_ID")
    private Integer bccRiskRatingYearID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BCC_PAPER_ID")
    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    @Column(name = "RISK_GRADING")
    private String riskGrading;

    @Column(name = "RISK_SCORE")
    private Double riskScore;

    @Column(name = "RISK_YEAR")
    private String riskYear;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getBccRiskRatingYearID() {
        return bccRiskRatingYearID;
    }

    public void setBccRiskRatingYearID(Integer bccRiskRatingYearID) {
        this.bccRiskRatingYearID = bccRiskRatingYearID;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public void setBoardCreditCommitteePaper(BoardCreditCommitteePaper boardCreditCommitteePaper) {
        this.boardCreditCommitteePaper = boardCreditCommitteePaper;
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
}
