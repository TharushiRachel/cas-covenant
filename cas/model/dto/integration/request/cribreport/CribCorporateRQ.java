package com.itechro.cas.model.dto.integration.request.cribreport;

import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.Date;

public class CribCorporateRQ implements Serializable {

    private String identificationType;

    private String identificationNumber;

    private String customerName;

    private String REGNo;

    private Date reportOrderDateDate;

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
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

    public String getREGNo() {
        return REGNo;
    }

    public void setREGNo(String REGNo) {
        this.REGNo = REGNo;
    }

    public Date getReportOrderDateDate() {
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
        return "CribCorporateRQ{" +
                "identificationType='" + identificationType + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", REGNo='" + REGNo + '\'' +
                '}';
    }
}
