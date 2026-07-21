package com.itechro.cas.service.bccpaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.bccpaper.BCCPaperDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.applicationform.AFRiskRate;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.applicationform.BasicInformation;
import com.itechro.cas.model.domain.bccpaper.*;
import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityFacilitySecurity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityInterestRate;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.bccpaper.BoardCreditCommitteePaperDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.bccpaper.pdf.BCCReportCreator;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.NumberUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.thymeleaf.TemplateEngine;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BCCCreateBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(BCCUpdateBuilder.class);

    private Environment environment;

    private TemplateEngine templateEngine;

    private FacilityPaperDao facilityPaperDao;

    private String branchName;

    private BCCPaperDao bccPaperDao;

    private ApplicationFormDao applicationFormDao;

    private BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO;

    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    private Date date = new Date();

    private CredentialsDTO credentialsDTO;

    private FacilityPaper facilityPaper;

    private ApplicationForm applicationForm;

    private BasicInformation primaryBasicInformation;

    private String bccPaperRefNumber;

    private Set<Facility> facilities;

    public BCCCreateBuilder(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) {
        this.boardCreditCommitteePaperDTO = boardCreditCommitteePaperDTO;
        this.credentialsDTO = credentialsDTO;
    }

    public void setFacilityPaperDao(FacilityPaperDao facilityPaperDao) {
        this.facilityPaperDao = facilityPaperDao;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public void setBccPaperDao(BCCPaperDao bccPaperDao) {
        this.bccPaperDao = bccPaperDao;
    }

    public BCCCreateBuilder init() throws AppsException {
        boolean isNew = boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID() == null;
        if (isNew) {
            facilityPaper = facilityPaperDao.getOne(boardCreditCommitteePaperDTO.getFacilityPaperID());
            this.facilities = facilityPaper.getActiveFacility();
            boardCreditCommitteePaper = new BoardCreditCommitteePaper();
            boardCreditCommitteePaper.setCreatedBy(credentialsDTO.getUserName());
            boardCreditCommitteePaper.setCreatedDate(date);
            boardCreditCommitteePaper.setBccRefNumber(this.getBccPaperRefNumber());
            boardCreditCommitteePaper.setCurrentAssignUser(credentialsDTO.getUserName());
            boardCreditCommitteePaper.setAssignUserDisplayName(boardCreditCommitteePaperDTO.getAssignUserDisplayName());
            boardCreditCommitteePaper.setCurrentAssignUserDivCode(credentialsDTO.getDivCode());
            boardCreditCommitteePaper.setIsSubmitted(AppsConstants.YesNo.N);
        } else {
            LOG.error("ERROR : Invalid Facility Paper Id : {} Not Found ", boardCreditCommitteePaperDTO.getFacilityPaperID());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_PAPER_NOT_FOUND);
        }
        return this;
    }

    public BCCCreateBuilder buildBCCValues() throws AppsException {

        boardCreditCommitteePaper.setBranchCode(boardCreditCommitteePaperDTO.getBranchCode());
        boardCreditCommitteePaper.setCreatedUserDisplayName(boardCreditCommitteePaperDTO.getCreatedUserDisplayName());
        boardCreditCommitteePaper.setUpmID(boardCreditCommitteePaperDTO.getUpmID());
        boardCreditCommitteePaper.setPaperType(boardCreditCommitteePaperDTO.getPaperType());
        boardCreditCommitteePaper.setStatus(boardCreditCommitteePaperDTO.getStatus());

        boardCreditCommitteePaper.setFacilityDateOfApproval(facilityPaper.getApprovedDate());
        boardCreditCommitteePaper.setProposedOutstandingAtDate(facilityPaper.getCreatedDate());
        boardCreditCommitteePaper.setExistingOutstandingAtDate(facilityPaper.getCreatedDate());

        LOG.info("END : BCC Paper Init Details : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder mapApplicationFormDetails() {

        if (facilityPaper.getApplicationFormID() != null) {
            applicationForm = applicationFormDao.getOne(facilityPaper.getApplicationFormID());
            primaryBasicInformation = applicationForm.getPrimaryBasicInformation();
            boardCreditCommitteePaper.setBusinessProfile(primaryBasicInformation.getNatureOfBusiness());
            if (primaryBasicInformation.getCapitalIssued() != null) {
                boardCreditCommitteePaper.setStartedCapital(primaryBasicInformation.getCapitalIssued().toString());
            }

            for (AFRiskRate afRiskRate : primaryBasicInformation.getAfRiskRateSet()) {
                if (afRiskRate.getStatus() == AppsConstants.Status.ACT) {
                    BccRiskRatingYear bccRiskRatingYear = new BccRiskRatingYear();
                    bccRiskRatingYear.setRiskScore(Double.valueOf(afRiskRate.getScoring()));
                    bccRiskRatingYear.setRiskGrading(afRiskRate.getRiskGrading());
                    bccRiskRatingYear.setCreatedBy(credentialsDTO.getUserName());
                    bccRiskRatingYear.setCreatedDate(date);
                    bccRiskRatingYear.setStatus(afRiskRate.getStatus());
                    if (afRiskRate.getLastRated() != null) {
                        Integer year = CalendarUtil.getYear(afRiskRate.getLastRated());
                        Integer nextYear = year + 1;
                        bccRiskRatingYear.setRiskYear(year.toString().concat("/").concat(nextYear.toString()));
                        boardCreditCommitteePaper.addBccRiskRatingYear(bccRiskRatingYear);
                    }
                }
            }
        }

        return this;
    }

    public BCCCreateBuilder setCustomer() {

        for (CASCustomer CASCustomer : facilityPaper.getOrderedCasCustomerList()) {
            if (CASCustomer.getIsPrimary() == AppsConstants.YesNo.Y) {
                if (CASCustomer.getCustomer() != null) {
                    boardCreditCommitteePaper.setCustomerName(CASCustomer.getCustomer().getCustomerName());
                }
            }

            for (CASCustomerCribReport CASCustomerCribReport : CASCustomer.getOrderedCasCustomerCribReportSet()) {
                if (CASCustomerCribReport.getStatus() == AppsConstants.Status.ACT) {
                    BccCustomerCribDetail bccCustomerCribDetail = new BccCustomerCribDetail();
                    bccCustomerCribDetail.setCribIssueDate(CASCustomerCribReport.getCribDate());
                    bccCustomerCribDetail.setReportDate(CASCustomerCribReport.getCribDate());
                    bccCustomerCribDetail.setCribStatus(CASCustomerCribReport.getCribStatus());
                    if (CASCustomerCribReport.getCASCustomer() != null) {
                        bccCustomerCribDetail.setBorrower(CASCustomerCribReport.getCASCustomer().getCustomer().getCustomerName());
                    }
                    bccCustomerCribDetail.setRemark(CASCustomerCribReport.getRemark());
                    bccCustomerCribDetail.setCreatedDate(date);
                    bccCustomerCribDetail.setCreatedBy(credentialsDTO.getUserName());
                    bccCustomerCribDetail.setStatus(AppsConstants.Status.ACT);
                    bccCustomerCribDetail.setBoardCreditCommitteePaper(boardCreditCommitteePaper);
                    boardCreditCommitteePaper.addCustomerCribDetails(bccCustomerCribDetail);
                }
            }

            for (CASCustomerCribDetail CASCustomerCribDetail : CASCustomer.getCASCustomerCribDetailSet()) {
                if (CASCustomerCribDetail.getStatus() == AppsConstants.Status.ACT) {
                    BccCustomerCribDetail bccCustomerCribDetail = new BccCustomerCribDetail();
                    bccCustomerCribDetail.setCribIssueDate(CASCustomerCribDetail.getCribIssueDate());
                    bccCustomerCribDetail.setReportDate(CASCustomerCribDetail.getCribIssueDate());
                    bccCustomerCribDetail.setCribStatus(CASCustomerCribDetail.getCribStatus());
                    bccCustomerCribDetail.setBorrower(CASCustomer.getCustomer().getCustomerName());
                    bccCustomerCribDetail.setRemark(CASCustomerCribDetail.getRemark());
                    bccCustomerCribDetail.setCreatedDate(date);
                    bccCustomerCribDetail.setCreatedBy(credentialsDTO.getUserName());
                    bccCustomerCribDetail.setStatus(AppsConstants.Status.ACT);
                    bccCustomerCribDetail.setBoardCreditCommitteePaper(boardCreditCommitteePaper);
                    boardCreditCommitteePaper.addCustomerCribDetails(bccCustomerCribDetail);
                }
            }
        }

        LOG.info("END : BCC Paper customer set : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder mapFacilitiesData() {

        //BigDecimal totalProposedPlusExisting;
        BigDecimal totalProposed;

        BigDecimal totalOutstandingProposed = BigDecimal.ZERO;
        BigDecimal totalOutstandingExisting = BigDecimal.ZERO;
        BigDecimal totalOutStandingProposedPlusExisting;

        for (Facility facility : facilities) {
            BccFacility bccFacility = new BccFacility();
            if (facility.getIsNew() == AppsConstants.YesNo.Y) {
                totalOutstandingProposed = DecimalCalculator.add(totalOutstandingProposed, DecimalCalculator.getFormattedActualValue(facility.getOutstandingAmount()));
                bccFacility.setBccFacilityType(DomainConstants.BCCFacilityType.PROPOSED);
            } else {
                bccFacility.setBccFacilityType(DomainConstants.BCCFacilityType.EXISTING);
                totalOutstandingExisting = DecimalCalculator.add(totalOutstandingExisting, DecimalCalculator.getFormattedActualValue(facility.getOutstandingAmount()));
            }
            StringBuilder individualSecurities = new StringBuilder();
            for (FacilityFacilitySecurity facilityFacilitySecurity : facility.getFacilityFacilitySecurities()) {
                if (facilityFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {
                    if (facilityFacilitySecurity.getFacilitySecurity().getIsCommonSecurity() != AppsConstants.YesNo.Y) {
                        individualSecurities.append(facilityFacilitySecurity.getFacilitySecurity().getSecurityDetail());
                        individualSecurities.append("\n");
                        individualSecurities.append("\n");
                    }
                }
            }
            bccFacility.setSecurity(individualSecurities.toString());
            bccFacility.setFacilityID(facility.getFacilityID());
            bccFacility.setCreatedBy(credentialsDTO.getUserName());
            bccFacility.setCreatedDate(date);
            bccFacility.setStatus(AppsConstants.Status.ACT);
            bccFacility.setFacilityCurrency(facility.getFacilityCurrency());
            bccFacility.setAmount(facility.getFacilityAmount());
            bccFacility.setPurpose(facility.getPurpose());
            bccFacility.setOutstandingAsAt(facility.getOutstandingAmount().toString());
            bccFacility.setSettlementPlan(facility.getRepayment());
            bccFacility.setType(facility.getCreditFacilityTemplate().getCreditFacilityName());
            bccFacility.setIsOneOff(facility.getIsOneOff());
            bccFacility.setDirectFacility(facility.getDirectFacility());
            bccFacility.setSeriesOfLoans(facility.getSeriesOfLoans());
            bccFacility.setRevolving(facility.getRevolving());
            bccFacility.setReduction(facility.getReduction());
            bccFacility.setEnhancement(facility.getEnhancement());
            if (facility.getParentFacilityID() != null) {
                Facility parentFacility = facilities.stream().filter(f -> facility.getParentFacilityID().equals(f.getFacilityID())).findFirst().orElse(null);
                if (parentFacility != null) {
                    bccFacility.setParentFacilityID(parentFacility.getFacilityID());
                    bccFacility.setParentCreditFacilityName(parentFacility.getCreditFacilityTemplate().getCreditFacilityName());
                    bccFacility.setParentFacilityTypeName(parentFacility.getFacilityType());
                    bccFacility.setParentFacilityAmount(parentFacility.getFacilityAmount());
                    bccFacility.setParentFacilityCurrency(parentFacility.getFacilityCurrency());
                } else {
                    LOG.warn("WARNING : Invalid Parent Facility ID : {} : {}", facility.getParentFacilityID(), credentialsDTO.getUserID());
                }
            }
            bccFacility.setInterestRate(getInterestRatesAsOneString(facility));
            boardCreditCommitteePaper.addBccFacility(bccFacility);
        }

            if (facilityPaper.getIsCommittee().equals(AppsConstants.YesNo.Y)){
                totalProposed = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposureExisting()));
                boardCreditCommitteePaper.setExistingFacilityTotal(facilityPaper.getTotalExposureExisting());
            } else {
                totalProposed = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposurePrevious()));
                boardCreditCommitteePaper.setExistingFacilityTotal(facilityPaper.getTotalExposurePrevious());
            }

            boardCreditCommitteePaper.setProposedFacilityTotal(totalProposed);
            boardCreditCommitteePaper.setExistingPlusProposedTotal(facilityPaper.getTotalExposureNew());

            totalOutStandingProposedPlusExisting = DecimalCalculator.add(totalOutstandingExisting, totalOutstandingProposed);
            boardCreditCommitteePaper.setExistingOutstandingFacilityTotal(totalOutstandingExisting);
            boardCreditCommitteePaper.setProposedOutstandingFacilityTotal(totalOutstandingProposed);
            boardCreditCommitteePaper.setExistingOutstandingPlusProposedOutstandingTotal(totalOutStandingProposedPlusExisting);

        LOG.info("END : BCC Paper facilities mapped : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder facilityTotalCalculations() {

        Map<Integer, BigDecimal> parentFacilityIDWiseProposedFacilitiesAmounts = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseExistingFacilitiesAmounts = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseExistingOutstandingFacilitiesAmounts = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseExistingPlusProposedFacilitiesAmounts = new HashMap<>();

        BigDecimal totalProposed = BigDecimal.ZERO;
        BigDecimal totalExisting = BigDecimal.ZERO;
        BigDecimal totalExistingOutstanding = BigDecimal.ZERO;
        BigDecimal totalExistingPlusProposed = BigDecimal.ZERO;


        for (Facility facility : facilities) {
            if (facility.getIsNew() != null && facility.getIsNew().getBoolVal()) {
                //the Following Calculation is for Proposed facilities (New Facilities)
                if (facility.getParentFacilityID() == null) {
                    parentFacilityIDWiseProposedFacilitiesAmounts.put(facility.getFacilityID(), facility.getFacilityAmount());
                } else if (facility.getParentFacilityID() != null) {
                    parentFacilityIDWiseProposedFacilitiesAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                    parentFacilityIDWiseProposedFacilitiesAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseProposedFacilitiesAmounts.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
                }

            } else {
                //the Following Calculation is for Existing facilities with outstanding values as well
                if (facility.getParentFacilityID() == null) {
                    parentFacilityIDWiseExistingFacilitiesAmounts.put(facility.getFacilityID(), facility.getFacilityAmount());
                    parentFacilityIDWiseExistingOutstandingFacilitiesAmounts.put(facility.getFacilityID(), facility.getOutstandingAmount());
                } else if (facility.getParentFacilityID() != null) {
                    parentFacilityIDWiseExistingFacilitiesAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                    parentFacilityIDWiseExistingFacilitiesAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseExistingFacilitiesAmounts.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));

                    parentFacilityIDWiseExistingOutstandingFacilitiesAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                    parentFacilityIDWiseExistingOutstandingFacilitiesAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseExistingOutstandingFacilitiesAmounts.get(facility.getParentFacilityID()).max(facility.getOutstandingAmount()));
                }
            }

            if (facility.getParentFacilityID() == null) {
                parentFacilityIDWiseExistingPlusProposedFacilitiesAmounts.put(facility.getFacilityID(), facility.getFacilityAmount());
            } else {
                parentFacilityIDWiseExistingPlusProposedFacilitiesAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                parentFacilityIDWiseExistingPlusProposedFacilitiesAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseExistingPlusProposedFacilitiesAmounts.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
            }
        }

        for (Integer facilityID : parentFacilityIDWiseProposedFacilitiesAmounts.keySet()) {
            totalProposed = DecimalCalculator.add(totalProposed, parentFacilityIDWiseProposedFacilitiesAmounts.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseExistingFacilitiesAmounts.keySet()) {
            totalExisting = DecimalCalculator.add(totalExisting, parentFacilityIDWiseExistingFacilitiesAmounts.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseExistingOutstandingFacilitiesAmounts.keySet()) {
            totalExistingOutstanding = DecimalCalculator.add(totalExistingOutstanding, parentFacilityIDWiseExistingOutstandingFacilitiesAmounts.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseExistingPlusProposedFacilitiesAmounts.keySet()) {
            totalExistingPlusProposed = DecimalCalculator.add(totalExistingPlusProposed, parentFacilityIDWiseExistingPlusProposedFacilitiesAmounts.get(facilityID));
        }

        //boardCreditCommitteePaper.setProposedFacilityTotal(totalProposed);
        //boardCreditCommitteePaper.setExistingFacilityTotal(totalExisting);
        //boardCreditCommitteePaper.setExistingOutstandingFacilityTotal(totalExistingOutstanding);
        //boardCreditCommitteePaper.setExistingPlusProposedTotal(totalExistingPlusProposed);
        //boardCreditCommitteePaper.setExistingOutstandingPlusProposedOutstandingTotal(totalExistingOutstanding); // The proposed facilities do not have and outstanding values

        LOG.info("END : BCC Paper Facilities Exposure Calculations :: Total Proposed : {} , Total Existing : {} , Total Existing Outstanding : {} , Total Proposed Existing : {}  :: by {}", totalProposed, totalExisting, totalExistingOutstanding, totalExistingPlusProposed, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder mapCommonSecurities() {
        Map<Integer, String> securityIDWiseSecurityDetails = new HashMap<>();
        Map<Integer, StringBuilder> securityIDWiseFacilityDetails = new HashMap<>();
        Set<Facility> facilities = facilityPaper.getActiveFacility();

        for (Facility facility : facilities) {
            for (FacilityFacilitySecurity facilityFacilitySecurity : facility.getFacilityFacilitySecurities())
                if (facilityFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {
                    if (facilityFacilitySecurity.getFacilitySecurity().getIsCommonSecurity() == AppsConstants.YesNo.Y) {
                        securityIDWiseFacilityDetails.putIfAbsent(facilityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID(), new StringBuilder());
                        StringBuilder temp = securityIDWiseFacilityDetails.get(facilityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID());
                        if (StringUtils.isEmpty(temp)) {
                            temp.append(facility.getCreditFacilityTemplate().getCreditFacilityName()).append(" : ").append(facility.getFacilityRefCode()).append(" : ").append(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facility.getFacilityAmount()))).append(" Rs.Mn");
                        } else {
                            temp.append("\n").append(facility.getCreditFacilityTemplate().getCreditFacilityName()).append(" : ").append(facility.getFacilityRefCode()).append(" : ").append(NumberUtil.getFormattedAmount(NumberUtil.getMillionValue(facility.getFacilityAmount()))).append(" Rs.Mn");
                        }
                    }
                }
        }

        for (Facility facility : facilities) {
            for (FacilityFacilitySecurity facilityFacilitySecurity : facility.getFacilityFacilitySecurities())
                if (facilityFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {
                    if (facilityFacilitySecurity.getFacilitySecurity().getIsCommonSecurity() == AppsConstants.YesNo.Y) {
                        securityIDWiseSecurityDetails.putIfAbsent(facilityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID(), new StringBuilder().append(facilityFacilitySecurity.getFacilitySecurity().getSecurityCode()).append(" : ").append(facilityFacilitySecurity.getFacilitySecurity().getSecurityDetail()).toString());
                    }
                }
        }

        StringBuilder stringBuilder = new StringBuilder();
        for (Integer securityID : securityIDWiseSecurityDetails.keySet()) {
            stringBuilder.append(securityIDWiseFacilityDetails.get(securityID)).append("\n");
            stringBuilder.append(securityIDWiseSecurityDetails.get(securityID));
            stringBuilder.append("\n--------------------------------------------------------------------------------------------------\n");
        }

        boardCreditCommitteePaper.setCommonSecurityText(stringBuilder.toString());
        LOG.info("END : BCC Paper common securities mapped : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder mapDirectorDetails() {

        for (FPDirectorDetail fpDirectorDetail : facilityPaper.getFpDirectorDetailSet()) {
            if (fpDirectorDetail.getStatus() == AppsConstants.Status.ACT) {
                BccCompanyDirector bccCompanyDirector = new BccCompanyDirector();

                String directorFullName = fpDirectorDetail.getDirectorName() + " " + fpDirectorDetail.getFullName();

                bccCompanyDirector.setCompanyDirectorName(directorFullName);
                bccCompanyDirector.setAddress(fpDirectorDetail.getAddress());
                bccCompanyDirector.setNicOrBRN(fpDirectorDetail.getNic());
                bccCompanyDirector.setShareHolding(fpDirectorDetail.getShareHolding());
                bccCompanyDirector.setStatus(fpDirectorDetail.getStatus());
                bccCompanyDirector.setCreatedDate(date);
                bccCompanyDirector.setConstitutionType(fpDirectorDetail.getConstitutionType());
                bccCompanyDirector.setCreditCard(fpDirectorDetail.getCreditCard());
                bccCompanyDirector.setCreatedBy(credentialsDTO.getUserName());
                boardCreditCommitteePaper.addBccCompanyDirector(bccCompanyDirector);
            }
        }
        LOG.info("END : BCC Paper Directors mapped : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder mapShareholderDetails() {

        for (FPShareHolderDetail fpShareHolderDetail : facilityPaper.getFpShareHolderDetailSet()) {
            if (fpShareHolderDetail.getStatus() == AppsConstants.Status.ACT) {
                BccCompanyShareholder bccCompanyShareholder = new BccCompanyShareholder();

                bccCompanyShareholder.setCompanyShareholderName(fpShareHolderDetail.getShareHolderName());
                bccCompanyShareholder.setShareHolding(fpShareHolderDetail.getShareHolding());
                bccCompanyShareholder.setStatus(fpShareHolderDetail.getStatus());
                bccCompanyShareholder.setCreatedDate(date);
                bccCompanyShareholder.setCreatedBy(credentialsDTO.getUserName());
                boardCreditCommitteePaper.addBccCompanyShareholder(bccCompanyShareholder);
            }
        }
        LOG.info("END : BCC Paper Shareholder mapped : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder mapRiskRatingYear() {

        for (CASCustomer casCustomer : facilityPaper.getCASCustomerSet()) {
            for (CustomerRatings  customerRatings : casCustomer.getCustomerRatingsSet()) {
                BccRiskRatingYear bccRiskRatingYear = new BccRiskRatingYear();
                bccRiskRatingYear.setRiskGrading(customerRatings.getRiskGrading());
                bccRiskRatingYear.setRiskScore(customerRatings.getRiskScore());
                bccRiskRatingYear.setRiskYear(CalendarUtil.getFormattedYear(facilityPaper.getCreatedDate()));
                bccRiskRatingYear.setStatus(AppsConstants.Status.ACT);
                bccRiskRatingYear.setCreatedBy(credentialsDTO.getUserName());
                bccRiskRatingYear.setCreatedDate(date);

                boardCreditCommitteePaper.addBccRiskRatingYear(bccRiskRatingYear);
            }
        }
        LOG.info("END : BCC Paper Risk Ratings mapped : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder setExposureCompany() {
        BccExposureCompany bccExposureCompany = new BccExposureCompany();
        bccExposureCompany.setStatus(AppsConstants.Status.ACT);
        bccExposureCompany.setCreatedBy(credentialsDTO.getUserName());
        bccExposureCompany.setCreatedDate(date);
        bccExposureCompany.setBoardCreditCommitteePaper(boardCreditCommitteePaper);

        BigDecimal existingExposureDirect = BigDecimal.ZERO;
        BigDecimal existingExposureIndirect = BigDecimal.ZERO;
        BigDecimal existingExposureTotal;

        BigDecimal additionalExposureDirect = BigDecimal.ZERO;
        BigDecimal additionalExposureIndirect = BigDecimal.ZERO;
        BigDecimal additionalExposureTotal;

        BigDecimal totalExposureDirect;
        BigDecimal totalExposureIndirect;
        BigDecimal totalExposureTotal;

        Map<Integer, BigDecimal> parentFacilityIDWiseExistingExposureDirectAmounts = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseExistingExposureIndirectAmounts = new HashMap<>();

        Map<Integer, BigDecimal> parentFacilityIDWiseAdditionalExposureDirectAmounts = new HashMap<>();
        Map<Integer, BigDecimal> parentFacilityIDWiseAdditionalExposureIndirectAmounts = new HashMap<>();


        for (Facility facility : facilities) {
            if (facility.getIsNew() != null && facility.getIsNew().getBoolVal()) {
                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {

                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseAdditionalExposureDirectAmounts.put(facility.getFacilityID(), facility.getFacilityAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseAdditionalExposureDirectAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseAdditionalExposureDirectAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseAdditionalExposureDirectAmounts.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
                    }

                } else {
                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseAdditionalExposureIndirectAmounts.put(facility.getFacilityID(), facility.getFacilityAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseAdditionalExposureIndirectAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseAdditionalExposureIndirectAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseAdditionalExposureIndirectAmounts.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
                    }
                }
            } else {
                if (facility.getDirectFacility() != null && facility.getDirectFacility().getBoolVal()) {

                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseExistingExposureDirectAmounts.put(facility.getFacilityID(), facility.getFacilityAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseExistingExposureDirectAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseExistingExposureDirectAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseExistingExposureDirectAmounts.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
                    }
                } else {
                    if (facility.getParentFacilityID() == null) {
                        parentFacilityIDWiseExistingExposureIndirectAmounts.put(facility.getFacilityID(), facility.getFacilityAmount());
                    } else if (facility.getParentFacilityID() != null) {
                        parentFacilityIDWiseExistingExposureIndirectAmounts.computeIfAbsent(facility.getParentFacilityID(), k -> DecimalCalculator.getDefaultZero());
                        parentFacilityIDWiseExistingExposureIndirectAmounts.put(facility.getParentFacilityID(), parentFacilityIDWiseExistingExposureIndirectAmounts.get(facility.getParentFacilityID()).max(facility.getFacilityAmount()));
                    }
                }
            }
        }

        for (Integer facilityID : parentFacilityIDWiseAdditionalExposureDirectAmounts.keySet()) {
            additionalExposureDirect = DecimalCalculator.add(additionalExposureDirect, parentFacilityIDWiseAdditionalExposureDirectAmounts.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseAdditionalExposureIndirectAmounts.keySet()) {
            additionalExposureIndirect = DecimalCalculator.add(additionalExposureIndirect, parentFacilityIDWiseAdditionalExposureIndirectAmounts.get(facilityID));
        }

        additionalExposureTotal = DecimalCalculator.add(additionalExposureDirect, additionalExposureIndirect);

        for (Integer facilityID : parentFacilityIDWiseExistingExposureDirectAmounts.keySet()) {
            existingExposureDirect = DecimalCalculator.add(existingExposureDirect, parentFacilityIDWiseExistingExposureDirectAmounts.get(facilityID));
        }

        for (Integer facilityID : parentFacilityIDWiseExistingExposureIndirectAmounts.keySet()) {
            existingExposureIndirect = DecimalCalculator.add(existingExposureIndirect, parentFacilityIDWiseExistingExposureIndirectAmounts.get(facilityID));
        }

        existingExposureTotal = DecimalCalculator.add(existingExposureDirect, existingExposureIndirect);

        totalExposureDirect = DecimalCalculator.add(additionalExposureDirect, existingExposureDirect);
        totalExposureIndirect = DecimalCalculator.add(additionalExposureIndirect, existingExposureIndirect);
        totalExposureTotal = DecimalCalculator.add(existingExposureTotal, additionalExposureTotal);

        bccExposureCompany.setExistingExposureDirect(existingExposureDirect);
        bccExposureCompany.setExistingExposureIndirect(existingExposureIndirect);
        bccExposureCompany.setExistingExposureTotal(existingExposureTotal);
        bccExposureCompany.setAdditionalExposureDirect(additionalExposureDirect);
        bccExposureCompany.setAdditionalExposureIndirect(additionalExposureIndirect);
        bccExposureCompany.setAdditionalExposureTotal(additionalExposureTotal);
        bccExposureCompany.setTotalExposureDirect(totalExposureDirect);
        bccExposureCompany.setTotalExposureIndirect(totalExposureIndirect);
        bccExposureCompany.setTotalExposureTotal(totalExposureTotal);
        boardCreditCommitteePaper.setBccExposureCompany(bccExposureCompany);
        LOG.info("END : BCC Paper set Exposure Company  : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder setExposureGroup() {
        BccExposureGroup bccExposureGroup = new BccExposureGroup();
        bccExposureGroup.setStatus(AppsConstants.Status.ACT);
        bccExposureGroup.setCreatedBy(credentialsDTO.getUserName());
        bccExposureGroup.setCreatedDate(date);
        bccExposureGroup.setBoardCreditCommitteePaper(boardCreditCommitteePaper);

        bccExposureGroup.setExistingExposureDirect(facilityPaper.getGroupTotalDirectExposurePrevious());
        bccExposureGroup.setExistingExposureIndirect(facilityPaper.getGroupTotalIndirectExposurePrevious());
        bccExposureGroup.setExistingExposureTotal(facilityPaper.getGroupTotalExposurePrevious());
        bccExposureGroup.setAdditionalExposureDirect(facilityPaper.getGroupTotalDirectExposureNew());
        bccExposureGroup.setAdditionalExposureIndirect(facilityPaper.getGroupTotalIndirectExposureNew());
        bccExposureGroup.setAdditionalExposureTotal(facilityPaper.getGroupTotalExposureNew());
        bccExposureGroup.setTotalExposureDirect(DecimalCalculator.add(facilityPaper.getGroupTotalDirectExposurePrevious(), facilityPaper.getGroupTotalDirectExposureNew()));
        bccExposureGroup.setTotalExposureIndirect(DecimalCalculator.add(facilityPaper.getGroupTotalIndirectExposurePrevious(), facilityPaper.getGroupTotalIndirectExposureNew()));
        bccExposureGroup.setTotalExposureTotal(DecimalCalculator.add(facilityPaper.getGroupTotalExposurePrevious(), facilityPaper.getGroupTotalExposureNew()));

        boardCreditCommitteePaper.setBccExposureGroup(bccExposureGroup);
        LOG.info("END : BCC Paper set Exposure Group  : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder updateFacilityPaper() {
        facilityPaper.setIsBccCreated(AppsConstants.YesNo.Y);
        boardCreditCommitteePaper.setFacilityPaper(facilityPaper);
        return this;
    }

    public BCCCreateBuilder generateAndSavePDFReport() throws AppsException {
        boardCreditCommitteePaper = bccPaperDao.saveAndFlush(boardCreditCommitteePaper);
        BCCReportCreator contentCreator = new BCCReportCreator(boardCreditCommitteePaper);
        contentCreator.setEnvironment(this.environment);
        contentCreator.setTemplateEngine(this.templateEngine);
        if (this.branchName != null) {
            contentCreator.setBranchName(this.branchName);
        }
        String pdfContent = contentCreator.getPdfContent();
        boardCreditCommitteePaper.setPdfReport(pdfContent);
        LOG.info("END : BCC Paper PDF Generated : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public void setApplicationFormDao(ApplicationFormDao applicationFormDao) {
        this.applicationFormDao = applicationFormDao;
    }

    private String getInterestRatesAsOneString(Facility facility) {
        StringBuilder stringBuilder = new StringBuilder();
        for (FacilityInterestRate facilityInterestRate : facility.getFacilityInterestRates()) {
            if (facilityInterestRate.getStatus() == AppsConstants.Status.ACT) {
                if (facilityInterestRate.getValue() != null) {
                    stringBuilder.append(facilityInterestRate.getValue());
                }
                if (facilityInterestRate.getUserComment() != null) {
                    stringBuilder.append(" : ").append(facilityInterestRate.getUserComment());
                }
                stringBuilder.append("\n\n");
            }
        }
        return stringBuilder.toString();
    }

    public String getBccPaperRefNumber() {
        return bccPaperRefNumber;
    }

    public void setBccPaperRefNumber(String bccPaperRefNumber) {
        this.bccPaperRefNumber = bccPaperRefNumber;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public BCCCreateBuilder setFPExposureCompany(){
        BccExposureCompany bccExposureCompany = new BccExposureCompany();
        bccExposureCompany.setStatus(AppsConstants.Status.ACT);
        bccExposureCompany.setCreatedBy(credentialsDTO.getUserName());
        bccExposureCompany.setCreatedDate(date);
        bccExposureCompany.setBoardCreditCommitteePaper(boardCreditCommitteePaper);

        BigDecimal additionalExposureDirect;
        BigDecimal additionalExposureIndirect;
        BigDecimal additionalExposureTotal;

        if (facilityPaper.getIsCommittee().equals(AppsConstants.YesNo.Y)){
            bccExposureCompany.setExistingExposureDirect(facilityPaper.getTotalDirectExposureExisting());
            bccExposureCompany.setExistingExposureIndirect(facilityPaper.getTotalIndirectExposureExisting());
            bccExposureCompany.setExistingExposureTotal(facilityPaper.getTotalExposureExisting());

            additionalExposureDirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalDirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalDirectExposureExisting()));
            additionalExposureIndirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalIndirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalIndirectExposureExisting()));
            additionalExposureTotal = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposureExisting()));
        }else {
            bccExposureCompany.setExistingExposureDirect(facilityPaper.getTotalDirectExposurePrevious());
            bccExposureCompany.setExistingExposureIndirect(facilityPaper.getTotalIndirectExposurePrevious());
            bccExposureCompany.setExistingExposureTotal(facilityPaper.getTotalExposurePrevious());

            additionalExposureDirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalDirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalDirectExposurePrevious()));
            additionalExposureIndirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalIndirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalIndirectExposurePrevious()));
            additionalExposureTotal = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getTotalExposurePrevious()));
        }

        bccExposureCompany.setTotalExposureDirect(facilityPaper.getTotalDirectExposureNew());
        bccExposureCompany.setTotalExposureIndirect(facilityPaper.getTotalIndirectExposureNew());
        bccExposureCompany.setTotalExposureTotal(facilityPaper.getTotalExposureNew());

        bccExposureCompany.setAdditionalExposureDirect(additionalExposureDirect);
        bccExposureCompany.setAdditionalExposureIndirect(additionalExposureIndirect);
        bccExposureCompany.setAdditionalExposureTotal(additionalExposureTotal);

        boardCreditCommitteePaper.setBccExposureCompany(bccExposureCompany);
        LOG.info("END : BCC Paper set Exposure Company  : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }

    public BCCCreateBuilder setFPExposureGroup() {
        BccExposureGroup bccExposureGroup = new BccExposureGroup();
        bccExposureGroup.setStatus(AppsConstants.Status.ACT);
        bccExposureGroup.setCreatedBy(credentialsDTO.getUserName());
        bccExposureGroup.setCreatedDate(date);
        bccExposureGroup.setBoardCreditCommitteePaper(boardCreditCommitteePaper);

        BigDecimal additionalExposureDirect;
        BigDecimal additionalExposureIndirect;
        BigDecimal additionalExposureTotal;

        if (facilityPaper.getIsCommittee().equals(AppsConstants.YesNo.Y)){
            bccExposureGroup.setExistingExposureDirect(facilityPaper.getGroupTotalDirectExposureExisting());
            bccExposureGroup.setExistingExposureIndirect(facilityPaper.getGroupTotalIndirectExposureExisting());
            bccExposureGroup.setExistingExposureTotal(facilityPaper.getGroupTotalExposureExisting());

            additionalExposureDirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalDirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalDirectExposureExisting()));
            additionalExposureIndirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalIndirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalIndirectExposureExisting()));
            additionalExposureTotal = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalExposureExisting()));
        } else {
            bccExposureGroup.setExistingExposureDirect(facilityPaper.getGroupTotalDirectExposurePrevious());
            bccExposureGroup.setExistingExposureIndirect(facilityPaper.getGroupTotalIndirectExposurePrevious());
            bccExposureGroup.setExistingExposureTotal(facilityPaper.getGroupTotalExposurePrevious());

            additionalExposureDirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalDirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalDirectExposurePrevious()));
            additionalExposureIndirect = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalIndirectExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalIndirectExposurePrevious()));
            additionalExposureTotal = DecimalCalculator.subtract(DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalExposureNew()), DecimalCalculator.getFormattedActualValue(facilityPaper.getGroupTotalExposurePrevious()));
        }

        bccExposureGroup.setTotalExposureDirect(facilityPaper.getGroupTotalDirectExposureNew());
        bccExposureGroup.setTotalExposureIndirect(facilityPaper.getGroupTotalIndirectExposureNew());
        bccExposureGroup.setTotalExposureTotal(facilityPaper.getGroupTotalExposureNew());

        bccExposureGroup.setAdditionalExposureDirect(additionalExposureDirect);
        bccExposureGroup.setAdditionalExposureIndirect(additionalExposureIndirect);
        bccExposureGroup.setAdditionalExposureTotal(additionalExposureTotal);

         boardCreditCommitteePaper.setBccExposureGroup(bccExposureGroup);
        LOG.info("END : BCC Paper set Exposure Group  : {} : {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());
        return this;
    }
}
