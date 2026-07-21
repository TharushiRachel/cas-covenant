package com.itechro.cas.model.dto.lead;

import lombok.Data;

import java.util.List;

@Data
public class DigitalizedApplicationRequestDTO {

    private Long compLeadId;
    private List<Long> compPartyIds;

}
