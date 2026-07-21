
package com.itechro.cas.model.dto.integration.ws.response;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "custtitle",
    "custfullname",
    "email",
    "customerAddressDTOList",
    "customerBankDetailsDTOList",
    "custnicno",
    "customerTelephoneDTOList",
    "custId",
    "custdob"
})
public class CustomerDetails {

    @JsonProperty("custtitle")
    private String custtitle;
    @JsonProperty("custfullname")
    private String custfullname;
    @JsonProperty("email")
    private String email;
    @JsonProperty("customerAddressDTOList")
    private List<CustomerAddressDTOList> customerAddressDTOList = null;
    @JsonProperty("customerBankDetailsDTOList")
    private List<CustomerBankDetailsDTOList> customerBankDetailsDTOList = null;
    @JsonProperty("custnicno")
    private String custnicno;
    @JsonProperty("customerTelephoneDTOList")
    private List<CustomerTelephoneDTOList> customerTelephoneDTOList = null;
    @JsonProperty("custId")
    private String custId;
    @JsonProperty("custdob")
    private String custdob;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("custtitle")
    public String getCusttitle() {
        return custtitle;
    }

    @JsonProperty("custtitle")
    public void setCusttitle(String custtitle) {
        this.custtitle = custtitle;
    }

    @JsonProperty("custfullname")
    public String getCustfullname() {
        return custfullname;
    }

    @JsonProperty("custfullname")
    public void setCustfullname(String custfullname) {
        this.custfullname = custfullname;
    }

    @JsonProperty("email")
    public String getEmail() {
        return email;
    }

    @JsonProperty("email")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("customerAddressDTOList")
    public List<CustomerAddressDTOList> getCustomerAddressDTOList() {
        return customerAddressDTOList;
    }

    @JsonProperty("customerAddressDTOList")
    public void setCustomerAddressDTOList(List<CustomerAddressDTOList> customerAddressDTOList) {
        this.customerAddressDTOList = customerAddressDTOList;
    }

    @JsonProperty("customerBankDetailsDTOList")
    public List<CustomerBankDetailsDTOList> getCustomerBankDetailsDTOList() {
        return customerBankDetailsDTOList;
    }

    @JsonProperty("customerBankDetailsDTOList")
    public void setCustomerBankDetailsDTOList(List<CustomerBankDetailsDTOList> customerBankDetailsDTOList) {
        this.customerBankDetailsDTOList = customerBankDetailsDTOList;
    }

    @JsonProperty("custnicno")
    public String getCustnicno() {
        return custnicno;
    }

    @JsonProperty("custnicno")
    public void setCustnicno(String custnicno) {
        this.custnicno = custnicno;
    }

    @JsonProperty("customerTelephoneDTOList")
    public List<CustomerTelephoneDTOList> getCustomerTelephoneDTOList() {
        return customerTelephoneDTOList;
    }

    @JsonProperty("customerTelephoneDTOList")
    public void setCustomerTelephoneDTOList(List<CustomerTelephoneDTOList> customerTelephoneDTOList) {
        this.customerTelephoneDTOList = customerTelephoneDTOList;
    }

    @JsonProperty("custId")
    public String getCustId() {
        return custId;
    }

    @JsonProperty("custId")
    public void setCustId(String custId) {
        this.custId = custId;
    }

    @JsonProperty("custdob")
    public String getCustdob() {
        return custdob;
    }

    @JsonProperty("custdob")
    public void setCustdob(String custdob) {
        this.custdob = custdob;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
