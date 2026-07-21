package com.itechro.cas.model.dto.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CustomerAdditionInfoDTO implements Serializable {

    private Integer customerID;

    private List<CustomerAddressDTO> customerAddressDTOList;

    private List<CustomerTelephoneDTO> customerTelephoneDTOList;

    private List<CustomerIdentificationDTO> customerIdentificationDTOList;

    private List<CustomerBankDetailsDTO> customerBankDetailsDTOList;

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public List<CustomerAddressDTO> getCustomerAddressDTOList() {
        if(customerAddressDTOList == null){
            customerAddressDTOList = new ArrayList<>();
        }
        return customerAddressDTOList;
    }

    public void setCustomerAddressDTOList(List<CustomerAddressDTO> customerAddressDTOList) {
        this.customerAddressDTOList = customerAddressDTOList;
    }

    public List<CustomerTelephoneDTO> getCustomerTelephoneDTOList() {
        if(customerTelephoneDTOList == null){
            customerTelephoneDTOList = new ArrayList<>();
        }
        return customerTelephoneDTOList;
    }

    public void setCustomerTelephoneDTOList(List<CustomerTelephoneDTO> customerTelephoneDTOList) {
        this.customerTelephoneDTOList = customerTelephoneDTOList;
    }

    public List<CustomerIdentificationDTO> getCustomerIdentificationDTOList() {
        if(customerIdentificationDTOList == null){
            customerIdentificationDTOList = new ArrayList<>();
        }
        return customerIdentificationDTOList;
    }

    public void setCustomerIdentificationDTOList(List<CustomerIdentificationDTO> customerIdentificationDTOList) {
        this.customerIdentificationDTOList = customerIdentificationDTOList;
    }

    public List<CustomerBankDetailsDTO> getCustomerBankDetailsDTOList() {
        if(customerBankDetailsDTOList == null){
            customerBankDetailsDTOList = new ArrayList<>();
        }
        return customerBankDetailsDTOList;
    }

    public void setCustomerBankDetailsDTOList(List<CustomerBankDetailsDTO> customerBankDetailsDTOList) {
        this.customerBankDetailsDTOList = customerBankDetailsDTOList;
    }

    @Override
    public String toString() {
        return "CustomerDTO{" +
                "customerID=" + customerID +
                ", customerAddressDTOList" + customerAddressDTOList +
                ", customerTelephoneDTOList" + customerTelephoneDTOList +
                ", customerIdentificationDTOList" + customerIdentificationDTOList +
                ", customerBankDetailsDTOList" + customerBankDetailsDTOList +
                '}';
    }
}
