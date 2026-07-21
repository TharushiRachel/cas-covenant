package com.itechro.cas.model.dto.customer;

import com.itechro.cas.model.domain.customer.CustomerCribResponse;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class CustomerCribResponseDTO implements Serializable {

    private Integer customerCribReportID;

    private String nic;

    private String brn;

    private String response;

    private String pdfReport;

    private String customerName;

    private String customerGender;

    private String createdDateStr;

    private String createdBy;

    public CustomerCribResponseDTO() {
    }

    public CustomerCribResponseDTO(CustomerCribResponse customerCribResponse) {
        this.customerCribReportID = customerCribResponse.getCustomerCribReportID();
        this.nic = customerCribResponse.getNic();
        this.brn = customerCribResponse.getBrn();
        this.customerName = customerCribResponse.getCustomerName();
        this.customerGender = customerCribResponse.getCustomerGender();
        this.response = customerCribResponse.getResponse();
        this.createdBy = customerCribResponse.getCreatedBy();
        if (customerCribResponse.getCreatedDate() != null) {
            this.createdDateStr = CalendarUtil.getDefaultFormattedDateTime(customerCribResponse.getCreatedDate());
        }
    }

    public Integer getCustomerCribReportID() {
        return customerCribReportID;
    }

    public void setCustomerCribReportID(Integer customerCribReportID) {
        this.customerCribReportID = customerCribReportID;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getBrn() {
        return brn;
    }

    public void setBrn(String brn) {
        this.brn = brn;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getCreatedDateStr() {
        return createdDateStr;
    }

    public void setCreatedDateStr(String createdDateStr) {
        this.createdDateStr = createdDateStr;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getPdfReport() {
        return pdfReport;
    }

    public void setPdfReport(String pdfReport) {
        this.pdfReport = pdfReport;
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

    @Override
    public String toString() {
        return "CustomerCribResponseDTO{" +
                "customerCribReportID=" + customerCribReportID +
                ", nic='" + nic + '\'' +
                ", brn='" + brn + '\'' +
                ", response='" + response + '\'' +
                ", pdfReport='" + pdfReport + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerGender='" + customerGender + '\'' +
                ", createdDateStr='" + createdDateStr + '\'' +
                ", createdBy='" + createdBy + '\'' +
                '}';
    }
}
