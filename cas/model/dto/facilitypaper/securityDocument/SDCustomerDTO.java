package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.model.dto.facilitypaper.CASCustomerAddressDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerIdentificationDTO;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class SDCustomerDTO implements Serializable {

    private String customerKey;

    private Integer casCustomerID;

    private Integer customerID;

    private String customerName;

    private String customerFinancialID;

    private String title;

    private String casCustomerName;

    private String nameWithInitials;

    private String emailAddress;

    private String dateOfBirthStr;

    private String civilStatus;

    private boolean isPrimary;

    private String registrationNo;

    private String salutation;

    private List<SDCustomerAddressDTO> customerAddress;

    private List<CASCustomerIdentificationDTO> customerIdentifications;

    public SDCustomerDTO(){}

    public SDCustomerDTO(CASCustomerDTO casCustomerDTO, String customerKey) {
        this.customerKey = customerKey;
        this.casCustomerID = casCustomerDTO.getCasCustomerID();
        this.customerID = casCustomerDTO.getCustomerID();
        this.customerName = casCustomerDTO.getCustomerName() != null ? casCustomerDTO.getCustomerName() : "";
        this.customerFinancialID = casCustomerDTO.getCustomerFinancialID();
        this.title = casCustomerDTO.getTitle() != null ? convertToLowerCase(casCustomerDTO.getTitle().toString()).concat(".") : "";
        this.nameWithInitials = getCustomerNameWithInitials();
        this.casCustomerName = casCustomerDTO.getCasCustomerName();
        this.civilStatus = casCustomerDTO.getCivilStatus();
        this.isPrimary = casCustomerDTO.isPrimary();
        this.salutation = casCustomerDTO.getSalutation();
        this.registrationNo = casCustomerDTO.getRegistrationNo() != null? casCustomerDTO.getRegistrationNo() : "";
        this.customerAddress = getCustomerAddress();
        if (casCustomerDTO.getCasCustomerAddressDTOList() != null){
            for (CASCustomerAddressDTO address: casCustomerDTO.getCasCustomerAddressDTOList()){
                SDCustomerAddressDTO sdCustomerAddressDTO = new SDCustomerAddressDTO();

                sdCustomerAddressDTO.setCasCustomerID(address.getCasCustomerID());
                sdCustomerAddressDTO.setCasCustomerAddressID(address.getCasCustomerAddressID());
                sdCustomerAddressDTO.setAddress1(capitalizeWords(address.getAddress1()));
                sdCustomerAddressDTO.setAddress2(capitalizeWords(address.getAddress2()));
                sdCustomerAddressDTO.setCity(capitalizeWords(address.getCity()));
                sdCustomerAddressDTO.setAddressType(address.getAddressType());
                sdCustomerAddressDTO.setStatus(address.getStatus());

                customerAddress.add(sdCustomerAddressDTO);
            }
        }

        if (casCustomerDTO.getCasCustomerIdentificationDTOList() != null){
            customerIdentifications = new ArrayList<>(casCustomerDTO.getCasCustomerIdentificationDTOList());
        }
    }

    public List<SDCustomerAddressDTO> getCustomerAddress() {
        if (customerAddress == null){
            return new ArrayList<>();
        }
        return customerAddress;
    }

    private String getCustomerNameWithInitials() {
        String[] parts = customerName.trim().split("\\s+");
        StringBuilder nameWithInitials = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            if (i < parts.length - 1) {
                nameWithInitials.append(parts[i].charAt(0)).append(" ");
            } else {
                nameWithInitials.append(" ").append(parts[i]);
            }
        }

        return nameWithInitials.toString().trim();
    }

    private String convertToLowerCase(String value){
        if (value != null && !value.isEmpty()) {
            String lowercase = value.toLowerCase();
            return lowercase.substring(0, 1).toUpperCase() + lowercase.substring(1);
        }
        return "";
    }


    public String capitalizeWords(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        String[] words = str.split(" ");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
            }
        }
        return sb.toString().trim();
    }


    public boolean isSalutationExist(){
        return salutation != null && !salutation.isEmpty();
    }

    public String getLowerCaseName(){
        if (customerName != null && !customerName.isEmpty()){
            return capitalizeWords(customerName);
        }
        return "";
    }
}
