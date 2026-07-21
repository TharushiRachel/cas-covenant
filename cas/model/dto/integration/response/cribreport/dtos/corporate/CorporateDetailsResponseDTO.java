package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CorporateDetailsResponse;

import java.io.Serializable;

public class CorporateDetailsResponseDTO implements Serializable {

    private CommercialProfileResponseDTO commercialProfileResponseDTO;

    private CommercialAddressResponseDTO commercialAddressResponseDTO;

    private CommercialNormalNamesResponseDTO commercialNormalNamesResponseDTO;

    private CommercialCreditSummaryResponseDTO commercialCreditSummaryResponseDTO;

    private CommercialCreditFacilitiesStatusResponseDTO commercialCreditFacilitiesStatusResponseDTO;

    private CommercialSettledCreditSummaryResponseDTO commercialSettledCreditSummaryResponseDTO;

    private CommercialSettledCreditSummaryDetailsResponseDTO commercialSettledCreditSummaryDetailsResponseDTO;

    private CommercialInquiryResponseDTO commercialInquiryResponseDTO;

    private CommercialCreditFacilityCatalogueResponseDTO commercialCreditFacilityCatalogueResponseDTO;

    public CorporateDetailsResponseDTO() {
    }

    public CorporateDetailsResponseDTO(CorporateDetailsResponse corporateDetailsResponse) {
        if (corporateDetailsResponse != null) {
            if (corporateDetailsResponse.getCommercialProfileResponse() != null) {
                this.commercialProfileResponseDTO = new CommercialProfileResponseDTO(corporateDetailsResponse.getCommercialProfileResponse());
            }

            if (corporateDetailsResponse.getCommercialAddressResponse() != null) {
                this.commercialAddressResponseDTO = new CommercialAddressResponseDTO(corporateDetailsResponse.getCommercialAddressResponse());
            }

            if (corporateDetailsResponse.getCommercialNormalNamesResponse() != null) {
                this.commercialNormalNamesResponseDTO = new CommercialNormalNamesResponseDTO(corporateDetailsResponse.getCommercialNormalNamesResponse());
            }

            if (corporateDetailsResponse.getCommercialCreditSummaryResponse() != null) {
                this.commercialCreditSummaryResponseDTO = new CommercialCreditSummaryResponseDTO(corporateDetailsResponse.getCommercialCreditSummaryResponse());
            }

            if (corporateDetailsResponse.getCommercialCreditFacilitiesStatusResponse() != null) {
                this.commercialCreditFacilitiesStatusResponseDTO = new CommercialCreditFacilitiesStatusResponseDTO(corporateDetailsResponse.getCommercialCreditFacilitiesStatusResponse());
            }

            if (corporateDetailsResponse.getCommercialSettledCreditSummaryResponse() != null) {
                this.commercialSettledCreditSummaryResponseDTO = new CommercialSettledCreditSummaryResponseDTO(corporateDetailsResponse.getCommercialSettledCreditSummaryResponse());
            }

            if (corporateDetailsResponse.getCommercialSettledSummaryDetailsResponse() != null) {
                this.commercialSettledCreditSummaryDetailsResponseDTO = new CommercialSettledCreditSummaryDetailsResponseDTO(corporateDetailsResponse.getCommercialSettledSummaryDetailsResponse());
            }

            if (corporateDetailsResponse.getCommercialInquiryResponse() != null) {
                this.commercialInquiryResponseDTO = new CommercialInquiryResponseDTO(corporateDetailsResponse.getCommercialInquiryResponse());
            }

            if (corporateDetailsResponse.getCommercialCreditfacilityCatalogueResponse() != null) {
                this.commercialCreditFacilityCatalogueResponseDTO = new CommercialCreditFacilityCatalogueResponseDTO(corporateDetailsResponse.getCommercialCreditfacilityCatalogueResponse());
            }
        }
    }

    public CommercialProfileResponseDTO getCommercialProfileResponseDTO() {
        return commercialProfileResponseDTO;
    }

    public void setCommercialProfileResponseDTO(CommercialProfileResponseDTO commercialProfileResponseDTO) {
        this.commercialProfileResponseDTO = commercialProfileResponseDTO;
    }

    public CommercialAddressResponseDTO getCommercialAddressResponseDTO() {
        return commercialAddressResponseDTO;
    }

    public void setCommercialAddressResponseDTO(CommercialAddressResponseDTO commercialAddressResponseDTO) {
        this.commercialAddressResponseDTO = commercialAddressResponseDTO;
    }

    public CommercialNormalNamesResponseDTO getCommercialNormalNamesResponseDTO() {
        return commercialNormalNamesResponseDTO;
    }

    public void setCommercialNormalNamesResponseDTO(CommercialNormalNamesResponseDTO commercialNormalNamesResponseDTO) {
        this.commercialNormalNamesResponseDTO = commercialNormalNamesResponseDTO;
    }

    public CommercialCreditSummaryResponseDTO getCommercialCreditSummaryResponseDTO() {
        return commercialCreditSummaryResponseDTO;
    }

    public void setCommercialCreditSummaryResponseDTO(CommercialCreditSummaryResponseDTO commercialCreditSummaryResponseDTO) {
        this.commercialCreditSummaryResponseDTO = commercialCreditSummaryResponseDTO;
    }

    public CommercialCreditFacilitiesStatusResponseDTO getCommercialCreditFacilitiesStatusResponseDTO() {
        return commercialCreditFacilitiesStatusResponseDTO;
    }

    public void setCommercialCreditFacilitiesStatusResponseDTO(CommercialCreditFacilitiesStatusResponseDTO commercialCreditFacilitiesStatusResponseDTO) {
        this.commercialCreditFacilitiesStatusResponseDTO = commercialCreditFacilitiesStatusResponseDTO;
    }

    public CommercialSettledCreditSummaryResponseDTO getCommercialSettledCreditSummaryResponseDTO() {
        return commercialSettledCreditSummaryResponseDTO;
    }

    public void setCommercialSettledCreditSummaryResponseDTO(CommercialSettledCreditSummaryResponseDTO commercialSettledCreditSummaryResponseDTO) {
        this.commercialSettledCreditSummaryResponseDTO = commercialSettledCreditSummaryResponseDTO;
    }

    public CommercialSettledCreditSummaryDetailsResponseDTO getCommercialSettledCreditSummaryDetailsResponseDTO() {
        return commercialSettledCreditSummaryDetailsResponseDTO;
    }

    public void setCommercialSettledCreditSummaryDetailsResponseDTO(CommercialSettledCreditSummaryDetailsResponseDTO commercialSettledCreditSummaryDetailsResponseDTO) {
        this.commercialSettledCreditSummaryDetailsResponseDTO = commercialSettledCreditSummaryDetailsResponseDTO;
    }

    public CommercialInquiryResponseDTO getCommercialInquiryResponseDTO() {
        return commercialInquiryResponseDTO;
    }

    public void setCommercialInquiryResponseDTO(CommercialInquiryResponseDTO commercialInquiryResponseDTO) {
        this.commercialInquiryResponseDTO = commercialInquiryResponseDTO;
    }

    public CommercialCreditFacilityCatalogueResponseDTO getCommercialCreditFacilityCatalogueResponseDTO() {
        return commercialCreditFacilityCatalogueResponseDTO;
    }

    public void setCommercialCreditFacilityCatalogueResponseDTO(CommercialCreditFacilityCatalogueResponseDTO commercialCreditFacilityCatalogueResponseDTO) {
        this.commercialCreditFacilityCatalogueResponseDTO = commercialCreditFacilityCatalogueResponseDTO;
    }
}
