package com.itechro.cas.model.dto.covenants;

import lombok.Data;

@Data
public class ExistingFacilityCovenantsDTO {

    private Long id;

    private String acctId;

    private Integer facilityId;

    private Integer facilityPaperId;
}
