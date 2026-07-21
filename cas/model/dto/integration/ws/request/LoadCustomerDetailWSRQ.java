package com.itechro.cas.model.dto.integration.ws.request;

import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Date;

import static com.itechro.cas.commons.constants.DomainConstants.CustomerIdentificationType;

public class LoadCustomerDetailWSRQ implements Serializable {

    private String accno;

    private String cumm;

    private String nic;

    private String valType;

    private String aduser;

    private String userId;

    private String refId;

    public LoadCustomerDetailWSRQ() {
    }

    public LoadCustomerDetailWSRQ(SearchCustomerRQ searchCustomerRQ, CredentialsDTO credentialsDTO) {
        this.accno = searchCustomerRQ.getBankAccountNumber();
        this.cumm = searchCustomerRQ.getCustomerFinancialID();
        this.nic = searchCustomerRQ.getIdentificationNumber();

        if (StringUtils.isNotBlank(searchCustomerRQ.getBankAccountNumber())) {
            this.valType = "A";
        }
        if (StringUtils.isNotBlank(searchCustomerRQ.getIdentificationNumber())) {
            this.valType = "N";
        }
        if (StringUtils.isNotBlank(searchCustomerRQ.getCustomerFinancialID())) {
            this.valType = "C";
        }

        if (StringUtils.isNotEmpty(searchCustomerRQ.getIdentificationType())
                && CustomerIdentificationType.BRC.toString().equals(searchCustomerRQ.getIdentificationType())) {
            this.valType = "BR";
        }

        this.aduser = credentialsDTO.getUserName();
        this.refId = searchCustomerRQ.getIdentificationNumber() +
                searchCustomerRQ.getCustomerFinancialID() +
                searchCustomerRQ.getBankAccountNumber() +
                credentialsDTO.getUserName() + CalendarUtil.getDefaultFormattedDateTime(new Date());
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
        return "LoadCustomerDetailWSRQ{" +
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
