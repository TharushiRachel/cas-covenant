package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;

import java.io.Serializable;

public class AFCribReportUpdateRQ implements Serializable {

    private Integer applicationFormID;

    private Integer cribReportID;

    private Integer basicInformationID;

    private String cribDateStr;

    private String reportContent;

    private DomainConstants.CribStatusType cribStatus;

    private String remark;

    private String identificationNo;

    private DomainConstants.CustomerIdentificationType identificationType;

    private AppsConstants.Status status;

    public AFCribReportUpdateRQ() {
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public Integer getCribReportID() {
        return cribReportID;
    }

    public void setCribReportID(Integer cribReportID) {
        this.cribReportID = cribReportID;
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
    }

    public String getCribDateStr() {
        return cribDateStr;
    }

    public void setCribDateStr(String cribDateStr) {
        this.cribDateStr = cribDateStr;
    }

    public String getReportContent() {
        return reportContent;
    }

    public void setReportContent(String reportContent) {
        this.reportContent = reportContent;
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

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "AFCribReportUpdateRQ{" +
                "applicationFormID=" + applicationFormID +
                ", cribReportID=" + cribReportID +
                ", basicInformationID=" + basicInformationID +
                ", cribDateStr='" + cribDateStr + '\'' +
                ", cribStatus='" + cribStatus + '\'' +
                ", remark='" + remark + '\'' +
                ", identificationNo='" + identificationNo + '\'' +
                ", identificationType='" + identificationType + '\'' +
                ", status=" + status +
                '}';
    }
}
