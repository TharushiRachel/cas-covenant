package com.itechro.cas.service.coveringApproval.command;

import com.itechro.cas.commons.command.ExecutionContext;
import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import com.itechro.cas.model.dto.coveringApproval.CoveringApprovalStatusChangeRQ;
import com.itechro.cas.model.dto.coveringApproval.CoveringApprovalStatusTransitionRQ;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.command.ApplicationFormModificationContext;
import lombok.Data;
import lombok.ToString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@ToString
public class CoveringApprovalModificationContext extends ExecutionContext {

    private static final Logger LOG = LoggerFactory.getLogger(CoveringApprovalModificationContext.class);
    private static final String COVERING_APPROVAL = "covering_approval";
    private static final String COVERING_APPROVAL_STATUS_CHANGE_RQ = "covering_approval_status_change_rq";
    private static final String CREDENTIALS_DTO = "credentials_dto";
    private static final String DATE = "date";
    private static final String COVERING_APPROVAL_STATUS_TRANSITION_RQ = "covering_approval_status_transition_rq";
    private static final String BRANCH_LIST = "branch_List";

    public CoveringApproval getCoveringApproval() {
        return getParam(COVERING_APPROVAL);
    }

    public void setCoveringApproval(CoveringApproval coveringApproval) {
        putParam(COVERING_APPROVAL, coveringApproval);
    }

    public CredentialsDTO getCredentialDto() {
        return getParam(CREDENTIALS_DTO);
    }

    public void setCredentialsDto(CredentialsDTO credentialsDTO) {
        putParam(CREDENTIALS_DTO, credentialsDTO);
    }

    public Date getDate() {
        return getParam(DATE);
    }

    public void setDate(Date date) {
        putParam(DATE, date);
    }

    public CoveringApprovalStatusChangeRQ getCoveringApprovalStatusChangeRQ() {
        return getParam(COVERING_APPROVAL_STATUS_CHANGE_RQ);
    }

    public void setCoveringApprovalStatusChangeRQ(CoveringApprovalStatusChangeRQ coveringApprovalStatusChangeRQ) {
        putParam(COVERING_APPROVAL_STATUS_CHANGE_RQ, coveringApprovalStatusChangeRQ);
    }

    public BranchLoadResponseListDTO getBranchLoadResponseListDTO() {
        return getParam(BRANCH_LIST);
    }

    public void setBranchLoadResponseListDTO(BranchLoadResponseListDTO branchLoadResponseListDTO) {
        putParam(BRANCH_LIST, branchLoadResponseListDTO);
    }

    public List<CoveringApprovalStatusTransitionRQ> getCoveringApprovalStatusTransitionRQ() {
        return getParam(COVERING_APPROVAL_STATUS_TRANSITION_RQ);
    }

    public void setCoveringApprovalStatusTransitionRQ(List<CoveringApprovalStatusTransitionRQ> emailRQList) {
        putParam(COVERING_APPROVAL_STATUS_TRANSITION_RQ, emailRQList);
    }

    public void addCoveringApprovalStatusTransitionRQ(CoveringApprovalStatusTransitionRQ emailRQ) {
        if (getCoveringApprovalStatusTransitionRQ() == null) {
            setCoveringApprovalStatusTransitionRQ(new ArrayList<>());
        }
        getCoveringApprovalStatusTransitionRQ().add(emailRQ);
    }
}