package com.itechro.cas.model.dto.advancedAnalytics;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class LeaseJourneyRequestDTO implements Serializable {

    private Integer leadId;

    private String leadRef;

    private String journeyType;

    private FacilityRequestDTO facility;

    private List<BorrowerRequestDTO> company;

    private List<BorrowerRequestDTO> individuals;
}
