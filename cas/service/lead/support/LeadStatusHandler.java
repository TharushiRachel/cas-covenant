package com.itechro.cas.service.lead.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.domain.lead.LeadAction;
import com.itechro.cas.model.domain.lead.LeadComment;
import com.itechro.cas.model.domain.lead.LeadFacilityDetail;
import com.itechro.cas.model.dto.integration.response.BranchAuthorityLevelResponseDTO;
import com.itechro.cas.model.dto.lead.LeadFacilityDetailDTO;
import com.itechro.cas.model.dto.lead.LeadFacilityDetailEmailDTO;
import com.itechro.cas.model.dto.lead.LeadStatusTransitionRQ;
import com.itechro.cas.model.dto.lead.LeadStatusUpdateRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.integration.IntegrationService;
import com.itechro.cas.service.lead.command.LeadModificationContext;
import com.itechro.cas.util.NumberUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Date;

public class LeadStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(LeadStatusHandler.class);

    protected Lead lead;

    protected LeadStatusUpdateRQ leadStatusUpdateRQ;

    protected CredentialsDTO credentialDTO;

    protected Date date;
    @Autowired
    private IntegrationService integrationService;

    private CasProperties casProperties;

    public LeadStatusHandler() {
    }

    public LeadStatusHandler(Lead lead, LeadStatusUpdateRQ leadStatusUpdateRQ, CredentialsDTO credentialsDTO, CasProperties casProperties) {
        this.lead = lead;
        this.leadStatusUpdateRQ = leadStatusUpdateRQ;
        this.credentialDTO = credentialsDTO;
        this.date = new Date();
        this.casProperties = casProperties;
    }

    public CasProperties getCasProperties() {
        return casProperties;
    }

    public void setLeadStatusUpdateRQ(LeadStatusUpdateRQ leadStatusUpdateRQ) {
        this.leadStatusUpdateRQ = leadStatusUpdateRQ;
    }

    public void updateLead() throws AppsException {

    }

    public void recordLeadAction() throws AppsException {
        LeadAction leadAction = new LeadAction();
        leadAction.setActionedBy(credentialDTO.getUserName());
        leadAction.setActionedByDisplayName(leadStatusUpdateRQ.getActionedByDisplayName());
        leadAction.setAction(leadStatusUpdateRQ.getAction());
        leadAction.setActionedTimestamp(date);
        leadAction.setFromLeadStatus(lead.getLeadStatus());
        leadAction.setToLeadStatus(leadStatusUpdateRQ.getLeadStatus());
        leadAction.setAssignUserID(leadStatusUpdateRQ.getAssignUserID());
        leadAction.setRemark(leadStatusUpdateRQ.getRemark());
        lead.addLeadAction(leadAction);
    }

    public void transitStatus() throws AppsException {
        if (isValidStatusTransition()) {
            LOG.info("START: Lead Update: {} :Status {}", lead.getLeadRefNumber(), lead.getLeadStatus());
            lead.setModifiedBy(credentialDTO.getUserName());
            lead.setModifiedDate(date);
            lead.setLeadStatus(leadStatusUpdateRQ.getLeadStatus());
            LOG.info("END: Lead Update: {} :Status {}", lead.getLeadRefNumber(), lead.getLeadStatus());
        } else {
            LOG.error("ERROR: Invalid transition request :lead {} :Status {}", lead.getLeadRefNumber(), lead.getLeadStatus());
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_INVALID_LEAD_STATUS_CHANGE);
        }
    }

    public Lead getLead() {
        return lead;
    }

    public boolean isValidStatusTransition() {
        return true;
    }

    public void sendEmailNotificationSubmitted(LeadModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {

            if (context.getUpmUsers50().getBranchAuthorityLevelResponseDTOList().size() > 0) {

                for (BranchAuthorityLevelResponseDTO branchAuthorityLevelResponseDTO : context.getUpmUsers50().getBranchAuthorityLevelResponseDTOList()) {
                    LeadStatusUpdateRQ leadStatusUpdateRQ = context.getLeadStatusUpdateRQ();
                    LeadStatusTransitionRQ emailRQ = new LeadStatusTransitionRQ();

                    emailRQ.setCredentialsDTO(context.getCredentialsDto());
                    emailRQ.setLeadID(leadStatusUpdateRQ.getLeadID());
                    emailRQ.setNewStatus(context.getLeadStatusUpdateRQ().getLeadStatus());
                    emailRQ.setLead(context.getLead());
                    emailRQ.setLeadDTO(context.getLeadDto());

//                    if (context.getCustomer() != null) {
//                        emailRQ.setCustomerName(context.getCustomer().getCustomerName());
//                    } else if (context.getCasCustomerDto() != null) {
//                        emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
//                    }
                    emailRQ.setCustomerName(context.getLead().getName());
                    emailRQ.setUserName(branchAuthorityLevelResponseDTO.getAdUserID());

//                    if (context.getLeadStatusUpdateRQ().getRemark() != null) {
//                        emailRQ.setLastComment(context.getLeadStatusUpdateRQ().getRemark());
//                        emailRQ.setLastCommentedUser(context.getLeadStatusUpdateRQ().getActionedByDisplayName());
//                    } else if (context.getLead().getLeadActions().size()>0) {
//                        LeadAction leadAction = context.getLead().getLeadActions().stream().max(Comparator.comparing(LeadAction::getActionedTimestamp))
//                                .orElse(null);
//                        if (leadAction.getRemark() != null) {
//                            emailRQ.setLastComment(leadAction.getRemark());
//                        }
//                        emailRQ.setLastCommentedUser(leadAction.getActionedByDisplayName());
//
//                    }

//                    if (context.getLead().getLeadCommentSet().size() > 0) {
//
//                        LeadComment leadComment = context.getLead().getLeadCommentSet().stream()
//                                .filter(user -> user.getCreatedBy().equals(credentialDTO.getUserName()))
//                                .max(Comparator.comparing(LeadComment::getCreatedDate))
//                                .orElse(null);
//                        if (leadComment != null) {
//                            emailRQ.setLastComment(leadComment.getUserComment());
//                        }
//                    }
                    emailRQ.setLastCommentedUser(context.getLeadStatusUpdateRQ().getActionedByDisplayName());

                    emailRQ.setAssignUserDisplayName(branchAuthorityLevelResponseDTO.getFirstName().concat(" ").concat(branchAuthorityLevelResponseDTO.getLastName()));
                    emailRQ.setPaperCreatedDate(context.getLeadDto().getCreatedDateStr());
                    if (context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName() != null) {

                        emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName());
                        emailRQ.setSolID(context.getCreatedUserResponse().getSolDivID());
                    }
                    emailRQ.setLeadRefNo(context.getLead().getLeadRefNumber());
//                    emailRQ.setAction(DomainConstants.LeadStatus.SUBMITTED.toString().toLowerCase());

                    for (LeadFacilityDetail leadFacilityDetail : context.getLead().getLeadFacilityDetails()) {
                        if (leadFacilityDetail.getStatus() == AppsConstants.Status.ACT) {
                            LeadFacilityDetailEmailDTO leadFacilityDetailEmailDTO = new LeadFacilityDetailEmailDTO(leadFacilityDetail);
                            leadFacilityDetailEmailDTO.setAmount(NumberUtil.getFormattedAmount(leadFacilityDetail.getAmount()));
                            emailRQ.getLeadFacilityDetailEmailDTO().add(leadFacilityDetailEmailDTO);
                        }
                    }

                    context.addLeadStatusTransitionRQ(emailRQ);

                }

            }
        }
    }


    public void sendEmailNotificationReturned(LeadModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {

            LeadStatusUpdateRQ leadStatusUpdateRQ = context.getLeadStatusUpdateRQ();
            LeadStatusTransitionRQ emailRQ = new LeadStatusTransitionRQ();

            emailRQ.setCredentialsDTO(context.getCredentialsDto());
            emailRQ.setLeadID(leadStatusUpdateRQ.getLeadID());
            emailRQ.setNewStatus(context.getLeadStatusUpdateRQ().getLeadStatus());
            emailRQ.setLead(context.getLead());
            emailRQ.setLeadDTO(context.getLeadDto());

//            if (context.getCustomer() != null) {
//                emailRQ.setCustomerName(context.getCustomer().getCustomerName());
//            } else if (context.getCasCustomerDto() != null) {
//                emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
//            }
            emailRQ.setCustomerName(context.getLead().getName());
//            emailRQ.setAction(DomainConstants.LeadStatus.RETURNED.toString().toLowerCase());
            emailRQ.setUserName(context.getLeadStatusUpdateRQ().getAssignUserID());
            emailRQ.setLastComment(context.getLeadStatusUpdateRQ().getRemark());
            emailRQ.setLastCommentedUser(context.getLeadStatusUpdateRQ().getActionedByDisplayName());
            emailRQ.setAssignUserDisplayName(context.getLeadStatusUpdateRQ().getAssignUserDisplayName());
            emailRQ.setPaperCreatedDate(context.getLeadDto().getCreatedDateStr());
            if (context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName() != null) {
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName());
                emailRQ.setSolID(context.getCreatedUserResponse().getSolDivID());
            }

            emailRQ.setLeadRefNo(context.getLead().getLeadRefNumber());

            for (LeadFacilityDetail leadFacilityDetail : context.getLead().getLeadFacilityDetails()) {
                if (leadFacilityDetail.getStatus() == AppsConstants.Status.ACT) {
                    LeadFacilityDetailEmailDTO leadFacilityDetailEmailDTO = new LeadFacilityDetailEmailDTO(leadFacilityDetail);
                    leadFacilityDetailEmailDTO.setAmount(NumberUtil.getFormattedAmount(leadFacilityDetail.getAmount()));
                    emailRQ.getLeadFacilityDetailEmailDTO().add(leadFacilityDetailEmailDTO);
                }
            }

            context.addLeadStatusTransitionRQ(emailRQ);

        }
    }


    public void sendEmailNotificationDeclined(LeadModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {

            LeadStatusUpdateRQ leadStatusUpdateRQ = context.getLeadStatusUpdateRQ();
            LeadStatusTransitionRQ emailRQ = new LeadStatusTransitionRQ();

            emailRQ.setCredentialsDTO(context.getCredentialsDto());
            emailRQ.setLeadID(leadStatusUpdateRQ.getLeadID());
            emailRQ.setNewStatus(context.getLeadStatusUpdateRQ().getLeadStatus());
            emailRQ.setLead(context.getLead());
            emailRQ.setLeadDTO(context.getLeadDto());

//            if (context.getCustomer() != null) {
//                emailRQ.setCustomerName(context.getCustomer().getCustomerName());
//            } else if (context.getCasCustomerDto() != null) {
//                emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
//            }
            emailRQ.setCustomerName(context.getLead().getName());
//            emailRQ.setAction(DomainConstants.LeadStatus.DECLINED.toString().toLowerCase());
            emailRQ.setUserName(context.getLead().getCreatedBy());
            emailRQ.setLastComment(context.getLeadStatusUpdateRQ().getRemark());
            emailRQ.setLastCommentedUser(context.getLeadStatusUpdateRQ().getActionedByDisplayName());
            emailRQ.setAssignUserDisplayName(context.getLead().getCreatedUserDisplayName());
            emailRQ.setPaperCreatedDate(context.getLeadDto().getCreatedDateStr());
            if (context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName() != null) {
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName());
                emailRQ.setSolID(context.getCreatedUserResponse().getSolDivID());
            }

            emailRQ.setLeadRefNo(context.getLead().getLeadRefNumber());

            for (LeadFacilityDetail leadFacilityDetail : context.getLead().getLeadFacilityDetails()) {
                if (leadFacilityDetail.getStatus() == AppsConstants.Status.ACT) {
                    LeadFacilityDetailEmailDTO leadFacilityDetailEmailDTO = new LeadFacilityDetailEmailDTO(leadFacilityDetail);
                    leadFacilityDetailEmailDTO.setAmount(NumberUtil.getFormattedAmount(leadFacilityDetail.getAmount()));
                    emailRQ.getLeadFacilityDetailEmailDTO().add(leadFacilityDetailEmailDTO);
                }
            }

            context.addLeadStatusTransitionRQ(emailRQ);

        }
    }


    public void sendEmailNotificationAccepted(LeadModificationContext context) throws AppsException {

        if (getCasProperties().isAllowSendingEmail()) {

            LeadStatusUpdateRQ leadStatusUpdateRQ = context.getLeadStatusUpdateRQ();
            LeadStatusTransitionRQ emailRQ = new LeadStatusTransitionRQ();
            LOG.info("context.getLeadStatusUpdateRQ().getLeadStatus(){}",context.getLeadStatusUpdateRQ().getLeadStatus());
            emailRQ.setCredentialsDTO(context.getCredentialsDto());
            emailRQ.setLeadID(leadStatusUpdateRQ.getLeadID());
            emailRQ.setNewStatus(context.getLeadStatusUpdateRQ().getLeadStatus());
            emailRQ.setLead(context.getLead());
            emailRQ.setLeadDTO(context.getLeadDto());

//            if (context.getCustomer() != null) {
//                emailRQ.setCustomerName(context.getCustomer().getCustomerName());
//            } else if (context.getCasCustomerDto() != null) {
//                emailRQ.setCustomerName(context.getCasCustomerDto().getCasCustomerName());
//            }
            emailRQ.setCustomerName(context.getLead().getName());
//            emailRQ.setAction(DomainConstants.LeadStatus.ACCEPTED.toString().toLowerCase());
            emailRQ.setUserName(context.getLead().getCreatedBy());
            emailRQ.setLastComment(context.getLeadStatusUpdateRQ().getRemark());
            emailRQ.setLastCommentedUser(context.getLeadStatusUpdateRQ().getActionedByDisplayName());
            emailRQ.setAssignUserDisplayName(context.getLead().getCreatedUserDisplayName());
            emailRQ.setPaperCreatedDate(context.getLeadDto().getCreatedDateStr());
            if (context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName() != null) {
                emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName());
                emailRQ.setSolID(context.getCreatedUserResponse().getSolDivID());
            }
            emailRQ.setBranchName(context.getBranchLoadResponseListDTO().getBranchResponse(context.getCreatedUserResponse().getSolDivID()).getBranchName());
            emailRQ.setSolID(context.getCreatedUserResponse().getSolDivID());
            emailRQ.setLeadRefNo(context.getLead().getLeadRefNumber());

            for (LeadFacilityDetail leadFacilityDetail : context.getLead().getLeadFacilityDetails()) {
                if (leadFacilityDetail.getStatus() == AppsConstants.Status.ACT) {
                    LeadFacilityDetailEmailDTO leadFacilityDetailEmailDTO = new LeadFacilityDetailEmailDTO(leadFacilityDetail);
                    leadFacilityDetailEmailDTO.setAmount(NumberUtil.getFormattedAmount(leadFacilityDetail.getAmount()));
                    emailRQ.getLeadFacilityDetailEmailDTO().add(leadFacilityDetailEmailDTO);
                }
            }

            context.addLeadStatusTransitionRQ(emailRQ);

        }
    }

}
