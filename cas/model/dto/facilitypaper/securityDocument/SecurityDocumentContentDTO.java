package com.itechro.cas.model.dto.facilitypaper.securityDocument;

import lombok.Data;

import java.util.List;

@Data
public class SecurityDocumentContentDTO {

    private Integer facilityPaperId;

    private Integer facilityId;

    private Integer facilityDisplayOrder;

    private String elementName;

    private String folderName;

    private String documentName;
}
