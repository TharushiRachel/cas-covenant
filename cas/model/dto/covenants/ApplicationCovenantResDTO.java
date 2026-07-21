package com.itechro.cas.model.dto.covenants;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.facilitypaper.request.ApplicationCovenantDetailsDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ApplicationCovenantResDTO {

    private Integer applicationCovenantId;

    private String RequestUUID;

    private String custId;

    private String casReference;

    private String createdUserDisplayName;

    private String createdBy;

    private Date createdDate;

    private AppsConstants.CovenantStatus status;
}
