package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.MailingAddressResponse;

import java.io.Serializable;

public class MailingAddressResponseDTO implements Serializable {

    private String serialNo;

    private String lastReportedDate;

    private String addressValue;

    private String blockFlag;

    public MailingAddressResponseDTO() {
    }

    public MailingAddressResponseDTO(MailingAddressResponse mailingAddressResponse) {
        this.serialNo = mailingAddressResponse.getSerialNo();
        this.lastReportedDate = mailingAddressResponse.getLastReportedDate();
        this.addressValue = mailingAddressResponse.getAddressValue();
        this.blockFlag = mailingAddressResponse.getBlockFlag();
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getLastReportedDate() {
        return lastReportedDate;
    }

    public void setLastReportedDate(String lastReportedDate) {
        this.lastReportedDate = lastReportedDate;
    }

    public String getAddressValue() {
        return addressValue;
    }

    public void setAddressValue(String addressValue) {
        this.addressValue = addressValue;
    }

    public String getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    @Override
    public String toString() {
        return "MailingAddressResponseDTO{" +
                "serialNo='" + serialNo + '\'' +
                ", lastReportedDate='" + lastReportedDate + '\'' +
                ", addressValue='" + addressValue + '\'' +
                ", blockFlag='" + blockFlag + '\'' +
                '}';
    }
}
