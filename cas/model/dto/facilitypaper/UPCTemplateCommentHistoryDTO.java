package com.itechro.cas.model.dto.facilitypaper;

import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class UPCTemplateCommentHistoryDTO {

    private Integer Id;

    private String createdBy;

    private String comment;

    private Timestamp createdDate;

    private String flag;

    private String status;

    private String createdUserDisplayName;

    private Date modifiedDate;

    private String modifiedBy;

    private Integer parentRecordId;

    private Integer isView;

    private Integer mainKey;

    private Integer subKey;

    private Integer createdUserWC;

    private String createdUserDesignation;

}
