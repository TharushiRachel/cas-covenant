package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.NameResponse;

import java.io.Serializable;

public class NameResponseDTO implements Serializable {

    private String sNo;

    private String name;

    private String blockFlag;

    public NameResponseDTO() {
    }

    public NameResponseDTO(NameResponse nameResponse) {
        this.sNo = nameResponse.getsNo();
        this.name = nameResponse.getName();
        this.blockFlag = nameResponse.getBlockFlag();
    }

    public String getsNo() {
        return sNo;
    }

    public void setsNo(String sNo) {
        this.sNo = sNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    @Override
    public String toString() {
        return "NameResponseDTO{" +
                "sNo='" + sNo + '\'' +
                ", name='" + name + '\'' +
                ", blockFlag='" + blockFlag + '\'' +
                '}';
    }
}
