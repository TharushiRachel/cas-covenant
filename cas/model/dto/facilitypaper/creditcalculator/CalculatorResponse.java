package com.itechro.cas.model.dto.facilitypaper.creditcalculator;

import java.util.ArrayList;
import java.util.List;

public class CalculatorResponse {
    private List<CalculationDTO> systemOutputResponseData;
    private List<CreditScheduleDTO> creditScheduleResponseData;
    private List<StipulatedLossValueDTO> stipulatedLossValueResponseData;

    public List<CalculationDTO> getSystemOutputResponseData() {
        if (systemOutputResponseData == null) {
            systemOutputResponseData = new ArrayList<>();
        }
        return systemOutputResponseData;
    }

    public void setSystemOutputResponseData(List<CalculationDTO> systemOutputResponseData) {
        this.systemOutputResponseData = systemOutputResponseData;
    }

    public List<CreditScheduleDTO> getCreditScheduleResponseData() {
        return creditScheduleResponseData;
    }

    public void setCreditScheduleResponseData(List<CreditScheduleDTO> creditScheduleResponseData) {
        this.creditScheduleResponseData = creditScheduleResponseData;
    }

    public List<StipulatedLossValueDTO> getStipulatedLossValueResponseData() {
        return stipulatedLossValueResponseData;
    }

    public void setStipulatedLossValueResponseData(List<StipulatedLossValueDTO> stipulatedLossValueResponseData) {
        this.stipulatedLossValueResponseData = stipulatedLossValueResponseData;
    }

    public CalculationDTO getCalculationDTOByOutputCode(String outPutCode) {
        return this.getSystemOutputResponseData().stream().
                filter(calculationDTO -> {
                    return calculationDTO.getOutputCode().equals(outPutCode);
                }).findFirst().orElse(null);
    }
}
