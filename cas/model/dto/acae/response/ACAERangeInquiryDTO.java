package com.itechro.cas.model.dto.acae.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class ACAERangeInquiryDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date tranDate;

    private String tranId;

    private String partTranSrl;

    private String tranType;

    private String tranSubType;

    private String drcrInd;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date valueDate;

    private String tranCrncy;

    private Double tranAmount;

    private String tranParticular;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date postedDate;

    private String instNumb;

    private Double acctBalance;

    private String tranSol;

    private String expMessage;

    private String tranRemarks;
}
