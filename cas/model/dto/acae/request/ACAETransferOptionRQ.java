package com.itechro.cas.model.dto.acae.request;

import com.itechro.cas.model.dto.acae.response.ACAETransferACAERecordDTO;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class ACAETransferOptionRQ {

    private List<ACAETransferACAERecordDTO> acaeRecordList;

    private String refNum;
    private String accNo;
    private String comment;
    private String nextUser;
    private String thisUser;
    private Integer acaeStatus;
    private Date negDate;
    private Date preNegDate;
    private String solId;
    private Date anticipatedDate;
    private String nextUserName;
}
