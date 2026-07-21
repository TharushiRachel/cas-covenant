package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "T_AF_RISK_RATE")
public class AFRiskRate extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_RISK_RATE")
    @SequenceGenerator(name = "SEQ_T_AF_RISK_RATE", sequenceName = "SEQ_T_AF_RISK_RATE", allocationSize = 1)
    @Column(name = "RISK_RATE_ID")
    private Integer riskRateID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BASIC_INFORMATION_ID")
    private BasicInformation basicInformation;

    @Column(name = "APPLICATION_FROM_ID")
    private Integer applicationFormID;

    @Column(name = "SCORING")
    private String scoring;

    @Column(name = "RISK_GRADING")
    private String riskGrading;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "LAST_RATED")
    private Date lastRated;

    @Column(name = "MODEL")
    private String model;

    @Column(name = "ASSET_CLASSIFICATION")
    private String assetClassification;

    @Column(name = "INITIATED_BRANCH")
    private String initiatedBranch;

    @Enumerated(EnumType.STRING)
    @Column(name = "RISK_CONFIRMED")
    private AppsConstants.YesNo riskConfirmed;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Integer getRiskRateID() {
        return riskRateID;
    }

    public void setRiskRateID(Integer riskRateID) {
        this.riskRateID = riskRateID;
    }

    public BasicInformation getBasicInformation() {
        return basicInformation;
    }

    public void setBasicInformation(BasicInformation basicInformation) {
        this.basicInformation = basicInformation;
    }

    public String getScoring() {
        return scoring;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public Date getLastRated() {
        return lastRated;
    }

    public void setLastRated(Date lastRated) {
        this.lastRated = lastRated;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getAssetClassification() {
        return assetClassification;
    }

    public void setAssetClassification(String assetClassification) {
        this.assetClassification = assetClassification;
    }

    public String getInitiatedBranch() {
        return initiatedBranch;
    }

    public void setInitiatedBranch(String initiatedBranch) {
        this.initiatedBranch = initiatedBranch;
    }

    public AppsConstants.YesNo getRiskConfirmed() {
        return riskConfirmed;
    }

    public void setRiskConfirmed(AppsConstants.YesNo riskConfirmed) {
        this.riskConfirmed = riskConfirmed;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getRiskGrading() {
        return riskGrading;
    }

    public void setRiskGrading(String riskGrading) {
        this.riskGrading = riskGrading;
    }
}
