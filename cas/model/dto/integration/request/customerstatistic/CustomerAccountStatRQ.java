package com.itechro.cas.model.dto.integration.request.customerstatistic;

import java.io.Serializable;

public class CustomerAccountStatRQ implements Serializable {

    private String accno;

    private String cumm;

    private String nic;

    private String valType;

    private String aduser;

    private String userId;

    private String refId;

    public CustomerAccountStatRQ() {
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

    @Override
    public String toString() {
        return "CustomerAccountStatRQ{" +
                "accno='" + accno + '\'' +
                ", cumm='" + cumm + '\'' +
                ", nic='" + nic + '\'' +
                ", valType='" + valType + '\'' +
                ", aduser='" + aduser + '\'' +
                ", userId='" + userId + '\'' +
                ", refId='" + refId + '\'' +
                '}';
    }
}
