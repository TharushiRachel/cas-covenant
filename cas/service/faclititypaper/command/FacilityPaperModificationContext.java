package com.itechro.cas.service.faclititypaper.command;

import com.itechro.cas.commons.command.ExecutionContext;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.casmaster.UserDaDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.CASCustomerDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperStatusTransitionRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperUpdateDTO;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.security.CredentialsDTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FacilityPaperModificationContext extends ExecutionContext {

    private static final String FACILITY_PAPER = "facility_paper";

    private static final String CREDENTIALS_DTO = "credentials_dto";

    private static final String DATE = "date";

    private static final String FACILITY_PAPER_UPDATE_DTO = "facility_paper_update_dto";

    private static final String USER_DA_DTO = "user_da_dto";

    private static final String FACILITY_PAPER_STATUS_TRANSITION_RQ = "facility_paper_status_transition_rq";

    private static final String BRANCH_LIST = "branch_List";

    private static final String CUSTOMER_DTO = "customer_dto";

    private static final String CAS_CUSTOMER_DTO = "cas_customer_dto";

    public FacilityPaper getFacilityPaper() {
        return getParam(FACILITY_PAPER);
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        putParam(FACILITY_PAPER, facilityPaper);
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

    public FacilityPaperUpdateDTO getFacilityPaperUpdateDto() {
        return getParam(FACILITY_PAPER_UPDATE_DTO);
    }


    public void setFacilityPaperUpdateDto(FacilityPaperUpdateDTO facilityPaperUpdateDto) {
        putParam(FACILITY_PAPER_UPDATE_DTO, facilityPaperUpdateDto);
    }

    public UserDaDTO getUserDaDTO() {
        return getParam(USER_DA_DTO);
    }

    public void setUserDaDTO(UserDaDTO userDaDTO) {
        putParam(USER_DA_DTO, userDaDTO);
    }


    public List<FacilityPaperStatusTransitionRQ> getFacilityPaperStatusTransitionRQ() {
        return getParam(FACILITY_PAPER_STATUS_TRANSITION_RQ);
    }

    public void setFacilityPaperStatusTransitionRQ(List<FacilityPaperStatusTransitionRQ> emailRQList) {
        putParam(FACILITY_PAPER_STATUS_TRANSITION_RQ, emailRQList);
    }

    public void addFacilityPaperStatusTransitionRQ(FacilityPaperStatusTransitionRQ emailRQ) {
        if (getFacilityPaperStatusTransitionRQ() == null) {
            putParam(FACILITY_PAPER_STATUS_TRANSITION_RQ, new ArrayList<>());
        }
        getFacilityPaperStatusTransitionRQ().add(emailRQ);
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
}
