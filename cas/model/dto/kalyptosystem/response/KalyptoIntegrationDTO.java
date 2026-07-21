package com.itechro.cas.model.dto.kalyptosystem.response;

import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoRatingEvalDTO;
import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoRatingPeriodDTO;
import lombok.Data;

import java.util.List;

@Data
public class KalyptoIntegrationDTO {

    private KalyptoRatingEvalDTO generalTabDetails;

    private List<KalyptoRatingPeriodDTO> tableColumnNames;

    private List<KalyptoRowDTO> tableRows;

    private boolean successfullyParse;

    private String message;
}
