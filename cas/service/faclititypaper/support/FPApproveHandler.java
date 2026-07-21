package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.casmaster.CreditFacilityTemplateDao;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityFacilitySecurity;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTemplateDTO;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facility.FacilityLoadOptionDTO;
import com.itechro.cas.model.dto.facilitypaper.FPLoadOptionDTO;
import com.itechro.cas.model.dto.facilitypaper.FPStatusHistoryDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.model.dto.integration.request.BranchAuthorityLevelRQ;
import com.itechro.cas.model.dto.integration.response.BranchAuthorityLevelResponseDTO;
import com.itechro.cas.model.dto.integration.response.BranchAuthorityLevelResponseListDTO;
import com.itechro.cas.model.dto.integration.response.creditschedule.CreditScheduleInformationDTO;
import com.itechro.cas.service.faclititypaper.command.FacilityPaperModificationContext;
import com.itechro.cas.service.faclititypaper.creditcalculator.CalculatorService;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.util.CalendarUtil;
import com.itechro.cas.util.DecimalCalculator;
import com.itechro.cas.util.NumberUtil;
import com.itechro.cas.util.WorkGroupUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

public class FPApproveHandler extends FacilityPaperStatusTransitionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(FPApproveHandler.class);

    private FacilityPaperJdbcDao facilityPaperJdbcDao;

    private CalculatorService calculatorService;

    private IntegrationService integrationService;

    private CasProperties casProperties;

    public FPApproveHandler(FacilityPaperModificationContext context, CasProperties casProperties) {
        super(context, casProperties);
        this.casProperties=casProperties;
    }

    public FPApproveHandler(FacilityPaperModificationContext context) {
        super(context);
    }

    protected boolean isValidStatusChange(FacilityPaperModificationContext context) throws AppsException {
        if (context.getFacilityPaperUpdateDto().getFacilityPaperStatus() != null) {
            if (context.getFacilityPaperUpdateDto().getFacilityPaperStatus() == DomainConstants.FacilityPaperStatus.APPROVED) {
                //toDo need to remove unlimited user DA level validation when committee integrated

                // Enable auto approval for CCPU 50 wc
                if (context.getFacilityPaperUpdateDto().getIsAutoApproval().equals(AppsConstants.YesNo.Y)){
                    LOG.info("Approve this facility paper ignoring the USER DA limits, by user class : {} paper Details :  {}", context.getFacilityPaperUpdateDto().getAssignUserUpmGroupCode(), context.getFacilityPaperUpdateDto());
                    return true;
                } else {
                    if (!context.getFacilityPaperUpdateDto().getAssignUserUpmGroupCode().equals(String.valueOf(WorkGroupUtil.getMD()))) {
                        if (context.getUserDaDTO() != null) {
                            BigDecimal useDAMaxAmount = context.getUserDaDTO().getMaxAmount();
                            BigDecimal totalFacilityPaperAmount = context.getFacilityPaper().getTotalExposureNew();
                            BigDecimal totalSecurityCashAmount = DecimalCalculator.getDefaultZero();
                            BigDecimal cashAmountDeductByTotalFacilityAmount = DecimalCalculator.getDefaultZero();
                            Map<Integer, BigDecimal> integerFacilitySecurityMap = new HashMap<>();
                            for (Facility facility : context.getFacilityPaper().getActiveFacility()) {
                                for (FacilityFacilitySecurity facilityFacilitySecurity : facility.getFacilityFacilitySecurities()) {
                                    if (facilityFacilitySecurity.getStatus() == AppsConstants.Status.ACT) {
                                        if (facilityFacilitySecurity.getFacilitySecurity().getIsCashSecurity() == AppsConstants.YesNo.Y) {
                                            integerFacilitySecurityMap.putIfAbsent(facilityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID(), facilityFacilitySecurity.getFacilitySecurity().getCashAmount());
                                        }
                                    }
                                }
                            }

                            for (BigDecimal cashAmounts : integerFacilitySecurityMap.values()) {
                                totalSecurityCashAmount = DecimalCalculator.add(totalSecurityCashAmount, cashAmounts);
                            }

                            cashAmountDeductByTotalFacilityAmount = DecimalCalculator.subtract(totalFacilityPaperAmount, totalSecurityCashAmount);

                            LOG.info("Approve this facility paper for this user totalFacilityPaperAmount :{}  totalCashAmount :{} totalFacilityAmountDeductByCashAmount : {} :: {} {}", totalFacilityPaperAmount, totalSecurityCashAmount, cashAmountDeductByTotalFacilityAmount, context.getFacilityPaperUpdateDto(), context.getUserDaDTO());

                            if (!DecimalCalculator.isGreaterThanOrEq(useDAMaxAmount, cashAmountDeductByTotalFacilityAmount)) {
                                LOG.info("NO Authority to approve this facility paper for this user totalFacilityPaperAmount:{}: totalCashAmount {} : totalFacilityAmountDeductByCashAmount {} : {} :{}", totalFacilityPaperAmount, totalSecurityCashAmount, cashAmountDeductByTotalFacilityAmount, context.getFacilityPaperUpdateDto(), context.getUserDaDTO());
                                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_NO_AUTHORITY_TO_STATUS_CHANGE);
                            }
                            return true;
                        } else {
                            LOG.info("NO Authority to approve this facility paper for this user: {}", context.getFacilityPaperUpdateDto());
                            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_NO_AUTHORITY_TO_STATUS_CHANGE);
                        }
                    } else {
                        LOG.info("Approve this facility paper ignoring the USER DA limits, by user class : {} paper Details :  {}", context.getFacilityPaperUpdateDto().getAssignUserUpmGroupCode(), context.getFacilityPaperUpdateDto());
                        return true;
                    }
                }
            } else {
                return true;
            }
        } else {
            LOG.info("NO Status transition found : {}", context.getFacilityPaperUpdateDto());
            return false;
        }
    }

    public void sendEmailNotification(FacilityPaperModificationContext context) throws AppsException {
        LOG.info("context 222222 {} ", context);
        if (super.getCasProperties().isAllowSendingEmail()) {
            FacilityPaper facilityPaper = context.getFacilityPaper();
            FacilityPaperDTO facilityPaperDTO = new FacilityPaperDTO(facilityPaper);

            this.facilityPaperJdbcDao.getFPDirectReturnUsersList(facilityPaperDTO);

            List<FPStatusHistoryDTO> fpStatusHistoryDTOS = facilityPaperJdbcDao.getFPDirectReturnUsersList(facilityPaperDTO).stream().filter(element -> {
                return !element.getAssignUser().equals(context.getCredentialsDto().getUserName());
            }).distinct().collect(Collectors.toList());

            BranchAuthorityLevelRQ branchAuthorityLevelRQ = new BranchAuthorityLevelRQ();
            LOG.info("this.casProperties.getManagerWorkClass() {} ", this.casProperties.getManagerWorkClass());
            branchAuthorityLevelRQ.setRoleId(this.casProperties.getManagerWorkClass());

            LOG.info("facilityPaper.getBranchCode() {} ", facilityPaper.getBranchCode());
            branchAuthorityLevelRQ.setSolId(facilityPaper.getBranchCode());

            LOG.info("this.casProperties.getApplicationCode() {} ", this.casProperties.getApplicationCode());
            branchAuthorityLevelRQ.setAppCode(this.casProperties.getApplicationCode());

            BranchAuthorityLevelResponseListDTO branchAuthorityLevelResponseListDTO = integrationService.getUserDetailListFormBranchAuthorityLevel(branchAuthorityLevelRQ , context.getCredentialsDto());
            LOG.info("branchAuthorityLevelResponseListDTO {} ", branchAuthorityLevelResponseListDTO);


            for(BranchAuthorityLevelResponseDTO branchAuthorityLevelResponseDTO : branchAuthorityLevelResponseListDTO.getBranchAuthorityLevelResponseDTOList()) {
                FPStatusHistoryDTO managerFPStatusHistoryDTO = new FPStatusHistoryDTO();
                managerFPStatusHistoryDTO.setAssignUser(branchAuthorityLevelResponseDTO.getAdUserID());
                managerFPStatusHistoryDTO.setAssignUserDisplayName(branchAuthorityLevelResponseDTO.getFirstName() + " " + branchAuthorityLevelResponseDTO.getLastName());
                LOG.info("managerFPStatusHistoryDTO {} ", managerFPStatusHistoryDTO);
                fpStatusHistoryDTOS.add(managerFPStatusHistoryDTO);

            }
            LOG.info("fpStatusHistoryDTOS {} ", fpStatusHistoryDTOS);

            for (FPStatusHistoryDTO fpStatusHistoryDTO : fpStatusHistoryDTOS) {
                LOG.info("fpStatusHistoryDTO {} ", fpStatusHistoryDTO);
                emailRQ = new FacilityPaperStatusTransitionRQ();
                emailRQ.setSolID(facilityPaper.getBranchCode());
                emailRQ.setCredentialsDTO(context.getCredentialsDto());
                emailRQ.setUserName(fpStatusHistoryDTO.getAssignUser());
                emailRQ.setFacilityPaperID(facilityPaper.getFacilityPaperID());
                emailRQ.setFacilityPaperRefNumber(facilityPaper.getFpRefNumber());
                if (context.getCustomerDto() != null) {
                    emailRQ.setCustomerName(context.getCustomerDto().getCustomerName());
                } else if (context.getCasCustomerDto() != null) {
                    emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
                }
                emailRQ.setLastComment(context.getFacilityPaperUpdateDto().getFpCommentDTO().getComment());
                emailRQ.setAssignUserDisplayName(fpStatusHistoryDTO.getAssignUserDisplayName());
                emailRQ.setNewStatus(context.getFacilityPaperUpdateDto().getFacilityPaperStatus());
                emailRQ.setTotalExposure(NumberUtil.getFormattedAmount(facilityPaper.getTotalExposureNew()));
                emailRQ.setLastCommentedUser(context.getFacilityPaperUpdateDto().getUpdatedByUserDisplayName());
                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCreatedDate()));
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(facilityPaper.getBranchCode()).getBranchName());
                for (Facility facility : context.getFacilityPaper().getActiveFacility()) {
                    emailRQ.getFacilityDTOList().add(new FacilityDTO(facility));

                }
                context.addFacilityPaperStatusTransitionRQ(emailRQ);
                LOG.info("emailRQ {} ", emailRQ);
            }

            List<String> ccduEmailLst = null;
            for (Facility facility : context.getFacilityPaper().getActiveFacility()) {
                if(facility.getCreditFacilityTemplate() != null){
                    Integer creditFacilityTemplateID = facility.getCreditFacilityTemplate().getCreditFacilityTemplateID();

                    CreditFacilityTemplateDTO creditFacilityTemplateDTO = this.facilityPaperJdbcDao.getCreditFacilityTemplateById(creditFacilityTemplateID);

                    if(String.valueOf(facility.getIsNew().getStrVal().charAt(0)).equals(String.valueOf(AppsConstants.YesNo.Y.getStrVal().charAt(0)))){
                        LOG.info("Checking is new facility ");
                        if(creditFacilityTemplateDTO.getNewFacilityEmail() != null){
                            ccduEmailLst = new ArrayList<>();
                            ccduEmailLst.add(creditFacilityTemplateDTO.getNewFacilityEmail());
                        }
                    }
                }
            }

            emailRQ = new FacilityPaperStatusTransitionRQ();
            emailRQ.setToAddresses(ccduEmailLst);
            emailRQ.setSolID(facilityPaper.getBranchCode());
            emailRQ.setCredentialsDTO(context.getCredentialsDto());
            emailRQ.setUserName("CCDU Officer");
            emailRQ.setAssignUserDisplayName("CCDU Officer");
            emailRQ.setFacilityPaperID(facilityPaper.getFacilityPaperID());
            emailRQ.setFacilityPaperRefNumber(facilityPaper.getFpRefNumber());
            if (context.getCustomerDto() != null) {
                emailRQ.setCustomerName(context.getCustomerDto().getCustomerName());
            } else if (context.getCasCustomerDto() != null) {
                emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
            }
            emailRQ.setLastComment(context.getFacilityPaperUpdateDto().getFpCommentDTO().getComment());
            emailRQ.setNewStatus(context.getFacilityPaperUpdateDto().getFacilityPaperStatus());
            emailRQ.setTotalExposure(NumberUtil.getFormattedAmount(facilityPaper.getTotalExposureNew()));
            emailRQ.setLastCommentedUser(context.getFacilityPaperUpdateDto().getUpdatedByUserDisplayName());
            emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(facilityPaper.getCreatedDate()));
            emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(facilityPaper.getBranchCode()).getBranchName());
            for (Facility facility1 : context.getFacilityPaper().getActiveFacility()) {
                emailRQ.getFacilityDTOList().add(new FacilityDTO(facility1));
            }

            LOG.info("CCDU emailRQ {} ", emailRQ);
            context.addFacilityPaperStatusTransitionRQ(emailRQ);

        }
    }

    public void approvePaper(FacilityPaperModificationContext context) throws AppsException {
        LOG.info("Approve Facility paper : {}", context.getFacilityPaperUpdateDto());
        context.getFacilityPaper().setPaperReviewStatus(DomainConstants.PaperReviewStatus.ACTION_REQUIRED);
        context.getFacilityPaper().setApprovedDate(context.getDate());
//        FIXME this is for update the UPM GROUP Code when approve the paper with history value
//        context.getFacilityPaperUpdateDto().setUpmGroupCode(context.getFacilityPaper().getUpmGroupCode());
        LOG.info("Approve Facility paper : {}", context.getFacilityPaperUpdateDto());
    }

    public void setFacilityPaperJdbcDao(FacilityPaperJdbcDao facilityPaperJdbcDao) {
        this.facilityPaperJdbcDao = facilityPaperJdbcDao;
    }

    public void sendCreditCalculationDataToBank() throws AppsException {
        FPLoadOptionDTO fpLoadOptionDTO = new FPLoadOptionDTO();
        fpLoadOptionDTO.loadFacilities();
        FacilityLoadOptionDTO facilityLoadOptionDTO = new FacilityLoadOptionDTO();
        facilityLoadOptionDTO.loadOtherFacilityInfo();
        fpLoadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOptionDTO);
        CreditScheduleInformationDTO creditScheduleInformationDTO = calculatorService.getCreditScheduleForFinacle(new FacilityPaperDTO(context.getFacilityPaper(), fpLoadOptionDTO), context.getCredentialsDto());
        if (creditScheduleInformationDTO != null) {
            integrationService.pushCreditScheduleToFinacle(creditScheduleInformationDTO, context.getCredentialsDto());
        }
    }

    public void setCalculatorService(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    public void setIntegrationService(IntegrationService integrationService) {
        this.integrationService = integrationService;
    }
}
