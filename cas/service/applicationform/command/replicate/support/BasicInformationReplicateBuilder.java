package com.itechro.cas.service.applicationform.command.replicate.support;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.*;
import com.itechro.cas.model.domain.storage.DocStorage;
import com.itechro.cas.model.dto.applicationform.ReplicateApplicationFormRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.support.AFCustomerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class BasicInformationReplicateBuilder {

    private static final Logger LOG = LoggerFactory.getLogger(AFCustomerBuilder.class);

    private ReplicateApplicationFormRQ getReplicateApplicationFromFormRQ;

    private CredentialsDTO credentialsDTO;

    private BasicInformation currentBasicInformation;

    private BasicInformation newBasicInformation;

    private Date date;

    private DocStorageDao docStorageDao;

    public BasicInformationReplicateBuilder(CredentialsDTO credentialsDTO) {
        this.credentialsDTO = credentialsDTO;
    }

    public BasicInformationReplicateBuilder init(BasicInformation currentBasicInformation, BasicInformation newBasicInformation, Date date) {
        this.currentBasicInformation = currentBasicInformation;
        this.newBasicInformation = newBasicInformation;
        this.date = date;
        return this;
    }

    public BasicInformationReplicateBuilder buildRiskRating() throws AppsException {
        for (AFRiskRate afRiskRate : currentBasicInformation.getOrderedAfRiskRates()) {
            if (afRiskRate.getStatus() == AppsConstants.Status.ACT) {
                AFRiskRate replicateRiskRate = new AFRiskRate();
                replicateRiskRate.setCreatedBy(credentialsDTO.getUserName());
                replicateRiskRate.setCreatedDate(date);
                replicateRiskRate.setStatus(AppsConstants.Status.ACT);
                replicateRiskRate.setRiskGrading(afRiskRate.getRiskGrading());
                replicateRiskRate.setScoring(afRiskRate.getScoring());
                replicateRiskRate.setModel(afRiskRate.getModel());
                replicateRiskRate.setAssetClassification(afRiskRate.getAssetClassification());
                replicateRiskRate.setInitiatedBranch(afRiskRate.getInitiatedBranch());
                replicateRiskRate.setLastRated(afRiskRate.getLastRated());
                replicateRiskRate.setRiskConfirmed(afRiskRate.getRiskConfirmed());
                replicateRiskRate.setApplicationFormID(afRiskRate.getApplicationFormID());
                newBasicInformation.addRiskRateDetails(replicateRiskRate);
            }
        }
        LOG.info("END : Risk Rating Details For {} ", currentBasicInformation.getIdentificationNo());
        return this;
    }

    public BasicInformationReplicateBuilder buildFinancialObligation() throws AppsException {
        for (AFFinancialObligation afFinancialObligation : currentBasicInformation.getOrderedAfFinancialObligation()) {
            if (afFinancialObligation.getStatus() == AppsConstants.Status.ACT) {
                AFFinancialObligation replicatedFinancialObligation = new AFFinancialObligation();
                replicatedFinancialObligation.setCreatedBy(credentialsDTO.getUserName());
                replicatedFinancialObligation.setCreatedDate(date);
                replicatedFinancialObligation.setStatus(AppsConstants.Status.ACT);
                replicatedFinancialObligation.setArrears(afFinancialObligation.getArrears());
                replicatedFinancialObligation.setFinancialInstitution(afFinancialObligation.getFinancialInstitution());
                replicatedFinancialObligation.setOriginalAmount(afFinancialObligation.getOriginalAmount());
                replicatedFinancialObligation.setPresentOutstanding(afFinancialObligation.getPresentOutstanding());
                replicatedFinancialObligation.setSecurities(afFinancialObligation.getSecurities());
                replicatedFinancialObligation.setSignedAs(afFinancialObligation.getSignedAs());
                replicatedFinancialObligation.setIdentificationType(afFinancialObligation.getIdentificationType());
                replicatedFinancialObligation.setIdentificationNumber(afFinancialObligation.getIdentificationNumber());
                replicatedFinancialObligation.setIsCribRecord(afFinancialObligation.getIsCribRecord());
                replicatedFinancialObligation.setCustomerCribResponseID(afFinancialObligation.getCustomerCribResponseID());
                replicatedFinancialObligation.setCustomerCribLiabilityID(afFinancialObligation.getCustomerCribLiabilityID());
                newBasicInformation.addAFFinancialObligation(replicatedFinancialObligation);
            }
        }
        LOG.info("END : Financial Obligation Details For {} ", currentBasicInformation.getIdentificationNo());
        return this;
    }


    public BasicInformationReplicateBuilder buildOtherBankDetails() throws AppsException {
        for (AFOtherBankDetails afOtherBankDetails : currentBasicInformation.getOrderedAfOtherBankDetails()) {
            if (afOtherBankDetails.getStatus() == AppsConstants.Status.ACT) {
                AFOtherBankDetails replicatedOtherBankDetails = new AFOtherBankDetails();
                replicatedOtherBankDetails.setCreatedBy(credentialsDTO.getUserName());
                replicatedOtherBankDetails.setCreatedDate(date);
                replicatedOtherBankDetails.setStatus(AppsConstants.Status.ACT);
                replicatedOtherBankDetails.setNameOfBank(afOtherBankDetails.getNameOfBank());
                replicatedOtherBankDetails.setNameOfBranch(afOtherBankDetails.getNameOfBranch());
                replicatedOtherBankDetails.setAccountNo(afOtherBankDetails.getAccountNo());
                replicatedOtherBankDetails.setTypeOfAccount(afOtherBankDetails.getTypeOfAccount());
                replicatedOtherBankDetails.setApplicationFormID(afOtherBankDetails.getApplicationFormID());
                newBasicInformation.addOtherBankDetails(replicatedOtherBankDetails);
            }
        }
        LOG.info("END : Other Bank Details For {} ", currentBasicInformation.getIdentificationNo());
        return this;
    }

    public BasicInformationReplicateBuilder buildBorrowerGuarantorDetails() throws AppsException {
        for (AFBorrowerGuarantor afBorrowerGuarantor : currentBasicInformation.getOrderedAfBorrowerGuarantor()) {
            if (afBorrowerGuarantor.getStatus() == AppsConstants.Status.ACT) {
                AFBorrowerGuarantor replicatedBorrowerGuarantor = new AFBorrowerGuarantor();
                replicatedBorrowerGuarantor.setCreatedBy(credentialsDTO.getUserName());
                replicatedBorrowerGuarantor.setCreatedDate(date);
                replicatedBorrowerGuarantor.setStatus(AppsConstants.Status.ACT);
                replicatedBorrowerGuarantor.setBankAndBranch(afBorrowerGuarantor.getBankAndBranch());
                replicatedBorrowerGuarantor.setAmount(afBorrowerGuarantor.getAmount());
                replicatedBorrowerGuarantor.setBorrowerName(afBorrowerGuarantor.getBorrowerName());
                replicatedBorrowerGuarantor.setDateGranted(afBorrowerGuarantor.getDateGranted());
                newBasicInformation.addAfBorrowerGuarantor(replicatedBorrowerGuarantor);
            }
        }
        LOG.info("END : Borrower Guarantor Details For {} ", currentBasicInformation.getIdentificationNo());
        return this;
    }

    public BasicInformationReplicateBuilder buildOwnerShipDetails() throws AppsException {
        for (OwnershipDetails ownershipDetails : currentBasicInformation.getOrderedOwnershipDetailsSet()) {
            if (ownershipDetails.getStatus() == AppsConstants.Status.ACT) {
                OwnershipDetails replicatedOwnershipDetails = new OwnershipDetails();
                replicatedOwnershipDetails.setStatus(AppsConstants.Status.ACT);
                replicatedOwnershipDetails.setCreatedBy(credentialsDTO.getUserName());
                replicatedOwnershipDetails.setCreatedDate(date);
                replicatedOwnershipDetails.setName(ownershipDetails.getName());
                replicatedOwnershipDetails.setAddress(ownershipDetails.getAddress());
                replicatedOwnershipDetails.setContactNo(ownershipDetails.getContactNo());
                replicatedOwnershipDetails.setIdentificationType(ownershipDetails.getIdentificationType());
                replicatedOwnershipDetails.setIdentificationNumber(ownershipDetails.getIdentificationNumber());
                replicatedOwnershipDetails.setShareHolding(ownershipDetails.getShareHolding());
                replicatedOwnershipDetails.setCivilStatus(ownershipDetails.getCivilStatus());
                replicatedOwnershipDetails.setConstitutionType(ownershipDetails.getConstitutionType());
                replicatedOwnershipDetails.setDateOfBirth(ownershipDetails.getDateOfBirth());
                replicatedOwnershipDetails.setCreditCard(ownershipDetails.getCreditCard());
                replicatedOwnershipDetails.setApplicationFormID(ownershipDetails.getApplicationFormID());
                newBasicInformation.addOwnerShipDetails(replicatedOwnershipDetails);
            }
        }
        LOG.info("END : Ownership Details For {} ", currentBasicInformation.getIdentificationNo());
        return this;
    }

    public BasicInformationReplicateBuilder buildCribAttachments() throws AppsException {
        for (AFCribAttachment afCribAttachment : currentBasicInformation.getOrderedAfCribAttachmentSet()) {
            if (afCribAttachment.getStatus() == AppsConstants.Status.ACT) {
                AFCribAttachment replicatedCribAttachment = new AFCribAttachment();
                replicatedCribAttachment.setCreatedBy(credentialsDTO.getUserName());
                replicatedCribAttachment.setCreatedDate(date);
                if (afCribAttachment.getDocStorage() != null) {
                    DocStorage docStorage = new DocStorage();
                    docStorage.setLastUpdatedDate(afCribAttachment.getDocStorage().getLastUpdatedDate());
                    docStorage.setFileName(afCribAttachment.getDocStorage().getFileName());
                    docStorage.setDescription(afCribAttachment.getDocStorage().getDescription());
                    docStorage.setDocument(afCribAttachment.getDocStorage().getDocument());
                    docStorage = this.docStorageDao.saveAndFlush(docStorage);
                    replicatedCribAttachment.setDocStorage(docStorage);
                }
                replicatedCribAttachment.setRemark(afCribAttachment.getRemark());
                replicatedCribAttachment.setCribStatus(afCribAttachment.getCribStatus());
                replicatedCribAttachment.setCribDate(afCribAttachment.getCribDate());
                replicatedCribAttachment.setSupportingDocID(afCribAttachment.getSupportingDocID());
                replicatedCribAttachment.setIdentificationNo(afCribAttachment.getIdentificationNo());
                replicatedCribAttachment.setApplicationFormID(afCribAttachment.getApplicationFormID());
                replicatedCribAttachment.setIdentificationType(afCribAttachment.getIdentificationType());
                replicatedCribAttachment.setUploadedDivCode(getReplicateApplicationFromFormRQ.getAssignUserDivCode());
                replicatedCribAttachment.setUploadedUserDisplayName(getReplicateApplicationFromFormRQ.getAssignUserDisplayName());
                replicatedCribAttachment.setStatus(AppsConstants.Status.ACT);
                newBasicInformation.addCribAttachments(replicatedCribAttachment);
            }
        }
        LOG.info("END : Crib Attachments For {} ", currentBasicInformation.getIdentificationNo());
        return this;
    }

    public BasicInformationReplicateBuilder buildCribReports() throws AppsException {
        for (AFCribReport afCribReport : currentBasicInformation.getOrderedAfCribReportSet()) {
            if (afCribReport.getStatus() == AppsConstants.Status.ACT) {
                AFCribReport replicatedCribReport = new AFCribReport();
                replicatedCribReport.setCreatedDate(date);
                replicatedCribReport.setCreatedBy(credentialsDTO.getUserName());
                replicatedCribReport.setStatus(AppsConstants.Status.ACT);
                replicatedCribReport.setIdentificationType(afCribReport.getIdentificationType());
                replicatedCribReport.setIdentificationNo(afCribReport.getIdentificationNo());
                replicatedCribReport.setReportContent(afCribReport.getReportContent());
                replicatedCribReport.setCribStatus(afCribReport.getCribStatus());
                replicatedCribReport.setRemark(afCribReport.getRemark());
                replicatedCribReport.setCribDate(afCribReport.getCribDate());
                newBasicInformation.addCribReport(replicatedCribReport);
            }
        }
        LOG.info("END : Crib Reports For {} ", currentBasicInformation.getIdentificationNo());
        return this;
    }

    public void setDocStorageDao(DocStorageDao docStorageDao) {
        this.docStorageDao = docStorageDao;
    }

    public void setGetReplicateApplicationFromFormRQ(ReplicateApplicationFormRQ getReplicateApplicationFromFormRQ) {
        this.getReplicateApplicationFromFormRQ = getReplicateApplicationFromFormRQ;
    }
}

