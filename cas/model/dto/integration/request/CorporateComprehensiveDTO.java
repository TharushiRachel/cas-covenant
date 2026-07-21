package com.itechro.cas.model.dto.integration.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class CorporateComprehensiveDTO implements Serializable {

    private static final long serialVersionUID = 4797714639904662279L;

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("REGNo")
    private String REGNo;

    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }

    @JsonProperty("REGNo")
    public String getREGNo() {
        return REGNo;
    }

    @JsonProperty("REGNo")
    public void setREGNo(String REGNo) {
        this.REGNo = REGNo;
    }

    @Override
    public String toString() {
        return "CorporateComprehensiveDTO{" +
                "Name='" + Name + '\'' +
                ", REGNo='" + REGNo + '\'' +
                '}';
    }
}
