package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

import java.sql.Date;
import java.util.List;
@Data
public class ACAETransferRefRecordDTO {

    String refNumber;
    Date createdOn;
    String createdBy;
    String refNo;
    Boolean isExpanded;
    String acaeCount;

    Boolean isSelectAll;
    List<ACAETransferACAERecordDTO> acaeRecords;

}
