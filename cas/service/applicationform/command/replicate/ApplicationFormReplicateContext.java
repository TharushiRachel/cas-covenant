package com.itechro.cas.service.applicationform.command.replicate;

import com.itechro.cas.commons.command.ExecutionContext;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.dto.applicationform.ReplicateApplicationFormRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class ApplicationFormReplicateContext extends ExecutionContext {

    private static final String CURRENT_APPLICATION_FORM = "current_application_form";

    private static final String NEW_APPLICATION_FORM = "new_application_form";

    private static final String CREDENTIALS_DTO = "credentials_dto";

    private static final String DATE = "date";

    private static final String APPLICATION_FORM_REPLICATE_RQ = "application_form_replicate_rq";

    private static final String WORK_FLOW_TEMPLATE = "work_flow_template";

    private static final String NEW_APPLICATION_FORM_REF_NUMBER = "new_application_from_ref_number";

    public ApplicationForm getCurrentApplicationForm() {
        return getParam(CURRENT_APPLICATION_FORM);
    }

    public void setCurrentApplicationForm(ApplicationForm applicationForm) {
        putParam(CURRENT_APPLICATION_FORM, applicationForm);
    }

    public ApplicationForm getNewApplicationForm() {
        return getParam(NEW_APPLICATION_FORM);
    }

    public void setNewApplicationForm(ApplicationForm applicationForm) {
        putParam(NEW_APPLICATION_FORM, applicationForm);
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

    public ReplicateApplicationFormRQ getReplicateApplicationFromFormRQ() {
        return getParam(APPLICATION_FORM_REPLICATE_RQ);
    }

    public void setReplicateApplicationFromFormRQ(ReplicateApplicationFormRQ applicationFormDto) {
        putParam(APPLICATION_FORM_REPLICATE_RQ, applicationFormDto);
    }

    public void setWorkflowTemplate(WorkFlowTemplate workflowTemplate) {
        putParam(WORK_FLOW_TEMPLATE, workflowTemplate);
    }

    public void setNewApplicationFormRefNumber(String refNumber) {
        putParam(NEW_APPLICATION_FORM_REF_NUMBER, refNumber);
    }

    public WorkFlowTemplate getWorkFlowTemplate() {
        return getParam(WORK_FLOW_TEMPLATE);
    }

    public String getNewApplicationFormRefNumber() {
        return getParam(NEW_APPLICATION_FORM_REF_NUMBER);
    }
}
