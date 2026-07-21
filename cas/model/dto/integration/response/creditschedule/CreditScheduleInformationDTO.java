package com.itechro.cas.model.dto.integration.response.creditschedule;

import java.util.List;

public class CreditScheduleInformationDTO {
    private List<CreditScheduleESBDTO> creditScheduleESBCatalog;

    public List<CreditScheduleESBDTO> getCreditScheduleESBCatalog() {
        return creditScheduleESBCatalog;
    }

    public void setCreditScheduleESBCatalog(List<CreditScheduleESBDTO> creditScheduleESBCatalog) {
        this.creditScheduleESBCatalog = creditScheduleESBCatalog;
    }

    @Override
    public String toString() {
        return "CreditScheduleInformationDTO{" +
                "creditScheduleESBCatalog=" + creditScheduleESBCatalog +
                '}';
    }
}
