package com.itechro.cas.service.lead.command;

import com.itechro.cas.commons.command.ExecutionContext;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.lead.Lead;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusChangeRQ;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperUpdateDTO;
import com.itechro.cas.model.dto.integration.response.BranchAuthorityLevelResponseListDTO;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.dto.integration.response.UpmDetailResponse;
import com.itechro.cas.model.dto.lead.LeadDTO;
import com.itechro.cas.model.dto.lead.LeadStatusTransitionRQ;
import com.itechro.cas.model.dto.lead.LeadStatusUpdateRQ;
import com.itechro.cas.model.security.CredentialsDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class LeadModificationContext extends ExecutionContext {

    private static final String CREDENTIALS_DTO = "credentials_dto";

    private static final String LEAD_STATUS_UPDATE_RQ = "lead_status_update_rq";

    private static final String LEAD = "lead";

    private static final String DATE = "date";

    private static final String LEAD_UPDATE_DTO = "lead_update_dto";

    private static final String LEAD_STATUS_TRANSITION_RQ = "lead_status_transition_rq";

    private static final String LEAD_DTO = "lead_dto";
    private static final String USER_DA_DTO = "user_da_dto";

    private static final String BRANCH_LIST = "branch_List";

    private static final String CUSTOMER = "customer";

    private static final String CAS_CUSTOMER_DTO = "cas_customer_dto";

    private static final String UPM_USERS_50 = "upm_users_50";

    private static final String CREATED_USER_RESPONSE = "created_user_response";


    public CredentialsDTO getCredentialsDto() {
        return getParam(CREDENTIALS_DTO);
    }

    public void setCredentialsDto(CredentialsDTO credentialsDto) {
        putParam(CREDENTIALS_DTO, credentialsDto);
    }

    public Date getDate() {
        return getParam(DATE);
    }

    public void setDate(Date date) {
        putParam(DATE, date);
    }

//    public LeadDTO getLeadUpdateDto() {
//        return getParam(LEAD_UPDATE_DTO);
//    }


    public void setLeadUpdateDto(LeadStatusUpdateRQ leadStatusUpdateRQ) {
        putParam(LEAD_UPDATE_DTO, leadStatusUpdateRQ);
    }



    public List<LeadStatusTransitionRQ> getLeadStatusTransitionRQ() {
        return getParam(LEAD_STATUS_TRANSITION_RQ);
    }

    public void setLeadStatusTransitionRQ(List<LeadStatusTransitionRQ> emailRQList) {
        putParam(LEAD_STATUS_TRANSITION_RQ, emailRQList);
    }

    public void addLeadStatusTransitionRQ(LeadStatusTransitionRQ emailRQ) {
        if (getLeadStatusTransitionRQ() == null) {
            putParam(LEAD_STATUS_TRANSITION_RQ, new ArrayList<>());
        }
        getLeadStatusTransitionRQ().add(emailRQ);
    }

    public BranchLoadResponseListDTO getBranchLoadResponseListDTO() {
        return getParam(BRANCH_LIST);
    }

    public void setBranchLoadResponseListDTO(BranchLoadResponseListDTO branchLoadResponseListDTO) {
        putParam(BRANCH_LIST, branchLoadResponseListDTO);
    }


    public Customer getCustomer() {
        return getParam(CUSTOMER);
    }

    public void setCustomer(Customer customer) {
        putParam(CUSTOMER, customer);
    }

    public CASCustomerDTO getCasCustomerDto() {
        return getParam(CAS_CUSTOMER_DTO);
    }

    public void setCasCustomerDto(CASCustomerDTO casCustomerDto) {
        putParam(CAS_CUSTOMER_DTO, casCustomerDto);
    }

    public LeadStatusUpdateRQ getLeadStatusUpdateRQ() {
        return getParam(LEAD_STATUS_UPDATE_RQ);
    }


    public void setLeadStatusUpdateRQ(LeadStatusUpdateRQ leadStatusUpdateRQ) {
        putParam(LEAD_STATUS_UPDATE_RQ, leadStatusUpdateRQ);
    }


    public LeadDTO getLeadDto() {
        return getParam(LEAD_DTO);
    }


    public void setLeadDto(LeadDTO leadDTO) {
        putParam(LEAD_DTO, leadDTO);
    }

    public Lead getLead() {
        return getParam(LEAD);
    }


    public void setLead(Lead lead) {
        putParam(LEAD, lead);
    }

    public BranchAuthorityLevelResponseListDTO getUpmUsers50() {
        return getParam(UPM_USERS_50);
    }

    public void setUpmUsers50(BranchAuthorityLevelResponseListDTO branchAuthorityLevelResponseListDTO) {
        putParam(UPM_USERS_50, branchAuthorityLevelResponseListDTO);
    }

    public UpmDetailResponse getCreatedUserResponse() {
        return getParam(CREATED_USER_RESPONSE);
    }


    public void setCreatedUserResponse(UpmDetailResponse upmDetailResponse) {
        putParam(CREATED_USER_RESPONSE, upmDetailResponse);
    }

}
