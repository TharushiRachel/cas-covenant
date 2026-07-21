package com.itechro.cas.model.dto.kalyptosystem.response;

import lombok.Data;

import java.util.List;

@Data
public class KalyptoDTO {

    private Integer Id;

    private Integer periodId;

    private String headerText;

    private Integer customerId;

    private Integer facilityId;

    List<KalyptoDataDTO> kalyptoData;

}
