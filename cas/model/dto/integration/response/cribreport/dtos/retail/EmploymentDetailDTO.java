package com.itechro.cas.model.dto.integration.response.cribreport.dtos.retail;

import com.itechro.cas.model.dto.integration.response.cribreport.responses.retail.EmploymentDetailResponse;

import java.io.Serializable;

public class EmploymentDetailDTO implements Serializable {

    private String employerName;

    private String lastReportedDate;

    private String ruid;

    private String blockFlag;

    public EmploymentDetailDTO() {
    }

    public EmploymentDetailDTO(EmploymentDetailResponse employmentDetailResponse) {
        this.employerName = employmentDetailResponse.getEmployerName();
        this.lastReportedDate = employmentDetailResponse.getLastReportedDate();
        this.ruid = employmentDetailResponse.getRuid();
        this.blockFlag = employmentDetailResponse.getBlockFlag();
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

    @Override
    public String toString() {
        return "EmploymentDetailDTO{" +
                "employerName='" + employerName + '\'' +
                ", lastReportedDate='" + lastReportedDate + '\'' +
                ", ruid='" + ruid + '\'' +
                ", blockFlag='" + blockFlag + '\'' +
                '}';
    }
}
