package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class InquiryDetailsResponse implements Serializable {

    @JsonProperty("SLNO")
    private String slno;

    @JsonProperty("CURRENCY")
    private String currency;

    @JsonProperty("INQUIRY_DATE")
    private String inquiryDate;

    @JsonProperty("AMOUNT")
    private String amount;

    @JsonProperty("REASON_ID")
    private String reasonID;

    @JsonProperty("REASON")
    private String reason;

    @JsonProperty("INSTITUTION_TYPE")
    private String institutionType;

    @JsonProperty("PRODUCT_NAME")
    private String productName;

    @JsonProperty("SLNO")
    public String getSlno() {
        return slno;
    }

    @JsonProperty("SLNO")
    public void setSlno(String slno) {
        this.slno = slno;
    }

    @JsonProperty("CURRENCY")
    public String getCurrency() {
        return currency;
    }

    @JsonProperty("CURRENCY")
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @JsonProperty("INQUIRY_DATE")
    public String getInquiryDate() {
        return inquiryDate;
    }

    @JsonProperty("INQUIRY_DATE")
    public void setInquiryDate(String inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    @JsonProperty("AMOUNT")
    public String getAmount() {
        return amount;
    }

    @JsonProperty("AMOUNT")
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @JsonProperty("REASON_ID")
    public String getReasonID() {
        return reasonID;
    }

    @JsonProperty("REASON_ID")
    public void setReasonID(String reasonID) {
        this.reasonID = reasonID;
    }

    @JsonProperty("REASON")
    public String getReason() {
        return reason;
    }

    @JsonProperty("REASON")
    public void setReason(String reason) {
        this.reason = reason;
    }

    @JsonProperty("INSTITUTION_TYPE")
    public String getInstitutionType() {
        return institutionType;
    }

    @JsonProperty("INSTITUTION_TYPE")
    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    @JsonProperty("PRODUCT_NAME")
    public String getProductName() {
        return productName;
    }

    @JsonProperty("PRODUCT_NAME")
    public void setProductName(String productName) {
        this.productName = productName;
    }
}
