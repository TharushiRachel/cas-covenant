package com.itechro.cas.service.faclititypaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.applicationform.ApplicationFormDao;
import com.itechro.cas.dao.casmaster.WorkFlowTemplateDao;
import com.itechro.cas.dao.facility.FacilitySecurityDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.casmaster.FacilityCustomInfoData;
import com.itechro.cas.model.domain.casmaster.WorkFlowTemplate;
import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import com.itechro.cas.model.domain.covenant.CustomerCovenant;
import com.itechro.cas.model.domain.customer.CustomerRatings;
import com.itechro.cas.model.domain.esg.EnvironmentalRiskData;
import com.itechro.cas.model.domain.facilitypaper.*;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.domain.storage.DocStorage;
import com.itechro.cas.model.dto.esg.EnvironmentalRiskDataDTO;
import com.itechro.cas.model.dto.facilitypaper.CalculateExposureRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperReplicationRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.stream.Collectors;

public class FacilityPaperReplicationBuilder {

    private String customerCovenantStatus;

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperReplicationBuilder.class);

    private FacilityPaper originalFacilityPaper;

    private FacilityPaper replicatedFacilityPaper;

    private Date date;

    private CredentialsDTO credentialsDTO;

    private FacilityPaperReplicationRQ replicationRQ;

    private FacilityPaperDao facilityPaperDao;

    private DocStorageDao docStorageDao;

    private FacilityPaperService facilityPaperService;

    private WorkFlowTemplateDao workFlowTemplateDao;

    private FacilitySecurityDao facilitySecurityDao;

    private FacilityPaperDTO calculateFacilityPaperExposure;

    private ApplicationFormDao applicationFormDao;

    private Map<Facility, Facility> facilityIDMap = new HashMap<>();

    public FacilityPaperReplicationBuilder(CredentialsDTO credentialsDTO, FacilityPaperReplicationRQ replicationRQ) {
        this.credentialsDTO = credentialsDTO;
        this.replicationRQ = replicationRQ;
        this.date = new Date();
    }

    public void setFacilityPaperDao(FacilityPaperDao facilityPaperDao) {
        this.facilityPaperDao = facilityPaperDao;
    }

    public void setFacilityPaperService(FacilityPaperService facilityPaperService) {
        this.facilityPaperService = facilityPaperService;
    }

    public void setDocStorageDao(DocStorageDao docStorageDao) {
        this.docStorageDao = docStorageDao;
    }

    public void setWorkFlowTemplateDao(WorkFlowTemplateDao workFlowTemplateDao) {
        this.workFlowTemplateDao = workFlowTemplateDao;
    }

    public void setFacilitySecurityDao(FacilitySecurityDao facilitySecurityDao) {
        this.facilitySecurityDao = facilitySecurityDao;
    }

    public void setApplicationFormDao(ApplicationFormDao applicationFormDao) {
        this.applicationFormDao = applicationFormDao;
    }

    public FacilityPaperReplicationBuilder buildInitialData() {
        this.originalFacilityPaper = this.facilityPaperDao.getOne(replicationRQ.getOriginalFacilityPaperID());
        this.replicatedFacilityPaper = new FacilityPaper();
        LOG.info("Facility paper replication initiated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildCalculateFacilityPaperExposure() {

        CalculateExposureRQ calculateExposureRQ = new CalculateExposureRQ();
        calculateExposureRQ.setFacilityPaperID(replicationRQ.getOriginalFacilityPaperID());
        calculateExposureRQ.setIsCooperate(AppsConstants.YesNo.N);

        this.calculateFacilityPaperExposure = facilityPaperService.calculateFacilityPaperExposure(calculateExposureRQ, credentialsDTO);

        LOG.info("Facility paper calculation replication : {} : {}", calculateFacilityPaperExposure, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildBaseFacilityData() throws AppsException {
        replicatedFacilityPaper.setCreatedBy(credentialsDTO.getUserName());
        replicatedFacilityPaper.setCreatedDate(date);
        replicatedFacilityPaper.setCreatedUserDisplayName(replicationRQ.getCreatedUserDisplayName());
        replicatedFacilityPaper.setOutstandingDate(date); //Default Outstanding Date is created date
        replicatedFacilityPaper.setCurrentCycle(0);
        replicatedFacilityPaper.setFpRefNumber(facilityPaperService.getFPaperRefCode());
        replicatedFacilityPaper.setCurrentFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
        if (replicationRQ.getBranchCode() != null) {
            replicatedFacilityPaper.setBranchCode(replicationRQ.getBranchCode());
        } else {
            replicatedFacilityPaper.setBranchCode(originalFacilityPaper.getBranchCode());
        }
        replicatedFacilityPaper.setCreatedUserBranchCode(replicationRQ.getCurrentAssignUserDivCode());
        replicatedFacilityPaper.setIsCooperate(AppsConstants.YesNo.N);
        replicatedFacilityPaper.setFacilityPaperNumber(originalFacilityPaper.getFacilityPaperNumber());
        replicatedFacilityPaper.setBankAccountID(originalFacilityPaper.getBankAccountID());
        replicatedFacilityPaper.setCurrentAssignUser(credentialsDTO.getUserName());
        replicatedFacilityPaper.setCurrentAssignUserID(credentialsDTO.getUserID());
        replicatedFacilityPaper.setCurrentAssignADUserID(credentialsDTO.getUserName());
        replicatedFacilityPaper.setAssignUserDisplayName(replicationRQ.getAssignUserDisplayName());
        replicatedFacilityPaper.setCurrentAssignUserDivCode(replicationRQ.getCurrentAssignUserDivCode());
        replicatedFacilityPaper.setAssignDepartmentCode(null);
        replicatedFacilityPaper.setAssignUserUpmID(replicationRQ.getAssignUserUpmID());
        replicatedFacilityPaper.setAssignUserUpmGroupCode(replicationRQ.getAssignUserUpmGroupCode());

        replicatedFacilityPaper.setFpApprovingAuthorityLevel(originalFacilityPaper.getFpApprovingAuthorityLevel());
        replicatedFacilityPaper.setCurrentAuthorityLevel(originalFacilityPaper.getCurrentAuthorityLevel());
        replicatedFacilityPaper.setExistingFacilitiesROA(originalFacilityPaper.getExistingFacilitiesROA());
        replicatedFacilityPaper.setProposedFacilitiesROA(originalFacilityPaper.getProposedFacilitiesROA());
        replicatedFacilityPaper.setUpcTemplate(originalFacilityPaper.getUpcTemplate());
        replicatedFacilityPaper.setIsCommittee(originalFacilityPaper.getIsCommittee());

        replicatedFacilityPaper.setTotalDirectExposurePrevious(calculateFacilityPaperExposure.getTotalDirectExposurePrevious());
        replicatedFacilityPaper.setTotalDirectExposureNew(calculateFacilityPaperExposure.getTotalDirectExposureNew());
        replicatedFacilityPaper.setTotalIndirectExposurePrevious(calculateFacilityPaperExposure.getTotalIndirectExposurePrevious());
        replicatedFacilityPaper.setTotalIndirectExposureNew(calculateFacilityPaperExposure.getTotalIndirectExposureNew());
        replicatedFacilityPaper.setTotalExposurePrevious(calculateFacilityPaperExposure.getTotalExposurePrevious());
        replicatedFacilityPaper.setTotalExposureNew(calculateFacilityPaperExposure.getTotalExposureNew());
        replicatedFacilityPaper.setGroupTotalDirectExposurePrevious(calculateFacilityPaperExposure.getGroupTotalDirectExposurePrevious());
        replicatedFacilityPaper.setGroupTotalDirectExposureNew(calculateFacilityPaperExposure.getGroupTotalDirectExposureNew());
        replicatedFacilityPaper.setGroupTotalIndirectExposurePrevious(calculateFacilityPaperExposure.getGroupTotalIndirectExposurePrevious());
        replicatedFacilityPaper.setGroupTotalIndirectExposureNew(calculateFacilityPaperExposure.getGroupTotalIndirectExposureNew());
        replicatedFacilityPaper.setGroupTotalExposurePrevious(calculateFacilityPaperExposure.getGroupTotalExposurePrevious());
        replicatedFacilityPaper.setGroupTotalExposureNew(calculateFacilityPaperExposure.getGroupTotalExposureNew());
        replicatedFacilityPaper.setTotalDirectExposureExisting(calculateFacilityPaperExposure.getTotalDirectExposureExisting());
        replicatedFacilityPaper.setTotalIndirectExposureExisting(calculateFacilityPaperExposure.getTotalIndirectExposureExisting());
        replicatedFacilityPaper.setTotalExposureExisting(calculateFacilityPaperExposure.getTotalExposureExisting());
        replicatedFacilityPaper.setExistingCashMargin(calculateFacilityPaperExposure.getExistingCashMargin());
        replicatedFacilityPaper.setGroupExistingCashMargin(calculateFacilityPaperExposure.getGroupExistingCashMargin());
        replicatedFacilityPaper.setGroupTotalDirectExposureExisting(calculateFacilityPaperExposure.getGroupTotalDirectExposureExisting());
        replicatedFacilityPaper.setGroupTotalIndirectExposureExisting(calculateFacilityPaperExposure.getGroupTotalIndirectExposureExisting());
        replicatedFacilityPaper.setGroupTotalExposureExisting(calculateFacilityPaperExposure.getGroupTotalExposureExisting());
        replicatedFacilityPaper.setNetTotalExposureNew(calculateFacilityPaperExposure.getNetTotalExposureNew());
        replicatedFacilityPaper.setNetTotalExposurePrevious(calculateFacilityPaperExposure.getNetTotalExposurePrevious());
        replicatedFacilityPaper.setNetTotalExposureExisting(calculateFacilityPaperExposure.getNetTotalExposureExisting());
        replicatedFacilityPaper.setGroupNetTotalExposureNew(calculateFacilityPaperExposure.getGroupNetTotalExposureNew());
        replicatedFacilityPaper.setGroupNetTotalExposurePrevious(calculateFacilityPaperExposure.getGroupNetTotalExposurePrevious());
        replicatedFacilityPaper.setGroupNetTotalExposureExisting(calculateFacilityPaperExposure.getGroupNetTotalExposureExisting());
        replicatedFacilityPaper.setOutstandingCashMargin(calculateFacilityPaperExposure.getOutstandingCashMargin());
        replicatedFacilityPaper.setProposedCashMargin(calculateFacilityPaperExposure.getProposedCashMargin());
        replicatedFacilityPaper.setGroupOutstandingCashMargin(calculateFacilityPaperExposure.getGroupOutstandingCashMargin());
        replicatedFacilityPaper.setGroupProposedCashMargin(calculateFacilityPaperExposure.getGroupProposedCashMargin());

        replicatedFacilityPaper.setIsESGPaper(AppsConstants.YesNo.N);
        replicatedFacilityPaper.setIsESGApproved(AppsConstants.YesNo.N);

//        if (originalFacilityPaper.getApprovedESGScore() != null){
//            replicatedFacilityPaper.setApprovedESGScore(originalFacilityPaper.getApprovedESGScore());
//        }

        if (replicationRQ.getWorkflowTemplateID() != null) {
            WorkFlowTemplate workFlowTemplate = this.workFlowTemplateDao.getOne(replicationRQ.getWorkflowTemplateID());
            if (workFlowTemplate.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED) {
                replicatedFacilityPaper.setWorkFlowTemplate(this.workFlowTemplateDao.getOne(replicationRQ.getWorkflowTemplateID()));
            } else {
                LOG.error("ERROR: Cannot applied not approved workflow template, {}", replicationRQ.getWorkflowTemplateID());
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_CANNOT_APPLY_NOT_APPROVED_TEMPLATE);
            }
        } else {
            replicatedFacilityPaper.setWorkFlowTemplate(this.originalFacilityPaper.getWorkFlowTemplate());
        }

        LOG.info("Facility paper base detail replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildDirectorDetails() {

        for (FPDirectorDetail originalFPDirectorDetail : originalFacilityPaper.getFpDirectorDetailSet()) {
            FPDirectorDetail replicateFPDirector = new FPDirectorDetail();
            replicateFPDirector.setCreatedBy(credentialsDTO.getUserName());
            replicateFPDirector.setCreatedDate(date);
            replicateFPDirector.setAddress(originalFPDirectorDetail.getAddress());
            replicateFPDirector.setCivilStatus(originalFPDirectorDetail.getCivilStatus());
            replicateFPDirector.setFullName(originalFPDirectorDetail.getFullName());
            replicateFPDirector.setDirectorName(originalFPDirectorDetail.getDirectorName());
            replicateFPDirector.setStatus(originalFPDirectorDetail.getStatus());
            replicateFPDirector.setDateOfBirth(originalFPDirectorDetail.getDateOfBirth());

            replicatedFacilityPaper.addFpDirectorDetail(replicateFPDirector);
        }

        LOG.info("Facility paper Director details replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildShareholderDetails() {

        for (FPShareHolderDetail originalFPShareHolderDetail : originalFacilityPaper.getFpShareHolderDetailSet()) {
            FPShareHolderDetail replicateFPShareHolder = new FPShareHolderDetail();
            replicateFPShareHolder.setCreatedBy(credentialsDTO.getUserName());
            replicateFPShareHolder.setCreatedDate(date);
            replicateFPShareHolder.setShareHolderName(originalFPShareHolderDetail.getShareHolderName());
            replicateFPShareHolder.setShareHolding(originalFPShareHolderDetail.getShareHolding());
            replicateFPShareHolder.setStatus(originalFPShareHolderDetail.getStatus());

            replicatedFacilityPaper.addFpShareHolderDetail(replicateFPShareHolder);
        }

        LOG.info("Facility paper Shareholder details replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildCompanyROADetails() {

        for (FPCompanyRoa originalFPCompanyRoa : originalFacilityPaper.getFpCompanyRoaSet()) {
            FPCompanyRoa fpCompanyRoa = new FPCompanyRoa();
            fpCompanyRoa.setCreatedBy(credentialsDTO.getUserName());
            fpCompanyRoa.setCreatedDate(date);
            fpCompanyRoa.setDescription(originalFPCompanyRoa.getDescription());
            fpCompanyRoa.setComment(originalFPCompanyRoa.getComment());
            fpCompanyRoa.setStatus(originalFPCompanyRoa.getStatus());
            replicatedFacilityPaper.addFpCompanyRoa(fpCompanyRoa);
        }

        LOG.info("Facility paper Company ROA details replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());

        return this;
    }

    public FacilityPaperReplicationBuilder buildFacilityPaperCustomers() {
        for (CASCustomer originalCASCustomer : originalFacilityPaper.getCASCustomerSet()) {
            CASCustomer replicatedCASCustomer = new CASCustomer();
            replicatedCASCustomer.setCreatedBy(credentialsDTO.getUserName());
            replicatedCASCustomer.setCreatedDate(date);
            replicatedCASCustomer.setCustomer(originalCASCustomer.getCustomer());
            replicatedCASCustomer.setIsPrimary(originalCASCustomer.getIsPrimary());
            replicatedCASCustomer.setDisplayOrder(originalCASCustomer.getDisplayOrder());
            replicatedCASCustomer.setStatus(originalCASCustomer.getStatus());
            replicatedCASCustomer.setCreatedBy(credentialsDTO.getUserName());
            replicatedCASCustomer.setCreatedDate(date);
            replicatedCASCustomer.setTitle(originalCASCustomer.getTitle());
            replicatedCASCustomer.setCasCustomerName(originalCASCustomer.getCasCustomerName());
            replicatedCASCustomer.setDateOfBirth(originalCASCustomer.getDateOfBirth());
            replicatedCASCustomer.setEmailAddress(originalCASCustomer.getEmailAddress());
            replicatedCASCustomer.setSecondaryEmailAddress(originalCASCustomer.getSecondaryEmailAddress());
            replicatedCASCustomer.setCivilStatus(originalCASCustomer.getCivilStatus());
            replicatedCASCustomer.setType(originalCASCustomer.getType());
            replicatedCASCustomer.setNameWithInitials(originalCASCustomer.getNameWithInitials());
            replicatedCASCustomer.setInitialRepresentation(originalCASCustomer.getInitialRepresentation());
            replicatedCASCustomer.setNameOfBusiness(originalCASCustomer.getNameOfBusiness());
            replicatedCASCustomer.setRegistrationNo(originalCASCustomer.getRegistrationNo());
            replicatedCASCustomer.setConstitution(originalCASCustomer.getConstitution());
            replicatedCASCustomer.setDateOfIncorporate(originalCASCustomer.getDateOfIncorporate());
            replicatedCASCustomer.setDateOfCommencement(originalCASCustomer.getDateOfCommencement());
            replicatedCASCustomer.setDateOfRegistration(originalCASCustomer.getDateOfRegistration());
            replicatedCASCustomer.setNatureOfBusiness(originalCASCustomer.getNatureOfBusiness());
            replicatedCASCustomer.setNoOfBusinessYears(originalCASCustomer.getNoOfBusinessYears());
            replicatedCASCustomer.setCitizenship(originalCASCustomer.getCitizenship());
            replicatedCASCustomer.setPrivateAddress(originalCASCustomer.getPrivateAddress());
            replicatedCASCustomer.setOfficialAddress(originalCASCustomer.getOfficialAddress());
            replicatedCASCustomer.setBusinessAddress(originalCASCustomer.getBusinessAddress());
            replicatedCASCustomer.setTelNumber(originalCASCustomer.getTelNumber());
            replicatedCASCustomer.setDateOfBirth(originalCASCustomer.getDateOfBirth());
            replicatedCASCustomer.setPlaceOfBirth(originalCASCustomer.getPlaceOfBirth());
            replicatedCASCustomer.setCivilStatus(originalCASCustomer.getCivilStatus());
            replicatedCASCustomer.setNationality(originalCASCustomer.getNationality());
            replicatedCASCustomer.setEmployment(originalCASCustomer.getEmployment());
            replicatedCASCustomer.setEmployer(originalCASCustomer.getEmployer());
            replicatedCASCustomer.setHighestEduAchievement(originalCASCustomer.getHighestEduAchievement());
            replicatedCASCustomer.setPosition(originalCASCustomer.getPosition());
            replicatedCASCustomer.setNoOfYearsEmployment(originalCASCustomer.getNoOfYearsEmployment());
            replicatedCASCustomer.setCapitalAuthorized(originalCASCustomer.getCapitalAuthorized());
            replicatedCASCustomer.setCapitalIssued(originalCASCustomer.getCapitalIssued());
            replicatedCASCustomer.setCapitalPaidUp(originalCASCustomer.getCapitalPaidUp());


            for (CASCustomerAddress casCustomerAddress : originalCASCustomer.getCASCustomerAddressSet()) {
                if (casCustomerAddress.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerAddress CASCustomerAddress = new CASCustomerAddress();
                    CASCustomerAddress.setAddress1(casCustomerAddress.getAddress1());
                    CASCustomerAddress.setAddress2(casCustomerAddress.getAddress2());
                    CASCustomerAddress.setAddressType(casCustomerAddress.getAddressType());
                    CASCustomerAddress.setCity(casCustomerAddress.getCity());
                    CASCustomerAddress.setStatus(casCustomerAddress.getStatus());
                    CASCustomerAddress.setCreatedDate(date);
                    CASCustomerAddress.setCreatedBy(credentialsDTO.getUserName());
                    replicatedCASCustomer.addCasCustomerAddress(CASCustomerAddress);
                }
            }

            for (CASCustomerTelephone casCustomerTelephone : originalCASCustomer.getCASCustomerTelephoneSet()) {
                if (casCustomerTelephone.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerTelephone CASCustomerTelephone = new CASCustomerTelephone();
                    CASCustomerTelephone.setContactNumber(casCustomerTelephone.getContactNumber());
                    CASCustomerTelephone.setDescription(casCustomerTelephone.getDescription());
                    CASCustomerTelephone.setStatus(casCustomerTelephone.getStatus());
                    CASCustomerTelephone.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerTelephone.setCreatedDate(date);
                    replicatedCASCustomer.addCasCustomerTelephone(CASCustomerTelephone);
                }
            }

            for (CASCustomerIdentification casCustomerIdentification : originalCASCustomer.getCASCustomerIdentificationSet()) {
                if (casCustomerIdentification.getStatus() == AppsConstants.Status.ACT) {
                    CASCustomerIdentification CASCustomerIdentification = new CASCustomerIdentification();
                    CASCustomerIdentification.setIdentificationNumber(casCustomerIdentification.getIdentificationNumber());
                    CASCustomerIdentification.setIdentificationType(casCustomerIdentification.getIdentificationType());
                    CASCustomerIdentification.setStatus(casCustomerIdentification.getStatus());
                    CASCustomerIdentification.setCreatedBy(credentialsDTO.getUserName());
                    CASCustomerIdentification.setCreatedDate(date);
                    replicatedCASCustomer.addCasCustomerIdentification(CASCustomerIdentification);
                }
            }

            for (CustomerRatings previousCustomerRatings : originalCASCustomer.getCustomerRatingsSet()) {
                CustomerRatings customerRatings = new CustomerRatings();
                customerRatings.setCustomerID(previousCustomerRatings.getCustomerID());
                customerRatings.setProposedFacilitiesROA(previousCustomerRatings.getProposedFacilitiesROA());
                customerRatings.setExistingFacilitiesROA(previousCustomerRatings.getExistingFacilitiesROA());
                customerRatings.setRiskScore(previousCustomerRatings.getRiskScore());
                customerRatings.setRiskGrading(previousCustomerRatings.getRiskGrading());
                customerRatings.setCreatedBy(credentialsDTO.getUserName());
                customerRatings.setCreatedDate(date);
                replicatedCASCustomer.addCustomerRatings(customerRatings);
            }

            for (CASCustomerCribDetail originalCribDetail : originalCASCustomer.getCASCustomerCribDetailSet()) {
                CASCustomerCribDetail replicatedCribDetail = new CASCustomerCribDetail();
                if (originalCribDetail.getDocStorage() != null) {
                    DocStorage docStorage = new DocStorage();
                    docStorage.setDescription(originalCribDetail.getDocStorage().getDescription());
                    docStorage.setDocument(originalCribDetail.getDocStorage().getDocument());
                    docStorage.setFileName(originalCribDetail.getDocStorage().getFileName());
                    docStorage.setLastUpdatedDate(originalCribDetail.getDocStorage().getLastUpdatedDate());
                    docStorage = docStorageDao.saveAndFlush(docStorage);
                    replicatedCribDetail.setDocStorage(docStorage);
                }
                replicatedCribDetail.setCreatedBy(credentialsDTO.getUserName());
                replicatedCribDetail.setCreatedDate(date);
                replicatedCribDetail.setCribStatus(originalCribDetail.getCribStatus());
                replicatedCribDetail.setRemark(originalCribDetail.getRemark());
                replicatedCribDetail.setCribIssueDate(originalCribDetail.getCribIssueDate());
                replicatedCribDetail.setStatus(originalCribDetail.getStatus());
                replicatedCribDetail.setUploadedDivCode(replicationRQ.getCurrentAssignUserDivCode());
                replicatedCribDetail.setUploadedUserDisplayName(replicationRQ.getCreatedUserDisplayName());

                replicatedCASCustomer.addCasCustomerCribDetail(replicatedCribDetail);
            }

            for (CasCustomerOtherBankFacility originalFPCustomerOBFacility : originalCASCustomer.getCasCustomerOtherBankFacilitySet()) {
                CasCustomerOtherBankFacility replicatedFPCustomerOBFacility = new CasCustomerOtherBankFacility();
                replicatedFPCustomerOBFacility.setCreatedBy(credentialsDTO.getUserName());
                replicatedFPCustomerOBFacility.setCreatedDate(date);
                replicatedFPCustomerOBFacility.setBankName(originalFPCustomerOBFacility.getBankName());
                replicatedFPCustomerOBFacility.setBranchName(originalFPCustomerOBFacility.getBranchName());
                replicatedFPCustomerOBFacility.setFacilityAmount(originalFPCustomerOBFacility.getFacilityAmount());
                replicatedFPCustomerOBFacility.setExistingAmount(originalFPCustomerOBFacility.getExistingAmount());
                replicatedFPCustomerOBFacility.setOriginalAmount(originalFPCustomerOBFacility.getOriginalAmount());
                replicatedFPCustomerOBFacility.setFacilityType(originalFPCustomerOBFacility.getFacilityType());
                replicatedFPCustomerOBFacility.setSecurities(originalFPCustomerOBFacility.getSecurities());
                replicatedFPCustomerOBFacility.setStatus(originalFPCustomerOBFacility.getStatus());
                replicatedFPCustomerOBFacility.setDisbursementDate(originalFPCustomerOBFacility.getDisbursementDate());
                replicatedFPCustomerOBFacility.setMaturityDate(originalFPCustomerOBFacility.getMaturityDate());

                replicatedCASCustomer.addCasCustomerOtherBankFacility(replicatedFPCustomerOBFacility);
            }
            for (CASCustomerDoc originalCASCustomerDoc : originalCASCustomer.getCASCustomerDocSet()) {
                CASCustomerDoc replicatedCASCustomerDoc = new CASCustomerDoc();
                replicatedCASCustomerDoc.setCreatedBy(credentialsDTO.getUserName());
                replicatedCASCustomerDoc.setCreatedDate(date);

                if (originalCASCustomerDoc.getDocStorage() != null) {
                    DocStorage docStorage = new DocStorage();
                    docStorage.setFileName(originalCASCustomerDoc.getDocStorage().getFileName());
                    docStorage.setDescription(originalCASCustomerDoc.getDocStorage().getDescription());
                    docStorage.setDocument(originalCASCustomerDoc.getDocStorage().getDocument());
                    docStorage.setLastUpdatedDate(date);
                    replicatedCASCustomerDoc.setDocStorage(this.docStorageDao.save(docStorage));
                }
                replicatedCASCustomerDoc.setDescription(originalCASCustomerDoc.getDescription());
                replicatedCASCustomerDoc.setSupportingDoc(originalCASCustomerDoc.getSupportingDoc());
                replicatedCASCustomer.addCasCustomerDoc(replicatedCASCustomerDoc);
            }
            replicatedFacilityPaper.addCasCustomer(replicatedCASCustomer);
        }

        LOG.info("Facility paper Customer details replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildFacilityPaperDocuments() {
        for (FPDocument originalFpDocument : originalFacilityPaper.getFpDocumentSet()) {
            FPDocument replicatedFpDocument = new FPDocument();
            replicatedFpDocument.setCreatedBy(credentialsDTO.getUserName());
            replicatedFpDocument.setCreatedDate(date);
            if (originalFpDocument.getDocStorage() != null) {
                DocStorage docStorage = new DocStorage();
                docStorage.setFileName(originalFpDocument.getDocStorage().getFileName());
                docStorage.setDescription(originalFpDocument.getDocStorage().getDescription());
                docStorage.setDocument(originalFpDocument.getDocStorage().getDocument());
                docStorage.setLastUpdatedDate(date);
                replicatedFpDocument.setDocStorage(this.docStorageDao.save(docStorage));
            }

            replicatedFpDocument.setUploadedDivCode(replicationRQ.getCurrentAssignUserDivCode());
            replicatedFpDocument.setUploadedUserDisplayName(replicationRQ.getCreatedUserDisplayName());
            replicatedFpDocument.setDescription(originalFpDocument.getDescription());
            replicatedFpDocument.setSupportingDoc(originalFpDocument.getSupportingDoc());
            replicatedFpDocument.setStatus(originalFpDocument.getStatus());

            replicatedFacilityPaper.addFpDocument(replicatedFpDocument);
        }

        LOG.info("Facility paper Documents replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());

        return this;
    }

    public FacilityPaperReplicationBuilder buildInitialHistory() {
        FPStatusHistory fpStatusHistory = new FPStatusHistory();
        fpStatusHistory.setFacilityPaperStatus(DomainConstants.FacilityPaperStatus.DRAFT);
//        fpStatusHistory.setRemark("Copy Facility Paper from " + originalFacilityPaper.getFpRefNumber());
        fpStatusHistory.setUpdateBy(replicationRQ.getAssignUserDisplayName());
        fpStatusHistory.setUpdatedUser(credentialsDTO.getUserName());
        fpStatusHistory.setUpdateDate(date);
        fpStatusHistory.setAuthorityLevel(replicationRQ.getCurrentAssignUser());
        fpStatusHistory.setAssignUser(replicationRQ.getCurrentAssignUser());
        fpStatusHistory.setAssignUserID(replicationRQ.getCurrentAssignUserID());
        fpStatusHistory.setAssignUserUpmID(replicationRQ.getAssignUserUpmID());
        fpStatusHistory.setAssignUserDivCode(replicationRQ.getCurrentAssignUserDivCode());
        fpStatusHistory.setAssignUserDisplayName(replicationRQ.getAssignUserDisplayName());
        fpStatusHistory.setAssignUserUpmGroupCode(replicationRQ.getAssignUserUpmGroupCode());
        fpStatusHistory.setAssignDepartmentCode(null);
        fpStatusHistory.setIsPublic(AppsConstants.YesNo.Y);
        fpStatusHistory.setIsUsersOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setIsDivisionOnly(AppsConstants.YesNo.N);
        fpStatusHistory.setWorkflowOrder("0");
        fpStatusHistory.setActionMessage("Copy by " + replicationRQ.getAssignUserDisplayName());
        replicatedFacilityPaper.addFpStatusHistory(fpStatusHistory);
        LOG.info(" buildInitialHistory : {} : {}", replicatedFacilityPaper.getAssignUserUpmGroupCode(),replicatedFacilityPaper.getAssignUserDisplayName());

        return this;
    }

    public FacilityPaperReplicationBuilder buildUPCSectionData() {
        for (FPUpcSectionData originalFPUpcSectionData : originalFacilityPaper.getFpUpcSectionDataSet()) {
            FPUpcSectionData fpUpcSectionData = new FPUpcSectionData();
            fpUpcSectionData.setCreatedBy(credentialsDTO.getUserName());
            fpUpcSectionData.setCreatedDate(date);
            fpUpcSectionData.setData(originalFPUpcSectionData.getData());
            fpUpcSectionData.setParentSectionID(originalFPUpcSectionData.getParentSectionID());
            fpUpcSectionData.setDisplayOrder(originalFPUpcSectionData.getDisplayOrder());
            fpUpcSectionData.setSectionLevel(originalFPUpcSectionData.getSectionLevel());
            fpUpcSectionData.setUpcSection(originalFPUpcSectionData.getUpcSection());
            fpUpcSectionData.setStatus(originalFPUpcSectionData.getStatus());

            replicatedFacilityPaper.addFpUpcSectionData(fpUpcSectionData);
        }


        return this;
    }

    public FacilityPaperReplicationBuilder buildFacilities() {

        Map<Integer, FacilitySecurity> facilitySecurityMap = new HashMap<>();
        Map<Integer, FacilitySecurity> addedFacilitySecuritiesMap = new HashMap<>();

        Map<Integer, Facility> facilityCovenantMap = new HashMap<>();
        Map<Integer, Facility> addedFacilityCovenantsMap = new HashMap<>();

        for (Facility originalFacility : originalFacilityPaper.getFacilitySet()) {
            for (FacilityFacilitySecurity originalFacilitySecurityFacilitySecurity : originalFacility.getFacilityFacilitySecurities()) {
                facilitySecurityMap.putIfAbsent(originalFacilitySecurityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID(), originalFacilitySecurityFacilitySecurity.getFacilitySecurity());
            }
        }


        for (Integer facilitySecurityID : facilitySecurityMap.keySet()) {
            FacilitySecurity facilitySecurity = new FacilitySecurity();
            FacilitySecurity mappedFacilitySecurity = facilitySecurityMap.get(facilitySecurityID);
            facilitySecurity.setSecurityDetail(mappedFacilitySecurity.getSecurityDetail());
            facilitySecurity.setSecurityAmount(mappedFacilitySecurity.getSecurityAmount());
            facilitySecurity.setStatus(mappedFacilitySecurity.getStatus());
            facilitySecurity.setIsCommonSecurity(mappedFacilitySecurity.getIsCommonSecurity());
            facilitySecurity.setCashAmount(mappedFacilitySecurity.getCashAmount());
            facilitySecurity.setIsCashSecurity(mappedFacilitySecurity.getIsCashSecurity());
            facilitySecurity.setSecurityCode(mappedFacilitySecurity.getSecurityCode());
            facilitySecurity.setSecurityCurrency(mappedFacilitySecurity.getSecurityCurrency());
            facilitySecurity.setCreatedDate(date);
            facilitySecurity.setCreatedBy(credentialsDTO.getUserName());
            facilitySecurity = facilitySecurityDao.saveAndFlush(facilitySecurity);
            addedFacilitySecuritiesMap.put(facilitySecurityID, facilitySecurity);
        }


        for (Facility originalFacility : originalFacilityPaper.getFacilitySet()) {
            Facility replicatedFacility = new Facility();
            replicatedFacility.setCreatedBy(credentialsDTO.getUserName());
            replicatedFacility.setCreatedDate(date);
            replicatedFacility.setCreditFacilityType(originalFacility.getCreditFacilityType());
            replicatedFacility.setCreditFacilityTemplate(originalFacility.getCreditFacilityTemplate());
            replicatedFacility.setCondition(originalFacility.getCondition());
            replicatedFacility.setDisbursementAccNumber(originalFacility.getDisbursementAccNumber());
            replicatedFacility.setDisplayOrder(originalFacility.getDisplayOrder());
            replicatedFacility.setFacilityAmount(originalFacility.getFacilityAmount());
            replicatedFacility.setExistingAmount(originalFacility.getExistingAmount());
            replicatedFacility.setOriginalAmount(originalFacility.getOriginalAmount());
            replicatedFacility.setFacilityCurrency(originalFacility.getFacilityCurrency());
            replicatedFacility.setIsCooperate(AppsConstants.YesNo.N);
            replicatedFacility.setIsOneOff(originalFacility.getIsOneOff());
            replicatedFacility.setSeriesOfLoans(originalFacility.getSeriesOfLoans());
            replicatedFacility.setRevolving(originalFacility.getRevolving());
            replicatedFacility.setDirectFacility(originalFacility.getDirectFacility());
            replicatedFacility.setIsNew(originalFacility.getIsNew());
            replicatedFacility.setReduction(originalFacility.getReduction());
            replicatedFacility.setEnhancement(originalFacility.getEnhancement());
            replicatedFacility.setIsAnnual(originalFacility.getIsAnnual());
            replicatedFacility.setIsTermsAmended(originalFacility.getIsTermsAmended());
            replicatedFacility.setIsAdditional(originalFacility.getIsAdditional());
            replicatedFacility.setIsReStructure(originalFacility.getIsReStructure());
            replicatedFacility.setIsReSchedule(originalFacility.getIsReSchedule());
            replicatedFacility.setSectorID(originalFacility.getSectorID());
            replicatedFacility.setSubSectorID(originalFacility.getSubSectorID());
            replicatedFacility.setCashFlowGenerationSectorID(originalFacility.getCashFlowGenerationSectorID());
            replicatedFacility.setStatus(originalFacility.getStatus());
            replicatedFacility.setOutstandingAmount(originalFacility.getOutstandingAmount());
            replicatedFacility.setRepayment(originalFacility.getRepayment());
            replicatedFacility.setPurpose(originalFacility.getPurpose());
            replicatedFacility.setPurposeOfAdvance(originalFacility.getPurposeOfAdvance());
            replicatedFacility.setFacilityRefCode(originalFacility.getFacilityRefCode());
            replicatedFacility.setFacilityType(originalFacility.getFacilityType());
            replicatedFacility.setRemark(originalFacility.getRemark());


            for (FacilityVitalInfoData facilityVitalInfoData : originalFacility.getFacilityVitalInfoData()) {
                FacilityVitalInfoData replicatedFacilityVitalInfoData = new FacilityVitalInfoData();
                replicatedFacilityVitalInfoData.setFacility(replicatedFacility);
                replicatedFacilityVitalInfoData.setCftVitalInfoID(facilityVitalInfoData.getCftVitalInfoID());
                replicatedFacilityVitalInfoData.setVitalInfoName(facilityVitalInfoData.getVitalInfoName());
                replicatedFacilityVitalInfoData.setVitalInfoData(facilityVitalInfoData.getVitalInfoData());
                replicatedFacilityVitalInfoData.setMandatory(facilityVitalInfoData.getMandatory());
                replicatedFacilityVitalInfoData.setStatus(facilityVitalInfoData.getStatus());
                replicatedFacilityVitalInfoData.setCreatedDate(date);
                replicatedFacilityVitalInfoData.setCreatedBy(credentialsDTO.getUserName());
                replicatedFacilityVitalInfoData.setVersion(0L);
                replicatedFacilityVitalInfoData.setDisplayOrder(facilityVitalInfoData.getDisplayOrder());
                replicatedFacility.addFacilityVitalInfoData(replicatedFacilityVitalInfoData);

            }

            for (FacilityFacilitySecurity originalFacilitySecurityFacilitySecurity : originalFacility.getFacilityFacilitySecurities()) {
                FacilityFacilitySecurity replicatedFacilityFacilitySecurity = new FacilityFacilitySecurity();
                replicatedFacilityFacilitySecurity.setFacility(replicatedFacility);
                replicatedFacilityFacilitySecurity.setFacilitySecurity(addedFacilitySecuritiesMap.get(originalFacilitySecurityFacilitySecurity.getFacilitySecurity().getFacilitySecurityID()));
                replicatedFacilityFacilitySecurity.setIsCashSecurity(originalFacilitySecurityFacilitySecurity.getIsCashSecurity());
                replicatedFacilityFacilitySecurity.setFacilitySecurityAmount(originalFacilitySecurityFacilitySecurity.getFacilitySecurityAmount());
                replicatedFacilityFacilitySecurity.setStatus(originalFacilitySecurityFacilitySecurity.getStatus());
                replicatedFacility.addFacilityFacilitySecurity(replicatedFacilityFacilitySecurity);
            }
            LOG.info("Facility paper facility securities replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());

            for (FacilityInterestRate originalFacilityInterestRate : originalFacility.getFacilityInterestRates()) {
                FacilityInterestRate replicatedFacilityInterestRate = new FacilityInterestRate();
                replicatedFacilityInterestRate.setCftInterestRateID(originalFacilityInterestRate.getCftInterestRateID());
                replicatedFacilityInterestRate.setRateName(originalFacilityInterestRate.getRateName());
                replicatedFacilityInterestRate.setInterestRatingSubCategory(originalFacilityInterestRate.getInterestRatingSubCategory());
                replicatedFacilityInterestRate.setIsEditable(originalFacilityInterestRate.getIsEditable());
                replicatedFacilityInterestRate.setRateCode(originalFacilityInterestRate.getRateCode());
                replicatedFacilityInterestRate.setUserComment(originalFacilityInterestRate.getUserComment());
                replicatedFacilityInterestRate.setValue(originalFacilityInterestRate.getValue());
                replicatedFacilityInterestRate.setIsDefault(originalFacilityInterestRate.getIsDefault());
                replicatedFacilityInterestRate.setStatus(originalFacilityInterestRate.getStatus());
                replicatedFacilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
                replicatedFacilityInterestRate.setCreatedDate(date);
                replicatedFacility.addFacilityInterestRate(replicatedFacilityInterestRate);
            }

            for (FacilityOtherFacilityInformation originalFacilityOtherFacilityInformation : originalFacility.getOrderedFacilityOtherFacilityInformations()) {
                if (originalFacilityOtherFacilityInformation.getStatus().equals(AppsConstants.Status.ACT)) {
                    FacilityOtherFacilityInformation facilityOtherFacilityInformation = new FacilityOtherFacilityInformation();
                    facilityOtherFacilityInformation.setCftOtherFacilityInfoID(originalFacilityOtherFacilityInformation.getCftOtherFacilityInfoID());
                    facilityOtherFacilityInformation.setOtherFacilityInfoName(originalFacilityOtherFacilityInformation.getOtherFacilityInfoName());
                    facilityOtherFacilityInformation.setOtherInfoData(originalFacilityOtherFacilityInformation.getOtherInfoData());
                    facilityOtherFacilityInformation.setOtherFacilityInfoCode(originalFacilityOtherFacilityInformation.getOtherFacilityInfoCode());
                    facilityOtherFacilityInformation.setOtherFacilityInfoFieldType(originalFacilityOtherFacilityInformation.getOtherFacilityInfoFieldType());
                    facilityOtherFacilityInformation.setDefaultValue(originalFacilityOtherFacilityInformation.getDefaultValue());
                    facilityOtherFacilityInformation.setDisplayOrder(originalFacilityOtherFacilityInformation.getDisplayOrder());
                    facilityOtherFacilityInformation.setCreatedBy(credentialsDTO.getUserName());
                    facilityOtherFacilityInformation.setCreatedDate(date);
                    facilityOtherFacilityInformation.setStatus(AppsConstants.Status.ACT);
                    replicatedFacility.addOtherFacilityInformation(facilityOtherFacilityInformation);
                }
            }

            for(FacilityCustomInfoData originalFacilityCustomFacilityInformation : originalFacility.getFacilityCustomInfoData()){
                if(originalFacilityCustomFacilityInformation.getStatus().equals(AppsConstants.Status.ACT)){
                    FacilityCustomInfoData facilityCustomInfoData = new FacilityCustomInfoData();
                    facilityCustomInfoData.setCftCustomFacilityInfoID(originalFacilityCustomFacilityInformation.getCftCustomFacilityInfoID());
                    facilityCustomInfoData.setCustomFacilityInfoName(originalFacilityCustomFacilityInformation.getCustomFacilityInfoName());
                    facilityCustomInfoData.setCustomFacilityInfoCode(originalFacilityCustomFacilityInformation.getCustomFacilityInfoCode());
                    facilityCustomInfoData.setCustomInfoData(originalFacilityCustomFacilityInformation.getCustomInfoData());
                    facilityCustomInfoData.setDisplayOrder(originalFacilityCustomFacilityInformation.getDisplayOrder());
                    facilityCustomInfoData.setCreatedBy(credentialsDTO.getUserName());
                    facilityCustomInfoData.setCreatedDate(date);
                    facilityCustomInfoData.setStatus(AppsConstants.Status.ACT);
                    replicatedFacility.addFacilityCustomInfoData(facilityCustomInfoData);
                }
            }

            LOG.info("Facility paper facility interest rate replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());

            for (FacilityPurposeOfAdvance originalFacilityPurposeOfAdvance : originalFacility.getFacilityPurposeOfAdvances()) {
                FacilityPurposeOfAdvance replicatedFacilityPurposeOfAdvance = new FacilityPurposeOfAdvance();
                replicatedFacilityPurposeOfAdvance.setPurposeOfAdvance(originalFacilityPurposeOfAdvance.getPurposeOfAdvance());
                replicatedFacilityPurposeOfAdvance.setReferenceCode(originalFacilityPurposeOfAdvance.getReferenceCode());
                replicatedFacilityPurposeOfAdvance.setReferenceDescription(originalFacilityPurposeOfAdvance.getReferenceDescription());
                replicatedFacilityPurposeOfAdvance.setStatus(originalFacilityPurposeOfAdvance.getStatus());
                replicatedFacilityPurposeOfAdvance.setCreatedBy(credentialsDTO.getUserName());
                replicatedFacilityPurposeOfAdvance.setCreatedDate(date);
                replicatedFacility.addFacilityPurposeOfAdvance(replicatedFacilityPurposeOfAdvance);
            }
            LOG.info("Facility paper facility purpose of advanced replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());

            for (FacilityDocument originalFacilityDocument : originalFacility.getFacilityDocuments()) {
                FacilityDocument replicatedFacilityDocument = new FacilityDocument();

                replicatedFacilityDocument.setRemark(originalFacilityDocument.getRemark());
                replicatedFacilityDocument.setCftSupportDocID(originalFacilityDocument.getCftSupportDocID());
                replicatedFacilityDocument.setDisplayOrder(originalFacilityDocument.getDisplayOrder());
                replicatedFacilityDocument.setMandatory(originalFacilityDocument.getMandatory());
                replicatedFacilityDocument.setSupportingDoc(originalFacilityDocument.getSupportingDoc());
                if (originalFacilityDocument.getDocStorage() != null) {
                    DocStorage docStorage = new DocStorage();
                    docStorage.setFileName(originalFacilityDocument.getDocStorage().getFileName());
                    docStorage.setDescription(originalFacilityDocument.getDocStorage().getDescription());
                    docStorage.setDocument(originalFacilityDocument.getDocStorage().getDocument());
                    docStorage.setLastUpdatedDate(date);
                    replicatedFacilityDocument.setDocStorage(this.docStorageDao.save(docStorage));
                }
                replicatedFacilityDocument.setStatus(originalFacilityDocument.getStatus());
                replicatedFacilityDocument.setCreatedBy(credentialsDTO.getUserName());
                replicatedFacilityDocument.setCreatedDate(date);
                replicatedFacility.addFacilityDocuments(replicatedFacilityDocument);
            }
            LOG.info("Facility paper facility documents replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());

            replicatedFacilityPaper.addFacility(replicatedFacility);

            facilityIDMap.put(originalFacility, replicatedFacility);
        }
        facilitySecurityMap = null;
        addedFacilitySecuritiesMap = null;
        LOG.info("Facility paper UPC Section Data replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder replicateSecuritySummary() {
        LOG.info("START : Facility Paper Security Summary Replicate: {} : {}", replicationRQ, credentialsDTO.getUserID());

        FPSecuritySummery originalFpSecuritySummery = originalFacilityPaper.getFpSecuritySummery();
        if (originalFpSecuritySummery != null) {
            FPSecuritySummery replicatedFpSecuritySummery = new FPSecuritySummery();
            replicatedFpSecuritySummery.setCompanyCash(originalFpSecuritySummery.getCompanyCash());
            replicatedFpSecuritySummery.setGroupCash(originalFpSecuritySummery.getGroupCash());
            replicatedFpSecuritySummery.setCompanyProperty(originalFpSecuritySummery.getCompanyProperty());
            replicatedFpSecuritySummery.setGroupProperty(originalFpSecuritySummery.getGroupProperty());
            replicatedFpSecuritySummery.setCompanySubTotalOne(originalFpSecuritySummery.getCompanySubTotalOne());
            replicatedFpSecuritySummery.setGroupSubTotalOne(originalFpSecuritySummery.getGroupSubTotalOne());
            replicatedFpSecuritySummery.setCompanyInvoiceReceivables(originalFpSecuritySummery.getCompanyInvoiceReceivables());
            replicatedFpSecuritySummery.setGroupInvoiceReceivables(originalFpSecuritySummery.getGroupInvoiceReceivables());
            replicatedFpSecuritySummery.setCompanyCorporateGuarantees(originalFpSecuritySummery.getCompanyCorporateGuarantees());
            replicatedFpSecuritySummery.setGroupCorporateGuarantees(originalFpSecuritySummery.getGroupCorporateGuarantees());
            replicatedFpSecuritySummery.setCompanyLeaseIndenture(originalFpSecuritySummery.getCompanyLeaseIndenture());
            replicatedFpSecuritySummery.setGroupLeaseIndenture(originalFpSecuritySummery.getGroupLeaseIndenture());
            replicatedFpSecuritySummery.setCompanySubTotalTwo(originalFpSecuritySummery.getCompanySubTotalTwo());
            replicatedFpSecuritySummery.setGroupSubTotalTwo(originalFpSecuritySummery.getGroupSubTotalTwo());
            replicatedFpSecuritySummery.setCompanyClean(originalFpSecuritySummery.getCompanyClean());
            replicatedFpSecuritySummery.setGroupClean(originalFpSecuritySummery.getGroupClean());
            replicatedFpSecuritySummery.setCompanyTotal(originalFpSecuritySummery.getCompanyTotal());
            replicatedFpSecuritySummery.setGroupTotal(originalFpSecuritySummery.getGroupTotal());
            replicatedFpSecuritySummery.setLimitSummery(originalFpSecuritySummery.getLimitSummery());
            replicatedFpSecuritySummery.setFacilitySecuritySummaryType(originalFpSecuritySummery.getFacilitySecuritySummaryType());
            replicatedFpSecuritySummery.setCreatedBy(credentialsDTO.getUserName());
            replicatedFpSecuritySummery.setCreatedDate(date);
            replicatedFpSecuritySummery.setCompanySubTotalThree(originalFpSecuritySummery.getCompanySubTotalThree());
            replicatedFpSecuritySummery.setGroupSubTotalThree(originalFpSecuritySummery.getGroupSubTotalThree());
            replicatedFpSecuritySummery.setCompanySubTotalFour(originalFpSecuritySummery.getCompanySubTotalFour());
            replicatedFpSecuritySummery.setGroupSubTotalFour(originalFpSecuritySummery.getGroupSubTotalFour());

            for (FPSecuritySummaryTopic originalFpSecuritySummaryTopic : originalFpSecuritySummery.getFpSecuritySummaryTopics()) {
                if (originalFpSecuritySummaryTopic.getStatus() == AppsConstants.Status.ACT) {
                    FPSecuritySummaryTopic replicatedSecuritySummaryTopic = new FPSecuritySummaryTopic();
                    replicatedSecuritySummaryTopic.setDisplayOrder(originalFpSecuritySummaryTopic.getDisplayOrder());
                    replicatedSecuritySummaryTopic.setSecurityType(originalFpSecuritySummaryTopic.getSecurityType());
                    replicatedSecuritySummaryTopic.setSecurityTypeGroup(originalFpSecuritySummaryTopic.getSecurityTypeGroup());
                    replicatedSecuritySummaryTopic.setCompanyValue(originalFpSecuritySummaryTopic.getCompanyValue());
                    replicatedSecuritySummaryTopic.setCompanyPercentage(originalFpSecuritySummaryTopic.getCompanyPercentage());
                    replicatedSecuritySummaryTopic.setGroupValue(originalFpSecuritySummaryTopic.getGroupValue());
                    replicatedSecuritySummaryTopic.setGroupPercentage(originalFpSecuritySummaryTopic.getGroupPercentage());
                    replicatedSecuritySummaryTopic.setStatus(originalFpSecuritySummaryTopic.getStatus());
                    replicatedSecuritySummaryTopic.setCreatedBy(credentialsDTO.getUserName());
                    replicatedSecuritySummaryTopic.setCreatedDate(date);
                    replicatedFpSecuritySummery.addFpSecuritySummaryTopic(replicatedSecuritySummaryTopic);
                }
            }

            replicatedFpSecuritySummery.setFacilityPaper(replicatedFacilityPaper);
            replicatedFacilityPaper.setFpSecuritySummery(replicatedFpSecuritySummery);
            LOG.info("END : Facility Paper Security Summary Replicated: {} : {} : {} ", replicationRQ, credentialsDTO.getUserID(),replicatedFacilityPaper.getCreatedDate());
        }
        return this;
    }

    public FacilityPaper getReplicatedFacilityPaper() {
        return replicatedFacilityPaper;
    }

    public FacilityPaperReplicationBuilder buildCustomerCovenant() {
        LOG.info("buildCustomerCovenant");
        for(CustomerCovenant originalCustomerCovenant : originalFacilityPaper.getCustomerCovenantSet()){

            if (originalCustomerCovenant.getIsExists().equals(AppsConstants.YesNo.N)) {

                CustomerCovenant replicateCustomerCovenant = new CustomerCovenant();
                replicateCustomerCovenant.setCovenant_Code(originalCustomerCovenant.getCovenant_Code());
                replicateCustomerCovenant.setCovenant_Description(originalCustomerCovenant.getCovenant_Description());
                replicateCustomerCovenant.setDisbursementType(originalCustomerCovenant.getDisbursementType());
                replicateCustomerCovenant.setCovenant_Frequency(originalCustomerCovenant.getCovenant_Frequency());
                replicateCustomerCovenant.setCovenant_Due_Date(originalCustomerCovenant.getCovenant_Due_Date());
                replicateCustomerCovenant.setFacilityPaper(replicatedFacilityPaper);
                replicateCustomerCovenant.setRequestUUID(replicatedFacilityPaper.getFpRefNumber());
                LOG.info("FaoriginalCustomerCovenant.getCustomerFinancialID() : {}", originalCustomerCovenant.getCustomerFinancialID());
                replicateCustomerCovenant.setCustomerFinancialID(originalCustomerCovenant.getCustomerFinancialID());
                replicateCustomerCovenant.setStatus(originalCustomerCovenant.getStatus());
                replicateCustomerCovenant.setCreatedBy(credentialsDTO.getUserName());
                replicateCustomerCovenant.setCreatedDate(date);
                replicateCustomerCovenant.setCreatedUserDisplayName(replicationRQ.getCreatedUserDisplayName());
                replicateCustomerCovenant.setCovenant_Due_Date(originalCustomerCovenant.getCovenant_Due_Date());
                replicateCustomerCovenant.setNoFrequencyDueDate(originalCustomerCovenant.getNoFrequencyDueDate());
                replicateCustomerCovenant.setIsExists(originalCustomerCovenant.getIsExists());
                replicateCustomerCovenant.setComplianceStatus(originalCustomerCovenant.getComplianceStatus());

                replicatedFacilityPaper.addCustomerCovenant(replicateCustomerCovenant);

            }
        }
        LOG.info("Facility paper Customer covenant replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildFacilityCovenant(){
        LOG.info("buildFacilityCovenant");
        for( ApplicationLevelCovenant originalFacilityCovenant : originalFacilityPaper.getFacilityCovenantSet()){

            if (originalFacilityCovenant.getIsExists().equals(AppsConstants.YesNo.N)) {

                ApplicationLevelCovenant replicateAapplicationLevelCovenant = new ApplicationLevelCovenant();//
                replicateAapplicationLevelCovenant.setCovenant_Code(originalFacilityCovenant.getCovenant_Code());//
                replicateAapplicationLevelCovenant.setCovenant_Description(originalFacilityCovenant.getCovenant_Description());//
                replicateAapplicationLevelCovenant.setCovenant_Frequency(originalFacilityCovenant.getCovenant_Frequency());//
                replicateAapplicationLevelCovenant.setCovenant_Due_Date(originalFacilityCovenant.getCovenant_Due_Date());//
                replicateAapplicationLevelCovenant.setDisbursementType(originalFacilityCovenant.getDisbursementType());

                replicateAapplicationLevelCovenant.setCustomerFinacleID(originalFacilityCovenant.getCustomerFinacleID());
                replicateAapplicationLevelCovenant.setStatus(originalFacilityCovenant.getStatus());//
                replicateAapplicationLevelCovenant.setCreatedBy(credentialsDTO.getUserName());//
                replicateAapplicationLevelCovenant.setCreatedUserDisplayName(replicationRQ.getCreatedUserDisplayName());//
                replicateAapplicationLevelCovenant.setCreatedDate(date);//
                replicateAapplicationLevelCovenant.setFacilityPaper(replicatedFacilityPaper);//
                replicateAapplicationLevelCovenant.setIsExists(originalFacilityCovenant.getIsExists());
                replicateAapplicationLevelCovenant.setComplianceStatus(originalFacilityCovenant.getComplianceStatus());

                List<FacilityCovenantFacilities> replicateFacilityCovenantFacilities = new ArrayList<>();
                for (FacilityCovenantFacilities facilityCovenantFacilities : originalFacilityCovenant.getFacilityCovenantFacilitiesSet()){

                    FacilityCovenantFacilities covenantFacilities = new FacilityCovenantFacilities();

                    covenantFacilities.setApplicationLevelCovenant(replicateAapplicationLevelCovenant);
                    covenantFacilities.setFacility(facilityIDMap.get(facilityCovenantFacilities.getFacility()));
                    covenantFacilities.setCreditFacilityTemplateID(facilityCovenantFacilities.getCreditFacilityTemplateID());
                    covenantFacilities.setCreditFacilityName(facilityCovenantFacilities.getCreditFacilityName());
                    covenantFacilities.setFacilityRefCode(facilityCovenantFacilities.getFacilityRefCode());
                    covenantFacilities.setFacilityCurrency(facilityCovenantFacilities.getFacilityCurrency());
                    covenantFacilities.setFacilityAmount(facilityCovenantFacilities.getFacilityAmount());
                    covenantFacilities.setDisplayOrder(facilityCovenantFacilities.getDisplayOrder());

                    replicateFacilityCovenantFacilities.add(covenantFacilities);
                }

                replicateAapplicationLevelCovenant.setFacilityCovenantFacilitiesSet(replicateFacilityCovenantFacilities);

                replicatedFacilityPaper.addFacilityCovenant(replicateAapplicationLevelCovenant);//
            }
        }
        LOG.info("Facility paper Facility covenant replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildCribDetails() {

        for (CribDetails originalCribDetail : originalFacilityPaper.getCribDetailSet()) {
            CribDetails replicatedCribDetail = new CribDetails();

            if (originalCribDetail.getDocStorage() != null) {
                DocStorage docStorage = new DocStorage();
                docStorage.setDescription(originalCribDetail.getDocStorage().getDescription());
                docStorage.setDocument(originalCribDetail.getDocStorage().getDocument());
                docStorage.setFileName(originalCribDetail.getDocStorage().getFileName());
                docStorage.setLastUpdatedDate(originalCribDetail.getDocStorage().getLastUpdatedDate());
                docStorage = docStorageDao.saveAndFlush(docStorage);
                replicatedCribDetail.setDocStorage(docStorage);
            }

            replicatedCribDetail.setIdentificationType(originalCribDetail.getIdentificationType());
            replicatedCribDetail.setIdentificationNo(originalCribDetail.getIdentificationNo());
            replicatedCribDetail.setFullName(originalCribDetail.getFullName());
            replicatedCribDetail.setGender(originalCribDetail.getGender());
            replicatedCribDetail.setCribStatus(originalCribDetail.getCribStatus());
            replicatedCribDetail.setInquiryReason(originalCribDetail.getInquiryReason());
            replicatedCribDetail.setRemark(originalCribDetail.getRemark());
            replicatedCribDetail.setCribIssueDate(originalCribDetail.getCribIssueDate());
            replicatedCribDetail.setFacilityPaper(replicatedFacilityPaper);
            replicatedCribDetail.setSupportingDoc(originalCribDetail.getSupportingDoc());
            replicatedCribDetail.setCreatedBy(credentialsDTO.getUserName());
            replicatedCribDetail.setCreatedDate(date);
            replicatedCribDetail.setStatus(originalCribDetail.getStatus());
            replicatedCribDetail.setUploadedDivCode(replicationRQ.getCurrentAssignUserDivCode());
            replicatedCribDetail.setUploadedUserDisplayName(replicationRQ.getCreatedUserDisplayName());
            replicatedCribDetail.setIsSystem(originalCribDetail.getIsSystem());

            replicatedFacilityPaper.addCribDetail(replicatedCribDetail);
        }

        LOG.info("Facility paper Crib details replicated : {} : {}", replicationRQ, credentialsDTO.getUserID());
        return this;
    }

    public FacilityPaperReplicationBuilder buildRiskCategories() {

        if (!originalFacilityPaper.getEnvironmentalRisks().isEmpty()) {

            List<EnvironmentalRiskDataDTO> prevEnvRiskCategories = originalFacilityPaper.getEnvironmentalRisks().stream()
                    .sorted(Comparator.comparingInt(EnvironmentalRiskData::getRiskDataId))
                    .map(EnvironmentalRiskDataDTO::new).collect(Collectors.toList());

            for (EnvironmentalRiskDataDTO envRisk : prevEnvRiskCategories) {
                EnvironmentalRiskData replicateEnvRisk = new EnvironmentalRiskData();
                if (originalFacilityPaper.getApplicationFormID() != null) {
                    applicationFormDao.findById(originalFacilityPaper.getApplicationFormID()).ifPresent(
                            replicateEnvRisk::setApplicationForm
                    );
                }
                replicateEnvRisk.setRiskCategoryId(envRisk.getRiskCategoryId());
                replicateEnvRisk.setCategoryParentId(envRisk.getCategoryParentId());
                replicateEnvRisk.setDescription(envRisk.getDescription());
                replicateEnvRisk.setScore(envRisk.getScore());
                replicateEnvRisk.setType(envRisk.getType());
                replicateEnvRisk.setCreatedBy(credentialsDTO.getUserName());
                replicateEnvRisk.setCreatedDate(date);
                replicatedFacilityPaper.addEnvironmentalRiskData(replicateEnvRisk);
            }
        }

        return this;
    }
}
