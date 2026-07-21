package com.itechro.cas.model.dto.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.dto.applicationform.applicationFormCustomer.AFCustomerDTO;
import com.itechro.cas.util.CalendarUtil;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class BasicInformationDTO implements Serializable {

    private Integer basicInformationID;

    private Integer applicationFormID;

    private DomainConstants.Title title;

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

    private String emailAddress;

    private String dateOfBirthStr;

    private String placeOfBirth;

    private DomainConstants.CivilStatus civilStatus;

    private String nationality;

    private String identificationNo;

    private String employment;

    private String employer;

    private String highestEduAchievement;

    private String position;

    private Double noOfYearsEmployment;

    private BigDecimal capitalAuthorized;

    private BigDecimal capitalIssued;

    private BigDecimal capitalPaidUp;

    private DomainConstants.CustomerIdentificationType identificationType;

    private AppsConstants.Status status;

    private AppsConstants.YesNo primaryInformation;

    private AppsConstants.YesNo isBorrowerOrGuarantor;

    private AFCustomerDTO afCustomerDTO;

    private List<OwnershipDetailsDTO> ownershipDetailsDTOList;

    private List<AFCribAttachmentDTO> afCribAttachmentDTOList;

    private List<AFCribReportDTO> afCribReportDTOList;

    private List<AFOtherBankDetailsDTO> afOtherBankDetailsDTOList;

    private List<AFRiskRateDTO> afRiskRateDTOList;

    private List<AFFinancialObligationDTO> afFinancialObligationDTOList;

    private List<AFBorrowerGuarantorDTO> afBorrowerGuarantorDTOList;


    public BasicInformationDTO() {
    }

    public BasicInformationDTO(BasicInformation basicInformation) {
        this(basicInformation, null);
    }

    public BasicInformationDTO(BasicInformation basicInformation, AFBasicInformationLoadOptionDTO afBasicInformationLoadOptionDTO) {

        this.basicInformationID = basicInformation.getBasicInformationID();
        this.applicationFormID = basicInformation.getApplicationForm().getApplicationFormID();
        this.title = basicInformation.getTitle();
        this.type = basicInformation.getType();
        this.nameWithInitials = basicInformation.getNameWithInitials();
        this.initialRepresentation = basicInformation.getInitialRepresentation();
        this.nameOfBusiness = basicInformation.getNameOfBusiness();
        this.registrationNo = basicInformation.getRegistrationNo();
        this.constitution = basicInformation.getConstitution();
        this.natureOfBusiness = basicInformation.getNatureOfBusiness();
        this.noOfBusinessYears = basicInformation.getNoOfBusinessYears();
        this.citizenship = basicInformation.getCitizenship();
        this.privateAddress = basicInformation.getPrivateAddress();
        this.officialAddress = basicInformation.getOfficialAddress();
        this.businessAddress = basicInformation.getBusinessAddress();
        this.telNumber = basicInformation.getTelNumber();
        this.emailAddress = basicInformation.getEmailAddress();
        this.placeOfBirth = basicInformation.getPlaceOfBirth();
        this.civilStatus = basicInformation.getCivilStatus();
        this.nationality = basicInformation.getNationality();
        this.identificationNo = basicInformation.getIdentificationNo();
        this.employment = basicInformation.getEmployment();
        this.employer = basicInformation.getEmployer();
        this.highestEduAchievement = basicInformation.getHighestEduAchievement();
        this.position = basicInformation.getPosition();
        this.noOfYearsEmployment = basicInformation.getNoOfYearsEmployment();
        this.capitalAuthorized = basicInformation.getCapitalAuthorized();
        this.capitalIssued = basicInformation.getCapitalIssued();
        this.capitalPaidUp = basicInformation.getCapitalPaidUp();
        this.identificationType = basicInformation.getIdentificationType();
        this.status = basicInformation.getStatus();
        this.primaryInformation = basicInformation.getPrimaryInformation();
        this.isBorrowerOrGuarantor = basicInformation.getIsBorrowerOrGuarantor();

        if (basicInformation.getDateOfCommencement() != null) {
            this.dateOfCommencementStr = CalendarUtil.getDefaultFormattedDateOnly(basicInformation.getDateOfCommencement());
        }
        if (basicInformation.getDateOfIncorporate() != null) {
            this.dateOfIncorporateStr = CalendarUtil.getDefaultFormattedDateOnly(basicInformation.getDateOfIncorporate());
        }

        if (basicInformation.getDateOfBirth() != null) {
            this.dateOfBirthStr = CalendarUtil.getDefaultFormattedDateOnly(basicInformation.getDateOfBirth());
        }

        if (basicInformation.getDateOfRegistration() != null) {
            this.dateOfRegistrationStr = CalendarUtil.getDefaultFormattedDateOnly(basicInformation.getDateOfRegistration());
        }


        if (afBasicInformationLoadOptionDTO != null) {

            if (afBasicInformationLoadOptionDTO.isLoadOwnershipDetails()) {
                if (!basicInformation.getOwnershipDetailsSet().isEmpty()) {
                    for (OwnershipDetails ownershipDetails : basicInformation.getOwnershipDetailsSet()) {
                        if (ownershipDetails.getStatus() == AppsConstants.Status.ACT) {
                            this.getOwnershipDetailsDTOList().add(new OwnershipDetailsDTO(ownershipDetails));
                        }
                    }
                }
            }

            if (afBasicInformationLoadOptionDTO.isLoadCribAttachments()) {
                if (!basicInformation.getOrderedAfCribAttachmentSet().isEmpty()) {
                    for (AFCribAttachment afCribAttachment : basicInformation.getOrderedAfCribAttachmentSet()) {
                        if (afCribAttachment.getStatus() == AppsConstants.Status.ACT) {
                            this.getAfCribAttachmentDTOList().add(new AFCribAttachmentDTO(afCribAttachment, false));
                        }
                    }
                }
            }

            if (afBasicInformationLoadOptionDTO.isLoadCribReports()) {
                if (!basicInformation.getOrderedAfCribReportSet().isEmpty()) {
                    for (AFCribReport afCribReport : basicInformation.getOrderedAfCribReportSet()) {
                        if (afCribReport.getStatus() == AppsConstants.Status.ACT) {
                            this.getAfCribReportDTOList().add(new AFCribReportDTO(afCribReport));
                        }
                    }
                }
            }

            if (afBasicInformationLoadOptionDTO.isLoadOtherBankDetails()) {
                if (!basicInformation.getAfOtherBankDetailsSet().isEmpty()) {
                    for (AFOtherBankDetails afOtherBankDetails : basicInformation.getOrderedAfOtherBankDetails()) {
                        if (afOtherBankDetails.getStatus() == AppsConstants.Status.ACT) {
                            this.getAfOtherBankDetailsDTOList().add(new AFOtherBankDetailsDTO(afOtherBankDetails));
                        }
                    }
                }
            }

            if (afBasicInformationLoadOptionDTO.isLoadRiskRates()) {
                if (!basicInformation.getAfRiskRateSet().isEmpty()) {
                    for (AFRiskRate afRiskRate : basicInformation.getOrderedAfRiskRates()) {
                        if (afRiskRate.getStatus() == AppsConstants.Status.ACT) {
                            this.getAfRiskRateDTOList().add(new AFRiskRateDTO(afRiskRate));
                        }
                    }
                }
            }

            if (afBasicInformationLoadOptionDTO.isLoadFinancialObligations()) {
                if (!basicInformation.getAfFinancialObligationSet().isEmpty()) {
                    for (AFFinancialObligation afFinancialObligation : basicInformation.getOrderedAfFinancialObligation()) {
                        if (afFinancialObligation.getStatus() == AppsConstants.Status.ACT) {
                            this.getAfFinancialObligationDTOList().add(new AFFinancialObligationDTO(afFinancialObligation));
                        }
                    }
                }
            }

            if (afBasicInformationLoadOptionDTO.isLoadBorrowerGuarantor()) {
                if (!basicInformation.getAfBorrowerGuarantorSet().isEmpty()) {
                    for (AFBorrowerGuarantor afBorrowerGuarantor : basicInformation.getOrderedAfBorrowerGuarantor()) {
                        if (afBorrowerGuarantor.getStatus() == AppsConstants.Status.ACT) {
                            this.getAfBorrowerGuarantorDTOList().add(new AFBorrowerGuarantorDTO(afBorrowerGuarantor));
                        }
                    }
                }
            }

            if (afBasicInformationLoadOptionDTO.isLoadCustomerDetails() && basicInformation.getAfCustomer() != null) {
                this.afCustomerDTO = new AFCustomerDTO(basicInformation.getAfCustomer());
            }
        }
    }

    public Integer getBasicInformationID() {
        return basicInformationID;
    }

    public void setBasicInformationID(Integer basicInformationID) {
        this.basicInformationID = basicInformationID;
    }

    public DomainConstants.Title getTitle() {
        return title;
    }

    public void setTitle(DomainConstants.Title title) {
        this.title = title;
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

    public String getDateOfBirthStr() {
        return dateOfBirthStr;
    }

    public void setDateOfBirthStr(String dateOfBirthStr) {
        this.dateOfBirthStr = dateOfBirthStr;
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

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public DomainConstants.CivilStatus getCivilStatus() {
        return civilStatus;
    }

    public void setCivilStatus(DomainConstants.CivilStatus civilStatus) {
        this.civilStatus = civilStatus;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getIdentificationNo() {
        return identificationNo;
    }

    public void setIdentificationNo(String identificationNo) {
        this.identificationNo = identificationNo;
    }

    public String getEmployment() {
        return employment;
    }

    public void setEmployment(String employment) {
        this.employment = employment;
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

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public List<OwnershipDetailsDTO> getOwnershipDetailsDTOList() {
        if (ownershipDetailsDTOList == null) {
            this.ownershipDetailsDTOList = new ArrayList<>();
        }
        return ownershipDetailsDTOList;
    }

    public void setOwnershipDetailsDTOList(List<OwnershipDetailsDTO> ownershipDetailsDTOList) {
        this.ownershipDetailsDTOList = ownershipDetailsDTOList;
    }

    public Integer getApplicationFormID() {
        return applicationFormID;
    }

    public void setApplicationFormID(Integer applicationFormID) {
        this.applicationFormID = applicationFormID;
    }

    public AppsConstants.Status getStatus() {
        return status;
    }

    public void setStatus(AppsConstants.Status status) {
        this.status = status;
    }

    public AppsConstants.YesNo getPrimaryInformation() {
        return primaryInformation;
    }

    public void setPrimaryInformation(AppsConstants.YesNo primaryInformation) {
        this.primaryInformation = primaryInformation;
    }

    public String getDateOfRegistrationStr() {
        return dateOfRegistrationStr;
    }

    public void setDateOfRegistrationStr(String dateOfRegistrationStr) {
        this.dateOfRegistrationStr = dateOfRegistrationStr;
    }

    public List<AFCribAttachmentDTO> getAfCribAttachmentDTOList() {
        if (afCribAttachmentDTOList == null) {
            this.afCribAttachmentDTOList = new ArrayList<>();
        }
        return afCribAttachmentDTOList;
    }

    public void setAfCribAttachmentDTOList(List<AFCribAttachmentDTO> afCribAttachmentDTOList) {
        this.afCribAttachmentDTOList = afCribAttachmentDTOList;
    }

    public List<AFCribReportDTO> getAfCribReportDTOList() {
        if (afCribReportDTOList == null) {
            this.afCribReportDTOList = new ArrayList<>();
        }
        return afCribReportDTOList;
    }

    public void setAfCribReportDTOList(List<AFCribReportDTO> afCribReportDTOList) {
        this.afCribReportDTOList = afCribReportDTOList;
    }

    public List<AFOtherBankDetailsDTO> getAfOtherBankDetailsDTOList() {
        if (afOtherBankDetailsDTOList == null) {
            this.afOtherBankDetailsDTOList = new ArrayList<>();
        }
        return afOtherBankDetailsDTOList;
    }

    public void setAfOtherBankDetailsDTOList(List<AFOtherBankDetailsDTO> afOtherBankDetailsDTOList) {
        this.afOtherBankDetailsDTOList = afOtherBankDetailsDTOList;
    }

    public List<AFRiskRateDTO> getAfRiskRateDTOList() {
        if (afRiskRateDTOList == null) {
            this.afRiskRateDTOList = new ArrayList<>();
        }
        return afRiskRateDTOList;
    }

    public void setAfRiskRateDTOList(List<AFRiskRateDTO> afRiskRateDTOList) {
        this.afRiskRateDTOList = afRiskRateDTOList;
    }

    public List<AFFinancialObligationDTO> getAfFinancialObligationDTOList() {
        if (afFinancialObligationDTOList == null) {
            this.afFinancialObligationDTOList = new ArrayList<>();
        }
        return afFinancialObligationDTOList;
    }

    public void setAfFinancialObligationDTOList(List<AFFinancialObligationDTO> afFinancialObligationDTOList) {
        this.afFinancialObligationDTOList = afFinancialObligationDTOList;
    }

    public List<AFBorrowerGuarantorDTO> getAfBorrowerGuarantorDTOList() {
        if (afBorrowerGuarantorDTOList == null) {
            this.afBorrowerGuarantorDTOList = new ArrayList<>();
        }
        return afBorrowerGuarantorDTOList;
    }

    public void setAfBorrowerGuarantorDTOList(List<AFBorrowerGuarantorDTO> afBorrowerGuarantorDTOList) {
        this.afBorrowerGuarantorDTOList = afBorrowerGuarantorDTOList;
    }

    public AppsConstants.YesNo getIsBorrowerOrGuarantor() {
        return isBorrowerOrGuarantor;
    }

    public void setIsBorrowerOrGuarantor(AppsConstants.YesNo isBorrowerOrGuarantor) {
        this.isBorrowerOrGuarantor = isBorrowerOrGuarantor;
    }

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public AFCustomerDTO getAfCustomerDTO() {
        return afCustomerDTO;
    }

    public void setAfCustomerDTO(AFCustomerDTO afCustomerDTO) {
        this.afCustomerDTO = afCustomerDTO;
    }

    @Override
    public String toString() {
        return "BasicInformationDTO{" +
                "basicInformationID=" + basicInformationID +
                ", applicationFormID=" + applicationFormID +
                ", title=" + title +
                ", type=" + type +
                ", nameWithInitials='" + nameWithInitials + '\'' +
                ", initialRepresentation='" + initialRepresentation + '\'' +
                ", nameOfBusiness='" + nameOfBusiness + '\'' +
                ", registrationNo='" + registrationNo + '\'' +
                ", constitution='" + constitution + '\'' +
                ", dateOfIncorporateStr='" + dateOfIncorporateStr + '\'' +
                ", dateOfCommencementStr='" + dateOfCommencementStr + '\'' +
                ", natureOfBusiness='" + natureOfBusiness + '\'' +
                ", noOfBusinessYears=" + noOfBusinessYears +
                ", citizenship='" + citizenship + '\'' +
                ", privateAddress='" + privateAddress + '\'' +
                ", officialAddress='" + officialAddress + '\'' +
                ", businessAddress='" + businessAddress + '\'' +
                ", telNumber='" + telNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", dateOfBirthStr='" + dateOfBirthStr + '\'' +
                ", placeOfBirth='" + placeOfBirth + '\'' +
                ", civilStatus=" + civilStatus +
                ", nationality='" + nationality + '\'' +
                ", identificationNo='" + identificationNo + '\'' +
                ", employment='" + employment + '\'' +
                ", highestEduAchievement='" + highestEduAchievement + '\'' +
                ", position='" + position + '\'' +
                ", noOfYearsEmployment=" + noOfYearsEmployment +
                ", capitalAuthorized=" + capitalAuthorized +
                ", capitalIssued=" + capitalIssued +
                ", capitalPaidUp=" + capitalPaidUp +
                ", identificationType='" + identificationType + '\'' +
                ", status=" + status +
                ", primaryInformation=" + primaryInformation +
                ", ownershipDetailsDTOList=" + ownershipDetailsDTOList +
                '}';
    }
}
