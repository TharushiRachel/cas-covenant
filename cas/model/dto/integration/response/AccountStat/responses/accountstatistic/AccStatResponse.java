package com.itechro.cas.model.dto.integration.response.AccountStat.responses.accountstatistic;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class AccStatResponse implements Serializable {

    @JsonProperty("acctype")
    private String acctype;

    @JsonProperty("lien")
    private String lien;

    @JsonProperty("curr")
    private String curr;

    @JsonProperty("reserved")
    private String reserved;

    @JsonProperty("avaiBal")
    private String avaiBal;

    @JsonProperty("acctype")
    public String getAcctype() {
        return acctype;
    }

    @JsonProperty("acctype")
    public void setAcctype(String acctype) {
        this.acctype = acctype;
    }

    @JsonProperty("lien")
    public String getLien() {
        return lien;
    }

    @JsonProperty("lien")
    public void setLien(String lien) {
        this.lien = lien;
    }

    @JsonProperty("curr")
    public String getCurr() {
        return curr;
    }

    @JsonProperty("curr")
    public void setCurr(String curr) {
        this.curr = curr;
    }

    @JsonProperty("reserved")
    public String getReserved() {
        return reserved;
    }

    @JsonProperty("reserved")
    public void setReserved(String reserved) {
        this.reserved = reserved;
    }

    @JsonProperty("avaiBal")
    public String getAvaiBal() {
        return avaiBal;
    }

    @JsonProperty("avaiBal")
    public void setAvaiBal(String avaiBal) {
        this.avaiBal = avaiBal;
    }

    @Override
    public String toString() {
        return "AccStatResponse{" +
                "acctype='" + acctype + '\'' +
                ", lien='" + lien + '\'' +
                ", curr='" + curr + '\'' +
                ", reserved='" + reserved + '\'' +
                ", avaiBal='" + avaiBal + '\'' +
                '}';
    }
}
