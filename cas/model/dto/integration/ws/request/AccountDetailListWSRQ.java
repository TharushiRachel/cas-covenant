package com.itechro.cas.model.dto.integration.ws.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.itechro.cas.model.dto.integration.request.AccountDetailListRQ;

import java.io.Serializable;

public class AccountDetailListWSRQ implements Serializable {

    @JsonProperty("accno")
    private String accno;

    @JsonProperty("cumm")
    private String cumm;

    @JsonProperty("nic")
    private String nic;

    @JsonProperty("valType")
    private String valType;

    @JsonProperty("aduser")
    private String aduser;

    @JsonProperty("userId")
    private String userId;

    @JsonProperty("refId")
    private String refId;

    public AccountDetailListWSRQ() {
    }

    public AccountDetailListWSRQ(AccountDetailListRQ detailListRQ) {
        this.accno = detailListRQ.getAccno();
        this.cumm = detailListRQ.getCumm();
        this.nic = detailListRQ.getNic();
        this.valType = detailListRQ.getValType();
        this.aduser = detailListRQ.getAduser();
        this.userId = detailListRQ.getUserId();
        this.refId = detailListRQ.getRefId();
    }

    public String getAccno() {
        return accno;
    }

    public void setAccno(String accno) {
        this.accno = accno;
    }

    public String getCumm() {
        return cumm;
    }

    public void setCumm(String cumm) {
        this.cumm = cumm;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getValType() {
        return valType;
    }

    public void setValType(String valType) {
        this.valType = valType;
    }

    public String getAduser() {
        return aduser;
    }

    public void setAduser(String aduser) {
        this.aduser = aduser;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRefId() {
        return refId;
    }

    public void setRefId(String refId) {
        this.refId = refId;
    }
}
