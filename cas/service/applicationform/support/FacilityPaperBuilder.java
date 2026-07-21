package com.itechro.cas.service.applicationform.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.casmaster.SupportingDocDao;
import com.itechro.cas.dao.casmaster.WorkFlowTemplateDao;
import com.itechro.cas.dao.customer.CustomerDao;
import com.itechro.cas.dao.facility.FacilitySecurityDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.domain.applicationform.applicationfromcustomer.*;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.customer.Customer;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.dto.applicationform.FacilityPaperGenerateRQ;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.customer.SearchCustomerRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.faclititypaper.FacilityPaperService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class FacilityPaperBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperBuilder.class);

    private static final String AGENT_LEAD_FACILITY_DEFAULT_WF_TEMPLATE_NAME = "DEFAULT_001";

    private FacilityPaper facilityPaper;

    private Date date;

    private CredentialsDTO credentialsDTO;

    private FacilityPaperGenerateRQ facilityPaperGenerateRQ;

    private ApplicationForm applicationForm;

    private FacilityPaperService facilityPaperService;

    private FacilitySecurityDao facilitySecurityDao;

    private CustomerDao customerDao;

    private CustomerService customerService;

    private SupportingDocDao supportingDocDao;

    private WorkFlowTemplateDao workFlowTemplateDao;

    public FacilityPaperBuilder(CredentialsDTO credentialsDTO, FacilityPaperGenerateRQ facilityPaperGenerateRQ, Date date) {
        this.credentialsDTO = credentialsDTO;
        this.facilityPaperGenerateRQ = facilityPaperGenerateRQ;
        this.date = date;
    }

    public FacilityPaperBuilder initFacilityPaper() throws AppsException {
        facilityPaper = new FacilityPaper();
        facilityPaper.setCreatedUserDisplayName(facilityPaperGenerateRQ.getCreatedUserDisplayName());
        facilityPaper.setApplicationFormID(applicationForm.getApplicationFormID());
        facilityPaper.setApplicationFormRefNumber(applicationForm.getAfRefNumber());
        facilityPaper.setApplicationFormType(applicationForm.getFormType());
        facilityPaper.setCreatedBy(credentialsDTO.getUserName());
        facilityPaper.setCreatedDate(date);
        facilityPaper.setOutstandingDate(date); // Default The Outstanding date is created date
        facilityPaper.setCurrentCycle(0);
        facilityPaper.setFpRefNumber(this.facilityPaperService.getFPaperRefCode());
        facilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        facilityPaper.setCurrentAssignUser(facilityPaperGenerateRQ.getCurrentAssignUser());
        facilityPaper.setCurrentAssignADUserID(facilityPaperGenerateRQ.getCurrentAssignUser());
        facilityPaper.setCurrentAssignUserID(facilityPaperGenerateRQ.getCurrentAssignUserID());
        facilityPaper.setAssignUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        facilityPaper.setCurrentAssignUserDivCode(facilityPaperGenerateRQ.getCurrentAssignUserDivCode());
        facilityPaper.setAssignDepartmentCode(null);
        facilityPaper.setAssignUserUpmID(facilityPaperGenerateRQ.getAssignUserUpmID());
        facilityPaper.setAssignUserUpmGroupCode(facilityPaperGenerateRQ.getAssignUserUpmGroupCode());
        facilityPaper.setIsBccCreated(AppsConstants.YesNo.N);
        facilityPaper.setBranchCode(applicationForm.getBranchCode());
        facilityPaper.setCreatedUserBranchCode(applicationForm.getBranchCode());
        if (applicationForm.getFormType() == DomainConstants.BasicInformationType.CORPORATE) {
            facilityPaper.setIsCooperate(AppsConstants.YesNo.Y);
        } else {
            facilityPaper.setIsCooperate(AppsConstants.YesNo.N);
        }
        facilityPaper.setIsCommittee(AppsConstants.YesNo.N);

        WorkFlowTemplate workFlowTemplate = this.workFlowTemplateDao.findByCodeAndStatusAndApproveStatus(
                AGENT_LEAD_FACILITY_DEFAULT_WF_TEMPLATE_NAME,
                AppsConstants.Status.ACT,
                DomainConstants.MasterDataApproveStatus.APPROVED
        );

        if (workFlowTemplate == null) {
            LOG.info("ERROR : Application Form : facility default workflow template is not defined for {}", facilityPaperGenerateRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_AGENT_FACILITY_DEFAULT_WF_NOT_FOUND);
        }

        facilityPaper.setWorkFlowTemplate(workFlowTemplate);

        if(applicationForm.getLeadID() != null) {
            facilityPaper.setLeadID(applicationForm.getLeadID());
        }

        //Set Esg status
        facilityPaper.setIsESGPaper(applicationForm.getIsESGPaper());
        facilityPaper.setIsESGApproved(applicationForm.getIsESGApproved());

        if (applicationForm.getApprovedESGScore() != null){
            facilityPaper.setApprovedESGScore(applicationForm.getApprovedESGScore());
        }

        LOG.info("Facility Paper Initiated: {}", facilityPaperGenerateRQ);
        return this;
    }

    public FacilityPaperBuilder buildCustomers() throws AppsException {

        int displayOrder = 0;
        for (BasicInformation basicInformation : applicationForm.getOrderedBasicInformation()) {
            if (basicInformation.getStatus() == AppsConstants.Status.ACT) {

                CustomerDTO customerDTO;
                Customer customer;
                CASCustomer casCustomer = new CASCustomer();
                try {
                    SearchCustomerRQ searchCustomerRQ = new SearchCustomerRQ();
                    searchCustomerRQ.setIdentificationNumber(basicInformation.getIdentificationNo());
                    searchCustomerRQ.setIdentificationType(basicInformation.getIdentificationType().name());
                    customerDTO = customerService.searchCustomerFrom360(searchCustomerRQ, credentialsDTO);
                    customer = customerDao.findByCustomerFinancialIDAndStatus(customerDTO.getCustomerFinancialID(), AppsConstants.Status.ACT);
                    casCustomer.setCustomer(customer);
                } catch (Exception e) {
                    LOG.error("WARN : Customer couldn't find on Finacle. {}", basicInformation, e);
//                    throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FINACLE_CUSTOMER_CANNOT_FIND);
                }

                AFCustomer afCustomer = basicInformation.getAfCustomer();
                casCustomer.setCasCustomerName(afCustomer.getCustomerName());
                casCustomer.setEmailAddress(afCustomer.getEmailAddress());
                casCustomer.setSecondaryEmailAddress(afCustomer.getSecondaryEmailAddress());
                casCustomer.setDateOfBirth(afCustomer.getDateOfBirth());
                casCustomer.setCivilStatus(afCustomer.getCivilStatus());
                casCustomer.setTitle(basicInformation.getTitle());
                if (basicInformation.getCivilStatus() != null) {
                    casCustomer.setCivilStatus(basicInformation.getCivilStatus().getDescription());
                }
                casCustomer.setIsPrimary(basicInformation.getPrimaryInformation());
                casCustomer.setDisplayOrder(displayOrder++);
                casCustomer.setType(basicInformation.getType());
                casCustomer.setNameWithInitials(basicInformation.getNameWithInitials());
                casCustomer.setInitialRepresentation(basicInformation.getInitialRepresentation());
                casCustomer.setNameOfBusiness(basicInformation.getNameOfBusiness());
                casCustomer.setRegistrationNo(basicInformation.getRegistrationNo());
                casCustomer.setConstitution(basicInformation.getConstitution());
                casCustomer.setDateOfIncorporate(basicInformation.getDateOfIncorporate());
                casCustomer.setDateOfCommencement(basicInformation.getDateOfCommencement());
                casCustomer.setDateOfRegistration(basicInformation.getDateOfRegistration());
                casCustomer.setNameOfBusiness(basicInformation.getNameOfBusiness());
                casCustomer.setCitizenship(basicInformation.getCitizenship());
                casCustomer.setPrivateAddress(basicInformation.getPrivateAddress());
                casCustomer.setOfficialAddress(basicInformation.getOfficialAddress());
                casCustomer.setBusinessAddress(basicInformation.getBusinessAddress());
                casCustomer.setTelNumber(basicInformation.getTelNumber());
                casCustomer.setPlaceOfBirth(basicInformation.getPlaceOfBirth());
                casCustomer.setNationality(basicInformation.getNationality());
                casCustomer.setEmployment(basicInformation.getEmployment());
                casCustomer.setEmployer(basicInformation.getEmployer());
                casCustomer.setHighestEduAchievement(basicInformation.getHighestEduAchievement());
                casCustomer.setPosition(basicInformation.getPosition());
                casCustomer.setNoOfYearsEmployment(basicInformation.getNoOfYearsEmployment());
                casCustomer.setCapitalAuthorized(basicInformation.getCapitalAuthorized());
                casCustomer.setCapitalIssued(basicInformation.getCapitalIssued());
                casCustomer.setCapitalPaidUp(basicInformation.getCapitalPaidUp());
                casCustomer.setStatus(AppsConstants.Status.ACT);
                casCustomer.setCreatedBy(credentialsDTO.getUserName());
                casCustomer.setCreatedDate(date);

                for (AFCustomerAddress afCustomerAddress : afCustomer.getAfCustomerAddresses()) {
                    if (afCustomerAddress.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerAddress casCustomerAddress = new CASCustomerAddress();
                        casCustomerAddress.setAddress1(afCustomerAddress.getAddress1());
                        casCustomerAddress.setAddress2(afCustomerAddress.getAddress2());
                        casCustomerAddress.setCity(afCustomerAddress.getCity());
                        casCustomerAddress.setAddressType(afCustomerAddress.getAddressType());
                        casCustomerAddress.setStatus(AppsConstants.Status.ACT);
                        casCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                        casCustomerAddress.setCreatedDate(date);
                        casCustomer.addCasCustomerAddress(casCustomerAddress);
                    }
                }

                for (AFCustomerTelephone afCustomerTelephone : afCustomer.getAfCustomerTelephones()) {
                    if (afCustomerTelephone.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerTelephone casCustomerTelephone = new CASCustomerTelephone();
                        casCustomerTelephone.setStatus(AppsConstants.Status.ACT);
                        casCustomerTelephone.setContactNumber(afCustomerTelephone.getContactNumber());
                        casCustomerTelephone.setDescription(afCustomerTelephone.getDescription());
                        casCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                        casCustomerTelephone.setCreatedDate(date);
                        casCustomer.addCasCustomerTelephone(casCustomerTelephone);
                    }
                }

                for (AFCustomerIdentification afCustomerIdentification : afCustomer.getAfCustomerIdentifications()) {
                    if (afCustomerIdentification.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerIdentification casCustomerIdentification = new CASCustomerIdentification();
                        casCustomerIdentification.setStatus(AppsConstants.Status.ACT);
                        casCustomerIdentification.setIdentificationNumber(afCustomerIdentification.getIdentificationNumber());
                        casCustomerIdentification.setIdentificationType(afCustomerIdentification.getIdentificationType());
                        casCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                        casCustomerIdentification.setCreatedDate(date);
                        casCustomer.addCasCustomerIdentification(casCustomerIdentification);
                    }
                }

                for (AFCustomerBankDetail afCustomerBankDetail : afCustomer.getAfCustomerBankDetails()) {
                    if (afCustomerBankDetail.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerBankDetail casCustomerBankDetail = new CASCustomerBankDetail();
                        casCustomerBankDetail.setStatus(AppsConstants.Status.ACT);
                        casCustomerBankDetail.setCreatedBy(credentialsDTO.getUserName());
                        casCustomerBankDetail.setCreatedDate(date);
                        casCustomerBankDetail.setBankAccountNumber(afCustomerBankDetail.getBankAccountNumber());
                        casCustomerBankDetail.setBankAccountType(afCustomerBankDetail.getBankAccountType());
                        casCustomerBankDetail.setBranchCode(afCustomerBankDetail.getBranchCode());
                        casCustomerBankDetail.setAccountCLSFlag(afCustomerBankDetail.getAccountCLSFlag());
                        casCustomerBankDetail.setAccSince(afCustomerBankDetail.getAccSince());
                        casCustomerBankDetail.setSchmCode(afCustomerBankDetail.getSchmCode());
                        casCustomerBankDetail.setSchmType(afCustomerBankDetail.getSchmType());
                        casCustomerBankDetail.setAccountCurrencyCode(afCustomerBankDetail.getAccountCurrencyCode());
                        casCustomerBankDetail.setAccountStatus(afCustomerBankDetail.getAccountStatus());
                        casCustomer.addCasCustomerBankDetail(casCustomerBankDetail);
                        facilityPaper.setBankAccountID(afCustomerBankDetail.getBankAccountNumber());

                    }
                }

                for (AFCribAttachment afCribAttachment : basicInformation.getAfCribAttachmentSet()) {
                    if (afCribAttachment.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerCribDetail CASCustomerCribDetail = new CASCustomerCribDetail();
                        CASCustomerCribDetail.setDocStorage(afCribAttachment.getDocStorage());
                        CASCustomerCribDetail.setRemark(afCribAttachment.getRemark());
                        CASCustomerCribDetail.setCribIssueDate(afCribAttachment.getCribDate());
                        CASCustomerCribDetail.setCribStatus(afCribAttachment.getCribStatus());
                        if (afCribAttachment.getSupportingDocID() != null) {
                            CASCustomerCribDetail.setSupportingDoc(supportingDocDao.getOne(afCribAttachment.getSupportingDocID()));
                        }
                        CASCustomerCribDetail.setCreatedBy(credentialsDTO.getUserName());
                        CASCustomerCribDetail.setCreatedDate(date);
                        CASCustomerCribDetail.setStatus(afCribAttachment.getStatus());
                        CASCustomerCribDetail.setUploadedDivCode(afCribAttachment.getUploadedDivCode());
                        CASCustomerCribDetail.setUploadedUserDisplayName(afCribAttachment.getUploadedUserDisplayName());
                        casCustomer.addCasCustomerCribDetail(CASCustomerCribDetail);
                    }
                }

                for (AFCribReport afCribReport : basicInformation.getOrderedAfCribReportSet()) {
                    if (afCribReport.getStatus() == AppsConstants.Status.ACT) {
                        CASCustomerCribReport CASCustomerCribReport = new CASCustomerCribReport();
                        CASCustomerCribReport.setCribDate(afCribReport.getCribDate());
                        CASCustomerCribReport.setCribStatus(afCribReport.getCribStatus());
                        CASCustomerCribReport.setRemark(afCribReport.getRemark());
                        CASCustomerCribReport.setIdentificationNo(afCribReport.getIdentificationNo());
                        CASCustomerCribReport.setIdentificationType(afCribReport.getIdentificationType().name());
                        CASCustomerCribReport.setReportContent(afCribReport.getReportContent());
                        CASCustomerCribReport.setCreatedBy(credentialsDTO.getUserName());
                        CASCustomerCribReport.setCreatedDate(date);
                        CASCustomerCribReport.setStatus(afCribReport.getStatus());
                        casCustomer.addCasCustomerCribReport(CASCustomerCribReport);
                    }
                }

                for (OwnershipDetails ownershipDetails : basicInformation.getOwnershipDetailsSet()) {
                    if (ownershipDetails.getStatus() == AppsConstants.Status.ACT) {
                        FPDirectorDetail fpDirectorDetail = new FPDirectorDetail();
                        fpDirectorDetail.setShareHolding(ownershipDetails.getShareHolding());
                        fpDirectorDetail.setNic(ownershipDetails.getIdentificationNumber());
                        fpDirectorDetail.setAddress(ownershipDetails.getAddress());
                        fpDirectorDetail.setCivilStatus(ownershipDetails.getCivilStatus());
                        fpDirectorDetail.setDateOfBirth(ownershipDetails.getDateOfBirth());
                        fpDirectorDetail.setConstitutionType(ownershipDetails.getConstitutionType());
                        fpDirectorDetail.setCreditCard(ownershipDetails.getCreditCard());
//                        fpDirectorDetail.setDirectorName(ownershipDetails.getName());
                        fpDirectorDetail.setStatus(ownershipDetails.getStatus());
                        fpDirectorDetail.setFullName(ownershipDetails.getName());
                        facilityPaper.addFpDirectorDetail(fpDirectorDetail);
                    }
                }

                facilityPaper.addCasCustomer(casCustomer);
            }
        }

        return this;
    }

    public FacilityPaperBuilder buildFacilities() throws AppsException {

        //TODO Parent facility

        Map<Integer, FacilitySecurity> facilitySecurityMap = new HashMap<>();

        for (AFFacility afFacility : applicationForm.getParentAFFacilitiesFirst()) {
            Facility facility = new Facility();
            facility.setFacilityRefCode(afFacility.getFacilityRefCode());
            facility.setCreditFacilityTemplate(afFacility.getCreditFacilityTemplate());
            facility.setCreditFacilityType(afFacility.getCreditFacilityType());
            facility.setFacilityCurrency(afFacility.getFacilityCurrency());
            facility.setDisbursementAccNumber(afFacility.getDisbursementAccNumber());
            facility.setFacilityAmount(afFacility.getFacilityAmount());
            facility.setExistingAmount(afFacility.getExistingAmount());
            facility.setOriginalAmount(afFacility.getOriginalAmount());
            facility.setIsCooperate(afFacility.getIsCooperate());
            facility.setOutstandingAmount(afFacility.getOutstandingAmount());
            facility.setSectorID(afFacility.getSectorID());
            facility.setSubSectorID(afFacility.getSubSectorID());
            facility.setCashFlowGenerationSectorID(afFacility.getCashFlowGenerationSectorID());
            facility.setPurposeOfAdvance(afFacility.getPurposeOfAdvance());
            facility.setFacilityType(afFacility.getFacilityType());
            facility.setIsOneOff(afFacility.getIsOneOff());
            facility.setDirectFacility(afFacility.getDirectFacility());
            facility.setSeriesOfLoans(afFacility.getSeriesOfLoans());
            facility.setRevolving(afFacility.getRevolving());
            facility.setReduction(afFacility.getReduction());
            facility.setEnhancement(afFacility.getEnhancement());
            facility.setRepayment(afFacility.getRepayment());
            facility.setCondition(afFacility.getCondition());
            facility.setPurpose(afFacility.getPurpose());
            facility.setIsNew(afFacility.getIsNew());
            facility.setDisplayOrder(afFacility.getDisplayOrder());
            facility.setRemark(afFacility.getRemark());
            facility.setCreatedDate(date);
            facility.setCreatedBy(credentialsDTO.getUserName());
            facility.setStatus(afFacility.getStatus());

            for (AFFacilityVitalInfoData afFacilityVitalInfoData : afFacility.getAfFacilityVitalInfoData()) {
                FacilityVitalInfoData facilityVitalInfoData = new FacilityVitalInfoData();
                facilityVitalInfoData.setCftVitalInfoID(afFacilityVitalInfoData.getCftVitalInfoID());
                facilityVitalInfoData.setVitalInfoName(afFacilityVitalInfoData.getVitalInfoName());
                facilityVitalInfoData.setVitalInfoData(afFacilityVitalInfoData.getVitalInfoData());
                facilityVitalInfoData.setMandatory(afFacilityVitalInfoData.getMandatory());
                facilityVitalInfoData.setDisplayOrder(afFacilityVitalInfoData.getDisplayOrder());
                facilityVitalInfoData.setStatus(afFacilityVitalInfoData.getStatus());
                facilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                facilityVitalInfoData.setCreatedDate(date);
                facility.addFacilityVitalInfoData(facilityVitalInfoData);
            }

            for (AFFacilityInterestRate afFacilityInterestRate : afFacility.getAfFacilityInterestRates()) {
                FacilityInterestRate facilityInterestRate = new FacilityInterestRate();
                facilityInterestRate.setFacility(facility);
                facilityInterestRate.setCftInterestRateID(afFacilityInterestRate.getCftInterestRateID());
                facilityInterestRate.setRateCode(afFacilityInterestRate.getRateCode());
                facilityInterestRate.setUserComment(afFacilityInterestRate.getUserComment());
                facilityInterestRate.setValue(afFacilityInterestRate.getValue());
                facilityInterestRate.setIsDefault(afFacilityInterestRate.getIsDefault());
                facilityInterestRate.setStatus(afFacilityInterestRate.getStatus());
                facilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                facilityInterestRate.setCreatedDate(date);
                facility.addFacilityInterestRate(facilityInterestRate);
            }

            for (AFFacilityDocument afFacilityDocument :
                    afFacility.getOrderedAfFacilityDocuments().stream().filter(afFacilityDocument ->
                            afFacilityDocument.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {
                FacilityDocument facilityDocument = new FacilityDocument();
                facilityDocument.setFacility(facility);
                facilityDocument.setCftSupportDocID(afFacilityDocument.getCftSupportDocID());
                facilityDocument.setSupportingDoc(afFacilityDocument.getSupportingDoc());
                facilityDocument.setDocStorage(afFacilityDocument.getDocStorage());
                facilityDocument.setMandatory(afFacilityDocument.getMandatory());
                facilityDocument.setDisplayOrder(afFacilityDocument.getDisplayOrder());
                facilityDocument.setRemark(afFacilityDocument.getRemark());
                facilityDocument.setStatus(afFacilityDocument.getStatus());
                facilityDocument.setCreatedBy(credentialsDTO.getUserName());
                facilityDocument.setCreatedDate(date);
                facility.addFacilityDocuments(facilityDocument);
            }

            for (AFFacilitySecurity afFacilitySecurity : afFacility.getAfFacilitySecurities()) {

                AFSecurity afSecurity = afFacilitySecurity.getAfSecurity();

                FacilitySecurity facilitySecurity = facilitySecurityMap.get(afFacilitySecurity.getAfSecurity().getSecurityID());
                if (facilitySecurity == null) {
                    facilitySecurity = new FacilitySecurity();
                    facilitySecurity.setSecurityCode(afSecurity.getSecurityCode());
                    facilitySecurity.setSecurityDetail(afSecurity.getSecurityDetail());
                    facilitySecurity.setSecurityAmount(afSecurity.getSecurityAmount());
                    facilitySecurity.setCashAmount(afSecurity.getCashAmount());
                    facilitySecurity.setSecurityCurrency(afSecurity.getSecurityCurrency());
                    facilitySecurity.setStatus(afSecurity.getStatus());
                    facilitySecurity.setIsCommonSecurity(afSecurity.getIsCommonSecurity());
                    facilitySecurity.setIsCashSecurity(afSecurity.getIsCashSecurity());
                    facilitySecurity.setCreatedBy(credentialsDTO.getUserName());
                    facilitySecurity.setCreatedDate(date);
                    facilitySecurity = facilitySecurityDao.saveAndFlush(facilitySecurity);
                }

                facilitySecurityMap.putIfAbsent(afFacilitySecurity.getAfSecurity().getSecurityID(), facilitySecurity);

                FacilityFacilitySecurity facilityFacilitySecurity = new FacilityFacilitySecurity();
                facilityFacilitySecurity.setFacility(facility);
                facilityFacilitySecurity.setFacilitySecurity(facilitySecurity);
                facilityFacilitySecurity.setFacilitySecurityAmount(afFacilitySecurity.getFacilitySecurityAmount());
                facilityFacilitySecurity.setStatus(afFacilitySecurity.getStatus());
                facilityFacilitySecurity.setIsCashSecurity(afFacilitySecurity.getIsCashSecurity());

                facility.addFacilityFacilitySecurity(facilityFacilitySecurity);
            }

            facilityPaper.addFacility(facility);


        }
        facilitySecurityMap = null;
        LOG.info("Facility Paper Facilities Created with Securities : {}", facilityPaperGenerateRQ);
        return this;
    }

    public FacilityPaperBuilder buildDocuments() throws AppsException {

        for (AFDocument afDocument : applicationForm.getAfDocumentSet()) {
            FPDocument fpDocument = new FPDocument();
            fpDocument.setStatus(afDocument.getStatus());
            fpDocument.setDescription(afDocument.getRemark());
            fpDocument.setUploadedDivCode(afDocument.getUploadedDivCode());
            fpDocument.setUploadedUserDisplayName(afDocument.getUploadedUserDisplayName());
            fpDocument.setSupportingDoc(afDocument.getSupportingDoc());
            fpDocument.setDocStorage(afDocument.getDocStorage());
            fpDocument.setCreatedBy(credentialsDTO.getUserName());
            fpDocument.setCreatedDate(date);
            facilityPaper.addFpDocument(fpDocument);
        }
        LOG.info("Facility Paper Documents uploaded : {}", facilityPaperGenerateRQ);
        return this;
    }

    public FacilityPaperBuilder buildComment() {
        FPComment fpComment = new FPComment();
        fpComment.setCreatedUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        fpComment.setComment(facilityPaperGenerateRQ.getAfCommentDTO().getComment());
        fpComment.setStatus(AppsConstants.Status.ACT);
        fpComment.setCreatedBy(credentialsDTO.getUserName());
        fpComment.setCreatedDate(date);
        fpComment.setCreatedUserID(facilityPaperGenerateRQ.getCurrentAssignUserID());
        fpComment.setCreatedUser(facilityPaperGenerateRQ.getCurrentAssignUser());
        fpComment.setCreatedUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        fpComment.setCreatedUserDivCode(facilityPaperGenerateRQ.getCurrentAssignUserDivCode());
        fpComment.setCreatedUserUpmCode(facilityPaperGenerateRQ.getAssignUserUpmGroupCode());
        fpComment.setAssignedUserID(facilityPaperGenerateRQ.getCurrentAssignUserID());
        fpComment.setAssignedUser(facilityPaperGenerateRQ.getCurrentAssignUser());
        fpComment.setAssignedUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        fpComment.setAssignedUserDivCode(facilityPaperGenerateRQ.getCurrentAssignUserDivCode());
        fpComment.setActionMessage("Drafted Facility Paper from Application Ref No : " + facilityPaperGenerateRQ.getAfRefNumber());
        fpComment.setIsPublic(AppsConstants.YesNo.Y);
        fpComment.setIsUsersOnly(AppsConstants.YesNo.N);
        fpComment.setIsDivisionOnly(AppsConstants.YesNo.N);
        fpComment.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        facilityPaper.addFpComment(fpComment);
        return this;
    }

    public FacilityPaperBuilder buildInitialHistory() {
        FPStatusHistory fpStatusHistory = new FPStatusHistory();
        fpStatusHistory.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
//        fpStatusHistory.setRemark("Create Facility Paper from " + facilityPaperGenerateRQ.getAfRefNumber());
        fpStatusHistory.setUpdateBy(facilityPaperGenerateRQ.getAssignUserDisplayName());
        fpStatusHistory.setUpdatedUser(credentialsDTO.getUserName());
        fpStatusHistory.setUpdateDate(date);
        fpStatusHistory.setAuthorityLevel(facilityPaperGenerateRQ.getCurrentAssignUser());
        fpStatusHistory.setAssignUserID(facilityPaperGenerateRQ.getCurrentAssignUserID());
        fpStatusHistory.setAssignUserDivCode(facilityPaperGenerateRQ.getCurrentAssignUserDivCode());
        fpStatusHistory.setAssignUser(facilityPaperGenerateRQ.getCurrentAssignUser());
        fpStatusHistory.setAssignUserDisplayName(facilityPaperGenerateRQ.getAssignUserDisplayName());
        fpStatusHistory.setAssignUserUpmID(facilityPaperGenerateRQ.getAssignUserUpmID());
        fpStatusHistory.setAssignUserUpmGroupCode(facilityPaperGenerateRQ.getAssignUserUpmGroupCode());
        fpStatusHistory.setAssignDepartmentCode(null);
        fpStatusHistory.setIsPublic(AppsConstants.YesNo.Y);
        fpStatusHistory.setIsUsersOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setIsDivisionOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setWorkflowOrder("0");
        fpStatusHistory.setActionMessage("Drafted Facility Paper from Application Ref No : " + applicationForm.getAfRefNumber());
        facilityPaper.addFpStatusHistory(fpStatusHistory);

        return this;
    }


    public FacilityPaper getFacilityPaper() {
        return facilityPaper;
    }

    public void setCredentialsDTO(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public void setFacilityPaperGenerateRQ(FacilityPaperGenerateRQ facilityPaperGenerateRQ) {
        this.facilityPaperGenerateRQ = facilityPaperGenerateRQ;
    }

    public ApplicationForm getApplicationForm() {
        return applicationForm;
    }

    public void setApplicationForm(ApplicationForm applicationForm) {
        this.applicationForm = applicationForm;
    }


    public void setFacilityPaperService(FacilityPaperService facilityPaperService) {
        this.facilityPaperService = facilityPaperService;
    }

    public void setFacilitySecurityDao(FacilitySecurityDao facilitySecurityDao) {
        this.facilitySecurityDao = facilitySecurityDao;
    }

    public void setCustomerDao(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public void setCustomerService(CustomerService customerService) {
        this.customerService = customerService;
    }

    public void setSupportingDocDao(SupportingDocDao supportingDocDao) {
        this.supportingDocDao = supportingDocDao;
    }

    public void setWorkFlowTemplateDao(WorkFlowTemplateDao workFlowTemplateDao) {
        this.workFlowTemplateDao = workFlowTemplateDao;
    }
}
