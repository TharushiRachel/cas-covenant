package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerDetailResponse implements Serializable {

    @JsonProperty("NAME")
    private String name;

    @JsonProperty("DATE_OF_BIRTH")
    private String dateOfBirth;

    @JsonProperty("GENDER")
    private String gender;

    @JsonProperty("CITIZENSHIP")
    private String citizenship;

    @JsonProperty("PHONE_NUMBER")
    private String phoneNumber;

    @JsonProperty("MOBILE_NUMBER")
    private String mobileNumber;

    @JsonProperty("EMAIL_ID")
    private String email;

    @JsonProperty("RUID")
    private String ruid;

    @JsonProperty("BLOCK_FLAG")
    private String blockFlag;

    @JsonProperty("NIC")
    private String nic;

    @JsonProperty("NIC_RUID")
    private String nicRuid;

    @JsonProperty("PAS_RUID")
    private String passRuid;

    @JsonProperty("RTO_RUID")
    private String rtoRuid;

    @JsonProperty("IDENTIFICATION_DETAILS_VER4")
    private List<IdentificationDetailsResponse> identificationDetailsResponse;

    @JsonProperty("NAME")
    public String getName() {
        return name;
    }

    @JsonProperty("NAME")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("DATE_OF_BIRTH")
    public String getDateOfBirth() {
        return dateOfBirth;
    }

    @JsonProperty("DATE_OF_BIRTH")
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @JsonProperty("GENDER")
    public String getGender() {
        return gender;
    }

    @JsonProperty("GENDER")
    public void setGender(String gender) {
        this.gender = gender;
    }

    @JsonProperty("CITIZENSHIP")
    public String getCitizenship() {
        return citizenship;
    }

    @JsonProperty("CITIZENSHIP")
    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    @JsonProperty("PHONE_NUMBER")
    public String getPhoneNumber() {
        return phoneNumber;
    }

    @JsonProperty("PHONE_NUMBER")
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @JsonProperty("MOBILE_NUMBER")
    public String getMobileNumber() {
        return mobileNumber;
    }

    @JsonProperty("MOBILE_NUMBER")
    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @JsonProperty("EMAIL_ID")
    public String getEmail() {
        return email;
    }

    @JsonProperty("EMAIL_ID")
    public void setEmail(String email) {
        this.email = email;
    }

    @JsonProperty("RUID")
    public String getRuid() {
        return ruid;
    }

    @JsonProperty("RUID")
    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    @JsonProperty("BLOCK_FLAG")
    public String getBlockFlag() {
        return blockFlag;
    }

    @JsonProperty("BLOCK_FLAG")
    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    @JsonProperty("NIC")
    public String getNic() {
        return nic;
    }

    @JsonProperty("NIC")
    public void setNic(String nic) {
        this.nic = nic;
    }

    @JsonProperty("NIC_RUID")
    public String getNicRuid() {
        return nicRuid;
    }

    @JsonProperty("NIC_RUID")
    public void setNicRuid(String nicRuid) {
        this.nicRuid = nicRuid;
    }

    @JsonProperty("PAS_RUID")
    public String getPassRuid() {
        return passRuid;
    }

    @JsonProperty("PAS_RUID")
    public void setPassRuid(String passRuid) {
        this.passRuid = passRuid;
    }

    @JsonProperty("RTO_RUID")
    public String getRtoRuid() {
        return rtoRuid;
    }

    @JsonProperty("RTO_RUID")
    public void setRtoRuid(String rtoRuid) {
        this.rtoRuid = rtoRuid;
    }

    @JsonProperty("IDENTIFICATION_DETAILS_VER4")
    public void setIdentificationDetailsResponse(List<IdentificationDetailsResponse> identificationDetailsResponse) {
        this.identificationDetailsResponse = identificationDetailsResponse;
    }

    @JsonProperty("IDENTIFICATION_DETAILS_VER4")
    public List<IdentificationDetailsResponse> getIdentificationDetailsResponse() {
        return identificationDetailsResponse;
    }
}
