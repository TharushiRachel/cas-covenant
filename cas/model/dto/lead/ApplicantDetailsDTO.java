package com.itechro.cas.model.dto.lead;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicantDetailsDTO {
    private String fullName;
    private String dob;
    private String nic;
    private String permanentAddress1;
    private String permanentAddress2;
    private String currentAddressType;  // Own / Rented / Other
    private Integer yearsAtAddress;
    private String currentAddressOther;
    private String communicationAddress;
    private String residencePhone;
    private String mobilePhone;
    private String officePhone;
    private String email;
    private String isContactPerson;
    private String contactPersonName;
    private String contactPersonMobile;
    private String contactPersonRelationship;
    private String isPep;
    private String pepDescription;
    private String highestAcademicQualification;
    private String professionalQualification;
    private String civilStatus;
    private String civilStatusDescription;
    private String noOfChildren;
    private List<String> nationality = new ArrayList<String>();
    private List<EmploymentDetailDTO> employmentDetailDTOS = new ArrayList<>();

    public List<EmploymentDetailDTO> getEmploymentDetailDTOS() {
        return employmentDetailDTOS;
    }
    public void setEmploymentDetailDTOS(List<EmploymentDetailDTO> employmentDetailDTOS) {
        this.employmentDetailDTOS = employmentDetailDTOS;
    }

    public ApplicantDetailsDTO(String fullName, String dob, String nic, String permanentAddress1, String permanentAddress2, String currentAddressType, String currentAddressOther, String communicationAddress, String residencePhone, String mobilePhone, String officePhone, String email, String contactPersonName, String contactPersonMobile, String contactPersonRelationship, String isPep, String pepDescription, String highestAcademicQualification, String professionalQualification, String civilStatus, String civilStatusDescription, String noOfChildren, List<String> nationality, Integer yearsAtAddress, String isContactPerson) {
        this.fullName = fullName;
        this.dob = dob;
        this.nic = nic;
        this.permanentAddress1 = permanentAddress1;
        this.permanentAddress2 = permanentAddress2;
        this.currentAddressType = currentAddressType;
        this.currentAddressOther = currentAddressOther;
        this.communicationAddress = communicationAddress;
        this.residencePhone = residencePhone;
        this.mobilePhone = mobilePhone;
        this.officePhone = officePhone;
        this.email = email;
        this.contactPersonName = contactPersonName;
        this.contactPersonMobile = contactPersonMobile;
        this.contactPersonRelationship = contactPersonRelationship;
        this.isPep = isPep;
        this.pepDescription = pepDescription;
        this.highestAcademicQualification = highestAcademicQualification;
        this.professionalQualification = professionalQualification;
        this.civilStatus = civilStatus;
        this.civilStatusDescription = civilStatusDescription;
        this.noOfChildren = noOfChildren;
        this.nationality = nationality;
        this.yearsAtAddress = yearsAtAddress;
        this.isContactPerson = isContactPerson;
    }

    public ApplicantDetailsDTO(List<EmploymentDetailDTO> employmentDetailDTOS) {
        this.employmentDetailDTOS = employmentDetailDTOS;
    }

    public ApplicantDetailsDTO() {
    }
}
