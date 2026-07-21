package com.itechro.cas.service.coveringApproval.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.dao.coveringApproval.jdbc.CoveringApprovalJdbcDao;
import com.itechro.cas.dao.facilitypaper.jdbc.FacilityPaperJdbcDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import com.itechro.cas.model.domain.coveringApproval.CovAppStatusHistory;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.model.dto.coveringApproval.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.coveringApproval.command.CoveringApprovalModificationContext;
import com.itechro.cas.util.CalendarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author tharushi
 */

public class CovAppStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CovAppStatusHandler.class);

    protected CoveringApproval coveringApproval;

    protected CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ;


    protected CredentialsDTO credentialsDTO;

    protected Date date;

    protected CasProperties casProperties;

    private CoveringApprovalJdbcDao coveringApprovalJdbcDao;

    public CovAppStatusHandler() {
    }

    public void setCoveringApprovalJdbcDao(CoveringApprovalJdbcDao coveringApprovalJdbcDao) {
        this.coveringApprovalJdbcDao = coveringApprovalJdbcDao;
    }

    public CovAppStatusHandler(CoveringApproval coveringApproval, CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ, CredentialsDTO credentialsDTO, CasProperties casProperties) {
        this.coveringApproval = coveringApproval;
        this.coveringApprovalStatusChangeRQ = coveringApprovalStatusChangeRQ;
        this.credentialsDTO = credentialsDTO;
        this.casProperties = casProperties;
        this.date = new Date();
    }

    public CasProperties getCasProperties() {
        return casProperties;
    }

    public boolean isValidStatusTransition() {
        return true;
    }

    public void updateCoveringApproval() throws AppsException {
        coveringApproval.setCurrentAssignUserId(coveringApprovalStatusChangeRQ.getCurrentAssignUserId());
        coveringApproval.setAssignUserDisplayName(coveringApprovalStatusChangeRQ.getAssignUserDisplayName());
        coveringApproval.setCurrentAssignUserDivCode(coveringApprovalStatusChangeRQ.getCurrentAssignUserDivCode());
        coveringApproval.setAssignUserUpmId(coveringApprovalStatusChangeRQ.getAssignUserUpmId());
        coveringApproval.setAssignUserUpmGroupCode(coveringApprovalStatusChangeRQ.getAssignUserUpmGroupCode());
        coveringApproval.setCurrentAssignUser(coveringApprovalStatusChangeRQ.getCurrentAssignUser());
        coveringApproval.setIsAuthorized(coveringApprovalStatusChangeRQ.getIsAuthorized());
        coveringApproval.setModifiedUserID(credentialsDTO.getUserID().toString());
    }

    public void transitStatus() throws AppsException {
        if (isValidStatusTransition()) {
            LOG.info("START: Covering Approval Update: {} : Current Status {}", coveringApproval.getCovAppRefNumber(), coveringApproval.getCurrentStatus());
            coveringApproval.setModifiedBy(credentialsDTO.getUserName());
            coveringApproval.setModifiedDate(date);
            coveringApproval.setCurrentStatus(coveringApprovalStatusChangeRQ.getCurrentStatus());
            LOG.info("END: Covering Approval: {} : updated Status {}", coveringApproval.getCovAppRefNumber(), coveringApproval.getCurrentStatus());
        } else {
            LOG.error("ERROR: Invalid transition request : Application form {} :Status {}", coveringApproval.getCovAppRefNumber(), coveringApproval.getCurrentStatus());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_COVERING_APPROVAL_STATUS_CHANGE);
        }
    }

    public void addComment() throws AppsException {

        CovAppCommentDTO covAppCommentDTO = coveringApprovalStatusChangeRQ.getCovAppCommentDTO();
        if (covAppCommentDTO.getUserComment() != null && !covAppCommentDTO.getUserComment().isEmpty()) {

            CovAppComment comment = new CovAppComment();

            comment.setActionMessage(coveringApprovalStatusChangeRQ.getActionMessage());
            comment.setUserComment(covAppCommentDTO.getUserComment());
            comment.setCurrentStatus(coveringApprovalStatusChangeRQ.getCurrentStatus());

            comment.setCreatedUserId(covAppCommentDTO.getCreatedUserId());
            comment.setCreatedUser(covAppCommentDTO.getCreatedUser());
            comment.setCreatedUserDisplayName(covAppCommentDTO.getCreatedUserDisplayName());
            comment.setCreatedUserDivCode(covAppCommentDTO.getCreatedUserDivCode());
            comment.setCreatedUserUpmCode(covAppCommentDTO.getCreatedUserUpmCode());

            comment.setIsPublic(covAppCommentDTO.getIsPublic());
            comment.setIsUsersOnly(covAppCommentDTO.getIsUsersOnly());
            comment.setIsDivisionOnly(covAppCommentDTO.getIsDivisionOnly());

            comment.setStatus(AppsConstants.Status.ACT);
            comment.setCreatedBy(credentialsDTO.getUserName());
            comment.setCreatedDate(date);
            coveringApproval.addComment(comment);

            LOG.info("INFO : Comment added to Application Form : {} : {}", coveringApprovalStatusChangeRQ, credentialsDTO.getUserName());
        } else {
            LOG.info("INFO : No Comment Mentioned for to Application Form : {} : {}", coveringApprovalStatusChangeRQ, credentialsDTO.getUserName());
        }
    }

    public void recordStatusHistory() throws AppsException {

        CovAppStatusHistory covAppStatusHistory = new CovAppStatusHistory();
        covAppStatusHistory.setActionMessage(coveringApprovalStatusChangeRQ.getActionMessage());
        covAppStatusHistory.setCurrentStatus(coveringApprovalStatusChangeRQ.getCurrentStatus());
        covAppStatusHistory.setAssignUser(coveringApprovalStatusChangeRQ.getCurrentAssignUser());
        covAppStatusHistory.setAssignUserID(coveringApprovalStatusChangeRQ.getCurrentAssignUserId());
        covAppStatusHistory.setAssignUserDisplayName(coveringApprovalStatusChangeRQ.getAssignUserDisplayName());
        covAppStatusHistory.setAssignUserUpmID(coveringApprovalStatusChangeRQ.getAssignUserUpmId());
        covAppStatusHistory.setAssignUserDivCode(coveringApprovalStatusChangeRQ.getCurrentAssignUserDivCode());
        covAppStatusHistory.setForwardType(coveringApprovalStatusChangeRQ.getForwardType());
        covAppStatusHistory.setUpdatedUserDisplayName(coveringApprovalStatusChangeRQ.getUpdatedByUserDisplayName());
        covAppStatusHistory.setUpdateBy(credentialsDTO.getUserName());
        covAppStatusHistory.setUpdateDate(date);
        covAppStatusHistory.setAssignUserUpmGroupCode(coveringApprovalStatusChangeRQ.getAssignUserUpmGroupCode());
        covAppStatusHistory.setUpdateUserId(credentialsDTO.getUserID().toString());
        coveringApproval.addStatusHistory(covAppStatusHistory);

        LOG.info("INFO : Status History record added to Covering approval Form : {} : {}", coveringApprovalStatusChangeRQ, credentialsDTO.getUserID());

    }


    public void sendEmailNotificationInProgress(CoveringApprovalModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {
            if (context.getCoveringApprovalStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.DIRECT_USER) {

                CoveringApproval coveringApproval = context.getCoveringApproval();
                CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ = context.getCoveringApprovalStatusChangeRQ();
                CoveringApprovalStatusTransitionRQ emailRQ = new CoveringApprovalStatusTransitionRQ();

                emailRQ.setCredentialsDTO(context.getCredentialDto());
                emailRQ.setCovAppId(coveringApprovalStatusChangeRQ.getCovAppId());
                emailRQ.setCovAppRefNumber(coveringApprovalStatusChangeRQ.getCovAppRefNumber());
                emailRQ.setSolID(coveringApproval.getBranchCode());
                emailRQ.setCustomerName(coveringApproval.getCovAppBasicInfoList().get(0).getCustomerName());
                emailRQ.setUserName(context.getCoveringApprovalStatusChangeRQ().getCurrentAssignUser());
                emailRQ.setLastComment(context.getCoveringApprovalStatusChangeRQ().getCovAppCommentDTO().getUserComment());
                emailRQ.setLastCommentedUser(context.getCoveringApprovalStatusChangeRQ().getUpdatedByUserDisplayName());
                emailRQ.setAssignUserDisplayName(context.getCoveringApprovalStatusChangeRQ().getAssignUserDisplayName());
                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(coveringApproval.getCreatedDate()));
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(coveringApproval.getBranchCode()).getBranchName());
                emailRQ.setChequeNumber(coveringApproval.getCovAppBasicInfoList().get(0).getChequeNumber());
                emailRQ.setTranAmount(coveringApproval.getCovAppBasicInfoList().get(0).getTranAmount());
                emailRQ.setAccountNumber(coveringApproval.getAccountNumber());
                emailRQ.setNewStatus(DomainConstants.CoveringApprovalStatus.FORWARDED);

                context.addCoveringApprovalStatusTransitionRQ(emailRQ);
            }
        }
    }

    public void sendEmailNotificationDeclined(CoveringApprovalModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {
            if (context.getCoveringApprovalStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.DIRECT_USER) {
                CoveringApproval coveringApproval = context.getCoveringApproval();
                CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ = context.getCoveringApprovalStatusChangeRQ();
                CoveringApprovalStatusTransitionRQ emailRQ = new CoveringApprovalStatusTransitionRQ();

                emailRQ.setCredentialsDTO(context.getCredentialDto());
                emailRQ.setCovAppId(coveringApprovalStatusChangeRQ.getCovAppId());
                emailRQ.setCovAppRefNumber(coveringApprovalStatusChangeRQ.getCovAppRefNumber());
                emailRQ.setSolID(coveringApproval.getBranchCode());
                emailRQ.setCustomerName(coveringApproval.getCovAppBasicInfoList().get(0).getCustomerName());
                emailRQ.setUserName(context.getCoveringApprovalStatusChangeRQ().getCurrentAssignUser());
                emailRQ.setLastComment(context.getCoveringApprovalStatusChangeRQ().getCovAppCommentDTO().getUserComment());
                emailRQ.setLastCommentedUser(context.getCoveringApprovalStatusChangeRQ().getUpdatedByUserDisplayName());
                emailRQ.setAssignUserDisplayName(context.getCoveringApprovalStatusChangeRQ().getAssignUserDisplayName());
                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(coveringApproval.getCreatedDate()));
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(coveringApproval.getBranchCode()).getBranchName());
                emailRQ.setChequeNumber(coveringApproval.getCovAppBasicInfoList().get(0).getChequeNumber());
                emailRQ.setTranAmount(coveringApproval.getCovAppBasicInfoList().get(0).getTranAmount());
                emailRQ.setAccountNumber(coveringApproval.getAccountNumber());
                emailRQ.setNewStatus(DomainConstants.CoveringApprovalStatus.RETURNED);

                context.addCoveringApprovalStatusTransitionRQ(emailRQ);

            }
        }
    }

    public void sendEmailNotificationReturned(CoveringApprovalModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {
            if (context.getCoveringApprovalStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.DIRECT_USER) {

                CoveringApproval coveringApproval = context.getCoveringApproval();
                CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ = context.getCoveringApprovalStatusChangeRQ();

                List<CovAppStatusHistoryDTO> covAppStatusHistoryDTOList = coveringApprovalJdbcDao.getCovAppDirectReturnUserList(coveringApproval.getCovAppId());

                for (CovAppStatusHistoryDTO covAppStatusHistoryDTO : covAppStatusHistoryDTOList) {
                    CoveringApprovalStatusTransitionRQ emailRQ = new CoveringApprovalStatusTransitionRQ();

                    emailRQ.setCredentialsDTO(context.getCredentialDto());
                    emailRQ.setCovAppId(coveringApprovalStatusChangeRQ.getCovAppId());
                    emailRQ.setCovAppRefNumber(coveringApprovalStatusChangeRQ.getCovAppRefNumber());
                    emailRQ.setSolID(coveringApproval.getBranchCode());
                    emailRQ.setCustomerName(coveringApproval.getCovAppBasicInfoList().get(0).getCustomerName());
                    emailRQ.setLastComment(context.getCoveringApprovalStatusChangeRQ().getCovAppCommentDTO().getUserComment());
                    emailRQ.setLastCommentedUser(context.getCoveringApprovalStatusChangeRQ().getUpdatedByUserDisplayName());
                    emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(coveringApproval.getCreatedDate()));
                    emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(coveringApproval.getBranchCode()).getBranchName());
                    emailRQ.setChequeNumber(coveringApproval.getCovAppBasicInfoList().get(0).getChequeNumber());
                    emailRQ.setTranAmount(coveringApproval.getCovAppBasicInfoList().get(0).getTranAmount());
                    emailRQ.setAccountNumber(coveringApproval.getAccountNumber());
                    emailRQ.setNewStatus(DomainConstants.CoveringApprovalStatus.REJECTED);

                    emailRQ.setUserName(covAppStatusHistoryDTO.getAssignUser());
                    emailRQ.setAssignUserDisplayName(covAppStatusHistoryDTO.getAssignUserDisplayName());

                    context.addCoveringApprovalStatusTransitionRQ(emailRQ);
                }
            }
        }
    }

    public void sendEmailNotificationApproved(CoveringApprovalModificationContext context) throws AppsException {
        if (getCasProperties().isAllowSendingEmail()) {
            if (context.getCoveringApprovalStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.DIRECT_USER) {

                CoveringApproval coveringApproval = context.getCoveringApproval();
                CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ = context.getCoveringApprovalStatusChangeRQ();

                List<CovAppStatusHistoryDTO> covAppStatusHistoryDTOList = coveringApprovalJdbcDao.getCovAppDirectReturnUserList(coveringApproval.getCovAppId());

                for (CovAppStatusHistoryDTO covAppStatusHistoryDTO : covAppStatusHistoryDTOList) {
                    CoveringApprovalStatusTransitionRQ emailRQ = new CoveringApprovalStatusTransitionRQ();

                    emailRQ.setCredentialsDTO(context.getCredentialDto());
                    emailRQ.setCovAppId(coveringApprovalStatusChangeRQ.getCovAppId());
                    emailRQ.setCovAppRefNumber(coveringApprovalStatusChangeRQ.getCovAppRefNumber());
                    emailRQ.setSolID(coveringApproval.getBranchCode());
                    emailRQ.setCustomerName(coveringApproval.getCovAppBasicInfoList().get(0).getCustomerName());
                    emailRQ.setLastComment(context.getCoveringApprovalStatusChangeRQ().getCovAppCommentDTO().getUserComment());
                    emailRQ.setLastCommentedUser(context.getCoveringApprovalStatusChangeRQ().getUpdatedByUserDisplayName());
                    emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(coveringApproval.getCreatedDate()));
                    emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(coveringApproval.getBranchCode()).getBranchName());
                    emailRQ.setChequeNumber(coveringApproval.getCovAppBasicInfoList().get(0).getChequeNumber());
                    emailRQ.setTranAmount(coveringApproval.getCovAppBasicInfoList().get(0).getTranAmount());
                    emailRQ.setAccountNumber(coveringApproval.getAccountNumber());
                    emailRQ.setNewStatus(DomainConstants.CoveringApprovalStatus.APPROVED);

                    emailRQ.setUserName(covAppStatusHistoryDTO.getAssignUser());
                    emailRQ.setAssignUserDisplayName(covAppStatusHistoryDTO.getAssignUserDisplayName());

                    context.addCoveringApprovalStatusTransitionRQ(emailRQ);
                }
            }
        }
    }

}
