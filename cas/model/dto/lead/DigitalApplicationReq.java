package com.itechro.cas.model.dto.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class DigitalApplicationReq {
    private Integer digitalApplicationID;
    private Integer leadID;
    @ToString.Exclude
    private String documentContent;
    private AppsConstants.DigitalApplicationStatus documentStatus;
    private String section;
    private List<Long> compPartyIds;
    private Integer compLeadId;
}
