package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail.support;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerHistorySummaryResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.MonthYearResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CreditFacilityHistoryHeadersDTO implements Serializable {

    private String SNO;

    private List<String> months;

    public CreditFacilityHistoryHeadersDTO() {
    }

    public CreditFacilityHistoryHeadersDTO(MonthYearResponse monthYearResponse) {
        if (monthYearResponse.getConsumerHistorySummaryResponses() != null) {

            this.SNO = monthYearResponse.getSno();

            for (ConsumerHistorySummaryResponse consumerHistorySummaryResponse : monthYearResponse.getConsumerHistorySummaryResponses()) {
                this.getMonths().add(consumerHistorySummaryResponse.getMonth());
            }
        }

    }

    public String getSNO() {
        return SNO;
    }

    public void setSNO(String SNO) {
        this.SNO = SNO;
    }

    public List<String> getMonths() {
        if (months == null) {
            this.months = new ArrayList<>();
        }
        return months;
    }

    public void setMonths(List<String> months) {
        this.months = months;
    }
}
