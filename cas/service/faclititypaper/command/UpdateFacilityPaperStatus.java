package com.itechro.cas.service.faclititypaper.command;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.casmaster.jdbc.MasterDataJdbcDao;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.dao.master.UserDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperUpdateDTO;
import com.itechro.cas.service.faclititypaper.creditcalculator.CalculatorService;
import com.itechro.cas.service.faclititypaper.support.*;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class UpdateFacilityPaperStatus extends CommandExecutor<FacilityPaperModificationContext> {

    private static final Logger LOG = LoggerFactory.getLogger(UpdateFacilityPaperStatus.class);

    @Autowired
    private CasProperties casProperties;

    @Autowired
    private UserDao userDao;

    @Autowired
    private FacilityPaperJdbcDao facilityPaperJdbcDao;

    @Autowired
    private MasterDataJdbcDao masterDataJdbcDao;

    @Autowired
    private CalculatorService calculatorService;

    @Autowired
    private IntegrationService integrationService;

    @Override
    public FacilityPaperModificationContext execute(FacilityPaperModificationContext context) throws AppsException {

        FacilityPaperUpdateDTO updateDto = context.getFacilityPaperUpdateDto();
        if (updateDto.getFacilityPaperStatus() != null) {
            FacilityPaperStatusTransitionHandler handler = null;
            switch (updateDto.getFacilityPaperStatus()) {
                case APPROVED:
                    handler = new FPApproveHandler(context, casProperties);
                    ((FPApproveHandler) handler).setFacilityPaperJdbcDao(facilityPaperJdbcDao);
                    ((FPApproveHandler) handler).approvePaper(context);
                    ((FPApproveHandler) handler).setCalculatorService(calculatorService);
                    ((FPApproveHandler) handler).setIntegrationService(integrationService);
                    ((FPApproveHandler) handler).sendCreditCalculationDataToBank();
                    LOG.info("context{}", context);
                    handler.sendEmailNotification(context);
                    handler.addComment();

                    break;
                case CANCEL: {
                    handler = new FPCancelHandler(context, casProperties);
                    handler.setMasterDataJdbcDao(masterDataJdbcDao);
                    ((FPCancelHandler) handler).setUserDao(userDao);
                    ((FPCancelHandler) handler).cancelFacilityPaper(context);
                    handler.blockCreditRiskComment();
                    handler.sendEmailNotification(context);
                    handler.addComment();
                    handler.blockCreditRiskDocument();
                    break;
                }
                case DRAFT:
                case IN_PROGRESS:
                    handler = new FPInProgressHandler(context, casProperties);
                    handler.setMasterDataJdbcDao(masterDataJdbcDao);
                    ((FPInProgressHandler) handler).inProgressFacilityPaper(context);
                    handler.blockCreditRiskComment();
                    LOG.info("getAssignDepartmentCode : {}", context.getFacilityPaperUpdateDto().getAssignDepartmentCode());
                    if (!Objects.equals(context.getFacilityPaperUpdateDto().getAssignDepartmentCode(), "CA")){
                        LOG.info("Not CA Mail Send : {}", context.getFacilityPaperUpdateDto().getAssignDepartmentCode());
                        handler.sendEmailNotification(context);
                    }
                    handler.addComment();
                    handler.blockCreditRiskDocument();
                    break;
                case REMOVED:
                case REJECTED:
                    handler = new FPRejectHandler(context, casProperties);
                    ((FPRejectHandler) handler).setFacilityPaperJdbcDao(facilityPaperJdbcDao);
                    ((FPRejectHandler) handler).rejectPaper(context);
                    handler.sendEmailNotification(context);
                    handler.addComment();
                    break;
                default:
                    handler = new FacilityPaperStatusTransitionHandler(context, casProperties);
                    break;
            }
            handler.transfer();
            LOG.info("Transfer the Facility paper status : {}", context.getFacilityPaperUpdateDto());
        } else {
            LOG.info("NO Status transition found : {}", context.getFacilityPaperUpdateDto());
        }

        return context;
    }


}
