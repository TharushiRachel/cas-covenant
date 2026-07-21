package com.itechro.cas.model.dto.applicationform.applicationFormCustomer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.*;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AFCustomerDTO implements Serializable {

    private Integer afCustomerID;

    private Integer customerID;

    //TODO
    private DomainConstants.Title title;

    private String customerFinancialID;

    private String customerName;

    private String emailAddress;

    private String secondaryEmailAddress;

    private String dateOfBirth;

    private String civilStatus;

    private AppsConstants.Status status;

    private String lastUpdateDateStr;

    private String telephoneNumber;

    private String bankAccNumber;

    private DomainConstants.CustomerIdentificationType identificationType;

    private List<AFCustomerAddressDTO> afCustomerAddressDTOList;

    private List<AFCustomerTelephoneDTO> afCustomerTelephoneDTOList;

    private List<AFCustomerIdentificationDTO> afCustomerIdentificationDTOList;

    private List<AFCustomerBankDetailsDTO> afCustomerBankDetailsDTOList;

    public AFCustomerDTO() {
    }

    public AFCustomerDTO(AFCustomer afCustomer) {

        this.afCustomerID = afCustomer.getAfCustomerID();
        this.customerID = afCustomer.getCustomerID();
        this.customerFinancialID = afCustomer.getCustomerFinancialID();
        this.customerName = afCustomer.getCustomerName();
        this.emailAddress = afCustomer.getEmailAddress();
        this.secondaryEmailAddress = afCustomer.getSecondaryEmailAddress();
        if (afCustomer.getDateOfBirth() != null) {
            this.dateOfBirth = CalendarUtil.getDefaultFormattedDateOnly(afCustomer.getDateOfBirth());
        }
        this.civilStatus = afCustomer.getCivilStatus();
        this.status = afCustomer.getStatus();
        if (afCustomer.getModifiedDate() != null) {
            this.lastUpdateDateStr = CalendarUtil.getDefaultFormattedDateTime(afCustomer.getModifiedDate());
        } else if (afCustomer.getCreatedDate() != null) {
            this.lastUpdateDateStr = CalendarUtil.getDefaultFormattedDateTime(afCustomer.getCreatedDate());
        }

        for (AFCustomerAddress afCustomerAddress : afCustomer.getOrderedAFCustomerAddress()) {
            if (afCustomerAddress.getStatus() == AppsConstants.Status.ACT) {
                this.getAfCustomerAddressDTOList().add(new AFCustomerAddressDTO(afCustomerAddress));
            }
        }

        for (AFCustomerTelephone afCustomerTelephone : afCustomer.getOrderedAFCustomerTelephones()) {
            if (afCustomerTelephone.getStatus() == AppsConstants.Status.ACT) {
                this.getAfCustomerTelephoneDTOList().add(new AFCustomerTelephoneDTO(afCustomerTelephone));
            }
        }

        for (AFCustomerIdentification afCustomerIdentification : afCustomer.getOrderedAFCustomerIdentification()) {
            if (afCustomerIdentification.getStatus() == AppsConstants.Status.ACT) {
                this.getAfCustomerIdentificationDTOList().add(new AFCustomerIdentificationDTO(afCustomerIdentification));
            }
        }

        for (AFCustomerBankDetail afCustomerBankDetail : afCustomer.getOrderedAFCustomerBankDetails()) {
            if (afCustomerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                this.getAfCustomerBankDetailsDTOList().add(new AFCustomerBankDetailsDTO(afCustomerBankDetail));
            }
        }

    }

    public Integer getAfCustomerID() {
        return afCustomerID;
    }

    public void setAfCustomerID(Integer afCustomerID) {
        this.afCustomerID = afCustomerID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public DomainConstants.Title getTitle() {
        return title;
    }

    public void setTitle(DomainConstants.Title title) {
        this.title = title;
    }

    public String getCustomerFinancialID() {
        return customerFinancialID;
    }

    public void setCustomerFinancialID(String customerFinancialID) {
        this.customerFinancialID = customerFinancialID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSecondaryEmailAddress() {
        return secondaryEmailAddress;
    }

    public void setSecondaryEmailAddress(String secondaryEmailAddress) {
        this.secondaryEmailAddress = secondaryEmailAddress;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public String getLastUpdateDateStr() {
        return lastUpdateDateStr;
    }

    public void setLastUpdateDateStr(String lastUpdateDateStr) {
        this.lastUpdateDateStr = lastUpdateDateStr;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public String getBankAccNumber() {
        return bankAccNumber;
    }

    public void setBankAccNumber(String bankAccNumber) {
        this.bankAccNumber = bankAccNumber;
    }

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public List<AFCustomerAddressDTO> getAfCustomerAddressDTOList() {
        if (afCustomerAddressDTOList == null) {
            this.afCustomerAddressDTOList = new ArrayList<>();
        }
        return afCustomerAddressDTOList;
    }

    public void setAfCustomerAddressDTOList(List<AFCustomerAddressDTO> afCustomerAddressDTOList) {
        this.afCustomerAddressDTOList = afCustomerAddressDTOList;
    }


    public List<AFCustomerTelephoneDTO> getAfCustomerTelephoneDTOList() {
        if (afCustomerTelephoneDTOList == null) {
            this.afCustomerTelephoneDTOList = new ArrayList<>();
        }
        return afCustomerTelephoneDTOList;
    }

    public void setAfCustomerTelephoneDTOList(List<AFCustomerTelephoneDTO> afCustomerTelephoneDTOList) {
        this.afCustomerTelephoneDTOList = afCustomerTelephoneDTOList;
    }

    public List<AFCustomerIdentificationDTO> getAfCustomerIdentificationDTOList() {
        if (afCustomerIdentificationDTOList == null) {
            this.afCustomerIdentificationDTOList = new ArrayList<>();
        }
        return afCustomerIdentificationDTOList;
    }

    public void setAfCustomerIdentificationDTOList(List<AFCustomerIdentificationDTO> afCustomerIdentificationDTOList) {
        this.afCustomerIdentificationDTOList = afCustomerIdentificationDTOList;
    }

    public List<AFCustomerBankDetailsDTO> getAfCustomerBankDetailsDTOList() {
        if (afCustomerBankDetailsDTOList == null) {
            this.afCustomerBankDetailsDTOList = new ArrayList<>();
        }
        return afCustomerBankDetailsDTOList;
    }

    public void setAfCustomerBankDetailsDTOList(List<AFCustomerBankDetailsDTO> afCustomerBankDetailsDTOList) {
        this.afCustomerBankDetailsDTOList = afCustomerBankDetailsDTOList;
    }
}
