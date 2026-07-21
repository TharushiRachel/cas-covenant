package com.itechro.cas.model.dto.kalyptosystem;

import lombok.Data;

import java.util.List;

@Data
public class KalyptoValueRPDTO {

    private List<KalyptoValuesDTO> values;
    private List<kalyptoPeriodValues> periods;
    private  KalyptoGeneralDTO kalyptoGeneralDTO;
}
