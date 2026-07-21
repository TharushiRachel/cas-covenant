package com.itechro.cas.model.dto.acae.response;

import lombok.Data;

import java.util.Date;

@Data
public class ACAETransferACAERecordDTO {

    private Integer tranCountNo;
    private String refNumber;
    private String accNo;
    private String accName;
    private Boolean isSelectEnable;
    private Boolean isSelected;
    private String currentUser;
    private String isAttended;
    private String isActive;
    private String recordNo;
    private Date receivedDate;
    private Integer acaeStatus;
}
