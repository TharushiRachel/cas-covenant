package com.itechro.cas.model.dto.integration.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "AddressDetails"
})
public class CustomerAdditionalDetailResponse implements Serializable {

    @JsonProperty("AddressDetails")
    private CustomerAddressDetailsResponse customerAddressDetailsResponse;

    public CustomerAddressDetailsResponse getCustomerAddressDetailsResponse() {
        return customerAddressDetailsResponse;
    }

    public void setCustomerAddressDetailsResponse(CustomerAddressDetailsResponse customerAddressDetailsResponse) {
        this.customerAddressDetailsResponse = customerAddressDetailsResponse;
    }

    @Override
    public String toString() {
        return "CustomerAdditionalDetailResponse{" +
                "customerAddressDetailsResponse=" + customerAddressDetailsResponse +
                '}';
    }
}
