package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.EmploymentDetailResponse;

import java.io.Serializable;

public class EmploymentDetailResponseDTO implements Serializable {

    private String employment;

    private String employerName;

    private String lastReportedDate;

    private String profession;

    private String ruid;

    private String blockFlag;

    public EmploymentDetailResponseDTO() {
    }

    public EmploymentDetailResponseDTO(EmploymentDetailResponse employmentDetailResponse) {
        this.employment = employmentDetailResponse.getEmployment();
        this.employerName = employmentDetailResponse.getEmployerName();
        this.lastReportedDate = employmentDetailResponse.getLastReportedDate();
        this.profession = employmentDetailResponse.getProfession();
        this.ruid = employmentDetailResponse.getRuid();
        this.blockFlag = employmentDetailResponse.getBlockFlag();
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getEmployerName() {
        return employerName;
    }

    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    public String getLastReportedDate() {
        return lastReportedDate;
    }

    public void setLastReportedDate(String lastReportedDate) {
        this.lastReportedDate = lastReportedDate;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
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

    public String getEmploymentType(String employment) {
        if (employment != null) {
            switch (employment) {
                case "001":
                    return "Employed";
                case "002":
                    return "Self Employed";
                case "003":
                    return "Unemployed";
                default:
                    return "";
            }
        } else {
            return "";
        }
    }

    @Override
    public String toString() {
        return "EmploymentDetailResponseDTO{" +
                "employerName='" + employerName + '\'' +
                ", lastReportedDate='" + lastReportedDate + '\'' +
                ", profession='" + profession + '\'' +
                ", ruid='" + ruid + '\'' +
                ", blockFlag='" + blockFlag + '\'' +
                '}';
    }
}
