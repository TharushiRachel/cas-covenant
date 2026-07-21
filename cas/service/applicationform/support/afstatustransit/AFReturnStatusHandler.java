package com.itechro.cas.service.applicationform.support.afstatustransit;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.config.CasProperties;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.applicationform.AFAssignDepartment;
import com.itechro.cas.model.domain.applicationform.ApplicationForm;
import com.itechro.cas.model.dto.applicationform.AFAssignDepartmentDTO;
import com.itechro.cas.model.dto.applicationform.ApplicationFormStatusChangeRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.support.AFStatusHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class AFReturnStatusHandler extends AFStatusHandler {

    private static final Logger LOG = LoggerFactory.getLogger(AFReturnStatusHandler.class);

    public AFReturnStatusHandler(ApplicationForm applicationForm, CredentialsDTO credentialsDTO, ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ, CasProperties casProperties) {
        super(applicationForm, credentialsDTO, applicationFormStatusChangeRQ, casProperties);
    }

    @Override
    public void updateApplicationForm() throws AppsException {
        if (applicationFormStatusChangeRQ.getForwardType() == DomainConstants.ForwardType.OTHER_SOL_USER_GROUP || applicationFormStatusChangeRQ.getForwardType() == DomainConstants.ForwardType.SAME_SOL_USER_GROUP) {

            if (applicationFormStatusChangeRQ.getAssignDepartmentCode() == null) {
                LOG.error("ERROR: Unable to transfer Application Form  : Invalid Request : {}", applicationFormStatusChangeRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_INVALID_APPLICATION_FORM_UPDATE_REQUEST);
            }

            LOG.info("INFO : Cluster Forwarding Application Form : {}", applicationFormStatusChangeRQ);

            for (AFAssignDepartment afAssignDepartment : applicationForm.getActiveAssignDepartmentSet()) {
                afAssignDepartment.setStatus(AppsConstants.Status.INA);
            }

            for (AFAssignDepartmentDTO afAssignDepartmentDTO : applicationFormStatusChangeRQ.getAfAssignDepartmentDTOList()) {

                AFAssignDepartment afAssignDepartment =
                        applicationForm.getActiveAssignDepartmentBySolIDAndUserGroupUPMGroup(afAssignDepartmentDTO.getDivCode(), afAssignDepartmentDTO.getUserGroupUPMCode());

                if (afAssignDepartment == null) {
                    afAssignDepartment = new AFAssignDepartment();
                    applicationForm.addAFAssignDepartment(afAssignDepartment);
                    afAssignDepartment.setCreatedBy(credentialDTO.getUserName());
                    afAssignDepartment.setCreatedDate(new Date());
                } else {
                    afAssignDepartment.setModifiedBy(credentialDTO.getUserName());
                    afAssignDepartment.setModifiedDate(new Date());
                }
                afAssignDepartment.setStatus(AppsConstants.Status.ACT);
                afAssignDepartment.setDivCode(afAssignDepartmentDTO.getDivCode());
                afAssignDepartment.setDepartmentName(afAssignDepartmentDTO.getDepartmentName());
                afAssignDepartment.setUserGroupUPMCode(afAssignDepartmentDTO.getUserGroupUPMCode());
                afAssignDepartment.setUserGroupName(afAssignDepartmentDTO.getUserGroupName());
            }

            applicationForm.setAssignDepartmentCode(applicationFormStatusChangeRQ.getAssignDepartmentCode());// Application Form is always forward to one Branch or Department (Sol ID) if multiple the this filed must remove and use the AssignDepartmentSet
            applicationForm.setAssignUserUpmID(null);
            applicationForm.setAssignUser(null);
            applicationForm.setAssignUserID(null);
            applicationForm.setAssignUserUpmGroupCode(null);
            applicationForm.setAssignUserDisplayName(null);
            applicationForm.setAssignUserDivCode(null);
        } else {
            if (applicationFormStatusChangeRQ.getAssignUser() == null || applicationFormStatusChangeRQ.getAssignUserID() == null) {
                LOG.error("ERROR: Unable to transfer Application Form  : Invalid Request : {}", applicationFormStatusChangeRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_INVALID_APPLICATION_FORM_UPDATE_REQUEST);
            }
            LOG.info("INFO : Direct User Forwarding Application Form : {}", applicationFormStatusChangeRQ);
            for (AFAssignDepartment afAssignDepartment : applicationForm.getActiveAssignDepartmentSet()) {
                afAssignDepartment.setStatus(AppsConstants.Status.INA);
            }
            applicationForm.setAssignUserUpmID(applicationFormStatusChangeRQ.getAssignUserUpmID());
            applicationForm.setAssignUser(applicationFormStatusChangeRQ.getAssignUser());
            applicationForm.setAssignUserID(applicationFormStatusChangeRQ.getAssignUserID());
            applicationForm.setAssignUserUpmGroupCode(applicationFormStatusChangeRQ.getAssignUserUpmGroupCode());
            applicationForm.setAssignUserDisplayName(applicationFormStatusChangeRQ.getAssignUserDisplayName());
            applicationForm.setAssignUserDivCode(applicationFormStatusChangeRQ.getAssignUserDivCode());
            applicationForm.setAssignDepartmentCode(null);
        }
    }
}
