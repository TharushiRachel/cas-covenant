package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerDCSummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerRelationshipResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.DetailsResponse;

import java.util.ArrayList;
import java.util.List;

public class DetailResponseDTO {

    private ConsumerProfileResponseDTO consumerProfileResponseDTO;

    private ConsumerAddressResponseDTO consumerAddressResponseDTO;

    private ConsumerNormalNameResponseDTO consumerNormalNameResponseDTO;

    private ConsumerEmploymentResponseDTO consumerEmploymentResponseDTO;

    private List<ConsumerRelationshipResponseDTO> consumerRelationshipResponseDTOList;

    private CreditSummaryResponseDTO creditSummaryResponseDTO;

    private List<ConsumerDCSummaryResponseDTO> consumerDCSummaryResponseDTOList;

    private ConsumerCreditFacilityStatusResponseDTO consumerCreditFacilityStatusResponseDTO;

    private ConsumerSettledCreditSummaryResponseDTO consumerSettledCreditSummaryResponseDTO;

    private ConsumerSettledCreditSummaryDetailsResponseDTO consumerSettledCreditSummaryDetailsResponseDTO;

    private ConsumerInquiryResponseDTO consumerInquiryResponseDTO;

    private ConsumerCreditFacilityResponseDTO consumerCreditFacilityResponseDTO;

    private ConsumerCFDisputeResponseDTO consumerCFDisputeResponseDTO;

    private ConsumerDCDetailsResponseDTO consumerDCDetailsResponseDTO;

    private ConsumerCreditFacilityCatalogueResponseDTO consumerCreditFacilityCatalogueResponseDTO;

    public DetailResponseDTO() {
    }


    public DetailResponseDTO(DetailsResponse detailsResponse) {
        if (detailsResponse != null) {
            if (detailsResponse.getConsumerProfileResponse() != null) {
                this.consumerProfileResponseDTO = new ConsumerProfileResponseDTO(detailsResponse.getConsumerProfileResponse());
            }
            if (detailsResponse.getConsumerAddressResponse() != null) {
                this.consumerAddressResponseDTO = new ConsumerAddressResponseDTO(detailsResponse.getConsumerAddressResponse());
            }
            if (detailsResponse.getConsumerNormalNameResponse() != null) {
                this.consumerNormalNameResponseDTO = new ConsumerNormalNameResponseDTO(detailsResponse.getConsumerNormalNameResponse());
            }
            if (detailsResponse.getConsumerEmploymentResponse() != null) {
                this.consumerEmploymentResponseDTO = new ConsumerEmploymentResponseDTO(detailsResponse.getConsumerEmploymentResponse());
            }

            if (detailsResponse.getConsumerRelationshipResponse() != null) {
                for (ConsumerRelationshipResponse consumerRelationshipResponse : detailsResponse.getConsumerRelationshipResponse())
                    this.getConsumerRelationshipResponseDTOList().add(new ConsumerRelationshipResponseDTO(consumerRelationshipResponse));
            }

            if (detailsResponse.getCreditSummaryResponse() != null) {
                this.creditSummaryResponseDTO = new CreditSummaryResponseDTO(detailsResponse.getCreditSummaryResponse());
            }
            if (detailsResponse.getConsumerDCSummaryResponse() != null) {
                for (ConsumerDCSummaryResponse consumerDCSummaryResponse : detailsResponse.getConsumerDCSummaryResponse()) {
                    this.getConsumerDCSummaryResponseDTOList().add(new ConsumerDCSummaryResponseDTO(consumerDCSummaryResponse));
                }
            }
            if (detailsResponse.getConsumerCreditFacilityStatusResponse() != null) {
                this.consumerCreditFacilityStatusResponseDTO = new ConsumerCreditFacilityStatusResponseDTO(detailsResponse.getConsumerCreditFacilityStatusResponse());
            }
            if (detailsResponse.getConsumerCreditFacilityStatusResponse() != null) {
                this.consumerSettledCreditSummaryResponseDTO = new ConsumerSettledCreditSummaryResponseDTO(detailsResponse.getConsumerSettledCreditSummaryResponse());
            }
            if (detailsResponse.getConsumerSettledCreditSummaryDetailsResponse() != null) {
                this.consumerSettledCreditSummaryDetailsResponseDTO = new ConsumerSettledCreditSummaryDetailsResponseDTO(detailsResponse.getConsumerSettledCreditSummaryDetailsResponse());
            }
            if (detailsResponse.getConsumerInquiryResponse() != null) {
                this.consumerInquiryResponseDTO = new ConsumerInquiryResponseDTO(detailsResponse.getConsumerInquiryResponse());
            }
            if (detailsResponse.getConsumerCreditFacilityResponse() != null) {
                this.consumerCreditFacilityResponseDTO = new ConsumerCreditFacilityResponseDTO(detailsResponse.getConsumerCreditFacilityResponse());
            }
//            if (detailsResponse.getConsumerCFDisputeResponse() != null) {
//                this.consumerCFDisputeResponseDTO = new ConsumerCFDisputeResponseDTO();
//            }
            if (detailsResponse.getConsumerCreditFacilityCatalogueResponse() != null) {
                this.consumerCreditFacilityCatalogueResponseDTO = new ConsumerCreditFacilityCatalogueResponseDTO(detailsResponse.getConsumerCreditFacilityCatalogueResponse());
            }
        }
    }

