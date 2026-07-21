package lk.sampath.cas_covenant.dto;

import lombok.Data;

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
