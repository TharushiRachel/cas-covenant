package com.itechro.cas.model.domain.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomer;
import com.itechro.cas.model.domain.common.UserTrackableEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_AF_BASIC_INFORMATION")
public class BasicInformation extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_AF_BASIC_INFORMATION")
    @SequenceGenerator(name = "SEQ_T_AF_BASIC_INFORMATION", sequenceName = "SEQ_T_AF_BASIC_INFORMATION", allocationSize = 1)
    @Column(name = "BASIC_INFORMATION_ID")
    private Integer basicInformationID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPLICATION_FORM_ID")
    private ApplicationForm applicationForm;

    @Enumerated(EnumType.STRING)
    @Column(name = "TITLE")
    private DomainConstants.Title title;

    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private DomainConstants.BasicInformationType type;

    @Column(name = "NAME_WITH_INITIAL")
    private String nameWithInitials;

    @Column(name = "INITIAL_PRESENTATION")
    private String initialRepresentation;

    @Column(name = "NAME_OF_BUSINESS")
    private String nameOfBusiness;

    @Column(name = "REGISTRATION_NO")
    private String registrationNo;

    @Column(name = "CONSTITUTION")
    private String constitution;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_INCORPORATE")
    private Date dateOfIncorporate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_COMMENCEMENT")
    private Date dateOfCommencement;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_REGISTRATION")
    private Date dateOfRegistration;

    @Column(name = "NATURE_OF_BUSINESS")
    private String natureOfBusiness;

    @Column(name = "NO_OF_BUSINESS_YEARS")
    private Double noOfBusinessYears;

    @Column(name = "CITIZENSHIP")
    private String citizenship;

    @Column(name = "PRIVATE_ADDRESS")
    private String privateAddress;

    @Column(name = "OFFICE_ADDRESS")
    private String officialAddress;

    @Column(name = "BUSINESS_ADDRESS")
    private String businessAddress;

    @Column(name = "TEL_NUMBER")
    private String telNumber;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "PLACE_OF_BIRTH")
    private String placeOfBirth;

    @Enumerated(EnumType.STRING)
    @Column(name = "CIVIL_STATUS")
    private DomainConstants.CivilStatus civilStatus;

    @Column(name = "NATIONALITY")
    private String nationality;

    @Column(name = "IDENTIFICATION_NO")
    private String identificationNo;

    @Column(name = "EMPLOYMENT")
    private String employment;

    @Column(name = "EMPLOYER")
    private String employer;

    @Column(name = "HIGHEST_EDU_ACHIEVEMENT")
    private String highestEduAchievement;

    @Column(name = "POSITION")
    private String position;

    @Column(name = "NO_YEARS_EMPLOYMENT")
    private Double noOfYearsEmployment;

    @Column(name = "CAPITAL_AUTHORIZED")
    private BigDecimal capitalAuthorized;

    @Column(name = "CAPITAL_ISSUED")
    private BigDecimal capitalIssued;

    @Column(name = "CAPITAL_PAID_UP")
    private BigDecimal capitalPaidUp;

    @Enumerated(EnumType.STRING)
    @Column(name = "IDENTIFICATION_TYPE")
    private DomainConstants.CustomerIdentificationType identificationType;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRIMARY_INFORMATION")
    private AppsConstants.YesNo primaryInformation;

    @Enumerated(EnumType.STRING)
    @Column(name = "BORROWER_OR_GUARANTOR")
    private AppsConstants.YesNo isBorrowerOrGuarantor;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "CUSTOMER_ID")
    private Integer customerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AF_CUSTOMER_ID")
    private AFCustomer afCustomer;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "basicInformation")
    private Set<OwnershipDetails> ownershipDetailsSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "basicInformation")
    private Set<AFCribAttachment> afCribAttachmentSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "basicInformation")
    private Set<AFCribReport> afCribReportSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "basicInformation")
    private Set<AFOtherBankDetails> afOtherBankDetailsSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "basicInformation")
    private Set<AFRiskRate> afRiskRateSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "basicInformation")
    private Set<AFFinancialObligation> afFinancialObligationSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "basicInformation")
    private Set<AFBorrowerGuarantor> afBorrowerGuarantorSet;

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

    public Date getDateOfIncorporate() {
        return dateOfIncorporate;
    }

    public void setDateOfIncorporate(Date dateOfIncorporate) {
        this.dateOfIncorporate = dateOfIncorporate;
    }

    public Date getDateOfCommencement() {
        return dateOfCommencement;
    }

    public void setDateOfCommencement(Date dateOfCommencement) {
        this.dateOfCommencement = dateOfCommencement;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
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

    public DomainConstants.CustomerIdentificationType getIdentificationType() {
        return identificationType;
    }

    public void setIdentificationType(DomainConstants.CustomerIdentificationType identificationType) {
        this.identificationType = identificationType;
    }

    public Set<OwnershipDetails> getOwnershipDetailsSet() {
        if (ownershipDetailsSet == null) {
            ownershipDetailsSet = new HashSet<>();
        }
        return ownershipDetailsSet;
    }

    public void setOwnershipDetailsSet(Set<OwnershipDetails> ownershipDetailsSet) {
        this.ownershipDetailsSet = ownershipDetailsSet;
    }

    public List<OwnershipDetails> getOrderedOwnershipDetailsSet() {
        return getOwnershipDetailsSet().stream().sorted(new Comparator<OwnershipDetails>() {
            @Override
            public int compare(OwnershipDetails o1, OwnershipDetails o2) {
                return o1.getOwnershipDetailsID().compareTo(o2.getOwnershipDetailsID());
            }
        }).collect(Collectors.toList());
    }

    public void addOwnerShipDetails(OwnershipDetails ownershipDetails) {
        ownershipDetails.setBasicInformation(this);
        this.getOwnershipDetailsSet().add(ownershipDetails);
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
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

    public Set<AFCribAttachment> getAfCribAttachmentSet() {
        if (afCribAttachmentSet == null) {
            afCribAttachmentSet = new HashSet<>();
        }
        return afCribAttachmentSet;
    }

    public void setAfCribAttachmentSet(Set<AFCribAttachment> afCribAttachmentSet) {
        this.afCribAttachmentSet = afCribAttachmentSet;
    }

    public Set<AFCribReport> getAfCribReportSet() {
        if (afCribReportSet == null) {
            this.afCribReportSet = new HashSet<>();
        }
        return afCribReportSet;
    }

    public void setAfCribReportSet(Set<AFCribReport> afCribReportSet) {
        this.afCribReportSet = afCribReportSet;
    }

    public void addCribReport(AFCribReport afCribReport) {
        afCribReport.setBasicInformation(this);
        this.getAfCribReportSet().add(afCribReport);
    }

    public List<AFCribReport> getOrderedAfCribReportSet() {
        return getAfCribReportSet().stream().sorted(new Comparator<AFCribReport>() {
            @Override
            public int compare(AFCribReport o1, AFCribReport o2) {
                return o1.getCribReportID().compareTo(o2.getCribReportID());
            }
        }).collect(Collectors.toList());
    }

    public List<AFCribAttachment> getOrderedAfCribAttachmentSet() {
        return getAfCribAttachmentSet().stream().sorted(new Comparator<AFCribAttachment>() {
            @Override
            public int compare(AFCribAttachment o1, AFCribAttachment o2) {
                return o1.getCribAttachmentID().compareTo(o2.getCribAttachmentID());
            }
        }).collect(Collectors.toList());
    }

    public AFCribAttachment getAFCribAttachmentByID(Integer afCribAttachmentID) {
        return this.getAfCribAttachmentSet().stream().filter(afCribAttachment ->
                afCribAttachmentID.equals(afCribAttachment.getCribAttachmentID()))
                .findFirst().orElse(null);
    }

    public void addCribAttachments(AFCribAttachment afCribAttachment) {
        afCribAttachment.setBasicInformation(this);
        this.getAfCribAttachmentSet().add(afCribAttachment);
    }

    public Set<AFOtherBankDetails> getAfOtherBankDetailsSet() {
        if (afOtherBankDetailsSet == null) {
            this.afOtherBankDetailsSet = new HashSet<>();
        }
        return afOtherBankDetailsSet;
    }

    public void setAfOtherBankDetailsSet(Set<AFOtherBankDetails> afOtherBankDetailsSet) {
        this.afOtherBankDetailsSet = afOtherBankDetailsSet;
    }

    public List<AFOtherBankDetails> getOrderedAfOtherBankDetails() {
        return getAfOtherBankDetailsSet().stream().sorted(new Comparator<AFOtherBankDetails>() {
            @Override
            public int compare(AFOtherBankDetails o1, AFOtherBankDetails o2) {
                return o1.getOtherBankDetailsID().compareTo(o2.getOtherBankDetailsID());
            }
        }).collect(Collectors.toList());
    }

    public AFOtherBankDetails getAfOtherBankDetailsByID(Integer otherBankDetailsID) {
        return this.getAfOtherBankDetailsSet().stream().filter(otherBankDetails ->
                otherBankDetailsID.equals(otherBankDetails.getOtherBankDetailsID()))
                .findFirst().orElse(null);
    }

    public void addOtherBankDetails(AFOtherBankDetails afOtherBankDetails) {
        afOtherBankDetails.setBasicInformation(this);
        this.getAfOtherBankDetailsSet().add(afOtherBankDetails);
    }

    public Set<AFRiskRate> getAfRiskRateSet() {
        if (afRiskRateSet == null) {
            this.afRiskRateSet = new HashSet<>();
        }
        return afRiskRateSet;
    }

    public void setAfRiskRateSet(Set<AFRiskRate> afRiskRateSet) {
        this.afRiskRateSet = afRiskRateSet;
    }

    public List<AFRiskRate> getOrderedAfRiskRates() {
        return getAfRiskRateSet().stream().sorted(new Comparator<AFRiskRate>() {
            @Override
            public int compare(AFRiskRate o1, AFRiskRate o2) {
                return o1.getRiskRateID().compareTo(o2.getRiskRateID());
            }
        }).collect(Collectors.toList());
    }

    public AFRiskRate getAFRiskRatesByID(Integer riskRateID) {
        return this.getAfRiskRateSet().stream().filter(riskRate ->
                riskRateID.equals(riskRate.getRiskRateID()))
                .findFirst().orElse(null);
    }

    public void addRiskRateDetails(AFRiskRate afRiskRate) {
        afRiskRate.setBasicInformation(this);
        this.getAfRiskRateSet().add(afRiskRate);
    }

    public Set<AFFinancialObligation> getAfFinancialObligationSet() {
        if (afFinancialObligationSet == null) {
            afFinancialObligationSet = new HashSet<>();
        }
        return afFinancialObligationSet;
    }

    public List<AFFinancialObligation> getOrderedAfFinancialObligation() {
        return getAfFinancialObligationSet().stream().sorted(new Comparator<AFFinancialObligation>() {
            @Override
            public int compare(AFFinancialObligation o1, AFFinancialObligation o2) {
                return o1.getFinancialObligationID().compareTo(o2.getFinancialObligationID());
            }
        }).collect(Collectors.toList());
    }

    public void setAfFinancialObligationSet(Set<AFFinancialObligation> afFinancialObligationSet) {
        this.afFinancialObligationSet = afFinancialObligationSet;
    }

    public void addAFFinancialObligation(AFFinancialObligation afFinancialObligation) {
        afFinancialObligation.setBasicInformation(this);
        this.getAfFinancialObligationSet().add(afFinancialObligation);
    }

    public AFFinancialObligation getAFFinancialObligationByID(Integer financialObligationID) {
        return this.getAfFinancialObligationSet().stream().filter(afFinancialObligation ->
                financialObligationID.equals(afFinancialObligation.getFinancialObligationID()))
                .findFirst().orElse(null);
    }

    public Set<AFBorrowerGuarantor> getAfBorrowerGuarantorSet() {
        if (afBorrowerGuarantorSet == null) {
            this.afBorrowerGuarantorSet = new HashSet<>();
        }
        return afBorrowerGuarantorSet;
    }

    public List<AFBorrowerGuarantor> getOrderedAfBorrowerGuarantor() {
        return getAfBorrowerGuarantorSet().stream().sorted(new Comparator<AFBorrowerGuarantor>() {
            @Override
            public int compare(AFBorrowerGuarantor o1, AFBorrowerGuarantor o2) {
                return o1.getBorrowerGuarantorID().compareTo(o2.getBorrowerGuarantorID());
            }
        }).collect(Collectors.toList());
    }

    public void setAfBorrowerGuarantorSet(Set<AFBorrowerGuarantor> afBorrowerGuarantorSet) {
        this.afBorrowerGuarantorSet = afBorrowerGuarantorSet;
    }

    public void addAfBorrowerGuarantor(AFBorrowerGuarantor afBorrowerGuarantor) {
        afBorrowerGuarantor.setBasicInformation(this);
        this.getAfBorrowerGuarantorSet().add(afBorrowerGuarantor);
    }

    public AFBorrowerGuarantor getAFBorrowerGuarantorByID(Integer borrowerGuarantorID) {
        return this.getAfBorrowerGuarantorSet().stream().filter(afBorrowerGuarantor ->
                borrowerGuarantorID.equals(afBorrowerGuarantor.getBorrowerGuarantorID()))
                .findFirst().orElse(null);
    }

    public AppsConstants.YesNo getIsBorrowerOrGuarantor() {
        return isBorrowerOrGuarantor;
    }

    public void setIsBorrowerOrGuarantor(AppsConstants.YesNo isBorrowerOrGuarantor) {
        this.isBorrowerOrGuarantor = isBorrowerOrGuarantor;
    }

    public Integer getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Integer customerID) {
        this.customerID = customerID;
    }

    public AFCustomer getAfCustomer() {
        return afCustomer;
    }

    public void setAfCustomer(AFCustomer afCustomer) {
        this.afCustomer = afCustomer;
    }

}
