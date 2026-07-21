package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.PermanentAddressResponse;

import java.io.Serializable;

public class PermanentAddressResponseDTO implements Serializable {

    private String serialNo;

    private String lastReportedDate;

    private String addressValue;

    private String blockFlag;

    public PermanentAddressResponseDTO(PermanentAddressResponse permanentAddressResponse) {
        this.serialNo = permanentAddressResponse.getSerialNo();
        this.lastReportedDate = permanentAddressResponse.getLastReportedDate();
        this.addressValue = permanentAddressResponse.getAddressValue();
        this.blockFlag = permanentAddressResponse.getBlockFlag();
    }

    public PermanentAddressResponseDTO() {
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
        return "PermanentAddressResponseDTO{" +
                "serialNo='" + serialNo + '\'' +
                ", lastReportedDate='" + lastReportedDate + '\'' +
                ", addressValue='" + addressValue + '\'' +
                ", blockFlag='" + blockFlag + '\'' +
                '}';
    }
}
