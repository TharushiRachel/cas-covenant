package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.InquiryDetailsResponse;

import java.io.Serializable;

public class InquiryDetailsResponseDTO implements Serializable {

    private String slno;

    private String currency;

    private String inquiryDate;

    private String amount;

    private String reasonID;

    private String reason;

    private String institutionType;

    private String productName;

    public InquiryDetailsResponseDTO() {
    }

    public InquiryDetailsResponseDTO(InquiryDetailsResponse inquiryDetailsResponse) {
        if (inquiryDetailsResponse != null) {
            this.slno = inquiryDetailsResponse.getSlno();
            this.currency = inquiryDetailsResponse.getCurrency();
            this.inquiryDate = inquiryDetailsResponse.getInquiryDate();
            this.amount = inquiryDetailsResponse.getAmount();
            this.reasonID = inquiryDetailsResponse.getReasonID();
            this.reason = inquiryDetailsResponse.getReason();
            this.institutionType = inquiryDetailsResponse.getInstitutionType();
            this.productName = inquiryDetailsResponse.getProductName();
        }
    }

    public String getSlno() {
        return slno;
    }

    public void setSlno(String slno) {
        this.slno = slno;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getInquiryDate() {
        return inquiryDate;
    }

    public void setInquiryDate(String inquiryDate) {
        this.inquiryDate = inquiryDate;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getReasonID() {
        return reasonID;
    }

    public void setReasonID(String reasonID) {
        this.reasonID = reasonID;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getInstitutionType() {
        return institutionType;
    }

    public void setInstitutionType(String institutionType) {
        this.institutionType = institutionType;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
