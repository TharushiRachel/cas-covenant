package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomerCribReport;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Date;

public class CASCustomerCribReportDTO implements Serializable {

    private Integer customerCribReportID;

    private Integer facilityPaperID;

    private Integer casCustomerID;

    private Date cribDate;

    private String cribDateStr;

    private String reportContent;

    private DomainConstants.CribStatusType cribStatus;

    private String remark;

    private String identificationType;

    private String identificationNo;

    private AppsConstants.Status status;

    private String savedUserDisplayName;

    private String savedUserDivCode;


    public CASCustomerCribReportDTO() {
    }

    public CASCustomerCribReportDTO(CASCustomerCribReport CASCustomerCribReport) {
        this.customerCribReportID = CASCustomerCribReport.getCasCustomerCribReportID();
        this.casCustomerID = CASCustomerCribReport.getCASCustomer().getCasCustomerID();
        this.cribDate = CASCustomerCribReport.getCribDate();
        this.cribDateStr = CalendarUtil.getDefaultFormattedDateTimeOnly(CASCustomerCribReport.getCribDate());
        this.reportContent = CASCustomerCribReport.getReportContent();
        this.cribStatus = CASCustomerCribReport.getCribStatus();
        this.remark = CASCustomerCribReport.getRemark();
        this.identificationType = CASCustomerCribReport.getIdentificationType();
        this.identificationNo = CASCustomerCribReport.getIdentificationNo();
        this.status = CASCustomerCribReport.getStatus();
        this.savedUserDisplayName = CASCustomerCribReport.getSavedUserDisplayName();
        this.savedUserDivCode = CASCustomerCribReport.getSavedUserDivCode();
    }

    public Integer getCustomerCribReportID() {
        return customerCribReportID;
    }

    public void setCustomerCribReportID(Integer customerCribReportID) {
        this.customerCribReportID = customerCribReportID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public Date getCribDate() {
        return cribDate;
    }

    public void setCribDate(Date cribDate) {
        this.cribDate = cribDate;
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

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
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

    public String getCribDateStr() {
        return cribDateStr;
    }

    public void setCribDateStr(String cribDateStr) {
        this.cribDateStr = cribDateStr;
    }

    public String getSavedUserDisplayName() {
        return savedUserDisplayName;
    }

    public void setSavedUserDisplayName(String savedUserDisplayName) {
        this.savedUserDisplayName = savedUserDisplayName;
    }

    public String getSavedUserDivCode() {
        return savedUserDivCode;
    }

    public void setSavedUserDivCode(String savedUserDivCode) {
        this.savedUserDivCode = savedUserDivCode;
    }

    @Override
    public String toString() {
        return "CASCustomerCribReportDTO{" +
                "customerCribReportID=" + customerCribReportID +
                ", facilityPaperID=" + facilityPaperID +
                ", casCustomerID=" + casCustomerID +
                ", cribDate=" + cribDate +
                ", cribDateStr='" + cribDateStr + '\'' +
                ", cribStatus=" + cribStatus +
                ", remark='" + remark + '\'' +
                ", identificationType='" + identificationType + '\'' +
                ", identificationNo='" + identificationNo + '\'' +
                ", status=" + status +
                ", savedUserDisplayName='" + savedUserDisplayName + '\'' +
                ", savedUserDivCode='" + savedUserDivCode + '\'' +
                '}';
    }
}
