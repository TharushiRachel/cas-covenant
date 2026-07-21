package com.itechro.cas.model.dto.integration.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class ConsumerComprehensiveDTO implements Serializable {

    @JsonProperty("Name")
    private String Name;

    @JsonProperty("Citizenship")
    private String Citizenship;

    @JsonProperty("NIC")
    private String NIC;

    @JsonProperty("Gender")
    private String Gender;

    @JsonProperty("BranchId")
    private String BranchId;

    @JsonProperty("Name")
    public String getName() {
        return Name;
    }

    @JsonProperty("Name")
    public void setName(String name) {
        Name = name;
    }

    @JsonProperty("Citizenship")
    public String getCitizenship() {
        return Citizenship;
    }

    @JsonProperty("Citizenship")
    public void setCitizenship(String citizenship) {
        Citizenship = citizenship;
    }

    @JsonProperty("NIC")
    public String getNIC() {
        return NIC;
    }

    @JsonProperty("NIC")
    public void setNIC(String NIC) {
        this.NIC = NIC;
    }

    @JsonProperty("Gender")
    public String getGender() {
        return Gender;
    }

    @JsonProperty("Gender")
    public void setGender(String gender) {
        Gender = gender;
    }

    @JsonProperty("BranchId")
    public String getBranchId() {
        return BranchId;
    }

    @JsonProperty("BranchId")
    public void setBranchId(String branchId) {
        BranchId = branchId;
    }

    @Override
    public String toString() {
        return "ConsumerComprehensiveDTO{" +
                "Name='" + Name + '\'' +
                ", Citizenship='" + Citizenship + '\'' +
                ", NIC='" + (NIC != null ? "000" + NIC.substring(3) : "") + '\'' +
                ", Gender='" + Gender + '\'' +
                ", BranchId='" + BranchId + '\'' +
                '}';
    }
}
