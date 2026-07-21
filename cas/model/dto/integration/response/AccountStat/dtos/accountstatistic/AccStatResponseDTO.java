package com.itechro.cas.model.dto.integration.response.AccountStat.dtos.accountstatistic;

import com.itechro.cas.model.dto.integration.response.AccountStat.responses.accountstatistic.AccStatResponse;

import java.io.Serializable;

public class AccStatResponseDTO implements Serializable {

    private String acctype;

    private String lien;

    private String curr;

    private String reserved;

    private String avaiBal;

    public AccStatResponseDTO() {
    }

    public AccStatResponseDTO(AccStatResponse accStatResponse) {
        acctype = accStatResponse.getAcctype();
        lien = accStatResponse.getLien();
        curr = accStatResponse.getCurr();
        reserved = accStatResponse.getReserved();
        avaiBal = accStatResponse.getAvaiBal();
    }

    public String getAcctype() {
        return acctype;
    }

    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    public String getLien() {
        return lien;
    }

    public void setLien(String lien) {
        this.lien = lien;
    }

    public String getCurr() {
        return curr;
    }

    public void setCurr(String curr) {
        this.curr = curr;
    }

    public String getReserved() {
        return reserved;
    }

    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    public String getAvaiBal() {
        return avaiBal;
    }

    public void setAvaiBal(String avaiBal) {
        this.avaiBal = avaiBal;
    }

    @Override
    public String toString() {
        return "AccStatResponseDTO{" +
                "acctype='" + acctype + '\'' +
                ", lien='" + lien + '\'' +
                ", curr='" + curr + '\'' +
                ", reserved='" + reserved + '\'' +
                ", avaiBal='" + avaiBal + '\'' +
                '}';
    }
}
