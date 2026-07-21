package com.itechro.cas.model.dto.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.dto.customer.CASCustomerBankDetailsDTO;
import com.itechro.cas.model.dto.customer.CustomerRatingsDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CASCustomerDTO implements Serializable {

    private Integer casCustomerID;

    private Integer facilityPaperID;

    private Integer customerID;

    private String customerName;

    private String customerFinancialID;

    private DomainConstants.Title title;

    private String casCustomerName;

    private String emailAddress;

    private String secondaryEmailAddress;

    private String dateOfBirthStr;

    private String civilStatus;

    private boolean isPrimary;

    private Integer displayOrder;

    private DomainConstants.BasicInformationType type;

    private String nameWithInitials;

    private String initialRepresentation;

    private String nameOfBusiness;

    private String registrationNo;

    private String constitution;

    private String dateOfIncorporateStr;

    private String dateOfCommencementStr;

    private String dateOfRegistrationStr;

    private String natureOfBusiness;

    private Double noOfBusinessYears;

    private String citizenship;

    private String privateAddress;

    private String officialAddress;

    private String businessAddress;

    private String telNumber;

    private String placeOfBirth;

    private String nationality;

    private String employment;

    private String employer;

    private String salutation;

    private String highestEduAchievement;

    private String position;

    private Double noOfYearsEmployment;

    private BigDecimal capitalAuthorized;

    private BigDecimal capitalIssued;

    private BigDecimal capitalPaidUp;

    private AppsConstants.YesNo isBorrowerOrGuarantor;

    private AppsConstants.Status status;

    private String registeredAddress;

    private List<CASCustomerCribDetailDTO> casCustomerCribDetailDTOList;

    private List<CASCustomerDocDTO> casCustomerDocDTOList;

    private List<CASCustomerOtherBankFacilityDTO> casCustomerOtherBankFacilityDTOList;

    private List<CASCustomerCribReportDTO> casCustomerCribReportDTOList;

    private List<CASCustomerAddressDTO> casCustomerAddressDTOList;

    private List<CASCustomerTelephoneDTO> casCustomerTelephoneDTOList;

    private List<CASCustomerIdentificationDTO> casCustomerIdentificationDTOList;

    private List<CASCustomerBankDetailsDTO> casCustomerBankDetailsDTOList;

    private List<CustomerRatingsDTO> customerRatingsDTOList;

    public CASCustomerDTO() {
    }

    public CASCustomerDTO(CASCustomer casCustomer, FPLoadOptionDTO loadOptionDTO) {
        this.casCustomerID = casCustomer.getCasCustomerID();
        this.facilityPaperID = casCustomer.getFacilityPaper().getFacilityPaperID();
        if (casCustomer.getCustomer() != null) {
            this.customerID = casCustomer.getCustomer().getCustomerID();
            this.customerName = casCustomer.getCustomer().getCustomerName();
            this.customerFinancialID = casCustomer.getCustomer().getCustomerFinancialID();
        } else {
            this.customerName = casCustomer.getCasCustomerName();
        }
        this.title = casCustomer.getTitle();
        this.casCustomerName = casCustomer.getCasCustomerName();
        this.emailAddress = casCustomer.getEmailAddress();
        this.secondaryEmailAddress = casCustomer.getSecondaryEmailAddress();
        this.civilStatus = casCustomer.getCivilStatus();
        if (casCustomer.getDateOfBirth() != null) {
            this.dateOfBirthStr = CalendarUtil.getDefaultFormattedDateOnly(casCustomer.getDateOfBirth());
        }
        this.isPrimary = casCustomer.getIsPrimary().getBoolVal();
        this.displayOrder = casCustomer.getDisplayOrder();
        this.type = casCustomer.getType();
        this.nameWithInitials = casCustomer.getNameWithInitials();
        this.initialRepresentation = casCustomer.getInitialRepresentation();
        this.nameOfBusiness = casCustomer.getNameOfBusiness();
        this.registrationNo = casCustomer.getRegistrationNo();
        this.constitution = casCustomer.getConstitution();
        if (casCustomer.getDateOfIncorporate() != null) {
            this.dateOfIncorporateStr = CalendarUtil.getDefaultFormattedDateOnly(casCustomer.getDateOfIncorporate());
        }
        if (casCustomer.getDateOfCommencement() != null) {
            this.dateOfCommencementStr = CalendarUtil.getDefaultFormattedDateOnly(casCustomer.getDateOfCommencement());
        }
        if (casCustomer.getDateOfRegistration() != null) {
            this.dateOfRegistrationStr = CalendarUtil.getDefaultFormattedDateOnly(casCustomer.getDateOfRegistration());
        }
        this.natureOfBusiness = casCustomer.getNatureOfBusiness();
        this.noOfBusinessYears = casCustomer.getNoOfBusinessYears();
        this.citizenship = casCustomer.getCitizenship();
        this.privateAddress = casCustomer.getPrivateAddress();
        this.officialAddress = casCustomer.getOfficialAddress();
        this.businessAddress = casCustomer.getBusinessAddress();
        this.telNumber = casCustomer.getTelNumber();
        this.placeOfBirth = casCustomer.getPlaceOfBirth();
        this.nationality = casCustomer.getNationality();
        this.employment = casCustomer.getEmployment();
        this.employer = casCustomer.getEmployer();
        this.highestEduAchievement = casCustomer.getHighestEduAchievement();
        this.position = casCustomer.getPosition();
        this.noOfYearsEmployment = casCustomer.getNoOfYearsEmployment();
        this.capitalAuthorized = casCustomer.getCapitalAuthorized();
        this.capitalIssued = casCustomer.getCapitalIssued();
        this.capitalPaidUp = casCustomer.getCapitalPaidUp();
        this.isBorrowerOrGuarantor = casCustomer.getIsBorrowerOrGuarantor();
        this.status = casCustomer.getStatus();

        if (this.title != null){
            switch (this.title) {
                case MR:
                    this.salutation = "Dear Sir";
                    break;
                case MRS:
                    this.salutation = "Madam";
                    break;
                case MS:
                    this.salutation = "Madam";
                    break;
                default:
                    this.salutation = "Dear Sir/Madam";
                    break;
            }
        }


        if (loadOptionDTO != null) {

            if (loadOptionDTO.isLoadCustomerRatings()) {
                for (CustomerRatings customerRatings : casCustomer.getCustomerRatingsSet()) {
                    this.getCustomerRatingsDTOList().add(new CustomerRatingsDTO(customerRatings));
                }
            }

            if (loadOptionDTO.isLoadCustomerDocument()) {
                for (CASCustomerDoc CASCustomerDoc : casCustomer.getCASCustomerDocSet()) {
                    this.getCasCustomerDocDTOList().add(new CASCustomerDocDTO(CASCustomerDoc, false));
                }
            }
            if (loadOptionDTO.isLoadCustomerOtherBankFacility()) {
                for (CasCustomerOtherBankFacility casCustomerOtherBankFacility : casCustomer.getOrderedCasCustomerOtherBankFacilitySet()) {
                    if (casCustomerOtherBankFacility.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerOtherBankFacilityDTOList().add(new CASCustomerOtherBankFacilityDTO(casCustomerOtherBankFacility));
                    }
                }
            }
            if (loadOptionDTO.isLoadCustomerCribDetail()) {
                for (CASCustomerCribDetail customerCribDetail : casCustomer.getOrderedCasCustomerCribDetailSet()) {
                    if (customerCribDetail.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerCribDetailDTOList().add(new CASCustomerCribDetailDTO(customerCribDetail, false));
                    }
                }

                for (CASCustomerCribReport CASCustomerCribReport : casCustomer.getOrderedCasCustomerCribReportSet()) {
                    if (CASCustomerCribReport.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerCribReportDTOList().add(new CASCustomerCribReportDTO(CASCustomerCribReport));
                    }
                }
            }

            if (loadOptionDTO.isLoadCustomerAddress()) {
                for (CASCustomerAddress CASCustomerAddress : casCustomer.getOrderedCasCustomerAddressSet()) {
                    if (CASCustomerAddress.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerAddressDTOList().add(new CASCustomerAddressDTO(CASCustomerAddress));
                    }
                }
            }

            if (loadOptionDTO.isLoadCustomerTelephone()) {
                for (CASCustomerTelephone CASCustomerTelephone : casCustomer.getOrderedCasCustomerTelephoneSet()) {
                    if (CASCustomerTelephone.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerTelephoneDTOList().add(new CASCustomerTelephoneDTO(CASCustomerTelephone));
                    }
                }
            }

            if (loadOptionDTO.isLoadCustomerIdentification()) {
                for (CASCustomerIdentification CASCustomerIdentification : casCustomer.getOrderedCasCustomerIdentificationSet()) {
                    if (CASCustomerIdentification.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerIdentificationDTOList().add(new CASCustomerIdentificationDTO(CASCustomerIdentification));
                    }
                }
            }

            if (loadOptionDTO.isLoadCustomerBankDetail()) {
                for (CASCustomerBankDetail CASCustomerBankDetail : casCustomer.getOrderedCasCustomerBankDetailSet()) {
                    if (CASCustomerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                        this.getCasCustomerBankDetailsDTOList().add(new CASCustomerBankDetailsDTO(CASCustomerBankDetail));
                    }
                }
            }

        }
    }

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public Integer getFacilityPaperID() {
        return facilityPaperID;
    }

    public void setFacilityPaperID(Integer facilityPaperID) {
        this.facilityPaperID = facilityPaperID;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public boolean getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(boolean isPrimary) {
        this.isPrimary = isPrimary;
    }

    public Integer getDisplayOrder() {
        return displayOrder;
    }

    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    public DomainConstants.Title getTitle() {
        return title;
    }

    public void setTitle(DomainConstants.Title title) {
        this.title = title;
    }

    public String getCasCustomerName() {
        return casCustomerName;
    }

    public void setCasCustomerName(String casCustomerName) {
        this.casCustomerName = casCustomerName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSecondaryEmailAddress() {
        return secondaryEmailAddress;
    }

    public void setSecondaryEmailAddress(String secondaryEmailAddress) {
        this.secondaryEmailAddress = secondaryEmailAddress;
    }

    public String getDateOfBirthStr() {
        return dateOfBirthStr;
    }

    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;
    }

    public String getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(String civilStatus) {
        this.civilStatus = civilStatus;
    }

    public DomainConstants.BasicInformationType getType() {
        return type;
    }

    public void setType(DomainConstants.BasicInformationType type) {
        this.type = type;
    }

    public String getNameWithInitials() {
        return nameWithInitials;
    }

    public void setNameWithInitials(String nameWithInitials) {
        this.nameWithInitials = nameWithInitials;
    }

    public String getInitialRepresentation() {
        return initialRepresentation;
    }

    public void setInitialRepresentation(String initialRepresentation) {
        this.initialRepresentation = initialRepresentation;
    }

    public String getNameOfBusiness() {
        return nameOfBusiness;
    }

    public void setNameOfBusiness(String nameOfBusiness) {
        this.nameOfBusiness = nameOfBusiness;
    }

    public String getRegistrationNo() {
        return registrationNo;
    }

    public void setRegistrationNo(String registrationNo) {
        this.registrationNo = registrationNo;
    }

    public String getConstitution() {
        return constitution;
    }

    public void setConstitution(String constitution) {
        this.constitution = constitution;
    }

    public String getDateOfIncorporateStr() {
        return dateOfIncorporateStr;
    }

    public void setDateOfIncorporateStr(String dateOfIncorporateStr) {
        this.dateOfIncorporateStr = dateOfIncorporateStr;
    }

    public String getDateOfCommencementStr() {
        return dateOfCommencementStr;
    }

    public void setDateOfCommencementStr(String dateOfCommencementStr) {
        this.dateOfCommencementStr = dateOfCommencementStr;
    }

    public String getDateOfRegistrationStr() {
        return dateOfRegistrationStr;
    }

    public void setDateOfRegistrationStr(String dateOfRegistrationStr) {
        this.dateOfRegistrationStr = dateOfRegistrationStr;
    }

    public String getNatureOfBusiness() {
        return natureOfBusiness;
    }

    public void setNatureOfBusiness(String natureOfBusiness) {
        this.natureOfBusiness = natureOfBusiness;
    }

    public Double getNoOfBusinessYears() {
        return noOfBusinessYears;
    }

    public void setNoOfBusinessYears(Double noOfBusinessYears) {
        this.noOfBusinessYears = noOfBusinessYears;
    }

    public String getCitizenship() {
        return citizenship;
    }

    public void setCitizenship(String citizenship) {
        this.citizenship = citizenship;
    }

    public String getPrivateAddress() {
        return privateAddress;
    }

    public void setPrivateAddress(String privateAddress) {
        this.privateAddress = privateAddress;
    }

    public String getOfficialAddress() {
        return officialAddress;
    }

    public void setOfficialAddress(String officialAddress) {
        this.officialAddress = officialAddress;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getTelNumber() {
        return telNumber;
    }

    public void setTelNumber(String telNumber) {
        this.telNumber = telNumber;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public String getHighestEduAchievement() {
        return highestEduAchievement;
    }

    public void setHighestEduAchievement(String highestEduAchievement) {
        this.highestEduAchievement = highestEduAchievement;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Double getNoOfYearsEmployment() {
        return noOfYearsEmployment;
    }

    public void setNoOfYearsEmployment(Double noOfYearsEmployment) {
        this.noOfYearsEmployment = noOfYearsEmployment;
    }

    public BigDecimal getCapitalAuthorized() {
        return capitalAuthorized;
    }

    public void setCapitalAuthorized(BigDecimal capitalAuthorized) {
        this.capitalAuthorized = capitalAuthorized;
    }

    public BigDecimal getCapitalIssued() {
        return capitalIssued;
    }

    public void setCapitalIssued(BigDecimal capitalIssued) {
        this.capitalIssued = capitalIssued;
    }

    public BigDecimal getCapitalPaidUp() {
        return capitalPaidUp;
    }

    public void setCapitalPaidUp(BigDecimal capitalPaidUp) {
        this.capitalPaidUp = capitalPaidUp;
    }

    public AppsConstants.YesNo getIsBorrowerOrGuarantor() {
        return isBorrowerOrGuarantor;
    }

    public void setIsBorrowerOrGuarantor(AppsConstants.YesNo isBorrowerOrGuarantor) {
        this.isBorrowerOrGuarantor = isBorrowerOrGuarantor;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }


    public List<CASCustomerCribDetailDTO> getCasCustomerCribDetailDTOList() {
        if (casCustomerCribDetailDTOList == null) {
            casCustomerCribDetailDTOList = new ArrayList<>();
        }
        return casCustomerCribDetailDTOList;
    }

    public void setCasCustomerCribDetailDTOList(List<CASCustomerCribDetailDTO> casCustomerCribDetailDTOList) {
        this.casCustomerCribDetailDTOList = casCustomerCribDetailDTOList;
    }

    public List<CASCustomerDocDTO> getCasCustomerDocDTOList() {
        if (casCustomerDocDTOList == null) {
            casCustomerDocDTOList = new ArrayList<>();
        }
        return casCustomerDocDTOList;
    }

    public void setCasCustomerDocDTOList(List<CASCustomerDocDTO> casCustomerDocDTOList) {
        this.casCustomerDocDTOList = casCustomerDocDTOList;
    }

    public List<CASCustomerOtherBankFacilityDTO> getCasCustomerOtherBankFacilityDTOList() {
        if (casCustomerOtherBankFacilityDTOList == null) {
            casCustomerOtherBankFacilityDTOList = new ArrayList<>();
        }
        return casCustomerOtherBankFacilityDTOList;
    }

    public void setCasCustomerOtherBankFacilityDTOList(List<CASCustomerOtherBankFacilityDTO> casCustomerOtherBankFacilityDTOList) {
        this.casCustomerOtherBankFacilityDTOList = casCustomerOtherBankFacilityDTOList;
    }

    public String getCustomerFinancialID() {
        return customerFinancialID;
    }

    public void setCustomerFinancialID(String customerFinancialID) {
        this.customerFinancialID = customerFinancialID;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<CASCustomerCribReportDTO> getCasCustomerCribReportDTOList() {
        if (casCustomerCribReportDTOList == null) {
            this.casCustomerCribReportDTOList = new ArrayList<>();
        }
        return casCustomerCribReportDTOList;
    }

    public void setCasCustomerCribReportDTOList(List<CASCustomerCribReportDTO> casCustomerCribReportDTOList) {
        this.casCustomerCribReportDTOList = casCustomerCribReportDTOList;
    }

    public List<CASCustomerAddressDTO> getCasCustomerAddressDTOList() {
        if (casCustomerAddressDTOList == null) {
            casCustomerAddressDTOList = new ArrayList<>();
        }
        return casCustomerAddressDTOList;
    }

    public void setCasCustomerAddressDTOList(List<CASCustomerAddressDTO> casCustomerAddressDTOList) {
        this.casCustomerAddressDTOList = casCustomerAddressDTOList;
    }

    public List<CASCustomerTelephoneDTO> getCasCustomerTelephoneDTOList() {
        if (casCustomerTelephoneDTOList == null) {
            this.casCustomerTelephoneDTOList = new ArrayList<>();
        }
        return casCustomerTelephoneDTOList;
    }

    public void setCasCustomerTelephoneDTOList(List<CASCustomerTelephoneDTO> casCustomerTelephoneDTOList) {
        this.casCustomerTelephoneDTOList = casCustomerTelephoneDTOList;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public void setPrimary(boolean primary) {
        isPrimary = primary;
    }

    public List<CASCustomerIdentificationDTO> getCasCustomerIdentificationDTOList() {
        if (casCustomerIdentificationDTOList == null) {
            this.casCustomerIdentificationDTOList = new ArrayList<>();
        }
        return casCustomerIdentificationDTOList;
    }

    public void setCasCustomerIdentificationDTOList(List<CASCustomerIdentificationDTO> casCustomerIdentificationDTOList) {
        this.casCustomerIdentificationDTOList = casCustomerIdentificationDTOList;
    }

    public List<CASCustomerBankDetailsDTO> getCasCustomerBankDetailsDTOList() {
        if (casCustomerBankDetailsDTOList == null) {
            this.casCustomerBankDetailsDTOList = new ArrayList<>();
        }
        return casCustomerBankDetailsDTOList;
    }

    public void setCasCustomerBankDetailsDTOList(List<CASCustomerBankDetailsDTO> casCustomerBankDetailsDTOList) {
        this.casCustomerBankDetailsDTOList = casCustomerBankDetailsDTOList;
    }

    public List<CustomerRatingsDTO> getCustomerRatingsDTOList() {
        if (customerRatingsDTOList == null) {
            customerRatingsDTOList = new ArrayList<>();
        }
        return customerRatingsDTOList;
    }

    public void setCustomerRatingsDTOList(List<CustomerRatingsDTO> customerRatingsDTOList) {
        this.customerRatingsDTOList = customerRatingsDTOList;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    @Override
    public String toString() {
        return "CASCustomerDTO{" +
                "casCustomerID=" + casCustomerID +
                ", facilityPaperID=" + facilityPaperID +
                ", customerID=" + customerID +
                ", customerName='" + customerName + '\'' +
                ", customerFinancialID='" + customerFinancialID + '\'' +
                ", title=" + title +
                ", casCustomerName=" + casCustomerName +
                ", emailAddress='" + emailAddress + '\'' +
                ", customerRatingsDTOList" + customerRatingsDTOList +
                ", secondaryEmailAddress='" + secondaryEmailAddress + '\'' +
                ", dateOfBirthStr='" + dateOfBirthStr + '\'' +
                ", civilStatus='" + civilStatus + '\'' +
                ", isPrimary=" + isPrimary +
                ", displayOrder=" + displayOrder +
                ", salutation=" + salutation +
                ", status=" + status +
                '}';
    }

    public String getRegisteredAddress() {
        return registeredAddress;
    }

    public void setRegisteredAddress(String registeredAddress) {
        this.registeredAddress = registeredAddress;
    }
}
