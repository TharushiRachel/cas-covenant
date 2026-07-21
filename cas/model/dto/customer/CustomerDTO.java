package com.itechro.cas.model.dto.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.customer.*;
import com.itechro.cas.model.domain.facilitypaper.CASCustomer;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerDTO implements Serializable {

    private static final long serialVersionUID = -6545256419286416949L;

    private Integer customerID;

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


    private String errorMessage;

    private DomainConstants.CustomerIdentificationType identificationType;

    private List<CustomerAddressDTO> customerAddressDTOList;

    private List<CustomerTelephoneDTO> customerTelephoneDTOList;

    private List<CustomerIdentificationDTO> customerIdentificationDTOList;

    private List<CustomerBankDetailsDTO> customerBankDetailsDTOList;

    public CustomerDTO() {
    }


    public CustomerDTO(Customer customer) {

        this.customerID = customer.getCustomerID();
        this.customerFinancialID = customer.getCustomerFinancialID();
        this.customerName = customer.getCustomerName();
        this.emailAddress = customer.getEmailAddress();
        this.secondaryEmailAddress = customer.getSecondaryEmailAddress();
        if (customer.getModifiedDate() != null) {
            this.lastUpdateDateStr = CalendarUtil.getDefaultFormattedDateTime(customer.getModifiedDate());
        } else if (customer.getCreatedDate() != null) {
            this.lastUpdateDateStr = CalendarUtil.getDefaultFormattedDateTime(customer.getCreatedDate());
        }
        if (customer.getDateOfBirth() != null) {
            this.dateOfBirth = CalendarUtil.getDefaultFormattedDateOnly(customer.getDateOfBirth());
        }
        this.civilStatus = customer.getCivilStatus();
        this.title = customer.getTitle();
        this.status = customer.getStatus();

        for (CustomerAddress customerAddress : customer.getCustomerAddresses()) {
            if (customerAddress.getStatus() == AppsConstants.Status.ACT) {
                this.getCustomerAddressDTOList().add(new CustomerAddressDTO(customerAddress));
            }
        }

        for (CustomerTelephone customerTelephone : customer.getCustomerTelephones()) {
            if (customerTelephone.getStatus() == AppsConstants.Status.ACT) {
                this.getCustomerTelephoneDTOList().add(new CustomerTelephoneDTO(customerTelephone));
            }
        }

        for (CustomerIdentification customerIdentification : customer.getCustomerIdentifications()) {
            if (customerIdentification.getStatus() == AppsConstants.Status.ACT) {
                this.getCustomerIdentificationDTOList().add(new CustomerIdentificationDTO(customerIdentification));
            }
        }

        for (CustomerBankDetail customerBankDetail : customer.getCustomerBankDetails()) {
            if (customerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                this.getCustomerBankDetailsDTOList().add(new CustomerBankDetailsDTO(customerBankDetail));
            }
        }
    }

    public CustomerDTO(Customer customer, CustomerLoadOptionDTO loadOptionDTO) {

        this.customerID = customer.getCustomerID();
        this.customerFinancialID = customer.getCustomerFinancialID();
        this.customerName = customer.getCustomerName();
        this.emailAddress = customer.getEmailAddress();
        this.secondaryEmailAddress = customer.getSecondaryEmailAddress();
        if (customer.getDateOfBirth() != null) {
            this.dateOfBirth = CalendarUtil.getDefaultFormattedDateOnly(customer.getDateOfBirth());
        }
        this.civilStatus = customer.getCivilStatus();
        this.title = customer.getTitle();
        this.status = customer.getStatus();

        if (loadOptionDTO != null) {
            if (loadOptionDTO.isLoadAddressDetail()) {
                for (CustomerAddress customerAddress : customer.getCustomerAddresses()) {
                    if (customerAddress.getStatus() == AppsConstants.Status.ACT) {
                        this.getCustomerAddressDTOList().add(new CustomerAddressDTO(customerAddress));
                    }
                }
            }

            if (loadOptionDTO.isLoadTelephoneDetail()) {
                for (CustomerTelephone customerTelephone : customer.getCustomerTelephones()) {
                    if (customerTelephone.getStatus() == AppsConstants.Status.ACT) {
                        this.getCustomerTelephoneDTOList().add(new CustomerTelephoneDTO(customerTelephone));
                    }
                }
            }

            if (loadOptionDTO.isLoadIdentificationDetail()) {
                for (CustomerIdentification customerIdentification : customer.getCustomerIdentifications()) {
                    if (customerIdentification.getStatus() == AppsConstants.Status.ACT) {
                        this.getCustomerIdentificationDTOList().add(new CustomerIdentificationDTO(customerIdentification));
                    }
                }
            }

            if (loadOptionDTO.isLoadBankDetail()) {
                for (CustomerBankDetail customerBankDetail : customer.getCustomerBankDetails()) {
                    if (customerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                        this.getCustomerBankDetailsDTOList().add(new CustomerBankDetailsDTO(customerBankDetail));
                    }
                }
            }
        }
    }

    public CustomerDTO(CASCustomer CASCustomer) {
        this.customerName = CASCustomer.getCasCustomerName();
        this.emailAddress = CASCustomer.getEmailAddress();
        this.secondaryEmailAddress = CASCustomer.getSecondaryEmailAddress();
        if (CASCustomer.getDateOfBirth() != null) {
            this.dateOfBirth = CalendarUtil.getDefaultFormattedDateOnly(CASCustomer.getDateOfBirth());
        }
        this.civilStatus = CASCustomer.getCivilStatus();
        this.title = CASCustomer.getTitle();
        this.status = CASCustomer.getStatus();
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public String getCustomerFinancialID() {
        return customerFinancialID;
    }

    public void setCustomerFinancialID(String customerFinancialID) {
        this.customerFinancialID = customerFinancialID;
    }

    public DomainConstants.Title getTitle() {
        return title;
    }

    public void setTitle(DomainConstants.Title title) {
        this.title = title;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public List<CustomerAddressDTO> getCustomerAddressDTOList() {
        if (customerAddressDTOList == null) {
            customerAddressDTOList = new ArrayList<>();
        }
        return customerAddressDTOList;
    }

    public void setCustomerAddressDTOList(List<CustomerAddressDTO> customerAddressDTOList) {
        this.customerAddressDTOList = customerAddressDTOList;
    }

    public List<CustomerTelephoneDTO> getCustomerTelephoneDTOList() {
        if (customerTelephoneDTOList == null) {
            customerTelephoneDTOList = new ArrayList<>();
        }
        return customerTelephoneDTOList;
    }

    public void setCustomerTelephoneDTOList(List<CustomerTelephoneDTO> customerTelephoneDTOList) {
        this.customerTelephoneDTOList = customerTelephoneDTOList;
    }

    public List<CustomerIdentificationDTO> getCustomerIdentificationDTOList() {
        if (customerIdentificationDTOList == null) {
            customerIdentificationDTOList = new ArrayList<>();
        }
        return customerIdentificationDTOList;
    }

    public void setCustomerIdentificationDTOList(List<CustomerIdentificationDTO> customerIdentificationDTOList) {
        this.customerIdentificationDTOList = customerIdentificationDTOList;
    }

    public List<CustomerBankDetailsDTO> getCustomerBankDetailsDTOList() {
        if (customerBankDetailsDTOList == null) {
            customerBankDetailsDTOList = new ArrayList<>();
        }
        return customerBankDetailsDTOList;
    }

    public void setCustomerBankDetailsDTOList(List<CustomerBankDetailsDTO> customerBankDetailsDTOList) {
        this.customerBankDetailsDTOList = customerBankDetailsDTOList;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerID=" + customerID +
                ", customerFinancialID=" + customerFinancialID +
                ", customerName=" + customerName +
                ", civilStatus=" + civilStatus +
                ", status=" + status +
                ", customerAddressDTOList" + customerAddressDTOList +
                ", customerTelephoneDTOList" + customerTelephoneDTOList +
                ", customerIdentificationDTOList" + customerIdentificationDTOList +
                ", customerBankDetailsDTOList" + customerBankDetailsDTOList +
                '}';
    }
}
