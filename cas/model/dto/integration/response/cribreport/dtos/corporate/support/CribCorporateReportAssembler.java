package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate.support;

import com.itechro.cas.config.CasProperties;
import com.itechro.cas.model.dto.integration.request.cribreport.CribCorporateRQ;
import com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate.CribCorporateReportResponseDTO;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledSummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CribCorporateReportResponse;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.util.List;

public class CribCorporateReportAssembler extends CribCorporateReportResponseDTO {

    private CasProperties casProperties;

    private CommSettledSummaryResponseHeadersDTO commSettledSummaryResponseHeadersDTO;

    private CribCorporateRQ cribCorporateRQ;

    private LocalDate reportOrderDateDate = LocalDate.now();

    public CribCorporateReportAssembler() {
    }

    public CribCorporateReportAssembler(CribCorporateReportResponse cribCorporateReportResponse, CasProperties casProperties, CribCorporateRQ cribCorporateRQ) {
        super(cribCorporateReportResponse);
        this.casProperties = casProperties;
        this.cribCorporateRQ = cribCorporateRQ;

        if (cribCorporateReportResponse.getCorporateDetailsResponse() != null) {

            //this is for create the headers o f the Summary of Settled Credit Facilities (Last 5 Years)
            if (cribCorporateReportResponse.getCorporateDetailsResponse().getCommercialSettledCreditSummaryResponse().getCommercialSettledSummaryResponses() != null) {
                CommercialSettledSummaryResponse commercialSettledSummaryResponse = cribCorporateReportResponse.getCorporateDetailsResponse().getCommercialSettledCreditSummaryResponse().getCommercialSettledSummaryResponses().get(0);
                commSettledSummaryResponseHeadersDTO = new CommSettledSummaryResponseHeadersDTO(commercialSettledSummaryResponse);
            }
        }
    }

    public CribCorporateRQ getCribCorporateRQ() {
        return cribCorporateRQ;
    }

    public LocalDate getReportOrderDateDate() {
        return reportOrderDateDate;
    }

    public boolean showContent(Object object) {
        return object != null;
    }

    public String getError() {
        if (!super.isSuccess() && !super.getErrors().isEmpty()) {
            return super.getErrors().get(0);
        }

        if (!super.isSuccess() && super.getCorporateFaultResponseDTO() != null && StringUtils.isNotEmpty(super.getCorporateFaultResponseDTO().getDescription())) {
            return super.getCorporateFaultResponseDTO().getDescription();
        }
        return "";
    }

    public boolean showReportBody() {
        return super.getCorporateDetailsResponseDTO() != null;
    }

    public String getDateFormString(String string) {
        if (StringUtils.isNotBlank(string)) {
            return string.substring(0, 10);
        } else {
            return "";
        }
    }

    public CommSettledSummaryResponseHeadersDTO getCommSettledSummaryResponseHeadersDTO() {
        return commSettledSummaryResponseHeadersDTO;
    }

    public boolean showTableBody(List<Object> objects) {
        return !objects.isEmpty();
    }
}
