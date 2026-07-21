package com.itechro.cas.model.dto.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import lombok.Data;
import java.util.Date;

@Data
public class CommitteeTypeDTO {

    private Integer committeeTypeId;

    private String committeeTypeName;

    private String committeeDescription;

    private AppsConstants.Status status;

    private String createdUserDisplayName;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;

    private Integer isSystem;
}
