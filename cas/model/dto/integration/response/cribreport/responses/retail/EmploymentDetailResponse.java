package com.itechro.cas.model.dto.integration.response.cribreport.responses.retail;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(ignoreUnknown = true)
public class EmploymentDetailResponse implements Serializable {

    @JsonProperty("EMPLOYMENT")
    private String employment;

    @JsonProperty("EMPLOYER_NAME")
    private String employerName;

    @JsonProperty("LAST_REPORTED_DATE")
    private String lastReportedDate;

    @JsonProperty("PROFESSION")
    private String profession;

    @JsonProperty("RUID")
    private String ruid;

    @JsonProperty("BLOCK_FLAG")
    private String blockFlag;

    @JsonProperty("EMPLOYMENT")
    public String getEmployment() {
        return employment;
    }

    @JsonProperty("EMPLOYMENT")
    public void setEmployment(String employment) {
        this.employment = employment;
    }

    @JsonProperty("EMPLOYER_NAME")
    public String getEmployerName() {
        return employerName;
    }

    @JsonProperty("EMPLOYER_NAME")
    public void setEmployerName(String employerName) {
        this.employerName = employerName;
    }

    @JsonProperty("LAST_REPORTED_DATE")
    public String getLastReportedDate() {
        return lastReportedDate;
    }

    @JsonProperty("LAST_REPORTED_DATE")
    public void setLastReportedDate(String lastReportedDate) {
        this.lastReportedDate = lastReportedDate;
    }

    @JsonProperty("PROFESSION")
    public String getProfession() {
        return profession;
    }

    @JsonProperty("PROFESSION")
    public void setProfession(String profession) {
        this.profession = profession;
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
}
