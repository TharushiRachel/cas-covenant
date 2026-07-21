package com.itechro.cas.model.dto.integration.request.cribreport;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.security.CredentialsDTO;

import java.io.Serializable;
import java.util.Date;

public class CribResponseSaveRQ implements Serializable {

    private DomainConstants.CustomerIdentificationType identificationType;

    private String identificationNumber;

    private String customerName;

    private String customerGender;

    private Date reportOrderDateDate;

    private CredentialsDTO credentialsDTO;

    private String responseString;

    private Date createdDate;

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
        return reportOrderDateDate;
    }

    public void setReportOrderDateDate(Date reportOrderDateDate) {
        this.reportOrderDateDate = reportOrderDateDate;
    }

    public CredentialsDTO getCredentialsDTO() {
        return credentialsDTO;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public String getResponseString() {
        return responseString;
    }

    public void setResponseString(String responseString) {
        this.responseString = responseString;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public String toString() {
        return "CribResponseSaveRQ{" +
                "identificationType='" + identificationType + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", customerName='" + customerName + '\'' +
                ", customerGender='" + customerGender + '\'' +
                ", reportOrderDateDate=" + reportOrderDateDate +
                ", credentialsDTO=" + credentialsDTO +
                ", createdDate=" + createdDate +
                '}';
    }
}
