package com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CorporateDetailsResponse implements Serializable {

    @JsonProperty("COMMERCIAL_PROFILE_VER4")
    private CommercialProfileResponse commercialProfileResponse;

    @JsonProperty("COMMERCIAL_ADDRESS_VER4")
    private CommercialAddressResponse commercialAddressResponse;

    @JsonProperty("COMMERCIAL_NORMAL_NAMES_VER4")
    private CommercialNormalNamesResponse commercialNormalNamesResponse;

    @JsonProperty("COMMERCIAL_CREDIT_SUMMARY_VER4")
    private CommercialCreditSummaryResponse commercialCreditSummaryResponse;

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_VER4")
    private CommercialCreditFacilitiesStatusResponse CommercialCreditFacilitiesStatusResponse;

    @JsonProperty("COMMERCIAL_SETTLED_CREDIT_SUMMARY_VER4")
    private CommercialSettledCreditSummaryResponse commercialSettledCreditSummaryResponse;

    @JsonProperty("COMMERCIAL_SETTLED_CREDIT_SUMMARY_DETAILS_VER1")
    private CommercialSettledCreditSummaryDetailsResponse commercialSettledSummaryDetailsResponse;

    @JsonProperty("COMMERCIAL_INQUIRY_VER4")
    private CommercialInquiryResponse commercialInquiryResponse;

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_CATALOGUE_VER4")
    private CommercialCreditFacilityCatalogueResponse commercialCreditfacilityCatalogueResponse;

    @JsonProperty("COMMERCIAL_SETTLED_CREDIT_SUMMARY_DETAILS_VER1")
    public CommercialSettledCreditSummaryDetailsResponse getCommercialSettledSummaryDetailsResponse() {
        return commercialSettledSummaryDetailsResponse;
    }

    @JsonProperty("COMMERCIAL_SETTLED_CREDIT_SUMMARY_DETAILS_VER1")
    public void setCommercialSettledSummaryDetailsResponse(CommercialSettledCreditSummaryDetailsResponse commercialSettledSummaryDetailsResponse) {
        this.commercialSettledSummaryDetailsResponse = commercialSettledSummaryDetailsResponse;
    }

    @JsonProperty("COMMERCIAL_NORMAL_NAMES_VER4")
    public CommercialNormalNamesResponse getCommercialNormalNamesResponse() {
        return commercialNormalNamesResponse;
    }

    @JsonProperty("COMMERCIAL_NORMAL_NAMES_VER4")
    public void setCommercialNormalNamesResponse(CommercialNormalNamesResponse commercialNormalNamesResponse) {
        this.commercialNormalNamesResponse = commercialNormalNamesResponse;
    }

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_CATALOGUE_VER4")
    public CommercialCreditFacilityCatalogueResponse getCommercialCreditfacilityCatalogueResponse() {
        return commercialCreditfacilityCatalogueResponse;
    }

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_CATALOGUE_VER4")
    public void setCommercialCreditfacilityCatalogueResponse(CommercialCreditFacilityCatalogueResponse commercialCreditfacilityCatalogueResponse) {
        this.commercialCreditfacilityCatalogueResponse = commercialCreditfacilityCatalogueResponse;
    }

    @JsonProperty("COMMERCIAL_INQUIRY_VER4")
    public CommercialInquiryResponse getCommercialInquiryResponse() {
        return commercialInquiryResponse;
    }

    @JsonProperty("COMMERCIAL_INQUIRY_VER4")
    public void setCommercialInquiryResponse(CommercialInquiryResponse commercialInquiryResponse) {
        this.commercialInquiryResponse = commercialInquiryResponse;
    }

    @JsonProperty("COMMERCIAL_SETTLED_CREDIT_SUMMARY_VER4")
    public CommercialSettledCreditSummaryResponse getCommercialSettledCreditSummaryResponse() {
        return commercialSettledCreditSummaryResponse;
    }

    @JsonProperty("COMMERCIAL_SETTLED_CREDIT_SUMMARY_VER4")
    public void setCommercialSettledCreditSummaryResponse(CommercialSettledCreditSummaryResponse commercialSettledCreditSummaryResponse) {
        this.commercialSettledCreditSummaryResponse = commercialSettledCreditSummaryResponse;
    }

    @JsonProperty("COMMERCIAL_ADDRESS_VER4")
    public CommercialAddressResponse getCommercialAddressResponse() {
        return commercialAddressResponse;
    }

    @JsonProperty("COMMERCIAL_ADDRESS_VER4")
    public void setCommercialAddressResponse(CommercialAddressResponse commercialAddressResponse) {
        this.commercialAddressResponse = commercialAddressResponse;
    }

    @JsonProperty("COMMERCIAL_PROFILE_VER4")
    public CommercialProfileResponse getCommercialProfileResponse() {
        return commercialProfileResponse;
    }

    @JsonProperty("COMMERCIAL_PROFILE_VER4")
    public void setCommercialProfileResponse(CommercialProfileResponse commercialProfileResponse) {
        this.commercialProfileResponse = commercialProfileResponse;
    }

    @JsonProperty("COMMERCIAL_CREDIT_SUMMARY_VER4")
    public CommercialCreditSummaryResponse getCommercialCreditSummaryResponse() {
        return commercialCreditSummaryResponse;
    }

    @JsonProperty("COMMERCIAL_CREDIT_SUMMARY_VER4")
    public void setCommercialCreditSummaryResponse(CommercialCreditSummaryResponse commercialCreditSummaryResponse) {
        this.commercialCreditSummaryResponse = commercialCreditSummaryResponse;
    }

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_VER4")
    public CommercialCreditFacilitiesStatusResponse getCommercialCreditFacilitiesStatusResponse() {
        return CommercialCreditFacilitiesStatusResponse;
    }

    @JsonProperty("COMMERCIAL_CREDIT_FACILITY_STATUS_VER4")
    public void setCommercialCreditFacilitiesStatusResponse(CommercialCreditFacilitiesStatusResponse commercialCreditFacilitiesStatusResponse) {
        CommercialCreditFacilitiesStatusResponse = commercialCreditFacilitiesStatusResponse;
    }

}
