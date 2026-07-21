package com.itechro.cas.model.dto.integration.response.cribreport.dtos.corporate;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.corporate.NamesResponse;

import java.io.Serializable;

public class NamesResponseDTO implements Serializable {

    private String sno;

    private String name;

    private String blockFlag;

    public NamesResponseDTO() {
    }

    public NamesResponseDTO(NamesResponse namesResponse) {
        if (namesResponse != null) {
            this.sno = namesResponse.getSno();
            this.name = namesResponse.getName();
            this.blockFlag = namesResponse.getBlockFlag();
        }
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
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
}
