package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.DomainConstants;
import lombok.Data;

import java.util.List;

@Data
public class CompPartyDataDTO {

    private String leadPurpose;

    private Integer compPartyId;

    private String partyType;

//    private List<CompPartyIdentificationDTO> identifications;
}
