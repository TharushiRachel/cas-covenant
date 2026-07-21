package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail.support;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRetailReportRQ;
import com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail.CreditDetailsResponseDTO;
import com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail.CribRetailReportResponseDTO;
import com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail.MonthYearResponseDTO;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsSettledSummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.CribRetailReportResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.MonthYearResponse;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CribRetailReportAssembler extends CribRetailReportResponseDTO {

    private CasProperties casProperties;

    private ConsSettledSummaryResponseHeadersDTO consSettledSummaryResponseHeadersDTO;

    private CreditFacilityHistoryHeadersDTO creditFacilityHistoryHeadersDTO;

    private Map<Integer, List<CreditDetailsResponseDTO>> creditDetailsResponseDTOMap;

    private Map<Integer, List<MonthYearResponseDTO>> monthYearResponseDTOMap;

    private CribRetailReportRQ cribRetailReportRQ;

    public CribRetailReportAssembler() {
    }

    public CribRetailReportAssembler(CribRetailReportResponse cribRetailReportResponse, CribRetailReportRQ cribRetailReportRQ, CasProperties casProperties) {
        super(cribRetailReportResponse);
        this.casProperties = casProperties;
        this.cribRetailReportRQ = cribRetailReportRQ;
        if (cribRetailReportResponse.getDetailsResponse() != null) {

            //this is for create the headers o f the Summary of Settled Credit Facilities (Last 5 Years)
            if (cribRetailReportResponse.getDetailsResponse().getConsumerSettledCreditSummaryResponse() != null && cribRetailReportResponse.getDetailsResponse().getConsumerSettledCreditSummaryResponse().getConsSettledSummaryResponses() != null) {
                ConsSettledSummaryResponse consSettledSummaryResponse = cribRetailReportResponse.getDetailsResponse().getConsumerSettledCreditSummaryResponse().getConsSettledSummaryResponses().get(0);
                consSettledSummaryResponseHeadersDTO = new ConsSettledSummaryResponseHeadersDTO(consSettledSummaryResponse);
            }

            if (cribRetailReportResponse.getDetailsResponse().getConsumerCreditFacilityResponse().getMasterCreditDetailsResponse() != null && cribRetailReportResponse.getDetailsResponse().getConsumerCreditFacilityResponse().getMasterCreditDetailsResponse().getMonthYearResponses() != null) {
                MonthYearResponse monthYearResponse = cribRetailReportResponse.getDetailsResponse().getConsumerCreditFacilityResponse().getMasterCreditDetailsResponse().getMonthYearResponses().get(0);
                creditFacilityHistoryHeadersDTO = new CreditFacilityHistoryHeadersDTO(monthYearResponse);
            }

            int i = 0;
            int creditDetailsKeyIndex = 0;

            for (CreditDetailsResponseDTO creditDetailsResponseDTO : super.getDetailResponseDTO().getConsumerCreditFacilityResponseDTO().getMasterCreditDetailsResponseDTO().getCreditDetailsResponsesDtos()) {
                if (i % this.casProperties.getCribFacilityViewLimit() == 0) {
                    creditDetailsKeyIndex++;
                }
                i++;
                this.getCreditDetailsResponseDTOMap().computeIfAbsent(creditDetailsKeyIndex, k -> new ArrayList<>());
                this.getCreditDetailsResponseDTOMap().get(creditDetailsKeyIndex).add(creditDetailsResponseDTO);
            }

            int j = 0;
            int monthYearKeyIndex = 0;
            for (MonthYearResponseDTO monthYearResponseDTO : super.getDetailResponseDTO().getConsumerCreditFacilityResponseDTO().getMasterCreditDetailsResponseDTO().getMonthYearResponsesDtos()) {
                if (j % this.casProperties.getCribFacilityViewLimit() == 0) {
                    monthYearKeyIndex++;
                }
                j++;
                this.getMonthYearResponseDTOMap().computeIfAbsent(monthYearKeyIndex, k -> new ArrayList<>());
                this.getMonthYearResponseDTOMap().get(monthYearKeyIndex).add(monthYearResponseDTO);
            }
        }
    }

    public String getDateFormString(String string) {
        if (StringUtils.isNotBlank(string)) {
            return string.substring(0, 10);
        } else {
            return "";
        }
    }

    public boolean showRelationShipDetails() {
        return super.getDetailResponseDTO().getConsumerRelationshipResponseDTOList() != null;
    }

    public boolean showConsumerDCSummary() {
        return super.getDetailResponseDTO().getConsumerDCSummaryResponseDTOList() != null;
    }

    public boolean showConsSettledSummary() {
        return getConsSettledSummaryResponseHeadersDTO() != null;
    }

    public boolean showContent(Object object) {
        return object != null;
    }

    public boolean showTableBody(List<Object> objects) {
        return !objects.isEmpty();
    }

    public boolean showReportBody() {
        return super.getDetailResponseDTO() != null;
    }

    public ConsSettledSummaryResponseHeadersDTO getConsSettledSummaryResponseHeadersDTO() {
        return consSettledSummaryResponseHeadersDTO;
    }

    public void setConsSettledSummaryResponseHeadersDTO(ConsSettledSummaryResponseHeadersDTO consSettledSummaryResponseHeadersDTO) {
        this.consSettledSummaryResponseHeadersDTO = consSettledSummaryResponseHeadersDTO;
    }

    public CreditFacilityHistoryHeadersDTO getCreditFacilityHistoryHeadersDTO() {
        return creditFacilityHistoryHeadersDTO;
    }

    public void setCreditFacilityHistoryHeadersDTO(CreditFacilityHistoryHeadersDTO creditFacilityHistoryHeadersDTO) {
        this.creditFacilityHistoryHeadersDTO = creditFacilityHistoryHeadersDTO;
    }


    public Map<Integer, List<CreditDetailsResponseDTO>> getCreditDetailsResponseDTOMap() {
        if (creditDetailsResponseDTOMap == null) {
            creditDetailsResponseDTOMap = new HashMap<>();
        }
        return creditDetailsResponseDTOMap;
    }

    public void setCreditDetailsResponseDTOMap(Map<Integer, List<CreditDetailsResponseDTO>> creditDetailsResponseDTOMap) {
        this.creditDetailsResponseDTOMap = creditDetailsResponseDTOMap;
    }

    public Map<Integer, List<MonthYearResponseDTO>> getMonthYearResponseDTOMap() {
        if (monthYearResponseDTOMap == null) {
            this.monthYearResponseDTOMap = new HashMap<>();
        }
        return monthYearResponseDTOMap;
    }

    public void setMonthYearResponseDTOMap(Map<Integer, List<MonthYearResponseDTO>> monthYearResponseDTOMap) {
        this.monthYearResponseDTOMap = monthYearResponseDTOMap;
    }

    public CasProperties getCasProperties() {
        return casProperties;
    }

    public void setCasProperties(CasProperties casProperties) {
        this.casProperties = casProperties;
    }

    public CribRetailReportRQ getCribRetailReportRQ() {
        return cribRetailReportRQ;
    }

    public void setCribRetailReportRQ(CribRetailReportRQ cribRetailReportRQ) {
        this.cribRetailReportRQ = cribRetailReportRQ;
    }

    public String showGender(String genderCode) {
        if (genderCode.equals("001")) {
            return "Male";
        } else if (genderCode.equals("002")) {
            return "Female";
        } else {
            return "";
        }
    }

    public String getError() {
        if (!super.isSuccess() && !super.getErrors().isEmpty()) {
            return super.getErrors().get(0);
        }

        if (!super.isSuccess() && super.getFaultResponseDTO() != null && StringUtils.isNotEmpty(super.getFaultResponseDTO().getDescription())) {
            return super.getFaultResponseDTO().getDescription();
        }
        return "";
    }

}
