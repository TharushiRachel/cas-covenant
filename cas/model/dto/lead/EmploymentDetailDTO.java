package com.itechro.cas.model.dto.lead;

import lombok.Data;

@Data
public class EmploymentDetailDTO {
    private String nameOfEmployer;
    private String employmentType;
    private String noOfYearsWithPresentEmployer;
    private String avgSalaryGrowth;
    private String avgSalaryGrowthInPercentage;
    private String addressOfTheEmployer;
    private String telephoneNo;
    private String natureOfBusiness;
    private String designation;
    private String previousEmployerName;
    private String previousEmploymentPeriod;
    private String previousDesignation;

    public EmploymentDetailDTO(String nameOfEmployer, String employmentType, String noOfYearsWithPresentEmployer, String avgSalaryGrowth, String addressOfTheEmployer, String telephoneNo, String natureOfBusiness, String designation, String previousEmployerName, String previousEmploymentPeriod, String previousDesignation, String avgSalaryGrowthInPercentage) {
        this.nameOfEmployer = nameOfEmployer;
        this.employmentType = employmentType;
        this.noOfYearsWithPresentEmployer = noOfYearsWithPresentEmployer;
        this.avgSalaryGrowth = avgSalaryGrowth;
        this.addressOfTheEmployer = addressOfTheEmployer;
        this.telephoneNo = telephoneNo;
        this.natureOfBusiness = natureOfBusiness;
        this.designation = designation;
        this.previousEmployerName = previousEmployerName;
        this.previousEmploymentPeriod = previousEmploymentPeriod;
        this.previousDesignation = previousDesignation;
        this.avgSalaryGrowthInPercentage = avgSalaryGrowthInPercentage;
    }
    public EmploymentDetailDTO() {
    }
}
