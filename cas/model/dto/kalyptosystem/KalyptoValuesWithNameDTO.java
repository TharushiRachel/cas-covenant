package com.itechro.cas.model.dto.kalyptosystem;

import lombok.Data;

import java.util.List;
@Data
public class KalyptoValuesWithNameDTO {

    private  String parameterName;
    private List<KalyptoValuesDTO> values;

}
