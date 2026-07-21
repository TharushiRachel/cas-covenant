package com.itechro.cas.model.dto.lead;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class InPrincipalSanctionLetterRQ {

    private Long compFacilityId;

    private String leadRefNo;

    private String decisionDate;

    private List<InPrincipalPartDetails> inPrincipalPartDetails;
}
