package com.itechro.cas.service.faclititypaper.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.WorkFlowTemplateDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.dto.facilitypaper.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.util.CalendarUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class NonFinacleCustomerFacilityPaperBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(NonFinacleCustomerFacilityPaperBuilder.class);

    private DraftFPWithNoneFincaleCustomerRQ draftFPWithNoneFincaleCustomerRQ;

    private CredentialsDTO credentialsDTO;

    private String fpRefNumber;

    private Date date;

    private FacilityPaperDao facilityPaperDao;

    private WorkFlowTemplateDao workFlowTemplateDao;

    private FacilityPaper facilityPaper;

    public NonFinacleCustomerFacilityPaperBuilder initFacilityPaper() throws AppsException {

        facilityPaper = new FacilityPaper();
        facilityPaper.setCreatedBy(credentialsDTO.getUserName());
        facilityPaper.setCreatedDate(date);
        facilityPaper.setCreatedUserDisplayName(draftFPWithNoneFincaleCustomerRQ.getCreatedUserDisplayName());
        facilityPaper.setOutstandingDate(date); // Default The Outstanding date is created date
        facilityPaper.setCurrentCycle(0);
        facilityPaper.setFpRefNumber(fpRefNumber);
        facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        facilityPaper.setCurrentAssignUser(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUser());
        facilityPaper.setCurrentAssignADUserID(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUser());
        facilityPaper.setCurrentAssignUserID(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUserID());
        facilityPaper.setAssignUserDisplayName(draftFPWithNoneFincaleCustomerRQ.getAssignUserDisplayName());
        facilityPaper.setCurrentAssignUserDivCode(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUserDivCode());
        facilityPaper.setAssignDepartmentCode(null);
        facilityPaper.setAssignUserUpmID(draftFPWithNoneFincaleCustomerRQ.getAssignUserUpmID());
        facilityPaper.setAssignUserUpmGroupCode(draftFPWithNoneFincaleCustomerRQ.getAssignUserUpmGroupCode());
        facilityPaper.setIsBccCreated(AppsConstants.YesNo.N);
        facilityPaper.setBranchCode(draftFPWithNoneFincaleCustomerRQ.getBranchCode());
        facilityPaper.setCreatedUserBranchCode(draftFPWithNoneFincaleCustomerRQ.getCreatedUserBranchCode());
        facilityPaper.setIsCooperate(draftFPWithNoneFincaleCustomerRQ.getIsCooperate());

        if (draftFPWithNoneFincaleCustomerRQ.getWorkflowTemplateID() != null) {
            WorkFlowTemplate workFlowTemplate = this.workFlowTemplateDao.getOne(draftFPWithNoneFincaleCustomerRQ.getWorkflowTemplateID());
            if (workFlowTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
                facilityPaper.setWorkFlowTemplate(this.workFlowTemplateDao.getOne(draftFPWithNoneFincaleCustomerRQ.getWorkflowTemplateID()));
            } else {
                LOG.error("ERROR: Cannot applied not approved template, {}", draftFPWithNoneFincaleCustomerRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE);
            }
        }

        LOG.info("END: Facility Paper Initiated : {}", draftFPWithNoneFincaleCustomerRQ);

        return this;
    }

    public NonFinacleCustomerFacilityPaperBuilder buildCasCustomerDetails() throws AppsException {

        if (draftFPWithNoneFincaleCustomerRQ.getCasCustomerDTO() != null) {

            CASCustomerDTO casCustomerDTO = draftFPWithNoneFincaleCustomerRQ.getCasCustomerDTO();
            CASCustomer casCustomer = null;
            FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
            loadOptionDTO.loadCustomerCribDetail();
            casCustomer = new CASCustomer();
            casCustomer.setCreatedBy(credentialsDTO.getUserName());
            casCustomer.setCreatedDate(date);

            casCustomer.setTitle(casCustomerDTO.getTitle());
            casCustomer.setCivilStatus(casCustomerDTO.getCivilStatus());
            casCustomer.setEmailAddress(casCustomerDTO.getEmailAddress());
            casCustomer.setStatus(AppsConstants.Status.ACT);
            switch (casCustomerDTO.getType()) {
                case PERSONAL:
                    casCustomer.setCasCustomerName(casCustomerDTO.getNameWithInitials());
                    break;
                case BUSINESS:
                case CORPORATE:
                    casCustomer.setCasCustomerName(casCustomerDTO.getNameOfBusiness());
                    break;
                default:
                    casCustomer.setCasCustomerName(casCustomerDTO.getCasCustomerName());
            }
            casCustomer.setIsPrimary(AppsConstants.YesNo.Y);
            casCustomer.setDisplayOrder(0);
            casCustomer.setSecondaryEmailAddress(casCustomerDTO.getSecondaryEmailAddress());
            if (StringUtils.isNotBlank(casCustomerDTO.getDateOfBirthStr())) {
                casCustomer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfBirthStr()));
            }
            casCustomer.setType(casCustomerDTO.getType());
            casCustomer.setNameWithInitials(casCustomerDTO.getNameWithInitials());
            casCustomer.setInitialRepresentation(casCustomerDTO.getInitialRepresentation());
            casCustomer.setNameOfBusiness(casCustomerDTO.getNameOfBusiness());
            casCustomer.setRegistrationNo(casCustomerDTO.getRegistrationNo());
            casCustomer.setConstitution(casCustomerDTO.getConstitution());
            if (StringUtils.isNotBlank(casCustomerDTO.getDateOfIncorporateStr())) {
                casCustomer.setDateOfIncorporate(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfIncorporateStr()));
            }
            if (StringUtils.isNotBlank(casCustomerDTO.getDateOfCommencementStr())) {
                casCustomer.setDateOfCommencement(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfCommencementStr()));
            }
            if (StringUtils.isNotBlank(casCustomerDTO.getDateOfRegistrationStr())) {
                casCustomer.setDateOfRegistration(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfRegistrationStr()));
            }
            casCustomer.setNatureOfBusiness(casCustomerDTO.getNatureOfBusiness());
            casCustomer.setNoOfBusinessYears(casCustomerDTO.getNoOfBusinessYears());
            casCustomer.setCitizenship(casCustomerDTO.getCitizenship());
            casCustomer.setPrivateAddress(casCustomerDTO.getPrivateAddress());
            casCustomer.setOfficialAddress(casCustomerDTO.getOfficialAddress());
            casCustomer.setBusinessAddress(casCustomerDTO.getBusinessAddress());
            casCustomer.setTelNumber(casCustomerDTO.getTelNumber());
            if (StringUtils.isNotBlank(casCustomerDTO.getDateOfBirthStr())) {
                casCustomer.setDateOfBirth(CalendarUtil.getDefaultParsedDateOnly(casCustomerDTO.getDateOfBirthStr()));
            }
            casCustomer.setPlaceOfBirth(casCustomerDTO.getPlaceOfBirth());
            casCustomer.setCivilStatus(casCustomerDTO.getCivilStatus());
            casCustomer.setNationality(casCustomerDTO.getNationality());
            casCustomer.setEmployment(casCustomerDTO.getEmployment());
            casCustomer.setEmployer(casCustomerDTO.getEmployer());
            casCustomer.setHighestEduAchievement(casCustomerDTO.getHighestEduAchievement());
            casCustomer.setPosition(casCustomerDTO.getPosition());
            casCustomer.setNoOfYearsEmployment(casCustomerDTO.getNoOfYearsEmployment());
            casCustomer.setCapitalAuthorized(casCustomerDTO.getCapitalAuthorized());
            casCustomer.setCapitalIssued(casCustomerDTO.getCapitalIssued());
            casCustomer.setCapitalPaidUp(casCustomerDTO.getCapitalPaidUp());

            for (CASCustomerAddressDTO CASCustomerAddressDTO : casCustomerDTO.getCasCustomerAddressDTOList()) {
                if (CASCustomerAddressDTO.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerAddress CASCustomerAddress = new CASCustomerAddress();
                    CASCustomerAddress.setAddress1(CASCustomerAddressDTO.getAddress1());
                    CASCustomerAddress.setAddress2(CASCustomerAddressDTO.getAddress2());
                    CASCustomerAddress.setAddressType(CASCustomerAddressDTO.getAddressType());
                    CASCustomerAddress.setCity(CASCustomerAddressDTO.getCity());
                    CASCustomerAddress.setStatus(CASCustomerAddressDTO.getStatus());
                    CASCustomerAddress.setCreatedDate(date);
                    CASCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                    casCustomer.addCasCustomerAddress(CASCustomerAddress);
                }
            }

            for (CASCustomerTelephoneDTO CASCustomerTelephoneDTO : casCustomerDTO.getCasCustomerTelephoneDTOList()) {
                if (CASCustomerTelephoneDTO.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerTelephone CASCustomerTelephone = new CASCustomerTelephone();
                    CASCustomerTelephone.setContactNumber(CASCustomerTelephoneDTO.getContactNumber());
                    CASCustomerTelephone.setDescription(CASCustomerTelephoneDTO.getDescription());
                    CASCustomerTelephone.setStatus(CASCustomerTelephoneDTO.getStatus());
                    CASCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerTelephone.setCreatedDate(date);
                    casCustomer.addCasCustomerTelephone(CASCustomerTelephone);
                }
            }

            for (CASCustomerIdentificationDTO CASCustomerIdentificationDTO : casCustomerDTO.getCasCustomerIdentificationDTOList()) {
                if (CASCustomerIdentificationDTO.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerIdentification CASCustomerIdentification = new CASCustomerIdentification();
                    CASCustomerIdentification.setIdentificationNumber(CASCustomerIdentificationDTO.getIdentificationNumber());
                    CASCustomerIdentification.setIdentificationType(CASCustomerIdentificationDTO.getIdentificationType());
                    CASCustomerIdentification.setStatus(CASCustomerIdentificationDTO.getStatus());
                    CASCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerIdentification.setCreatedDate(date);
                    casCustomer.addCasCustomerIdentification(CASCustomerIdentification);
                }
            }
            facilityPaper.addCasCustomer(casCustomer);
        }
        LOG.info("END: Non Finacle Customer Details Added to Facility Paper : {}", draftFPWithNoneFincaleCustomerRQ);

        return this;
    }

    public NonFinacleCustomerFacilityPaperBuilder buildHistory() throws AppsException {

        FPStatusHistory fpStatusHistory = new FPStatusHistory();
        fpStatusHistory.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        fpStatusHistory.setUpdateBy(draftFPWithNoneFincaleCustomerRQ.getAssignUserDisplayName());
        fpStatusHistory.setUpdatedUser(credentialsDTO.getUserName());
        fpStatusHistory.setUpdateDate(date);
        fpStatusHistory.setAuthorityLevel(facilityPaper.getCurrentAuthorityLevel());
        fpStatusHistory.setAssignUserID(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUserID());
        fpStatusHistory.setAssignUser(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUser());
        fpStatusHistory.setAssignUserDisplayName(draftFPWithNoneFincaleCustomerRQ.getAssignUserDisplayName());
        fpStatusHistory.setAssignUserUpmID(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUserID());
        fpStatusHistory.setAssignUserUpmGroupCode(draftFPWithNoneFincaleCustomerRQ.getAssignUserUpmGroupCode());
        fpStatusHistory.setAssignUserDivCode(draftFPWithNoneFincaleCustomerRQ.getCurrentAssignUserDivCode());
        fpStatusHistory.setAssignDepartmentCode(null);
        fpStatusHistory.setIsPublic(AppsConstants.YesNo.Y);
        fpStatusHistory.setIsUsersOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setIsDivisionOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setWorkflowOrder(facilityPaper.getCurrentCycle() + "");
        fpStatusHistory.setActionMessage("Draft by " + draftFPWithNoneFincaleCustomerRQ.getAssignUserDisplayName());
        facilityPaper.addFpStatusHistory(fpStatusHistory);

        LOG.info("END: History Record Added to Facility Paper : {}", draftFPWithNoneFincaleCustomerRQ);

        return this;
    }

    public void setDraftFPWithNoneFincaleCustomerRQ(DraftFPWithNoneFincaleCustomerRQ draftFPWithNoneFincaleCustomerRQ) {
        this.draftFPWithNoneFincaleCustomerRQ = draftFPWithNoneFincaleCustomerRQ;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public void setFpRefNumber(String fpRefNumber) {
        this.fpRefNumber = fpRefNumber;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setFacilityPaperDao(FacilityPaperDao facilityPaperDao) {
        this.facilityPaperDao = facilityPaperDao;
    }

    public void setWorkFlowTemplateDao(WorkFlowTemplateDao workFlowTemplateDao) {
        this.workFlowTemplateDao = workFlowTemplateDao;
    }

    public void setFacilityPaper(FacilityPaper facilityPaper) {
        this.facilityPaper = facilityPaper;
    }

    public FacilityPaper getFacilityPaper() {
        return facilityPaperDao.saveAndFlush(facilityPaper);
    }
}
