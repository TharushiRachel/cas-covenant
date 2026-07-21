package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsResponse {

    @JsonProperty("CONSUMER_PROFILE_VER4")
    private ConsumerProfileResponse consumerProfileResponse;

    @JsonProperty("CONSUMER_ADDRESS_VER4")
    private ConsumerAddressResponse consumerAddressResponse;

    @JsonProperty("CONSUMER_NORMAL_NAMES_VER4")
    private ConsumerNormalNameResponse consumerNormalNameResponse;

    @JsonProperty("CONSUMER_EMPLOYMENT_VER4")
    private ConsumerEmploymentResponse consumerEmploymentResponse;

    @JsonProperty("CONSUMER_RELATIONSHIPS_VER4")
    private List<ConsumerRelationshipResponse> consumerRelationshipResponse;

    @JsonProperty("CREDIT_SUMMARY_VER4")
    private CreditSummaryResponse creditSummaryResponse;

    @JsonProperty("CONSUMER_DC_SUMMARY_VER4")
    private List<ConsumerDCSummaryResponse> consumerDCSummaryResponse;

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_VER4")
    private ConsumerCreditFacilityStatusResponse consumerCreditFacilityStatusResponse;

    @JsonProperty("CONSUMER_SETTLED_CREDIT_SUMMARY_VER4")
    private ConsumerSettledCreditSummaryResponse consumerSettledCreditSummaryResponse;

    @JsonProperty("CONSUMER_SETTLED_CREDIT_SUMMARY_DETAILS_VER1")
    private ConsumerSettledCreditSummaryDetailsResponse consumerSettledCreditSummaryDetailsResponse;

    @JsonProperty("CONSUMER_INQUIRY_VER4")
    private ConsumerInquiryResponse consumerInquiryResponse;

    @JsonProperty("CONSUMER_CREDIT_FACILITY_VER8")
    private ConsumerCreditFacilityResponse consumerCreditFacilityResponse;

//    @JsonProperty("CONSUMER_CF_DISPUTE_VER4")
//    private ConsumerCFDisputeResponse consumerCFDisputeResponse;

//    @JsonProperty("CONSUMER_DC_DETAILS_VER4")
//    private ConsumerDCDetailsResponse consumerDCDetailsResponse;

    @JsonProperty("CONSUMER_CREDIT_FACILITY_CATALOGUE_VER4")
    private ConsumerCreditFacilityCatalogueResponse consumerCreditFacilityCatalogueResponse;

    @JsonProperty("CONSUMER_PROFILE_VER4")
    public ConsumerProfileResponse getConsumerProfileResponse() {
        return consumerProfileResponse;
    }

    @JsonProperty("CONSUMER_PROFILE_VER4")
    public void setConsumerProfileResponse(ConsumerProfileResponse consumerProfileResponse) {
        this.consumerProfileResponse = consumerProfileResponse;
    }


    @JsonProperty("CONSUMER_ADDRESS_VER4")
    public ConsumerAddressResponse getConsumerAddressResponse() {
        return consumerAddressResponse;
    }

    @JsonProperty("CONSUMER_ADDRESS_VER4")
    public void setConsumerAddressResponse(ConsumerAddressResponse consumerAddressResponse) {
        this.consumerAddressResponse = consumerAddressResponse;
    }


    @JsonProperty("CONSUMER_NORMAL_NAMES_VER4")
    public ConsumerNormalNameResponse getConsumerNormalNameResponse() {
        return consumerNormalNameResponse;
    }

    @JsonProperty("CONSUMER_NORMAL_NAMES_VER4")
    public void setConsumerNormalNameResponse(ConsumerNormalNameResponse consumerNormalNameResponse) {
        this.consumerNormalNameResponse = consumerNormalNameResponse;
    }


    @JsonProperty("CONSUMER_EMPLOYMENT_VER4")
    public ConsumerEmploymentResponse getConsumerEmploymentResponse() {
        return consumerEmploymentResponse;
    }

    @JsonProperty("CONSUMER_EMPLOYMENT_VER4")
    public void setConsumerEmploymentResponse(ConsumerEmploymentResponse consumerEmploymentResponse) {
        this.consumerEmploymentResponse = consumerEmploymentResponse;
    }

    @JsonProperty("CONSUMER_RELATIONSHIPS_VER4")
    public List<ConsumerRelationshipResponse> getConsumerRelationshipResponse() {
        return consumerRelationshipResponse;
    }

    @JsonProperty("CONSUMER_RELATIONSHIPS_VER4")
    public void setConsumerRelationshipResponse(List<ConsumerRelationshipResponse> consumerRelationshipResponse) {
        this.consumerRelationshipResponse = consumerRelationshipResponse;
    }

    @JsonProperty("CREDIT_SUMMARY_VER4")
    public CreditSummaryResponse getCreditSummaryResponse() {
        return creditSummaryResponse;
    }

    @JsonProperty("CREDIT_SUMMARY_VER4")
    public void setCreditSummaryResponse(CreditSummaryResponse creditSummaryResponse) {
        this.creditSummaryResponse = creditSummaryResponse;
    }

    @JsonProperty("CONSUMER_DC_SUMMARY_VER4")
    public List<ConsumerDCSummaryResponse> getConsumerDCSummaryResponse() {
        return consumerDCSummaryResponse;
    }

    @JsonProperty("CONSUMER_DC_SUMMARY_VER4")
    public void setConsumerDCSummaryResponse(List<ConsumerDCSummaryResponse> consumerDCSummaryResponse) {
        this.consumerDCSummaryResponse = consumerDCSummaryResponse;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_VER4")
    public ConsumerCreditFacilityStatusResponse getConsumerCreditFacilityStatusResponse() {
        return consumerCreditFacilityStatusResponse;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_STATUS_VER4")
    public void setConsumerCreditFacilityStatusResponse(ConsumerCreditFacilityStatusResponse consumerCreditFacilityStatusResponse) {
        this.consumerCreditFacilityStatusResponse = consumerCreditFacilityStatusResponse;
    }

    @JsonProperty("CONSUMER_SETTLED_CREDIT_SUMMARY_VER4")
    public ConsumerSettledCreditSummaryResponse getConsumerSettledCreditSummaryResponse() {
        return consumerSettledCreditSummaryResponse;
    }

    @JsonProperty("CONSUMER_SETTLED_CREDIT_SUMMARY_VER4")
    public void setConsumerSettledCreditSummaryResponse(ConsumerSettledCreditSummaryResponse consumerSettledCreditSummaryResponse) {
        this.consumerSettledCreditSummaryResponse = consumerSettledCreditSummaryResponse;
    }

    @JsonProperty("CONSUMER_SETTLED_CREDIT_SUMMARY_DETAILS_VER1")
    public ConsumerSettledCreditSummaryDetailsResponse getConsumerSettledCreditSummaryDetailsResponse() {
        return consumerSettledCreditSummaryDetailsResponse;
    }

    @JsonProperty("CONSUMER_SETTLED_CREDIT_SUMMARY_DETAILS_VER1")
    public void setConsumerSettledCreditSummaryDetailsResponse(ConsumerSettledCreditSummaryDetailsResponse consumerSettledCreditSummaryDetailsResponse) {
        this.consumerSettledCreditSummaryDetailsResponse = consumerSettledCreditSummaryDetailsResponse;
    }

    @JsonProperty("CONSUMER_INQUIRY_VER4")
    public ConsumerInquiryResponse getConsumerInquiryResponse() {
        return consumerInquiryResponse;
    }

    @JsonProperty("CONSUMER_INQUIRY_VER4")
    public void setConsumerInquiryResponse(ConsumerInquiryResponse consumerInquiryResponse) {
        this.consumerInquiryResponse = consumerInquiryResponse;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_VER8")
    public ConsumerCreditFacilityResponse getConsumerCreditFacilityResponse() {
        return consumerCreditFacilityResponse;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_VER8")
    public void setConsumerCreditFacilityResponse(ConsumerCreditFacilityResponse consumerCreditFacilityResponse) {
        this.consumerCreditFacilityResponse = consumerCreditFacilityResponse;
    }

//    @JsonProperty("CONSUMER_CF_DISPUTE_VER4")
//    public ConsumerCFDisputeResponse getConsumerCFDisputeResponse() {
//        return consumerCFDisputeResponse;
//    }
//
//    @JsonProperty("CONSUMER_CF_DISPUTE_VER4")
//    public void setConsumerCFDisputeResponse(ConsumerCFDisputeResponse consumerCFDisputeResponse) {
//        this.consumerCFDisputeResponse = consumerCFDisputeResponse;
//    }

//    @JsonProperty("CONSUMER_DC_DETAILS_VER4")
//    public ConsumerDCDetailsResponse getConsumerDCDetailsResponse() {
//        return consumerDCDetailsResponse;
//    }
//
//    @JsonProperty("CONSUMER_DC_DETAILS_VER4")
//    public void setConsumerDCDetailsResponse(ConsumerDCDetailsResponse consumerDCDetailsResponse) {
//        this.consumerDCDetailsResponse = consumerDCDetailsResponse;
//    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_CATALOGUE_VER4")
    public ConsumerCreditFacilityCatalogueResponse getConsumerCreditFacilityCatalogueResponse() {
        return consumerCreditFacilityCatalogueResponse;
    }

    @JsonProperty("CONSUMER_CREDIT_FACILITY_CATALOGUE_VER4")
    public void setConsumerCreditFacilityCatalogueResponse(ConsumerCreditFacilityCatalogueResponse consumerCreditFacilityCatalogueResponse) {
        this.consumerCreditFacilityCatalogueResponse = consumerCreditFacilityCatalogueResponse;
    }
}
