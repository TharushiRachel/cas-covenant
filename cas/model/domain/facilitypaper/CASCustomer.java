package com.itechro.cas.model.domain.facilitypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.common.UserTrackableEntity;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Entity
@Table(name = "T_CAS_CUSTOMER")
public class CASCustomer extends UserTrackableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_CAS_CUSTOMER")
    @SequenceGenerator(name = "SEQ_T_CAS_CUSTOMER", sequenceName = "SEQ_T_CAS_CUSTOMER", allocationSize = 1)
    @Column(name = "CAS_CUSTOMER_ID")
    private Integer casCustomerID;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FACILITY_PAPER_ID")
    private FacilityPaper facilityPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Enumerated(EnumType.STRING)
    @Column(name = "TITLE")
    private DomainConstants.Title title;

    @Column(name = "CAS_CUSTOMER_NAME")
    private String casCustomerName;

    @Column(name = "EMAIL_ADDRESS")
    private String emailAddress;

    @Column(name = "SECONDARY_EMAIL_ADDRESS")
    private String secondaryEmailAddress;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_BIRTH")
    private Date dateOfBirth;

    @Column(name = "CIVIL_STATUS")
    private String civilStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "IS_PRIMARY")
    private AppsConstants.YesNo isPrimary;

    @Column(name = "DISPLAY_ORDER")
    private Integer displayOrder;

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

    @Column(name = "PLACE_OF_BIRTH")
    private String placeOfBirth;

    @Column(name = "NATIONALITY")
    private String nationality;

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
    @Column(name = "BORROWER_OR_GUARANTOR")
    private AppsConstants.YesNo isBorrowerOrGuarantor;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private AppsConstants.Status status;

    @Column(name = "TURNOVER")
    private String turnover;

    @Column(name = "CURRENCY_CODE")
    private String currencyCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CASCustomerCribDetail> CASCustomerCribDetailSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CASCustomerDoc> CASCustomerDocSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CasCustomerOtherBankFacility> casCustomerOtherBankFacilitySet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CASCustomerCribReport> CASCustomerCribReportSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CASCustomerAddress> CASCustomerAddressSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CASCustomerTelephone> CASCustomerTelephoneSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CASCustomerIdentification> CASCustomerIdentificationSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CASCustomerBankDetail> CASCustomerBankDetailSet;

    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true, mappedBy = "CASCustomer")
    private Set<CustomerRatings> CustomerRatingsSet;

    public Integer getCasCustomerID() {
        return casCustomerID;
    }

    public void setCasCustomerID(Integer casCustomerID) {
        this.casCustomerID = casCustomerID;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public AppsConstants.YesNo getIsPrimary() {
        return isPrimary;
    }

    public void setIsPrimary(AppsConstants.YesNo isPrimary) {
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
        if (StringUtils.isNotEmpty(casCustomerName)) {
            this.casCustomerName = casCustomerName.toUpperCase();
        }
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public Date getDateOfRegistration() {
        return dateOfRegistration;
    }

    public void setDateOfRegistration(Date dateOfRegistration) {
        this.dateOfRegistration = dateOfRegistration;
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

    public Set<CASCustomerCribDetail> getCASCustomerCribDetailSet() {
        if (CASCustomerCribDetailSet == null) {
            CASCustomerCribDetailSet = new HashSet<>();
        }
        return CASCustomerCribDetailSet;
    }

    public List<CASCustomerCribDetail> getOrderedCasCustomerCribDetailSet() {
        return getCASCustomerCribDetailSet().stream().sorted(new Comparator<CASCustomerCribDetail>() {
            @Override
            public int compare(CASCustomerCribDetail o1, CASCustomerCribDetail o2) {
                return o1.getCasCustomerCribDetailsID().compareTo(o2.getCasCustomerCribDetailsID());
            }
        }).collect(Collectors.toList());
    }

    public void setCASCustomerCribDetailSet(Set<CASCustomerCribDetail> CASCustomerCribDetailSet) {
        this.CASCustomerCribDetailSet = CASCustomerCribDetailSet;
    }

    public void addCasCustomerCribDetail(CASCustomerCribDetail CASCustomerCribDetail) {
        CASCustomerCribDetail.setCASCustomer(this);
        this.getCASCustomerCribDetailSet().add(CASCustomerCribDetail);
    }

    public CASCustomerCribDetail getCasCustomerCribDetailByID(Integer cribDetailID) {
        return this.getCASCustomerCribDetailSet().stream().filter(casCustomerCribDetail ->
                cribDetailID.equals(casCustomerCribDetail.getCasCustomerCribDetailsID()))
                .findFirst().orElse(null);
    }

    public Set<CASCustomerDoc> getCASCustomerDocSet() {
        if (CASCustomerDocSet == null) {
            CASCustomerDocSet = new HashSet<>();
        }
        return CASCustomerDocSet;
    }

    public void setCASCustomerDocSet(Set<CASCustomerDoc> CASCustomerDocSet) {
        this.CASCustomerDocSet = CASCustomerDocSet;
    }

    public void addCasCustomerDoc(CASCustomerDoc CASCustomerDoc) {
        CASCustomerDoc.setCASCustomer(this);
        this.getCASCustomerDocSet().add(CASCustomerDoc);
    }

    public CASCustomerDoc getCasCustomerDocByID(Integer casCustomerDocID) {
        return this.getCASCustomerDocSet().stream().filter(casCustomerDoc ->
                casCustomerDocID.equals(casCustomerDoc.getCasCustomerDocID()))
                .findFirst().orElse(null);
    }

    public Set<CasCustomerOtherBankFacility> getCasCustomerOtherBankFacilitySet() {
        if (casCustomerOtherBankFacilitySet == null) {
            casCustomerOtherBankFacilitySet = new HashSet<>();
        }

        return casCustomerOtherBankFacilitySet;
    }

    public List<CasCustomerOtherBankFacility> getOrderedCasCustomerOtherBankFacilitySet() {
        return getCasCustomerOtherBankFacilitySet().stream().sorted(new Comparator<CasCustomerOtherBankFacility>() {
            @Override
            public int compare(CasCustomerOtherBankFacility o1, CasCustomerOtherBankFacility o2) {
                return o1.getCasCusOtherBankFacilityID().compareTo(o2.getCasCusOtherBankFacilityID());
            }
        }).collect(Collectors.toList());
    }

    public void setCasCustomerOtherBankFacilitySet(Set<CasCustomerOtherBankFacility> casCustomerOtherBankFacilitySet) {
        this.casCustomerOtherBankFacilitySet = casCustomerOtherBankFacilitySet;
    }

    public void addCasCustomerOtherBankFacility(CasCustomerOtherBankFacility casCustomerOtherBankFacility) {
        casCustomerOtherBankFacility.setCASCustomer(this);
        this.getCasCustomerOtherBankFacilitySet().add(casCustomerOtherBankFacility);
    }

    public CasCustomerOtherBankFacility getCasCustomerOtherBankFacilityByID(Integer casCustomerOtherBankFacilityID) {
        return this.getCasCustomerOtherBankFacilitySet().stream().filter(casCustomerOtherBankFacility ->
                casCustomerOtherBankFacilityID.equals(casCustomerOtherBankFacility.getCasCusOtherBankFacilityID()))
                .findFirst().orElse(null);
    }

    public Set<CASCustomerCribReport> getCASCustomerCribReportSet() {
        if (CASCustomerCribReportSet == null) {
            CASCustomerCribReportSet = new HashSet<>();
        }
        return CASCustomerCribReportSet;
    }

    public void setCASCustomerCribReportSet(Set<CASCustomerCribReport> CASCustomerCribReportSet) {
        this.CASCustomerCribReportSet = CASCustomerCribReportSet;
    }

    public void addCasCustomerCribReport(CASCustomerCribReport CASCustomerCribReport) {
        CASCustomerCribReport.setCASCustomer(this);
        this.getCASCustomerCribReportSet().add(CASCustomerCribReport);
    }

    public CASCustomerCribReport getCasCustomerCribReportByID(Integer customerCribReportID) {
        return this.getCASCustomerCribReportSet().stream().filter(casCustomerCribReport ->
                customerCribReportID.equals(casCustomerCribReport.getCasCustomerCribReportID()))
                .findFirst().orElse(null);
    }

    public List<CASCustomerCribReport> getOrderedCasCustomerCribReportSet() {
        return getCASCustomerCribReportSet().stream().sorted(new Comparator<CASCustomerCribReport>() {
            @Override
            public int compare(CASCustomerCribReport o1, CASCustomerCribReport o2) {
                return o1.getCasCustomerCribReportID().compareTo(o2.getCasCustomerCribReportID());
            }
        }).collect(Collectors.toList());
    }

    public Set<CASCustomerAddress> getCASCustomerAddressSet() {
        if (CASCustomerAddressSet == null) {
            this.CASCustomerAddressSet = new HashSet<>();
        }
        return CASCustomerAddressSet;
    }

    public List<CASCustomerAddress> getOrderedCasCustomerAddressSet() {
        return getCASCustomerAddressSet().stream().sorted(new Comparator<CASCustomerAddress>() {
            @Override
            public int compare(CASCustomerAddress o1, CASCustomerAddress o2) {
                if (o1.getAddressType() != null && o2.getAddressType() != null) {
                    return o1.getAddressType().compareTo(o2.getAddressType());
                } else {
                    return o1.getCasCustomerAddressID().compareTo(o2.getCasCustomerAddressID());
                }
            }
        }).collect(Collectors.toList());
    }

    public void setCASCustomerAddressSet(Set<CASCustomerAddress> CASCustomerAddressSet) {
        this.CASCustomerAddressSet = CASCustomerAddressSet;
    }

    public void addCasCustomerAddress(CASCustomerAddress CASCustomerAddress) {
        CASCustomerAddress.setCASCustomer(this);
        this.getCASCustomerAddressSet().add(CASCustomerAddress);
    }


    public CASCustomerAddress getCasCustomerAddressByID(Integer casCustomerAddressID) {
        return this.getCASCustomerAddressSet().stream().filter(casCustomerAddress ->
                casCustomerAddressID.equals(casCustomerAddress.getCasCustomerAddressID()))
                .findFirst().orElse(null);
    }

    public Set<CASCustomerTelephone> getCASCustomerTelephoneSet() {
        if (CASCustomerTelephoneSet == null) {
            this.CASCustomerTelephoneSet = new HashSet<>();
        }
        return CASCustomerTelephoneSet;
    }

    public List<CASCustomerTelephone> getOrderedCasCustomerTelephoneSet() {
        return getCASCustomerTelephoneSet().stream().sorted(new Comparator<CASCustomerTelephone>() {
            @Override
            public int compare(CASCustomerTelephone o1, CASCustomerTelephone o2) {
                if (o1.getDescription() != null && o2.getDescription() != null) {
                    return o1.getDescription().compareTo(o2.getDescription());
                } else {
                    return o1.getCasCustomerTelephoneID().compareTo(o2.getCasCustomerTelephoneID());
                }
            }
        }).collect(Collectors.toList());
    }

    public void setCASCustomerTelephoneSet(Set<CASCustomerTelephone> CASCustomerTelephoneSet) {
        this.CASCustomerTelephoneSet = CASCustomerTelephoneSet;
    }

    public CASCustomerTelephone getcasCustomerTelephoneByID(Integer casCustomerTelephoneID) {
        return this.getCASCustomerTelephoneSet().stream().filter(casCustomerTelephone ->
                casCustomerTelephoneID.equals(casCustomerTelephone.getCasCustomerTelephoneID()))
                .findFirst().orElse(null);
    }

    public void addCasCustomerTelephone(CASCustomerTelephone CASCustomerTelephone) {
        CASCustomerTelephone.setCASCustomer(this);
        this.getCASCustomerTelephoneSet().add(CASCustomerTelephone);
    }

    public Set<CASCustomerIdentification> getCASCustomerIdentificationSet() {
        if (CASCustomerIdentificationSet == null) {
            this.CASCustomerIdentificationSet = new HashSet<>();
        }
        return CASCustomerIdentificationSet;
    }

    public void setCASCustomerIdentificationSet(Set<CASCustomerIdentification> CASCustomerIdentificationSet) {
        this.CASCustomerIdentificationSet = CASCustomerIdentificationSet;
    }

    public void addCasCustomerIdentification(CASCustomerIdentification CASCustomerIdentification) {
        CASCustomerIdentification.setCASCustomer(this);
        this.getCASCustomerIdentificationSet().add(CASCustomerIdentification);
    }

    public CASCustomerIdentification getCasCustomerIdentificationByID(Integer casCustomerIdentificationID) {
        return this.getCASCustomerIdentificationSet().stream().filter(casCustomerIdentification ->
                casCustomerIdentificationID.equals(casCustomerIdentification.getCasCustomerIdentificationID()))
                .findFirst().orElse(null);
    }

    public List<CASCustomerIdentification> getOrderedCasCustomerIdentificationSet() {
        return getCASCustomerIdentificationSet().stream().sorted(new Comparator<CASCustomerIdentification>() {
            @Override
            public int compare(CASCustomerIdentification o1, CASCustomerIdentification o2) {
                return o1.getCasCustomerIdentificationID().compareTo(o2.getCasCustomerIdentificationID());
            }
        }).collect(Collectors.toList());
    }

    public Set<CASCustomerBankDetail> getCASCustomerBankDetailSet() {
        if (CASCustomerBankDetailSet == null) {
            this.CASCustomerBankDetailSet = new HashSet<>();
        }
        return CASCustomerBankDetailSet;
    }

    public void setCASCustomerBankDetailSet(Set<CASCustomerBankDetail> CASCustomerBankDetailSet) {
        this.CASCustomerBankDetailSet = CASCustomerBankDetailSet;
    }

    public void addCasCustomerBankDetail(CASCustomerBankDetail CASCustomerBankDetail) {
        CASCustomerBankDetail.setCASCustomer(this);
        this.getCASCustomerBankDetailSet().add(CASCustomerBankDetail);
    }

    public List<CASCustomerBankDetail> getOrderedCasCustomerBankDetailSet() {
        return getCASCustomerBankDetailSet().stream().sorted(new Comparator<CASCustomerBankDetail>() {
            @Override
            public int compare(CASCustomerBankDetail o1, CASCustomerBankDetail o2) {
                return o1.getCasCustomerBankDetailsID().compareTo(o2.getCasCustomerBankDetailsID());
            }
        }).collect(Collectors.toList());
    }

    public void addCustomerRatings(CustomerRatings CustomerRatings) {
        CustomerRatings.setCASCustomer(this);
        this.getCustomerRatingsSet().add(CustomerRatings);
    }

    public Set<CustomerRatings> getCustomerRatingsSet() {
        if (CustomerRatingsSet == null) {
            this.CustomerRatingsSet = new HashSet<>();
        }
        return CustomerRatingsSet;
    }

    public void setCustomerRatingsSet(Set<CustomerRatings> CustomerRatingsSet) {
        this.CustomerRatingsSet = CustomerRatingsSet;
    }

    public String getTurnover() {
        return turnover;
    }

    public void setTurnover(String  turnover) {
        this.turnover = turnover;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
