package com.itechro.cas.model.dto.kalyptosystem;

import com.itechro.cas.model.dto.integration.response.kalipto.KalyptoRatingEvalDTO;
import lombok.Data;

import java.util.List;
@Data
public class KalyptoValueNameRPDTO {

    private List<KalyptoValuesWithNameDTO> values;

    private List<kalyptoPeriodValues> periods;

    private KalyptoGeneralDTO KalyptoRatingEvalDTO;

    private boolean successfullyParse;

    private String message;
}
