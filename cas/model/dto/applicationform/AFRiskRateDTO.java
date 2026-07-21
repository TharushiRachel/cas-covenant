package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFRiskRate;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class AFRiskRateDTO implements Serializable {

    private Integer applicationFormID;

    private Integer riskRateID;

    private Integer basicInformationID;

    private String scoring;

    private String riskGrading;

    private String lastRatedStr;

    private String model;

    private String assetClassification;

    private String initiatedBranch;

    private AppsConstants.YesNo riskConfirmed;

    private AppsConstants.Status status;

    public AFRiskRateDTO() {
    }

    public AFRiskRateDTO(AFRiskRate afRiskRate) {
        this.applicationFormID = afRiskRate.getApplicationFormID();
        this.riskRateID = afRiskRate.getRiskRateID();
        this.basicInformationID = afRiskRate.getBasicInformation().getBasicInformationID();
        this.scoring = afRiskRate.getScoring();
        this.riskGrading = afRiskRate.getRiskGrading();
        if (afRiskRate.getLastRated() != null) {
            this.lastRatedStr = CalendarUtil.getDefaultFormattedDateOnly(afRiskRate.getLastRated());
        }
        this.model = afRiskRate.getModel();
        this.assetClassification = afRiskRate.getAssetClassification();
        this.initiatedBranch = afRiskRate.getInitiatedBranch();
        this.riskConfirmed = afRiskRate.getRiskConfirmed();
        this.status = afRiskRate.getStatus();
    }

    public Integer getRiskRateID() {
        return riskRateID;
    }

    public void setRiskRateID(Integer riskRateID) {
        this.riskRateID = riskRateID;
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
    }

    public String getScoring() {
        return scoring;
    }

    public void setScoring(String scoring) {
        this.scoring = scoring;
    }

    public String getLastRatedStr() {
        return lastRatedStr;
    }

    public void setLastRatedStr(String lastRatedStr) {
        this.lastRatedStr = lastRatedStr;
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

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
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

    @Override
    public String toString() {
        return "AFRiskRateDTO{" +
                "applicationFormID=" + applicationFormID +
                ", riskRateID=" + riskRateID +
                ", basicInformationID=" + basicInformationID +
                ", scoring='" + scoring + '\'' +
                ", riskGrading='" + riskGrading + '\'' +
                ", lastRatedStr='" + lastRatedStr + '\'' +
                ", model='" + model + '\'' +
                ", assetClassification='" + assetClassification + '\'' +
                ", initiatedBranch='" + initiatedBranch + '\'' +
                ", riskConfirmed=" + riskConfirmed +
                ", status=" + status +
                '}';
    }
}
