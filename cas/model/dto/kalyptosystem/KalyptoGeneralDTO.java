package com.itechro.cas.model.dto.kalyptosystem;

import lombok.Data;

@Data
public class KalyptoGeneralDTO {

    private Integer id;
    private String counterpartyName;
    private String uniqueIdentifier;
    private String model;
    private String ratingscore;
    private String facilityId;
    private String customerId;
}
