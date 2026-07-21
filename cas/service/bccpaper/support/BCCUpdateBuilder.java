package com.itechro.cas.service.bccpaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.bccpaper.*;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.bccpaper.*;
import com.itechro.cas.model.dto.bccpaper.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class BCCUpdateBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(BCCUpdateBuilder.class);

    private BCCPaperDao bccPaperDao;

    private BCCRiskRatingYearDao bccRiskRatingYearDao;

    private BCCCompanyDirectorDao bccCompanyDirectorDao;

    private BCCFacilityDao bccFacilityDao;

    private BccCustomerCribDetailDao bccCustomerCribDetailDao;

    private BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO;

    private BoardCreditCommitteePaper boardCreditCommitteePaper;

    private Date date = new Date();

    private CredentialsDTO credentialsDTO;

    public BCCUpdateBuilder() {
    }

    public BCCUpdateBuilder(BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO, CredentialsDTO credentialsDTO) {
        this.boardCreditCommitteePaperDTO = boardCreditCommitteePaperDTO;
        this.credentialsDTO = credentialsDTO;
    }

    public void setBccPaperDao(BCCPaperDao bccPaperDao) {
        this.bccPaperDao = bccPaperDao;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public void setBccRiskRatingYearDao(BCCRiskRatingYearDao bccRiskRatingYearDao) {
        this.bccRiskRatingYearDao = bccRiskRatingYearDao;
    }

    public void setBccCompanyDirectorDao(BCCCompanyDirectorDao bccCompanyDirectorDao) {
        this.bccCompanyDirectorDao = bccCompanyDirectorDao;
    }

    public void setBccCustomerCribDetailDao(BccCustomerCribDetailDao bccCustomerCribDetailDao) {
        this.bccCustomerCribDetailDao = bccCustomerCribDetailDao;
    }

    public void setBccFacilityDao(BCCFacilityDao bccFacilityDao) {
        this.bccFacilityDao = bccFacilityDao;
    }

    public BoardCreditCommitteePaper getBoardCreditCommitteePaper() {
        return boardCreditCommitteePaper;
    }

    public BCCUpdateBuilder init() throws AppsException {

        if (boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID() != null) {
            boardCreditCommitteePaper = bccPaperDao.getOne(boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID());
            boardCreditCommitteePaper.setModifiedBy(credentialsDTO.getUserName());
            boardCreditCommitteePaper.setModifiedDate(date);
        } else {
            LOG.error("ERROR : BCC Paper Not found {}", boardCreditCommitteePaperDTO);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_BCC_PAPER_NOT_FOUND);
        }
        return this;
    }


    public BCCUpdateBuilder updateBasicInfo() throws AppsException {
        boardCreditCommitteePaper.setCustomerName(boardCreditCommitteePaperDTO.getCustomerName());
        boardCreditCommitteePaper.setBranchCode(boardCreditCommitteePaperDTO.getBranchCode());
        boardCreditCommitteePaper.setBusinessProfile(boardCreditCommitteePaperDTO.getBusinessProfile());
        boardCreditCommitteePaper.setStartedCapital(boardCreditCommitteePaperDTO.getStartedCapital());
        boardCreditCommitteePaper.setNote(boardCreditCommitteePaperDTO.getNote());
        boardCreditCommitteePaper.setPdfReport(boardCreditCommitteePaperDTO.getPdfReport());
        LOG.info("END : BCC paper: {}  basic info updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }

    public BCCUpdateBuilder updateRiskRatingYear() throws AppsException {
        for (BccRiskRatingYearDTO bccRiskRatingYearDTO : boardCreditCommitteePaperDTO.getBccRiskRatingYearDTOS()) {
            boolean isNew = bccRiskRatingYearDTO.getBccRiskRatingYearID() == null;
            BccRiskRatingYear bccRiskRatingYear;
            if (isNew) {
                if (bccRiskRatingYearDTO.getRiskGrading() == null) {
                    LOG.error("ERROR : BCC paper: {} Empty risk rating year updated by : {}", bccRiskRatingYearDTO, credentialsDTO.getUserID());
                    continue;
                }
                bccRiskRatingYear = new BccRiskRatingYear();
                bccRiskRatingYear.setCreatedBy(credentialsDTO.getUserName());
                bccRiskRatingYear.setCreatedDate(date);
            } else {
                bccRiskRatingYear = bccRiskRatingYearDao.getOne(bccRiskRatingYearDTO.getBccRiskRatingYearID());
                bccRiskRatingYear.setModifiedDate(date);
                bccRiskRatingYear.setModifiedBy(credentialsDTO.getUserName());
            }
            bccRiskRatingYear.setRiskGrading(bccRiskRatingYearDTO.getRiskGrading());
            bccRiskRatingYear.setRiskScore(bccRiskRatingYearDTO.getRiskScore());
            bccRiskRatingYear.setRiskYear(bccRiskRatingYearDTO.getRiskYear());
            bccRiskRatingYear.setStatus(bccRiskRatingYearDTO.getStatus());
            boardCreditCommitteePaper.addBccRiskRatingYear(bccRiskRatingYear);
        }

        LOG.info("END : BCC paper: {} risk rating year updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }

    public BCCUpdateBuilder updateCustomerCribDetails() throws AppsException {
        for (BccCustomerCribDetailDTO bccCustomerCribDetailDTO : boardCreditCommitteePaperDTO.getBccCustomerCribDetailDTOS()) {
            boolean isNew = bccCustomerCribDetailDTO.getBccCustomerCribDetailsID() == null;
            BccCustomerCribDetail bccCustomerCribDetail;
            if (isNew) {
                if (bccCustomerCribDetailDTO.getBorrower() == null) {
                    LOG.error("ERROR : BCC paper: {} Empty B0rrower updated by : {}", bccCustomerCribDetailDTO, credentialsDTO.getUserID());
                    continue;
                }
                bccCustomerCribDetail = new BccCustomerCribDetail();
                bccCustomerCribDetail.setCreatedBy(credentialsDTO.getUserName());
                bccCustomerCribDetail.setCreatedDate(date);
            } else {
                bccCustomerCribDetail = bccCustomerCribDetailDao.getOne(bccCustomerCribDetailDTO.getBccCustomerCribDetailsID());
                bccCustomerCribDetail.setModifiedDate(date);
                bccCustomerCribDetail.setModifiedBy(credentialsDTO.getUserName());
            }
            bccCustomerCribDetail.setBorrower(bccCustomerCribDetailDTO.getBorrower());
            bccCustomerCribDetail.setCribStatus(bccCustomerCribDetailDTO.getCribStatus());
            if (StringUtils.isNotBlank(bccCustomerCribDetailDTO.getReportDateStr())) {
                bccCustomerCribDetail.setReportDate(CalendarUtil.getDefaultParsedDateOnly(bccCustomerCribDetailDTO.getReportDateStr()));
            }

            bccCustomerCribDetail.setRemark(bccCustomerCribDetailDTO.getRemark());
            bccCustomerCribDetail.setStatus(bccCustomerCribDetailDTO.getStatus());
            boardCreditCommitteePaper.addCustomerCribDetails(bccCustomerCribDetail);
        }

        LOG.info("END : BCC paper: {} customer crib detail updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }


    public BCCUpdateBuilder updateCompanyDirectors() throws AppsException {
        for (BccCompanyDirectorDTO bccCompanyDirectorDTO : boardCreditCommitteePaperDTO.getBccCompanyDirectorDTOS()) {
            boolean isNew = bccCompanyDirectorDTO.getBccCompanyDirectorID() == null;
            BccCompanyDirector bccCompanyDirector;
            if (isNew) {
                if (bccCompanyDirectorDTO.getCompanyDirectorName() == null) {
                    LOG.error("ERROR : BCC paper: {} Empty Company Directory updated by : {}", bccCompanyDirectorDTO, credentialsDTO.getUserID());
                    continue;
                }
                bccCompanyDirector = new BccCompanyDirector();
                bccCompanyDirector.setCreatedBy(credentialsDTO.getUserName());
                bccCompanyDirector.setCreatedDate(date);
            } else {
                bccCompanyDirector = bccCompanyDirectorDao.getOne(bccCompanyDirectorDTO.getBccCompanyDirectorID());
                bccCompanyDirector.setModifiedDate(date);
                bccCompanyDirector.setModifiedBy(credentialsDTO.getUserName());
            }
            bccCompanyDirector.setCompanyDirectorName(bccCompanyDirectorDTO.getCompanyDirectorName());
            bccCompanyDirector.setAge(bccCompanyDirectorDTO.getAge());
            bccCompanyDirector.setNicOrBRN(bccCompanyDirectorDTO.getNicOrBRN());
            bccCompanyDirector.setAddress(bccCompanyDirectorDTO.getAddress());
            bccCompanyDirector.setShareHolding(bccCompanyDirectorDTO.getShareHolding());
            bccCompanyDirector.setConstitutionType(bccCompanyDirectorDTO.getConstitutionType());
            bccCompanyDirector.setCreditCard(bccCompanyDirectorDTO.getCreditCard());
            bccCompanyDirector.setStatus(bccCompanyDirectorDTO.getStatus());

            boardCreditCommitteePaper.addBccCompanyDirector(bccCompanyDirector);
        }
        LOG.info("END : BCC paper: {} company director updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }


    public BCCUpdateBuilder updateStrength() throws AppsException {
        boardCreditCommitteePaper.setFinancialStability(boardCreditCommitteePaperDTO.getFinancialStability());
        boardCreditCommitteePaper.setAuditorName(boardCreditCommitteePaperDTO.getAuditorName());

        boardCreditCommitteePaper.setFinancialYearOne(boardCreditCommitteePaperDTO.getFinancialYearOne());
        boardCreditCommitteePaper.setFinancialYearOneFinancial(boardCreditCommitteePaperDTO.getFinancialYearOneFinancial());
        boardCreditCommitteePaper.setFinancialYearTwo(boardCreditCommitteePaperDTO.getFinancialYearTwo());
        boardCreditCommitteePaper.setFinancialYearTwoFinancial(boardCreditCommitteePaperDTO.getFinancialYearTwoFinancial());
        boardCreditCommitteePaper.setIncomeAmountYearOne(boardCreditCommitteePaperDTO.getIncomeAmountYearOne());
        boardCreditCommitteePaper.setIncomeGrowthYearOne(boardCreditCommitteePaperDTO.getIncomeGrowthYearOne());
        boardCreditCommitteePaper.setIncomeAmountYearTwo(boardCreditCommitteePaperDTO.getIncomeAmountYearTwo());
        boardCreditCommitteePaper.setIncomeGrowthYearTwo(boardCreditCommitteePaperDTO.getIncomeGrowthYearTwo());
        boardCreditCommitteePaper.setProfitAfterTaxAmountYearOne(boardCreditCommitteePaperDTO.getProfitAfterTaxAmountYearOne());
        boardCreditCommitteePaper.setProfitAfterTaxGrowthYearOne(boardCreditCommitteePaperDTO.getProfitAfterTaxGrowthYearOne());
        boardCreditCommitteePaper.setProfitAfterTaxAmountYearTwo(boardCreditCommitteePaperDTO.getProfitAfterTaxAmountYearTwo());
        boardCreditCommitteePaper.setProfitAfterTaxGrowthYearTwo(boardCreditCommitteePaperDTO.getProfitAfterTaxGrowthYearTwo());

        boardCreditCommitteePaper.setNetInterestMargin(boardCreditCommitteePaperDTO.getNetInterestMargin());
        boardCreditCommitteePaper.setGrossNPL(boardCreditCommitteePaperDTO.getGrossNPL());
        boardCreditCommitteePaper.setInterestCover(boardCreditCommitteePaperDTO.getInterestCover());
        boardCreditCommitteePaper.setGearing(boardCreditCommitteePaperDTO.getGearing());
        boardCreditCommitteePaper.setMarketPosition(boardCreditCommitteePaperDTO.getMarketPosition());
        boardCreditCommitteePaper.setBusinessManagementStrength(boardCreditCommitteePaperDTO.getBusinessManagementStrength());
        boardCreditCommitteePaper.setBusinessManagementStrength(boardCreditCommitteePaperDTO.getBusinessManagementStrength());
        boardCreditCommitteePaper.setSecurityCover(boardCreditCommitteePaperDTO.getSecurityCover());


        LOG.info("END : BCC paper: {} strength info updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }

    public BCCUpdateBuilder updateCostOfFund() throws AppsException {

        boardCreditCommitteePaper.setCostOfFundMonth(boardCreditCommitteePaperDTO.getCostOfFundMonth());
        boardCreditCommitteePaper.setCostOfFundYear(boardCreditCommitteePaperDTO.getCostOfFundYear());
        boardCreditCommitteePaper.setMonthlyCostOfFundsLkr(boardCreditCommitteePaperDTO.getMonthlyCostOfFundsLkr());
        boardCreditCommitteePaper.setMonthlyCostOfFundsFcy(boardCreditCommitteePaperDTO.getMonthlyCostOfFundsFcy());
        boardCreditCommitteePaper.setCumulativeCostOfFundsLkr(boardCreditCommitteePaperDTO.getCumulativeCostOfFundsLkr());
        boardCreditCommitteePaper.setCumulativeCostOfFundsFcy(boardCreditCommitteePaperDTO.getCumulativeCostOfFundsFcy());
        boardCreditCommitteePaper.setIncrementalCostOfFundsLkr(boardCreditCommitteePaperDTO.getIncrementalCostOfFundsLkr());
        boardCreditCommitteePaper.setIncrementalCostOfFundsFcy(boardCreditCommitteePaperDTO.getIncrementalCostOfFundsFcy());
        boardCreditCommitteePaper.setRecommendation(boardCreditCommitteePaperDTO.getRecommendation());
        boardCreditCommitteePaper.setRecommendationRemark(boardCreditCommitteePaperDTO.getRecommendationRemark());
        boardCreditCommitteePaper.setRiskBasedPricing(boardCreditCommitteePaperDTO.getRiskBasedPricing());
        boardCreditCommitteePaper.setJustification(boardCreditCommitteePaperDTO.getJustification());

        LOG.info("END : BCC paper: {} cost of fund updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }


    public BCCUpdateBuilder updateBCCFacilities() throws AppsException {

        for (BccFacilityDTO bccFacilityDTO : boardCreditCommitteePaperDTO.getBccFacilityDTOS()) {

            boolean isNew = bccFacilityDTO.getBccFacilityID() == null;
            BccFacility bccFacility;
            if (isNew) {
                bccFacility = new BccFacility();
                bccFacility.setCreatedBy(credentialsDTO.getUserName());
                bccFacility.setCreatedDate(date);
                bccFacility.setStatus(AppsConstants.Status.ACT);
            } else {
                bccFacility = bccFacilityDao.getOne(bccFacilityDTO.getBccFacilityID());
                bccFacility.setModifiedBy(credentialsDTO.getUserName());
                bccFacility.setModifiedDate(date);
            }
            bccFacility.setType(bccFacilityDTO.getType());
            bccFacility.setDateGranted(bccFacilityDTO.getDateGranted());
            bccFacility.setAmount(bccFacilityDTO.getAmount());
            bccFacility.setInterestRate(bccFacilityDTO.getInterestRate());
            bccFacility.setPurpose(bccFacilityDTO.getPurpose());
            bccFacility.setOutstandingAsAt(bccFacilityDTO.getOutstandingAsAt());
            bccFacility.setSettlementDate(bccFacilityDTO.getSettlementDate());
            bccFacility.setSettlementPlan(bccFacilityDTO.getSettlementPlan());
            bccFacility.setSecurity(bccFacilityDTO.getSecurity());
            if (bccFacilityDTO.getStatus() != null) {
                bccFacility.setStatus(bccFacilityDTO.getStatus());
            }

            if (bccFacilityDTO.getBccFacilityType() != null) {
                bccFacility.setBccFacilityType(bccFacilityDTO.getBccFacilityType());
            }
            boardCreditCommitteePaper.addBccFacility(bccFacility);
        }

        if (StringUtils.isNotBlank(boardCreditCommitteePaperDTO.getFacilityDateOfApproval())) {
            boardCreditCommitteePaper.setFacilityDateOfApproval(CalendarUtil.getDefaultParsedDateOnly(boardCreditCommitteePaperDTO.getFacilityDateOfApproval()));
        }

        if (StringUtils.isNotBlank(boardCreditCommitteePaperDTO.getProposedOutstandingAtDate())) {
            boardCreditCommitteePaper.setProposedOutstandingAtDate(CalendarUtil.getDefaultParsedDateOnly(boardCreditCommitteePaperDTO.getProposedOutstandingAtDate()));
        }

        if (StringUtils.isNotBlank(boardCreditCommitteePaperDTO.getExistingOutstandingAtDate())) {
            boardCreditCommitteePaper.setExistingOutstandingAtDate(CalendarUtil.getDefaultParsedDateOnly(boardCreditCommitteePaperDTO.getExistingOutstandingAtDate()));
        }

        boardCreditCommitteePaper.setProposedFacilityTotal(boardCreditCommitteePaperDTO.getProposedFacilityTotal());
        boardCreditCommitteePaper.setExistingFacilityTotal(boardCreditCommitteePaperDTO.getExistingFacilityTotal());
        boardCreditCommitteePaper.setExistingPlusProposedTotal(boardCreditCommitteePaperDTO.getExistingPlusProposedTotal());

        LOG.info("END : BCC paper: {} bcc facilities updated : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());

        return this;
    }

    public BCCUpdateBuilder updateCommonSecurityText() throws AppsException {
        boardCreditCommitteePaper.setCommonSecurityText(boardCreditCommitteePaperDTO.getCommonSecurityText());
        LOG.info("END : BCC paper: {} common security updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }

    public BCCUpdateBuilder updateBCCExposureCompany() throws AppsException {
        BccExposureCompany bccExposureCompany = boardCreditCommitteePaper.getBccExposureCompany();
        if (bccExposureCompany == null) {
            bccExposureCompany = new BccExposureCompany();
            bccExposureCompany.setCreatedDate(date);
            bccExposureCompany.setCreatedBy(credentialsDTO.getUserName());
            boardCreditCommitteePaper.setBccExposureCompany(bccExposureCompany);
            bccExposureCompany.setBoardCreditCommitteePaper(boardCreditCommitteePaper);
        }
        BccExposureCompanyDTO bccExposureCompanyDTO = boardCreditCommitteePaperDTO.getBccExposureCompanyDTO();
        if (bccExposureCompanyDTO != null) {
            bccExposureCompany.setExistingExposureDirect(bccExposureCompanyDTO.getExistingExposureDirect());
            bccExposureCompany.setExistingExposureIndirect(bccExposureCompanyDTO.getExistingExposureIndirect());
            bccExposureCompany.setExistingExposureTotal(bccExposureCompanyDTO.getExistingExposureTotal());
            bccExposureCompany.setAdditionalExposureDirect(bccExposureCompanyDTO.getAdditionalExposureDirect());
            bccExposureCompany.setAdditionalExposureIndirect(bccExposureCompanyDTO.getAdditionalExposureIndirect());
            bccExposureCompany.setAdditionalExposureTotal(bccExposureCompanyDTO.getAdditionalExposureTotal());
            bccExposureCompany.setTotalExposureDirect(bccExposureCompanyDTO.getTotalExposureDirect());
            bccExposureCompany.setTotalExposureIndirect(bccExposureCompanyDTO.getTotalExposureIndirect());
            bccExposureCompany.setTotalExposureTotal(bccExposureCompanyDTO.getTotalExposureTotal());
            bccExposureCompany.setType(bccExposureCompanyDTO.getType());
            bccExposureCompany.setExposureSecuredBy(bccExposureCompanyDTO.getExposureSecuredBy());
            bccExposureCompany.setApprovedFSV(bccExposureCompanyDTO.getApprovedFSV());
            bccExposureCompany.setAgainstApprovedFSV(bccExposureCompanyDTO.getAgainstApprovedFSV());
            bccExposureCompany.setAgainstTotalExposure(bccExposureCompanyDTO.getAgainstTotalExposure());
        }
        LOG.info("END : BCC paper: {} exposure company updated : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }


    public BCCUpdateBuilder updateBCCExposureGroup() throws AppsException {
        BccExposureGroup bccExposureGroup = boardCreditCommitteePaper.getBccExposureGroup();
        BccExposureGroupDTO bccExposureGroupDTO = boardCreditCommitteePaperDTO.getBccExposureGroupDTO();
        if (bccExposureGroup == null) {
            bccExposureGroup = new BccExposureGroup();
            bccExposureGroup.setCreatedDate(date);
            bccExposureGroup.setCreatedBy(credentialsDTO.getUserName());
            boardCreditCommitteePaper.setBccExposureGroup(bccExposureGroup);
            bccExposureGroup.setBoardCreditCommitteePaper(boardCreditCommitteePaper);
        }
        if (bccExposureGroupDTO != null) {
            bccExposureGroup.setExistingExposureDirect(bccExposureGroupDTO.getExistingExposureDirect());
            bccExposureGroup.setExistingExposureIndirect(bccExposureGroupDTO.getExistingExposureIndirect());
            bccExposureGroup.setExistingExposureTotal(bccExposureGroupDTO.getExistingExposureTotal());
            bccExposureGroup.setAdditionalExposureDirect(bccExposureGroupDTO.getAdditionalExposureDirect());
            bccExposureGroup.setAdditionalExposureIndirect(bccExposureGroupDTO.getAdditionalExposureIndirect());
            bccExposureGroup.setAdditionalExposureTotal(bccExposureGroupDTO.getAdditionalExposureTotal());
            bccExposureGroup.setTotalExposureDirect(bccExposureGroupDTO.getTotalExposureDirect());
            bccExposureGroup.setTotalExposureIndirect(bccExposureGroupDTO.getTotalExposureIndirect());
            bccExposureGroup.setTotalExposureTotal(bccExposureGroupDTO.getTotalExposureTotal());
            bccExposureGroup.setType(bccExposureGroupDTO.getType());
            bccExposureGroup.setExposureSecuredBy(bccExposureGroupDTO.getExposureSecuredBy());
            bccExposureGroup.setApprovedFSV(bccExposureGroupDTO.getApprovedFSV());
            bccExposureGroup.setAgainstApprovedFSV(bccExposureGroupDTO.getAgainstApprovedFSV());
            bccExposureGroup.setAgainstTotalExposure(bccExposureGroupDTO.getAgainstTotalExposure());
        }
        LOG.info("END : BCC paper: {} exposure group update : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }


    public BCCUpdateBuilder updateException() throws AppsException {
        boardCreditCommitteePaper.setExceptions(boardCreditCommitteePaperDTO.getExceptions());
        LOG.info("END : BCC paper: {} exception update by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }

    public BCCUpdateBuilder updateOfficers() throws AppsException {
        boardCreditCommitteePaper.setOfficerOne(boardCreditCommitteePaperDTO.getOfficerOne());
        boardCreditCommitteePaper.setOfficerTwo(boardCreditCommitteePaperDTO.getOfficerTwo());
        boardCreditCommitteePaper.setOfficerThree(boardCreditCommitteePaperDTO.getOfficerThree());
        boardCreditCommitteePaper.setOfficerFour(boardCreditCommitteePaperDTO.getOfficerFour());
        boardCreditCommitteePaper.setOfficerOneDesignation(boardCreditCommitteePaperDTO.getOfficerOneDesignation());
        boardCreditCommitteePaper.setOfficerTwoDesignation(boardCreditCommitteePaperDTO.getOfficerTwoDesignation());
        boardCreditCommitteePaper.setOfficerThreeDesignation(boardCreditCommitteePaperDTO.getOfficerThreeDesignation());
        boardCreditCommitteePaper.setOfficerFourDesignation(boardCreditCommitteePaperDTO.getOfficerFourDesignation());
        boardCreditCommitteePaper.setEacMemberOne(boardCreditCommitteePaperDTO.getEacMemberOne());
        boardCreditCommitteePaper.setEacMemberTwo(boardCreditCommitteePaperDTO.getEacMemberTwo());
        boardCreditCommitteePaper.setEacMemberThree(boardCreditCommitteePaperDTO.getEacMemberThree());
        boardCreditCommitteePaper.setEacMemberFour(boardCreditCommitteePaperDTO.getEacMemberFour());
        boardCreditCommitteePaper.setEacMemberOneDesignation(boardCreditCommitteePaperDTO.getEacMemberOneDesignation());
        boardCreditCommitteePaper.setEacMemberTwoDesignation(boardCreditCommitteePaperDTO.getEacMemberTwoDesignation());
        boardCreditCommitteePaper.setEacMemberThreeDesignation(boardCreditCommitteePaperDTO.getEacMemberThreeDesignation());
        boardCreditCommitteePaper.setEacMemberFourDesignation(boardCreditCommitteePaperDTO.getEacMemberFourDesignation());
        LOG.info("END : BCC paper: {} officers updated by : {}", boardCreditCommitteePaperDTO.getBoardCreditCommitteePaperID(), credentialsDTO.getUserID());
        return this;
    }

}
