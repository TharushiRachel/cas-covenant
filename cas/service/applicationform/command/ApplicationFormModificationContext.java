package com.itechro.cas.service.applicationform.command;

import com.itechro.cas.commons.command.ExecutionContext;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.AFCustomer;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusChangeRQ;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusTransitionRQ;
import com.itechro.cas.model.dto.applicationform.FacilityPaperGenerateRQ;
import com.itechro.cas.model.dto.casmaster.UserDaDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperUpdateDTO;
import com.itechro.cas.model.dto.integration.response.BranchAuthorityLevelResponseListDTO;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.notification.support.ApplicationFormEmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ApplicationFormModificationContext extends ExecutionContext {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormModificationContext.class);

    private static final String APPLICATION_FORM = "application_form";
    private static final String APPLICATION_FORM_STATUS_CHANGE_RQ = "application_form_status_change_rq";

    private static final String CREDENTIALS_DTO = "credentials_dto";

    private static final String DATE = "date";

    private static final String APPLICATION_FORM_UPDATE_DTO = "application_form_update_dto";

    private static final String USER_DA_DTO = "user_da_dto";

    private static final String APPLICATION_FORM_STATUS_TRANSITION_RQ = "application_form_status_transition_rq";

    private static final String BRANCH_LIST = "branch_List";

    private static final String CUSTOMER_DTO = "customer_dto";

    private static final String CAS_CUSTOMER_DTO = "cas_customer_dto";

    private static final String AF_CUSTOMER = "af_customer";

    private static final String FACILITY_PAPER_GENERATE_RQ = "facility_paper_generate_rq";

    private static final String UPM_USERS_50 = "upm_users_50";


    public ApplicationForm getApplicationForm() {
        return getParam(APPLICATION_FORM);
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        putParam(APPLICATION_FORM, applicationForm);
    }

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

    public ApplicationFormStatusChangeRQ getApplicationFormStatusChangeRQ() {
        return getParam(APPLICATION_FORM_STATUS_CHANGE_RQ);
    }


    public void setApplicationFormStatusChangeRQ(ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ) {
        putParam(APPLICATION_FORM_STATUS_CHANGE_RQ, applicationFormStatusChangeRQ);
    }

    public UserDaDTO getUserDaDTO() {
        return getParam(USER_DA_DTO);
    }

    public void setUserDaDTO(UserDaDTO userDaDTO) {
        putParam(USER_DA_DTO, userDaDTO);
    }


    public List<ApplicationFormStatusTransitionRQ> getApplicationFormStatusTransitionRQ() {
        LOG.info("getParam(APPLICATION_FORM_STATUS_TRANSITION_RQ) {} ", APPLICATION_FORM_STATUS_TRANSITION_RQ);
        return getParam(APPLICATION_FORM_STATUS_TRANSITION_RQ);
    }

    public void setApplicationFormStatusTransitionRQ(List<ApplicationFormStatusTransitionRQ> emailRQList) {
        putParam(APPLICATION_FORM_STATUS_TRANSITION_RQ, emailRQList);
    }

    public void addApplicationFormStatusTransitionRQ(ApplicationFormStatusTransitionRQ emailRQ) {
        if (getApplicationFormStatusTransitionRQ() == null) {
            putParam(APPLICATION_FORM_STATUS_TRANSITION_RQ, new ArrayList<>());
        }
        getApplicationFormStatusTransitionRQ().add(emailRQ);
    }

    public BranchLoadResponseListDTO getBranchLoadResponseListDTO() {
        return getParam(BRANCH_LIST);
    }

    public void setBranchLoadResponseListDTO(BranchLoadResponseListDTO branchLoadResponseListDTO) {
        putParam(BRANCH_LIST, branchLoadResponseListDTO);
    }

    public CustomerDTO getCustomerDto() {
        return getParam(CUSTOMER_DTO);
    }

    public void setCustomerDto(CustomerDTO customerDto) {
        putParam(CUSTOMER_DTO, customerDto);
    }

    public CASCustomerDTO getCasCustomerDto() {
        return getParam(CAS_CUSTOMER_DTO);
    }

    public void setCasCustomerDto(CASCustomerDTO casCustomerDto) {
        putParam(CAS_CUSTOMER_DTO, casCustomerDto);
    }

    public List<AFCustomer> getAFCustomer() {
        return getParam(AF_CUSTOMER);
    }

    public void setAFCustomer(List<AFCustomer> afCustomer) {
        putParam(AF_CUSTOMER, afCustomer);
    }

    public FacilityPaperGenerateRQ getFacilityPaperGenerateRQ() {
        return getParam(FACILITY_PAPER_GENERATE_RQ);
    }


    public void setFacilityPaperGenerateRQ(FacilityPaperGenerateRQ facilityPaperGenerateRQ) {
        putParam(FACILITY_PAPER_GENERATE_RQ, facilityPaperGenerateRQ);
    }

    public BranchAuthorityLevelResponseListDTO getUpmUsers50() {
        return getParam(UPM_USERS_50);
    }

    public void setUpmUsers50(BranchAuthorityLevelResponseListDTO branchAuthorityLevelResponseListDTO) {
        putParam(UPM_USERS_50, branchAuthorityLevelResponseListDTO);
    }


}
