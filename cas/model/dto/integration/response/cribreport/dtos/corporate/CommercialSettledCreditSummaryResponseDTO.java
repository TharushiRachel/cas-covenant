package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledCreditSummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.CommercialSettledSummaryResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CommercialSettledCreditSummaryResponseDTO implements Serializable {

    private List<CommercialSettledSummaryResponseDTO> commercialSettledSummaryResponsesDTOList;

    public CommercialSettledCreditSummaryResponseDTO() {
    }

    public CommercialSettledCreditSummaryResponseDTO(CommercialSettledCreditSummaryResponse commercialSettledCreditSummaryResponse) {
        if (commercialSettledCreditSummaryResponse != null) {
            if (commercialSettledCreditSummaryResponse.getCommercialSettledSummaryResponses() != null) {
                for (CommercialSettledSummaryResponse commercialSettledSummaryResponse : commercialSettledCreditSummaryResponse.getCommercialSettledSummaryResponses())
                    getCommercialSettledSummaryResponsesDTOList().add(new CommercialSettledSummaryResponseDTO(commercialSettledSummaryResponse));
            }
        }
    }

    public List<CommercialSettledSummaryResponseDTO> getCommercialSettledSummaryResponsesDTOList() {
        if (commercialSettledSummaryResponsesDTOList == null) {
            this.commercialSettledSummaryResponsesDTOList = new ArrayList<>();
        }
        return commercialSettledSummaryResponsesDTOList;
    }

    public void setCommercialSettledSummaryResponsesDTOList(List<CommercialSettledSummaryResponseDTO> commercialSettledSummaryResponsesDTOList) {
        this.commercialSettledSummaryResponsesDTOList = commercialSettledSummaryResponsesDTOList;
    }
}
