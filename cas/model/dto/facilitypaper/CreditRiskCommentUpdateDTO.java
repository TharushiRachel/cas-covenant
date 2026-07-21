package com.itechro.cas.model.dto.facilitypaper;

import java.io.Serializable;
import java.util.List;

public class CreditRiskCommentUpdateDTO implements Serializable {

    private static final long serialVersionUID = -6346584885758886260L;

    private Integer facilityPaperID;

    private String userName;

    private Integer upmID;

    private List<FPCreditRiskCommentDTO> fpCreditRiskCommentDTOS;

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public List<FPCreditRiskCommentDTO> getFpCreditRiskCommentDTOS() {
        return fpCreditRiskCommentDTOS;
    }

    public void setFpCreditRiskCommentDTOS(List<FPCreditRiskCommentDTO> fpCreditRiskCommentDTOS) {
        this.fpCreditRiskCommentDTOS = fpCreditRiskCommentDTOS;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUpmID() {
        return upmID;
    }

    public void setUpmID(Integer upmID) {
        this.upmID = upmID;
    }

    @Override
    public String toString() {
        return "CreditRiskCommentUpdateDTO{" +
                "facilityPaperID=" + facilityPaperID +
                ", userName='" + userName + '\'' +
                ", upmID=" + upmID +
                ", fpCreditRiskCommentDTOS=" + fpCreditRiskCommentDTOS +
                '}';
    }
}
