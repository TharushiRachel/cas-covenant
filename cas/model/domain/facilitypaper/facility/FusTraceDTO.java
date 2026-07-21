package com.itechro.cas.model.domain.facilitypaper.facility;
import lombok.Data;

import java.util.Date;

@Data
public class FusTraceDTO {

    private Integer id;

    private Integer parentRecId;

    private String comment;

    private String flag;

    private String condition;

    private Integer fpUPCSectionDataId;

    private Integer facilityPaperId;

    private Integer upcSectionId;

    private String status;

    private Integer version;

    private Date createdDate;

    private String createdBy;

    private Integer mainKey;

    private Integer subKey;

    private String userDisplayName;

    private Integer createdUserWC;

    private String createdUserDesignation;
}