    public ConsumerProfileResponseDTO getConsumerProfileResponseDTO() {
        return consumerProfileResponseDTO;
    }

    public void setConsumerProfileResponseDTO(ConsumerProfileResponseDTO consumerProfileResponseDTO) {
        this.consumerProfileResponseDTO = consumerProfileResponseDTO;
    }

    public ConsumerAddressResponseDTO getConsumerAddressResponseDTO() {
        return consumerAddressResponseDTO;
    }

    public void setConsumerAddressResponseDTO(ConsumerAddressResponseDTO consumerAddressResponseDTO) {
        this.consumerAddressResponseDTO = consumerAddressResponseDTO;
    }

    public ConsumerNormalNameResponseDTO getConsumerNormalNameResponseDTO() {
        return consumerNormalNameResponseDTO;
    }

    public void setConsumerNormalNameResponseDTO(ConsumerNormalNameResponseDTO consumerNormalNameResponseDTO) {
        this.consumerNormalNameResponseDTO = consumerNormalNameResponseDTO;
    }

    public ConsumerEmploymentResponseDTO getConsumerEmploymentResponseDTO() {
        return consumerEmploymentResponseDTO;
    }

    public void setConsumerEmploymentResponseDTO(ConsumerEmploymentResponseDTO consumerEmploymentResponseDTO) {
        this.consumerEmploymentResponseDTO = consumerEmploymentResponseDTO;
    }

    public CreditSummaryResponseDTO getCreditSummaryResponseDTO() {
        return creditSummaryResponseDTO;
    }

    public void setCreditSummaryResponseDTO(CreditSummaryResponseDTO creditSummaryResponseDTO) {
        this.creditSummaryResponseDTO = creditSummaryResponseDTO;
    }

    public List<ConsumerDCSummaryResponseDTO> getConsumerDCSummaryResponseDTOList() {
        if (consumerDCSummaryResponseDTOList == null) {
            this.consumerDCSummaryResponseDTOList = new ArrayList<>();
        }
        return consumerDCSummaryResponseDTOList;
    }

    public void setConsumerDCSummaryResponseDTOList(List<ConsumerDCSummaryResponseDTO> consumerDCSummaryResponseDTOList) {
        this.consumerDCSummaryResponseDTOList = consumerDCSummaryResponseDTOList;
    }

    public ConsumerCreditFacilityResponseDTO getConsumerCreditFacilityResponseDTO() {
        return consumerCreditFacilityResponseDTO;
    }

