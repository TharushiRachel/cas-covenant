package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.AFCribReport;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class AFCribReportDTO implements Serializable {

    private Integer cribReportID;

    private Integer basicInformationID;

    private String cribDateStr;

    private String reportContent;

    private DomainConstants.CribStatusType cribStatus;

    private String remark;

    private String identificationNo;

    private DomainConstants.CustomerIdentificationType identificationType;

    private AppsConstants.Status status;

    public AFCribReportDTO() {
    }

    public AFCribReportDTO(AFCribReport afCribReport) {
        this.cribReportID = afCribReport.getCribReportID();
        this.basicInformationID = afCribReport.getBasicInformation().getBasicInformationID();
        if (afCribReport.getCribDate() != null) {
            this.cribDateStr = CalendarUtil.getDefaultFormattedDateOnly(afCribReport.getCribDate());
        }
        this.reportContent = afCribReport.getReportContent();
        this.cribStatus = afCribReport.getCribStatus();
        this.remark = afCribReport.getRemark();
        this.identificationNo = afCribReport.getIdentificationNo();
        this.status = afCribReport.getStatus();
        this.identificationType = afCribReport.getIdentificationType();
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

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    @Override
    public String toString() {
        return "AFCribReportDTO{" +
                "cribReportID=" + cribReportID +
                ", basicInformationID=" + basicInformationID +
                ", cribDateStr='" + cribDateStr + '\'' +
                ", reportContent='" + reportContent + '\'' +
                ", cribStatus='" + cribStatus + '\'' +
                ", remark='" + remark + '\'' +
                ", identificationNo='" + identificationNo + '\'' +
                ", identificationType='" + identificationType + '\'' +
                ", status=" + status +
                '}';
    }
}
