package com.itechro.cas.model.dto.finacle.response;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoanAccCovenantInquiryDTO implements Serializable {
    private String srlNum;

    private String custId;

    private String acctId;

    private String covCod;

    private String covTyp;

    private String covFrq;

    private String covDue;

    private String compSt;

    private String covRem;
}
