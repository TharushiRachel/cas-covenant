package com.itechro.cas.model.dto.covenants;

import lombok.Data;

/**
 *
 *
 * @author tharushi
 */
@Data
public class CovenantInquiryDTO {

    private String srlNum;

    private String custId;

    private String acctId;

    private String covCod;

    private String covTyp;

    private String covFrq;

    private String covDue;

    private String compSt;

    private String covRem;

    private NoneComplianceCovenantDTO nonComplianceCovenantDTO;

}
