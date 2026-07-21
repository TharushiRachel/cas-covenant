package com.itechro.cas.model.domain.casv1;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Entity
@Getter
@Setter
@Table(name = "CASV1_CUSTOMER_DETAILS_TABLE")
public class CustomerDetail {

    @Id
    @Column(name = "REF_NUM")
    private String refNo;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "LAST_NAME")
    private String lastName;

    @Column(name = "OTHER_NAMES")
    private String otherNames;

    @Column(name = "DEPENDANTS")
    private Integer dependants;

    @Column(name = "GENDER")
    private String gender;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "DATE_OF_BIRTH")
    private Date dob;

    @Column(name = "NIC")
    private String nic;

    @Column(name = "PASSPORT_NO")
    private String passportNo;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PASSPORT_ISSUE_DATE")
    private Date passportIssueDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PASSPORT_EXP_DATE")
    private Date passportExpDate;

    @Column(name = "CORRESPONDANCE_ADDRESS_1")
    private String correspondanceAddress1;

    @Column(name = "CORRESPONDANCE_ADDRESS_2")
    private String correspondanceAddress2;


    @Column(name = "CORRESPONDANCE_CITY")
    private String correspondanceCity;

    @Column(name = "CORRESPONDANCE_COUNTRY")
    private String correspondanceCountry;

    @Column(name = "CORRESPONDANCE_POSTAL_CODE")
    private String correspondancePostalCode;

    @Column(name = "CORRESPONDANCE_PHONE")
    private String correspondancePhone;

    @Column(name = "CORRESPONDANCE_FAX")
    private String correspondanceFax;

    @Column(name = "CORRESPONDANCE_EMAIL")
    private String correspondanceEmail;

    @Column(name = "MOBILE_PHONE")
    private String mobileNo;

    @Column(name = "RESIDENTIAL_ADDRESS_1")
    private String residentialAddress1;

    @Column(name = "RESIDENTIAL_ADDRESS_2")
    private String residentialAddress2;

    @Column(name = "RESIDENTIAL_CITY")
    private String residentialCity;

    @Column(name = "RESIDENTIAL_COUNTRY")
    private String residentialCountry;

    @Column(name = "RESIDENTIAL_POSTAL_CODE")
    private String residentialPostalCode;

    @Column(name = "RESIDENTIAL_PHONE")
    private String residentialPhone;

    @Column(name = "RESIDENTIAL_FAX")
    private String residentialFax;

    @Column(name = "EMPLOYMENT_STATUS")
    private String employmentStatus;

    @Column(name = "PROFESSION")
    private String profession;

    @Column(name = "EMPLOYER")
    private String employer;

    @Column(name = "TYPE_OF_ORGANIZATION")
    private String typeOfOrganization;

    @Column(name = "NATURE_OF_BUSSINESS")
    private String natureOfBusiness;

    @Column(name = "EMPLOYER_ADDRESS_1")
    private String employerAddress1;

    @Column(name = "EMPLOYER_ADDRESS_2")
    private String employerAddress2;

    @Column(name = "EMPLOYER_CITY")
    private String employerCity;

    @Column(name = "EMPLOYER_COUNTRY")
    private String employerCountry;

    @Column(name = "EMPLOYER_POSTAL_CODE")
    private String employerPostalCode;

    @Column(name = "EMPLOYER_PHONE")
    private String employerPhone;

    @Column(name = "EMPLOYER_FAX")
    private String employerFax;

    @Column(name = "ESTABLISH_YEAR")
    private String establishYear;

    @Column(name = "OTHER_BANKERS")
    private String otherBankers;

    @Column(name = "TAKEN_FACI")
    private String takenFaci;

    @Column(name = "GIVEN_SEQ")
    private String givenSeq;

    @Column(name = "TAX_PERTICULARS")
    private String taxPerticulars;

    @Column(name = "TAX_LIABILITIES")
    private String taxLiabilities;

    @Column(name = "OTHER_INFO")
    private String otherInfo;

    @Column(name = "AC_NO")
    private String accNo;

    @Column(name = "STATE")
    private Integer state;

    @Column(name = "PRE_LEVEL")
    private Integer preLevel;

    @Column(name = "CURRENT_LEVEL")
    private Integer currentLevel;

    @Column(name = "SOL")
    private String sol;

    @Column(name = "CURRENT_USER")
    private String currentUser;

    @Column(name = "PRE_USER")
    private String preUser;

    @Temporal(TemporalType.TIMESTAMP)

    @Column(name = "ADD_DATE")
    private Date addDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "AUTHO_DATE")
    private Date authoDate;

    @Column(name = "ADD_USER")
    private String addUser;

    @Column(name = "CUSTOMER_RISK_GRADING")
    private String customerRiskGrading;

    @Column(name = "ROAEXISTING")
    private Float roaexisting;

    @Column(name = "ROANEW")
    private Float roanew;

    @Column(name = "RISKSCORE")
    private Float riskScore;

    @Column(name = "CRIB_FAVOURABLITY")
    private String cribFavourablity;

    @Column(name = "FINANCIALS_AUDIT")
    private String financialAudit;

    @Column(name = "UNSPECIFIED")
    private String unspecified;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CRIB_DATE")
    private Date cribDate;
}
