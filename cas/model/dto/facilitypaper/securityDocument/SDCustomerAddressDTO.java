package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.CASCustomerAddress;
import lombok.Data;

import java.io.Serializable;

@Data
public class SDCustomerAddressDTO implements Serializable {

    private Integer casCustomerAddressID;

    private Integer casCustomerID;

    private String addressType;

    private String address1;

    private String address2;

    private String city;

    private AppsConstants.Status status;

    public SDCustomerAddressDTO() {
        this.casCustomerAddressID = 0;
        this.casCustomerID = 0;
        this.addressType = "";
        this.address1 = "";
        this.address2 = "";
        this.city = "";
    }

    public SDCustomerAddressDTO(CASCustomerAddress CASCustomerAddress) {
        this.casCustomerAddressID = CASCustomerAddress.getCasCustomerAddressID();
        this.casCustomerID = CASCustomerAddress.getCASCustomer().getCasCustomerID();
        this.addressType = CASCustomerAddress.getAddressType();
        this.address1 = CASCustomerAddress.getAddress1();
        this.address2 = CASCustomerAddress.getAddress2();
        this.city = CASCustomerAddress.getCity();
        this.status = CASCustomerAddress.getStatus();
    }

    public boolean isAddress1Exist(){
        return address1 != null && !address1.isEmpty();
    }

    public boolean isAddress2Exist(){
        return address1 != null && !address1.isEmpty();
    }


    @Override
    public String toString() {
        return "CASCustomerAddressDTO{" +
                "casCustomerAddressID=" + casCustomerAddressID +
                ", casCustomerID=" + casCustomerID +
                ", addressType='" + addressType + '\'' +
                ", address1='" + address1 + '\'' +
                ", address2='" + address2 + '\'' +
                ", city='" + city + '\'' +
                ", status=" + status +
                '}';
    }
}