    public void setConsumerCreditFacilityResponseDTO(ConsumerCreditFacilityResponseDTO consumerCreditFacilityResponseDTO) {
        this.consumerCreditFacilityResponseDTO = consumerCreditFacilityResponseDTO;
    }

    public ConsumerSettledCreditSummaryResponseDTO getConsumerSettledCreditSummaryResponseDTO() {
        return consumerSettledCreditSummaryResponseDTO;
    }

    public void setConsumerSettledCreditSummaryResponseDTO(ConsumerSettledCreditSummaryResponseDTO consumerSettledCreditSummaryResponseDTO) {
        this.consumerSettledCreditSummaryResponseDTO = consumerSettledCreditSummaryResponseDTO;
    }

    public ConsumerInquiryResponseDTO getConsumerInquiryResponseDTO() {
        return consumerInquiryResponseDTO;
    }

    public void setConsumerInquiryResponseDTO(ConsumerInquiryResponseDTO consumerInquiryResponseDTO) {
        this.consumerInquiryResponseDTO = consumerInquiryResponseDTO;
    }

    public ConsumerCFDisputeResponseDTO getConsumerCFDisputeResponseDTO() {
        return consumerCFDisputeResponseDTO;
    }

    public void setConsumerCFDisputeResponseDTO(ConsumerCFDisputeResponseDTO consumerCFDisputeResponseDTO) {
        this.consumerCFDisputeResponseDTO = consumerCFDisputeResponseDTO;
    }

    public ConsumerDCDetailsResponseDTO getConsumerDCDetailsResponseDTO() {
        return consumerDCDetailsResponseDTO;
    }

    public void setConsumerDCDetailsResponseDTO(ConsumerDCDetailsResponseDTO consumerDCDetailsResponseDTO) {
        this.consumerDCDetailsResponseDTO = consumerDCDetailsResponseDTO;
    }

    public ConsumerCreditFacilityCatalogueResponseDTO getConsumerCreditFacilityCatalogueResponseDTO() {
        return consumerCreditFacilityCatalogueResponseDTO;
    }

    public void setConsumerCreditFacilityCatalogueResponseDTO(ConsumerCreditFacilityCatalogueResponseDTO consumerCreditFacilityCatalogueResponseDTO) {
        this.consumerCreditFacilityCatalogueResponseDTO = consumerCreditFacilityCatalogueResponseDTO;
    }

    public ConsumerCreditFacilityStatusResponseDTO getConsumerCreditFacilityStatusResponseDTO() {
        return consumerCreditFacilityStatusResponseDTO;
    }

    public void setConsumerCreditFacilityStatusResponseDTO(ConsumerCreditFacilityStatusResponseDTO consumerCreditFacilityStatusResponseDTO) {
        this.consumerCreditFacilityStatusResponseDTO = consumerCreditFacilityStatusResponseDTO;
    }

    public ConsumerSettledCreditSummaryDetailsResponseDTO getConsumerSettledCreditSummaryDetailsResponseDTO() {
        return consumerSettledCreditSummaryDetailsResponseDTO;
    }

    public void setConsumerSettledCreditSummaryDetailsResponseDTO(ConsumerSettledCreditSummaryDetailsResponseDTO consumerSettledCreditSummaryDetailsResponseDTO) {
        this.consumerSettledCreditSummaryDetailsResponseDTO = consumerSettledCreditSummaryDetailsResponseDTO;
    }

    public List<ConsumerRelationshipResponseDTO> getConsumerRelationshipResponseDTOList() {
        if (consumerRelationshipResponseDTOList == null) {
            this.consumerRelationshipResponseDTOList = new ArrayList<>();
        }
        return consumerRelationshipResponseDTOList;
    }

    public void setConsumerRelationshipResponseDTOList(List<ConsumerRelationshipResponseDTO> consumerRelationshipResponseDTOList) {
        this.consumerRelationshipResponseDTOList = consumerRelationshipResponseDTOList;
    }
}
