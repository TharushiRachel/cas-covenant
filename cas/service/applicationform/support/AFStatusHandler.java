package com.itechro.cas.service.applicationform.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.applicationform.AFComment;
import com.itechro.cas.model.domain.applicationform.AFStatusHistory;
import com.itechro.cas.model.domain.applicationform.AFWorkflowRouting;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomer;
import com.itechro.cas.model.dto.applicationform.*;
import com.itechro.cas.model.dto.integration.response.BranchAuthorityLevelResponseDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.command.ApplicationFormModificationContext;
import com.itechro.cas.util.CalendarUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AFStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AFStatusHandler.class);

    protected ApplicationForm applicationForm;

    protected ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ;

    protected CredentialsDTO credentialDTO;

    protected Date date;

    protected ApplicationFormStatusTransitionRQ emailRQ;

    private CasProperties casProperties;

    public AFStatusHandler() {
    }

    public AFStatusHandler(ApplicationForm applicationForm, CredentialsDTO credentialsDTO, ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ, CasProperties casProperties) {
        this.applicationFormStatusChangeRQ = applicationFormStatusChangeRQ;
        this.applicationForm = applicationForm;
        this.credentialDTO = credentialsDTO;
        this.date = new Date();
        this.casProperties = casProperties;
    }

    public CasProperties getCasProperties() {
        return casProperties;
    }


    public void updateApplicationForm() throws AppsException {

    }


    public void transitStatus() throws AppsException {
        if (isValidStatusTransition()) {
            LOG.info("START: Application From Update: {} : Current Status {}", applicationForm.getAfRefNumber(), applicationForm.getCurrentApplicationFormStatus());
            applicationForm.setModifiedBy(credentialDTO.getUserName());
            applicationForm.setModifiedDate(date);
            applicationForm.setCurrentApplicationFormStatus(applicationFormStatusChangeRQ.getApplicationFormStatus());
            LOG.info("END: Application From: {} : updated Status {}", applicationForm.getAfRefNumber(), applicationForm.getCurrentApplicationFormStatus());
        } else {
            LOG.error("ERROR: Invalid transition request : Application form {} :Status {}", applicationForm.getAfRefNumber(), applicationForm.getCurrentApplicationFormStatus());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_APPLICATION_FORM_STATUS_CHANGE);
        }
    }

    public void recordStatusHistory() throws AppsException {

        AFStatusHistory afStatusHistory = new AFStatusHistory();
        afStatusHistory.setActionMessage(applicationFormStatusChangeRQ.getActionMessage());
        afStatusHistory.setApplicationFormStatus(applicationFormStatusChangeRQ.getApplicationFormStatus());
        afStatusHistory.setAssignUserID(applicationFormStatusChangeRQ.getAssignUserID());
        afStatusHistory.setAssignUser(applicationFormStatusChangeRQ.getAssignUser());
        afStatusHistory.setAssignUserDisplayName(applicationFormStatusChangeRQ.getAssignUserDisplayName());
        afStatusHistory.setAssignUserUpmID(applicationFormStatusChangeRQ.getAssignUserUpmID());
        afStatusHistory.setAssignUserUpmGroupCode(applicationFormStatusChangeRQ.getAssignUserUpmGroupCode());
        afStatusHistory.setAssignUserDivCode(applicationFormStatusChangeRQ.getAssignUserDivCode());
        afStatusHistory.setAssignDepartmentCode(applicationFormStatusChangeRQ.getAssignDepartmentCode()); //For Cluster Forwarding
        afStatusHistory.setForwardType(applicationFormStatusChangeRQ.getForwardType()); //For Cluster Forwarding
        afStatusHistory.setUpdatedUserDisplayName(applicationFormStatusChangeRQ.getUpdatedByUserDisplayName());
        afStatusHistory.setUpdateBy(credentialDTO.getUserName());
        afStatusHistory.setUpdateDate(date);
        applicationForm.addAFStatusHistory(afStatusHistory);

        LOG.info("INFO : Status History record added to Application Form : {} : {}", applicationFormStatusChangeRQ, credentialDTO.getUserID());

    }

    public void addComment() throws AppsException {

        AFCommentDTO afCommentDTO = applicationFormStatusChangeRQ.getAfCommentDTO();
        if (afCommentDTO.getComment() != null && !afCommentDTO.getComment().isEmpty()) {
            AFComment afComment = new AFComment();

            afComment.setActionMessage(applicationFormStatusChangeRQ.getActionMessage());
            afComment.setUserComment(afCommentDTO.getComment());

            afComment.setCreatedUser(afCommentDTO.getCreatedUser());
            afComment.setCreatedUserID(afCommentDTO.getCreatedUserID());
            afComment.setCreatedUserDivCode(afCommentDTO.getCreatedUserDivCode());
            afComment.setCreatedUserUpmCode(afCommentDTO.getCreatedUserUpmCode());
            afComment.setCreatedUserDisplayName(afCommentDTO.getCreatedUserDisplayName());

            afComment.setAssignedUser(afCommentDTO.getAssignedUser());
            afComment.setAssignedUserID(afCommentDTO.getAssignedUserID());
            afComment.setAssignedUserDivCode(afCommentDTO.getAssignedUserDivCode());
            afComment.setAssignedUserDisplayName(afCommentDTO.getAssignedUserDisplayName());

            afComment.setAssignDepartmentCode(afCommentDTO.getAssignDepartmentCode());

            afComment.setIsPublic(afCommentDTO.getIsPublic());
            afComment.setIsDivisionOnly(afCommentDTO.getIsDivisionOnly());
            afComment.setIsUsersOnly(afCommentDTO.getIsUsersOnly());

            afComment.setCurrentApplicationFormStatus(applicationFormStatusChangeRQ.getApplicationFormStatus());
            afComment.setCreatedBy(credentialDTO.getUserName());
            afComment.setCreatedDate(this.date);
            afComment.setStatus(AppsConstants.Status.ACT);
            applicationForm.addAFComment(afComment);

            LOG.info("INFO : Comment added to Application Form : {} : {}", applicationFormStatusChangeRQ, credentialDTO.getUserID());
        } else {
            LOG.info("INFO : No Comment Mentioned for to Application Form : {} : {}", applicationFormStatusChangeRQ, credentialDTO.getUserID());
        }

    }

    public void recordWorkflowRouting(DomainConstants.ApplicationFormRoutingStatus applicationFormRoutingStatus) throws AppsException {

        AFWorkflowRouting afWorkflowRouting = new AFWorkflowRouting();
        afWorkflowRouting.setAssignDate(date);
        afWorkflowRouting.setAssignUser(applicationFormStatusChangeRQ.getAssignUser());
        afWorkflowRouting.setAssignUserID(applicationFormStatusChangeRQ.getAssignUserID());
        afWorkflowRouting.setAssignUserUpmID(applicationFormStatusChangeRQ.getAssignUserUpmID());
        afWorkflowRouting.setAssignUserDisplayName(applicationFormStatusChangeRQ.getAssignUserDisplayName());
        afWorkflowRouting.setAssignUserUpmGroupCode(applicationFormStatusChangeRQ.getAssignUserUpmGroupCode());
        afWorkflowRouting.setRoutingRemarks(applicationFormStatusChangeRQ.getActionMessage());
        afWorkflowRouting.setStatus(AppsConstants.Status.ACT);
        afWorkflowRouting.setRoutingStatus(applicationFormRoutingStatus);
        afWorkflowRouting.setCreatedBy(credentialDTO.getUserName());
        afWorkflowRouting.setCreatedDate(date);
        if (applicationFormStatusChangeRQ.getForwardType() == DomainConstants.ForwardType.SAME_SOL_USER_GROUP || applicationFormStatusChangeRQ.getForwardType() == DomainConstants.ForwardType.OTHER_SOL_USER_GROUP) {
            afWorkflowRouting.setAssignDepartmentCode(applicationFormStatusChangeRQ.getAssignDepartmentCode());
        }
        applicationForm.addAfWorkflowRouting(afWorkflowRouting);
        LOG.info("END : Application Form Workflow Routing Added : {} : {}", applicationFormStatusChangeRQ, credentialDTO.getUserID());
    }

    public boolean isValidStatusTransition() {
        return true;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void sendEmailNotificationInProgress(ApplicationFormModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {
            if (context.getApplicationFormStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.DIRECT_USER ) {

                ApplicationForm applicationForm = context.getApplicationForm();
                ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ = context.getApplicationFormStatusChangeRQ();
                ApplicationFormStatusTransitionRQ emailRQ = new ApplicationFormStatusTransitionRQ();

                emailRQ.setCredentialsDTO(context.getCredentialsDto());
                emailRQ.setApplicationFormID(applicationFormStatusChangeRQ.getApplicationFormID());
                emailRQ.setAfRefNumber(applicationFormStatusChangeRQ.getAfRefNumber());
                emailRQ.setSolID(applicationForm.getBranchCode());
                List<String> afCustomers = new ArrayList<>();
                if (context.getAFCustomer() != null) {
                    for (AFCustomer afCustomer: context.getAFCustomer()) {
                        afCustomers.add(afCustomer.getCustomerName());
                    }
                    emailRQ.setCustomerName(afCustomers);
                }
                emailRQ.setUserName(context.getApplicationFormStatusChangeRQ().getAssignUser());
                emailRQ.setLastComment(context.getApplicationFormStatusChangeRQ().getAfCommentDTO().getComment());
                emailRQ.setLastCommentedUser(context.getApplicationFormStatusChangeRQ().getUpdatedByUserDisplayName());
                emailRQ.setAssignUserDisplayName(context.getApplicationFormStatusChangeRQ().getAssignUserDisplayName());
                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate()));
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(applicationForm.getBranchCode()).getBranchName());
                emailRQ.setNewStatus(DomainConstants.ApplicationFormStatus.FORWARDED);

                context.addApplicationFormStatusTransitionRQ(emailRQ);


            }
            else if (context.getApplicationFormStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.OTHER_SOL_USER_GROUP ) {

                if (context.getUpmUsers50().getBranchAuthorityLevelResponseDTOList().size() > 0) {

                    for (BranchAuthorityLevelResponseDTO branchAuthorityLevelResponseDTO : context.getUpmUsers50().getBranchAuthorityLevelResponseDTOList()) {

                        ApplicationForm applicationForm = context.getApplicationForm();
                        ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ = context.getApplicationFormStatusChangeRQ();
                        ApplicationFormStatusTransitionRQ emailRQ = new ApplicationFormStatusTransitionRQ();

                        emailRQ.setCredentialsDTO(context.getCredentialsDto());
                        emailRQ.setApplicationFormID(applicationFormStatusChangeRQ.getApplicationFormID());
                        emailRQ.setAfRefNumber(applicationFormStatusChangeRQ.getAfRefNumber());
                        emailRQ.setSolID(applicationForm.getBranchCode());
                        List<String> afCustomers = new ArrayList<>();
                        if (context.getAFCustomer() != null) {
                            for (AFCustomer afCustomer: context.getAFCustomer()) {
                                afCustomers.add(afCustomer.getCustomerName());
                            }
                            emailRQ.setCustomerName(afCustomers);
                        }
                        emailRQ.setUserName(branchAuthorityLevelResponseDTO.getAdUserID());
                        emailRQ.setLastComment(context.getApplicationFormStatusChangeRQ().getAfCommentDTO().getComment());
                        emailRQ.setLastCommentedUser(context.getApplicationFormStatusChangeRQ().getUpdatedByUserDisplayName());
                        emailRQ.setAssignUserDisplayName(branchAuthorityLevelResponseDTO.getFirstName().concat(" ").concat(branchAuthorityLevelResponseDTO.getLastName()));
                        emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate()));
                        emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(applicationForm.getBranchCode()).getBranchName());
                        emailRQ.setNewStatus(DomainConstants.ApplicationFormStatus.FORWARDED_TO_USER_GROUP);

                        context.addApplicationFormStatusTransitionRQ(emailRQ);

                    }
                }
            }
        }
    }

    public void sendEmailNotificationDeclined(ApplicationFormModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {
            ApplicationForm applicationForm = context.getApplicationForm();
            ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ = context.getApplicationFormStatusChangeRQ();
            ApplicationFormDTO applicationFormDTO = new ApplicationFormDTO(applicationForm);


            if (applicationForm.getAfStatusHistorySet() != null) {

                List<AFStatusHistory> afStatusHistoryList = new ArrayList<>(applicationForm.getAfStatusHistorySet());

                List<AFStatusHistory> distinctAfStatusHistoryList = new ArrayList<>(afStatusHistoryList.stream()
                        .collect(Collectors.toMap(AFStatusHistory::getAssignUser, // Key mapper
                                afStatusHistory -> afStatusHistory, // Value mapper (choose the object itself)
                                (existing, replacement) -> existing)) // Merge function (if duplicates occur, choose the existing object)
                        .values());

                for (AFStatusHistory afStatusHistoryDTO : distinctAfStatusHistoryList) {

                    if (afStatusHistoryDTO.getAssignUser() != null) {

                        if (!afStatusHistoryDTO.getAssignUser().equals(context.getCredentialsDto().getUserName())) {

                            ApplicationFormStatusTransitionRQ emailRQ = new ApplicationFormStatusTransitionRQ();
                            emailRQ.setCredentialsDTO(context.getCredentialsDto());
                            emailRQ.setApplicationFormID(applicationFormStatusChangeRQ.getApplicationFormID());
                            emailRQ.setAfRefNumber(applicationFormStatusChangeRQ.getAfRefNumber());
                            emailRQ.setNewStatus(DomainConstants.ApplicationFormStatus.DECLINED);
                            emailRQ.setSolID(applicationForm.getBranchCode());
                            List<String> afCustomers = new ArrayList<>();

                            if (context.getAFCustomer() != null) {
                                for (AFCustomer afCustomer: context.getAFCustomer()) {
                                    afCustomers.add(afCustomer.getCustomerName());
                                }
                                emailRQ.setCustomerName(afCustomers);

                            }
                            emailRQ.setUserName(afStatusHistoryDTO.getAssignUser());
                            emailRQ.setLastComment(context.getApplicationFormStatusChangeRQ().getAfCommentDTO().getComment());
                            emailRQ.setLastCommentedUser(context.getApplicationFormStatusChangeRQ().getUpdatedByUserDisplayName());
                            emailRQ.setAssignUserDisplayName(afStatusHistoryDTO.getAssignUserDisplayName());
                            emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate()));
                            emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(applicationForm.getBranchCode()).getBranchName());

                            context.addApplicationFormStatusTransitionRQ(emailRQ);

                        }

                    }

                }
            }

        }
    }


    public void sendEmailNotificationReturned(ApplicationFormModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {
            if (context.getApplicationFormStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.DIRECT_USER ) {

                ApplicationForm applicationForm = context.getApplicationForm();
                ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ = context.getApplicationFormStatusChangeRQ();
                ApplicationFormStatusTransitionRQ emailRQ = new ApplicationFormStatusTransitionRQ();

                emailRQ.setCredentialsDTO(context.getCredentialsDto());
                emailRQ.setApplicationFormID(applicationFormStatusChangeRQ.getApplicationFormID());
                emailRQ.setAfRefNumber(applicationFormStatusChangeRQ.getAfRefNumber());
                emailRQ.setSolID(applicationForm.getBranchCode());
                List<String> afCustomers = new ArrayList<>();
                if (context.getAFCustomer() != null) {
                    for (AFCustomer afCustomer: context.getAFCustomer()) {
                        afCustomers.add(afCustomer.getCustomerName());
                    }
                    emailRQ.setCustomerName(afCustomers);
                }

                emailRQ.setLastComment(context.getApplicationFormStatusChangeRQ().getAfCommentDTO().getComment());
                emailRQ.setLastCommentedUser(context.getApplicationFormStatusChangeRQ().getUpdatedByUserDisplayName());

                emailRQ.setUserName(context.getApplicationFormStatusChangeRQ().getAssignUser());
                emailRQ.setAssignUserDisplayName(context.getApplicationFormStatusChangeRQ().getAssignUserDisplayName());

                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate()));
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(applicationForm.getBranchCode()).getBranchName());
                emailRQ.setNewStatus(DomainConstants.ApplicationFormStatus.RETURNED);

                context.addApplicationFormStatusTransitionRQ(emailRQ);

            }
            else if (context.getApplicationFormStatusChangeRQ().getForwardType() == DomainConstants.ForwardType.OTHER_SOL_USER_GROUP ) {

                if (context.getUpmUsers50().getBranchAuthorityLevelResponseDTOList().size() > 0) {

                    for (BranchAuthorityLevelResponseDTO branchAuthorityLevelResponseDTO : context.getUpmUsers50().getBranchAuthorityLevelResponseDTOList()) {

                        ApplicationForm applicationForm = context.getApplicationForm();
                        ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ = context.getApplicationFormStatusChangeRQ();
                        ApplicationFormStatusTransitionRQ emailRQ = new ApplicationFormStatusTransitionRQ();

                        emailRQ.setCredentialsDTO(context.getCredentialsDto());
                        emailRQ.setApplicationFormID(applicationFormStatusChangeRQ.getApplicationFormID());
                        emailRQ.setAfRefNumber(applicationFormStatusChangeRQ.getAfRefNumber());
                        emailRQ.setSolID(applicationForm.getBranchCode());
                        List<String> afCustomers = new ArrayList<>();
                        if (context.getAFCustomer() != null) {
                            for (AFCustomer afCustomer: context.getAFCustomer()) {
                                afCustomers.add(afCustomer.getCustomerName());
                            }
                            emailRQ.setCustomerName(afCustomers);
                        }
                        emailRQ.setUserName(branchAuthorityLevelResponseDTO.getAdUserID());
                        emailRQ.setLastComment(context.getApplicationFormStatusChangeRQ().getAfCommentDTO().getComment());
                        emailRQ.setLastCommentedUser(context.getApplicationFormStatusChangeRQ().getUpdatedByUserDisplayName());
                        emailRQ.setAssignUserDisplayName(branchAuthorityLevelResponseDTO.getFirstName().concat(" ").concat(branchAuthorityLevelResponseDTO.getLastName()));
                        emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate()));
                        emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(applicationForm.getBranchCode()).getBranchName());
                        emailRQ.setNewStatus(DomainConstants.ApplicationFormStatus.RETURNED_TO_USER_GROUP);

                        context.addApplicationFormStatusTransitionRQ(emailRQ);

                    }
                }

            }


        }
    }


    public void sendEmailNotificationFacilityPaperCreated(ApplicationFormModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {
            ApplicationForm applicationForm = context.getApplicationForm();
            FacilityPaperGenerateRQ facilityPaperGenerateRQ = context.getFacilityPaperGenerateRQ();
            ApplicationFormDTO applicationFormDTO = new ApplicationFormDTO(applicationForm);

            if (applicationForm.getAfStatusHistorySet() != null) {

                List<AFStatusHistory> afStatusHistoryList = new ArrayList<>(applicationForm.getAfStatusHistorySet());

                List<AFStatusHistory> distinctAfStatusHistoryList = new ArrayList<>(afStatusHistoryList.stream()
                        .collect(Collectors.toMap(AFStatusHistory::getAssignUser, // Key mapper
                                afStatusHistory -> afStatusHistory, // Value mapper (choose the object itself)
                                (existing, replacement) -> existing)) // Merge function (if duplicates occur, choose the existing object)
                        .values());

                if (distinctAfStatusHistoryList.size() > 0) {

                    for (AFStatusHistory afStatusHistoryDTO : distinctAfStatusHistoryList) {
                        if (afStatusHistoryDTO.getAssignUser() != null) {

                            if (!afStatusHistoryDTO.getAssignUser().equals(context.getCredentialsDto().getUserName())) {

                                ApplicationFormStatusTransitionRQ emailRQ = new ApplicationFormStatusTransitionRQ();
                                emailRQ.setCredentialsDTO(context.getCredentialsDto());
                                emailRQ.setApplicationFormID(applicationForm.getApplicationFormID());
                                emailRQ.setAfRefNumber(applicationForm.getAfRefNumber());
                                emailRQ.setNewStatus(DomainConstants.ApplicationFormStatus.PAPER_CREATED);
                                emailRQ.setSolID(applicationForm.getBranchCode());
                                List<String> afCustomers = new ArrayList<>();
                                if (context.getAFCustomer() != null) {
                                    for (AFCustomer afCustomer: context.getAFCustomer()) {
                                        afCustomers.add(afCustomer.getCustomerName());
                                    }
                                    emailRQ.setCustomerName(afCustomers);
                                }
                                emailRQ.setUserName(afStatusHistoryDTO.getAssignUser());
                                emailRQ.setLastComment(context.getFacilityPaperGenerateRQ().getAfCommentDTO().getComment());
                                emailRQ.setLastCommentedUser(context.getFacilityPaperGenerateRQ().getCreatedUserDisplayName());
                                emailRQ.setAssignUserDisplayName(afStatusHistoryDTO.getAssignUserDisplayName());
                                emailRQ.setPaperCreatedDate(CalendarUtil.getDefaultFormattedDateTime(applicationForm.getCreatedDate()));
                                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(applicationForm.getBranchCode()).getBranchName());

                                context.addApplicationFormStatusTransitionRQ(emailRQ);

                            }

                        }

                    }


                }


            }

        }
    }

}
