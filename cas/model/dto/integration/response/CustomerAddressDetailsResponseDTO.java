package com.itechro.cas.model.dto.integration.response;

import java.io.Serializable;

public class CustomerAddressDetailsResponseDTO implements Serializable {

    private String communicationAddr;

    private String emplAddr;

    private String permenentAddress;

    private boolean success;

    public CustomerAddressDetailsResponseDTO(CustomerAddressDetailsResponse customerAddressDetailsResponse){

        this.communicationAddr = customerAddressDetailsResponse.getCommunicationAddr();
        this.emplAddr = customerAddressDetailsResponse.getEmplAddr();
        this.permenentAddress = customerAddressDetailsResponse.getPermenentAddress();
    }

    public String getCommunicationAddr() {
        return communicationAddr;
    }

    public void setCommunicationAddr(String communicationAddr) {
        this.communicationAddr = communicationAddr;
    }

    public String getEmplAddr() {
        return emplAddr;
    }

    public void setEmplAddr(String emplAddr) {
        this.emplAddr = emplAddr;
    }

    public String getPermenentAddress() {
        return permenentAddress;
    }

    public void setPermenentAddress(String permenentAddress) {
        this.permenentAddress = permenentAddress;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
