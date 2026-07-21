package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.OwnershipDetails;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;

public class OwnershipDetailsDTO implements Serializable {

    private Integer ownershipDetailsID;

    private Integer basicInformationID;

    private Integer applicationFormID;

    private String name;

    private String address;

    private String contactNo;

    private String identificationType;

    private String identificationNumber;

    private Double shareHolding;

    private DomainConstants.CivilStatus civilStatus;

    private String dateOfBirthStr;

    private DomainConstants.ConstitutionType constitutionType;

    private AppsConstants.YesNo creditCard;

    private AppsConstants.Status status;

    public OwnershipDetailsDTO() {
    }

    public OwnershipDetailsDTO(OwnershipDetails ownershipDetails) {
        this.ownershipDetailsID = ownershipDetails.getOwnershipDetailsID();
        this.basicInformationID = ownershipDetails.getBasicInformation().getBasicInformationID();
        this.applicationFormID = ownershipDetails.getApplicationFormID();
        this.name = ownershipDetails.getName();
        this.address = ownershipDetails.getAddress();
        this.contactNo = ownershipDetails.getContactNo();
        this.identificationType = ownershipDetails.getIdentificationType();
        this.identificationNumber = ownershipDetails.getIdentificationNumber();
        this.shareHolding = ownershipDetails.getShareHolding();
        this.civilStatus = ownershipDetails.getCivilStatus();
        if (ownershipDetails.getDateOfBirth() != null) {
            this.dateOfBirthStr = CalendarUtil.getDefaultFormattedDateOnly(ownershipDetails.getDateOfBirth());
        }
        this.constitutionType = ownershipDetails.getConstitutionType();
        this.creditCard = ownershipDetails.getCreditCard();
        this.status = ownershipDetails.getStatus();
    }

    public Integer getOwnershipDetailsID() {
        return ownershipDetailsID;
    }

    public void setOwnershipDetailsID(Integer ownershipDetailsID) {
        this.ownershipDetailsID = ownershipDetailsID;
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(String identificationType) {
        this.identificationType = identificationType;
    }

    public String getIdentificationNumber() {
        return identificationNumber;
    }

    public void setIdentificationNumber(String identificationNumber) {
        this.identificationNumber = identificationNumber;
    }

    public Double getShareHolding() {
        return shareHolding;
    }

    public void setShareHolding(Double shareHolding) {
        this.shareHolding = shareHolding;
    }

    public DomainConstants.CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(DomainConstants.CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getDateOfBirthStr() {
        return dateOfBirthStr;
    }

    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;
    }

    public DomainConstants.ConstitutionType getConstitutionType() {
        return constitutionType;
    }

    public void setConstitutionType(DomainConstants.ConstitutionType constitutionType) {
        this.constitutionType = constitutionType;
    }

    public AppsConstants.YesNo getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(AppsConstants.YesNo creditCard) {
        this.creditCard = creditCard;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "OwnershipDetailsDTO{" +
                "ownershipDetailsID=" + ownershipDetailsID +
                ", basicInformationID=" + basicInformationID +
                ", applicationFormID=" + applicationFormID +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contactNo='" + contactNo + '\'' +
                ", identificationType='" + identificationType + '\'' +
                ", identificationNumber='" + identificationNumber + '\'' +
                ", shareHolding=" + shareHolding +
                ", civilStatus=" + civilStatus +
                ", dateOfBirthStr='" + dateOfBirthStr + '\'' +
                ", constitutionType=" + constitutionType +
                ", creditCard=" + creditCard +
                ", status=" + status +
                '}';
    }
}
