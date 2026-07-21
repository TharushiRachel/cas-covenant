package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.ConsumerDetailResponse;
import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.IdentificationDetailsResponse;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsumerDetailResponseDTO implements Serializable {
    private String name;
    private String dateOfBirth;
    private String gender;
    private String citizenship;
    private String phoneNumber;
    private String mobileNumber;
    private String email;
    private String ruid;
    private String blockFlag;
    private String nic;
    private String nicRuid;
    private String passRuid;
    private String rtoRuid;
    private List<IdentificationDetailsDTO> identificationDetailsDTOList;

    public ConsumerDetailResponseDTO() {
    }

    public ConsumerDetailResponseDTO(ConsumerDetailResponse consumerDetailResponse) {
        this.name = consumerDetailResponse.getName();
        this.dateOfBirth = consumerDetailResponse.getDateOfBirth();
        if (consumerDetailResponse.getGender() != null) {
            if (consumerDetailResponse.getGender().equals("001")) {
                this.gender = "Male";
            } else {
                this.gender = "Female";
            }
        }

        if (consumerDetailResponse.getCitizenship() != null) {
            if (consumerDetailResponse.getCitizenship().equals("001")) {
                this.citizenship = "Sri Lankan Citizen";
            } else {
                this.citizenship = "Non Sri Lankan Citizen";
            }
        }
        this.phoneNumber = consumerDetailResponse.getPhoneNumber();
        this.mobileNumber = consumerDetailResponse.getMobileNumber();
        this.email = consumerDetailResponse.getEmail();
        this.ruid = consumerDetailResponse.getRuid();
        this.blockFlag = consumerDetailResponse.getBlockFlag();
        this.nic = consumerDetailResponse.getNic();
        this.nicRuid = consumerDetailResponse.getNicRuid();
        this.passRuid = consumerDetailResponse.getPassRuid();
        this.rtoRuid = consumerDetailResponse.getRtoRuid();
        if (consumerDetailResponse.getIdentificationDetailsResponse() != null) {
            for (IdentificationDetailsResponse identificationDetailsResponse : consumerDetailResponse.getIdentificationDetailsResponse()) {
                this.getIdentificationDetailsDTOList().add(new IdentificationDetailsDTO(identificationDetailsResponse));
            }
        }
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRuid() {
        return ruid;
    }

    public void setRuid(String ruid) {
        this.ruid = ruid;
    }

    public String getBlockFlag() {
        return blockFlag;
    }

    public void setBlockFlag(String blockFlag) {
        this.blockFlag = blockFlag;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public String getNicRuid() {
        return nicRuid;
    }

    public void setNicRuid(String nicRuid) {
        this.nicRuid = nicRuid;
    }

    public String getPassRuid() {
        return passRuid;
    }

    public void setPassRuid(String passRuid) {
        this.passRuid = passRuid;
    }

    public String getRtoRuid() {
        return rtoRuid;
    }

    public void setRtoRuid(String rtoRuid) {
        this.rtoRuid = rtoRuid;
    }

    public List<IdentificationDetailsDTO> getIdentificationDetailsDTOList() {
        if (identificationDetailsDTOList == null) {
            identificationDetailsDTOList = new ArrayList<>();
        }
        return identificationDetailsDTOList;
    }

    public void setIdentificationDetailsDTOList(List<IdentificationDetailsDTO> identificationDetailsDTOList) {
        this.identificationDetailsDTOList = identificationDetailsDTOList;
    }

    @Override
    public String toString() {
        return "ConsumerDetailResponseDTO{" +
                "name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", gender='" + gender + '\'' +
                ", citizenship='" + citizenship + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", mobileNumber='" + mobileNumber + '\'' +
                ", email='" + email + '\'' +
                ", ruid='" + ruid + '\'' +
                ", blockFlag='" + blockFlag + '\'' +
                ", nic='" + nic + '\'' +
                ", nicRuid='" + nicRuid + '\'' +
                ", passRuid='" + passRuid + '\'' +
                ", rtoRuid='" + rtoRuid + '\'' +
                ", identificationDetailsDTOList=" + identificationDetailsDTOList +
                '}';
    }
}
