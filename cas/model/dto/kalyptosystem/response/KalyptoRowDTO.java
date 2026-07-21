package com.itechro.cas.model.dto.kalyptosystem.response;

import lombok.Data;

import java.util.List;

@Data
public class KalyptoRowDTO {
    private String name;

    private List<KalyptoPeriodDTO> row;



}
