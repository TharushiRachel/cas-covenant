package com.itechro.cas.model.dto.integration.request.cribreport;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Date;

public class CribRetailReportRQ implements Serializable {

    private DomainConstants.CustomerIdentificationType identificationType;

    private String identificationNumber;

    private String customerName;

    private String customerGender;

    private Date reportOrderDateDate;

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerGender() {
        return customerGender;
    }

    public void setCustomerGender(String customerGender) {
        this.customerGender = customerGender;
    }

    public Date getReportOrderDateDate() {
        if (reportOrderDateDate == null) {
            reportOrderDateDate = new Date();
        }
        return reportOrderDateDate;
    }

    public void setReportOrderDateDate(Date reportOrderDateDate) {
        this.reportOrderDateDate = reportOrderDateDate;
    }

    public String getReportOrderDateDateStr() {
        if (this.getReportOrderDateDate() != null) {
            return CalendarUtil.getDefaultFormattedDateOnly(reportOrderDateDate);
        }
        return null;
    }

    @Override
    public String toString() {
        return "CribRetailReportRQ{" +
                "identificationType='" + identificationType + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerGender='" + customerGender + '\'' +
                ", reportOrderDateDate=" + reportOrderDateDate +
                '}';
    }
}
