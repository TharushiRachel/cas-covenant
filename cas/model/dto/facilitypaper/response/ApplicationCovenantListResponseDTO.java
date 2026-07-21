package com.itechro.cas.model.dto.facilitypaper.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.dto.facilitypaper.request.ApplicationCovenantDetailsDTO;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ApplicationCovenantListResponseDTO {

    private Integer applicationCovenantId;

    @JsonProperty("RequestUUID")
    private String RequestUUID;

    private String custId;

    private String casReference;

    private List<ApplicationCovenantDetailsDTO> covenantDetails;

    private String createdUserDisplayName;

    private String createdBy;

    private Date createdDate;

    private AppsConstants.CovenantStatus status;

    private String workClass;

//    private Integer facilityPaperID;

}
