package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

@Data
public class SecurityDocumentReq implements Serializable {

    private Integer securityDocumentID;

    private Integer facilityPaperID;

    private Integer creditFacilityTemplateID;

    private String creditFacilityName;

    private Integer elementID;

    private Integer facilityID;

    private String documentName;

    @ToString.Exclude
    private String documentContent;

    private AppsConstants.SecurityDocumentStatus documentStatus;

    private String actionComment;

    private List<FPSecurityDocumentTagDTO> documentTags;
}
