package com.itechro.cas.model.dto.casv1;

import com.itechro.cas.model.domain.casv1.CustomerDetail;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CustomerDetailDTO {

    private String refNo;

    private String title;

    private String lastName;

    private String otherNames;

    private Integer dependants;

    private String gender;

    private Date dob;

    private String nic;

    private String passportNo;

    private Date passportIssueDate;

    private Date passportExpDate;

    private String correspondanceAddress1;

    private String correspondanceAddress2;

    private String correspondanceCity;

    private String correspondanceCountry;

    private String correspondancePostalCode;

    private String correspondancePhone;

    private String correspondanceFax;

    private String correspondanceEmail;

    private String mobileNo;

    private String residentialAddress1;

    private String residentialAddress2;

    private String residentialCity;

    private String residentialCountry;

    private String residentialPostalCode;

    private String residentialPhone;

    private String residentialFax;

    private String employmentStatus;

    private String profession;

    private String employer;

    private String typeOfOrganization;

    private String natureOfBusiness;

    private String employerAddress1;

    private String employerAddress2;

    private String employerCity;

    private String employerCountry;

    private String employerPostalCode;

    private String employerPhone;

    private String employerFax;

    private String establishYear;

    private String otherBankers;

    private String takenFaci;

    private String givenSeq;

    private String taxPerticulars;

    private String taxLiabilities;

    private String otherInfo;

    private String accNo;

    private Integer state;

    private Integer preLevel;

    private Integer currentLevel;

    private String sol;

    private String currentUser;

    private String preUser;

    private Date addDate;

    private Date authoDate;

    private String addUser;

    private String customerRiskGrading;

    private Float roaexisting;

    private Float roanew;

    private Float riskScore;

    private String cribFavourablity;

    private String financialAudit;

    private String unspecified;

    private Date cribDate;

    private String cribDescription;

    private String financialDescription;

    private List<DirectorDTO> directors;

    private List<CompanyDetailDTO> companies;

    private List<FacilityStatusDTO> facilityPapers;

    private List<OtherFacilityDTO> otherFacilities;

    public CustomerDetailDTO() {
    }

    public CustomerDetailDTO(CustomerDetail customerDetailDTO){

         this.refNo = customerDetailDTO.getRefNo();
         this.title = customerDetailDTO.getTitle();
         this.lastName = customerDetailDTO.getLastName();
         this.otherNames = customerDetailDTO.getOtherNames();
         this.dependants = customerDetailDTO.getDependants();
         this.gender = customerDetailDTO.getGender();
         this.dob = customerDetailDTO.getDob();
         this.nic = customerDetailDTO.getNic();
         this.passportNo = customerDetailDTO.getPassportNo();
         this.passportIssueDate = customerDetailDTO.getPassportIssueDate();
         this.passportExpDate = customerDetailDTO.getPassportExpDate();
         this.correspondanceAddress1 = customerDetailDTO.getCorrespondanceAddress1();
         this.correspondanceAddress2 = customerDetailDTO.getCorrespondanceAddress2();
         this.correspondanceCity = customerDetailDTO.getCorrespondanceCity();
         this.correspondanceCountry = customerDetailDTO.getCorrespondanceCountry();
         this.correspondancePostalCode = customerDetailDTO.getCorrespondancePostalCode();
         this.correspondancePhone = customerDetailDTO.getCorrespondancePhone();
         this.correspondanceFax = customerDetailDTO.getCorrespondanceFax();
         this.correspondanceEmail = customerDetailDTO.getCorrespondanceEmail();
         this.mobileNo = customerDetailDTO.getMobileNo();
         this.residentialAddress1 = customerDetailDTO.getResidentialAddress1();
         this.residentialAddress2 = customerDetailDTO.getResidentialAddress2();
         this.residentialCity = customerDetailDTO.getResidentialCity();
         this.residentialCountry = customerDetailDTO.getResidentialCountry();
         this.residentialPostalCode = customerDetailDTO.getResidentialPostalCode();
         this.residentialPhone = customerDetailDTO.getResidentialPhone();
         this.residentialFax = customerDetailDTO.getResidentialFax();
         this.employmentStatus = customerDetailDTO.getEmploymentStatus();
         this.profession = customerDetailDTO.getProfession();
         this.employer = customerDetailDTO.getEmployer();
         this.typeOfOrganization = customerDetailDTO.getTypeOfOrganization();
         this.natureOfBusiness = customerDetailDTO.getNatureOfBusiness();
         this.employerAddress1 = customerDetailDTO.getEmployerAddress1();
         this.employerAddress2 = customerDetailDTO.getEmployerAddress2();
         this.employerCity = customerDetailDTO.getEmployerCity();
         this.employerCountry = customerDetailDTO.getEmployerCountry();
         this.employerPostalCode = customerDetailDTO.getEmployerPostalCode();
         this.employerPhone = customerDetailDTO.getEmployerPhone();
         this.employerFax = customerDetailDTO.getEmployerFax();
         this.establishYear = customerDetailDTO.getEstablishYear();
         this.otherBankers = customerDetailDTO.getOtherBankers();
         this.takenFaci = customerDetailDTO.getTakenFaci();
         this.givenSeq = customerDetailDTO.getGivenSeq();
         this.taxPerticulars = customerDetailDTO.getTaxPerticulars();
         this.taxLiabilities = customerDetailDTO.getTaxLiabilities();
         this.otherInfo = customerDetailDTO.getOtherInfo();
         this.accNo = customerDetailDTO.getAccNo();
         this.state = customerDetailDTO.getState();
         this.preLevel = customerDetailDTO.getPreLevel();
         this.currentLevel = customerDetailDTO.getCurrentLevel();
         this.sol = customerDetailDTO.getSol();
         this.currentUser = customerDetailDTO.getCurrentUser();
         this.preUser = customerDetailDTO.getPreUser();
         this.addDate = customerDetailDTO.getAddDate();
         this.authoDate = customerDetailDTO.getAuthoDate();
         this.addUser = customerDetailDTO.getAddUser();
         this.customerRiskGrading = customerDetailDTO.getCustomerRiskGrading();
         this.roaexisting = customerDetailDTO.getRoaexisting();
         this.roanew = customerDetailDTO.getRoanew();
         this.riskScore = customerDetailDTO.getRiskScore();
         this.cribFavourablity = customerDetailDTO.getCribFavourablity();
         this.financialAudit = customerDetailDTO.getFinancialAudit();
         this.unspecified = customerDetailDTO.getUnspecified();
         this.cribDate = customerDetailDTO.getCribDate();
    }
}
