package com.itechro.cas.model.domain.facilitypaper.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;

@Data
public class FPUPCSectionDataHistoryDTO {
    private Integer fpUpcSectionDataHistoryID;
    private Integer fpUpcSectionDataID;
    private Integer facilityPaperID;
    private Integer upcSectionID;
    private Integer parentSectionID;
    private Integer sectionLevel;
    private Integer displayOrder;
    private AppsConstants.Status status;
    private String createdDate;
    private String createdBy;
    private String modifiedBy;
    private String modifiedDateStr;
    private String version;
    private String data;
    private String modifiedUserDisplayName;
    private String comment;
    private String isForward;
    private String forwardCount;
}
