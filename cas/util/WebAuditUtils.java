package com.itechro.cas.util;

import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.model.dto.audit.*;
import com.itechro.cas.model.dto.casmaster.*;
import com.itechro.cas.model.dto.customer.*;
import com.itechro.cas.model.dto.facility.*;
import com.itechro.cas.model.dto.facilitypaper.*;
import com.itechro.cas.model.dto.lead.LeadDTO;
import com.itechro.cas.model.dto.lead.LeadDocumentDTO;
import com.itechro.cas.model.dto.master.RoleDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;

import java.util.Date;

public class WebAuditUtils {

    public static WebAuditDTO constructSupportingDocAudit(SupportingDocDTO updateSupportingDocDTO,
                                                          SupportingDocDTO previousSupportingDocDTO,
                                                          CredentialsDTO credentialsDTO,
                                                          Date date, boolean isDocCreation) {

        WebAuditDTO webAuditDTO;

        SupportingDocContentDTO supportingDocPreviousContentDTO = new SupportingDocContentDTO();
        SupportingDocContentDTO supportingDocUpdateContentDTO = new SupportingDocContentDTO();

        if (previousSupportingDocDTO != null) {
            supportingDocPreviousContentDTO.setSupportingDocID(previousSupportingDocDTO.getSupportingDocID());
            supportingDocPreviousContentDTO.setDocumentName(previousSupportingDocDTO.getDocumentName());
            supportingDocPreviousContentDTO.setDescription(previousSupportingDocDTO.getDescription());
            supportingDocPreviousContentDTO.setSupportDocumentType(previousSupportingDocDTO.getSupportDocumentType());
            if (previousSupportingDocDTO.getApproveStatus() != null) {
                supportingDocPreviousContentDTO.setApproveStatus(previousSupportingDocDTO.getApproveStatus().toString());
            }
            if (previousSupportingDocDTO.getStatus() != null) {
                supportingDocPreviousContentDTO.setStatus(previousSupportingDocDTO.getStatus().toString());
            }
            supportingDocPreviousContentDTO.setApprovedDateStr(previousSupportingDocDTO.getApprovedDateStr());
        }

        supportingDocUpdateContentDTO.setSupportingDocID(updateSupportingDocDTO.getSupportingDocID());
        supportingDocUpdateContentDTO.setDocumentName(updateSupportingDocDTO.getDocumentName());
        supportingDocUpdateContentDTO.setDescription(updateSupportingDocDTO.getDescription());
        supportingDocUpdateContentDTO.setSupportDocumentType(updateSupportingDocDTO.getSupportDocumentType());
        if (updateSupportingDocDTO.getApproveStatus() != null) {
            supportingDocUpdateContentDTO.setApproveStatus(updateSupportingDocDTO.getApproveStatus().toString());
        }
        if (updateSupportingDocDTO.getStatus() != null) {
            supportingDocUpdateContentDTO.setStatus(updateSupportingDocDTO.getStatus().toString());
        }
        supportingDocUpdateContentDTO.setApprovedDateStr(updateSupportingDocDTO.getApprovedDateStr());

        if (isDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.SUPPORTING_DOC, updateSupportingDocDTO.getSupportingDocID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.SUPPORTING_DOC_ADD, date, null, supportingDocUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.SUPPORTING_DOC, updateSupportingDocDTO.getSupportingDocID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.SUPPORTING_DOC_EDIT, date, supportingDocPreviousContentDTO, supportingDocUpdateContentDTO);
        }

        return webAuditDTO;

    }


    public static WebAuditDTO constructLeadAudit(LeadDTO updateLeadDTO,
                                                 LeadDTO previousLeadDTO,
                                                 CredentialsDTO credentialsDTO,
                                                 Date date, boolean isLeadCreation, WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        LeadContentDTO leadPreviousContentDTO = new LeadContentDTO();
        LeadContentDTO leadUpdateContentDTO = new LeadContentDTO();

        if (previousLeadDTO != null) {
            leadPreviousContentDTO.setLeadID(previousLeadDTO.getLeadID());
            leadPreviousContentDTO.setLeadName(previousLeadDTO.getLeadName());
            leadPreviousContentDTO.setLeadRefNumber(previousLeadDTO.getLeadRefNumber());
            if (previousLeadDTO.getCustomerID() != null) {
                leadPreviousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(previousLeadDTO.getCustomerID()));
            }
            leadPreviousContentDTO.setName(previousLeadDTO.getName());
            if (previousLeadDTO.getIsExistingCustomer() != null) {
                leadPreviousContentDTO.setIsExistingCustomer(previousLeadDTO.getIsExistingCustomer().toString());
            }
            if (previousLeadDTO.getLeadFsType() != null) {
                leadPreviousContentDTO.setFsType(previousLeadDTO.getLeadFsType());
            }
            leadPreviousContentDTO.setTypeOfBusiness(previousLeadDTO.getTypeOfBusiness());
            leadPreviousContentDTO.setDesignation(previousLeadDTO.getDesignation());
            if (previousLeadDTO.getLeadCreationType() != null) {
                leadPreviousContentDTO.setLeadCreationType(previousLeadDTO.getLeadCreationType().toString());
            }
            leadPreviousContentDTO.setContactPerson(previousLeadDTO.getContactPerson());
            leadPreviousContentDTO.setEmail(previousLeadDTO.getEmail());
            leadPreviousContentDTO.setAccountNumber(previousLeadDTO.getAccountNumber());
            leadPreviousContentDTO.setMobileNo(previousLeadDTO.getMobileNo());
            leadPreviousContentDTO.setDateOfBirth(previousLeadDTO.getDateOfBirthStr());
            leadPreviousContentDTO.setAddress(previousLeadDTO.getAddress());
            if (previousLeadDTO.getCivilStatus() != null) {
                leadPreviousContentDTO.setCivilStatus(previousLeadDTO.getCivilStatus().toString());
            }
            leadPreviousContentDTO.setIdentificationType(previousLeadDTO.getIdentificationType());
            leadPreviousContentDTO.setIdentificationNumber(previousLeadDTO.getIdentificationNumber());
            leadPreviousContentDTO.setNationality(previousLeadDTO.getNationality());
            leadPreviousContentDTO.setBranchCode(previousLeadDTO.getBranchCode());
            leadPreviousContentDTO.setBranchName(previousLeadDTO.getBranchName());
            if (previousLeadDTO.getLeadStatus() != null) {
                leadPreviousContentDTO.setLeadStatus(previousLeadDTO.getLeadStatus().toString());
            }
            leadPreviousContentDTO.setAssignUserID(previousLeadDTO.getAssignUserID());
        }

        leadUpdateContentDTO.setLeadID(updateLeadDTO.getLeadID());
        leadUpdateContentDTO.setLeadName(updateLeadDTO.getLeadName());
        leadUpdateContentDTO.setLeadRefNumber(updateLeadDTO.getLeadRefNumber());
        if (updateLeadDTO.getCustomerID() != null) {
            leadUpdateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(updateLeadDTO.getCustomerID()));
        }
        leadUpdateContentDTO.setName(updateLeadDTO.getName());
        if (updateLeadDTO.getIsExistingCustomer() != null) {
            leadUpdateContentDTO.setIsExistingCustomer(updateLeadDTO.getIsExistingCustomer().toString());
        }
        if (updateLeadDTO.getLeadFsType() != null) {
            leadUpdateContentDTO.setFsType(updateLeadDTO.getLeadFsType());
        }
        leadUpdateContentDTO.setTypeOfBusiness(updateLeadDTO.getTypeOfBusiness());
        leadUpdateContentDTO.setDesignation(updateLeadDTO.getDesignation());
        if (updateLeadDTO.getLeadCreationType() != null) {
            leadUpdateContentDTO.setLeadCreationType(updateLeadDTO.getLeadCreationType().toString());
        }
        leadUpdateContentDTO.setContactPerson(updateLeadDTO.getContactPerson());
        leadUpdateContentDTO.setEmail(updateLeadDTO.getEmail());
        leadUpdateContentDTO.setAccountNumber(updateLeadDTO.getAccountNumber());
        leadUpdateContentDTO.setMobileNo(updateLeadDTO.getMobileNo());
        leadUpdateContentDTO.setDateOfBirth(updateLeadDTO.getDateOfBirthStr());
        leadUpdateContentDTO.setAddress(updateLeadDTO.getAddress());
        if (updateLeadDTO.getCivilStatus() != null) {
            leadUpdateContentDTO.setCivilStatus(updateLeadDTO.getCivilStatus().toString());
        }
        leadUpdateContentDTO.setIdentificationType(updateLeadDTO.getIdentificationType());
        leadUpdateContentDTO.setIdentificationNumber(updateLeadDTO.getIdentificationNumber());
        leadUpdateContentDTO.setNationality(updateLeadDTO.getNationality());
        leadUpdateContentDTO.setBranchCode(updateLeadDTO.getBranchCode());
        leadUpdateContentDTO.setBranchName(updateLeadDTO.getBranchName());
        if (leadUpdateContentDTO.getLeadStatus() != null) {
            leadUpdateContentDTO.setLeadStatus(updateLeadDTO.getLeadStatus().toString());
        }
        leadUpdateContentDTO.setAssignUserID(updateLeadDTO.getAssignUserID());

        if (isLeadCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.LEAD, updateLeadDTO.getLeadID(), DomainConstants.WebAuditMainCategory.LEAD,
                    DomainConstants.WebAuditSubCategory.LEAD_ADD, date, null, leadUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.LEAD, updateLeadDTO.getLeadID(), DomainConstants.WebAuditMainCategory.LEAD,
                    DomainConstants.WebAuditSubCategory.LEAD_EDIT, date, leadPreviousContentDTO, leadUpdateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructLeadStatusAudit(LeadDTO updateLeadDTO,
                                                       LeadDTO previousLeadDTO,
                                                       CredentialsDTO credentialsDTO,
                                                       Date date, WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        LeadStatusUpdateContentDTO leadStatusPreviousContentDTO = new LeadStatusUpdateContentDTO();
        LeadStatusUpdateContentDTO leadStatusUpdateContentDTO = new LeadStatusUpdateContentDTO();

        if (previousLeadDTO != null) {
            leadStatusPreviousContentDTO.setLeadID(previousLeadDTO.getLeadID());
            leadStatusPreviousContentDTO.setLeadRefNumber(previousLeadDTO.getLeadRefNumber());
            leadStatusPreviousContentDTO.setLeadName(previousLeadDTO.getLeadName());
            if (previousLeadDTO.getCustomerID() != null) {
                leadStatusPreviousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(previousLeadDTO.getCustomerID()));
            }
            leadStatusPreviousContentDTO.setAssignUserName(previousLeadDTO.getAssignUserID());
            if (previousLeadDTO.getLeadStatus() != null) {
                leadStatusPreviousContentDTO.setLeadStatus(previousLeadDTO.getLeadStatus().toString());
            }
        }
        leadStatusUpdateContentDTO.setLeadID(updateLeadDTO.getLeadID());
        leadStatusUpdateContentDTO.setLeadRefNumber(updateLeadDTO.getLeadRefNumber());
        leadStatusUpdateContentDTO.setLeadName(updateLeadDTO.getLeadName());
        if (updateLeadDTO.getCustomerID() != null) {
            leadStatusUpdateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(updateLeadDTO.getCustomerID()));
        }
        leadStatusUpdateContentDTO.setAssignUserName(updateLeadDTO.getAssignUserID());
        if (updateLeadDTO.getLeadStatus() != null) {
            leadStatusUpdateContentDTO.setLeadStatus(updateLeadDTO.getLeadStatus().toString());
        }

        webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.LEAD, updateLeadDTO.getLeadID(), DomainConstants.WebAuditMainCategory.LEAD,
                DomainConstants.WebAuditSubCategory.LEAD_STATUS_EDIT, date, leadStatusPreviousContentDTO, leadStatusUpdateContentDTO);

        return webAuditDTO;
    }


    public static WebAuditDTO constructLeadDocumentAudit(LeadDocumentDTO updateLeadDocDTO,
                                                         LeadDocumentDTO previousLeadDocDTO,
                                                         CredentialsDTO credentialsDTO,
                                                         Date date, boolean isLeadDocCreation,
                                                         WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        LeadDocContentDTO previousContentDTO = new LeadDocContentDTO();
        LeadDocContentDTO updateContentDTO = new LeadDocContentDTO();

        if (previousLeadDocDTO != null) {
            previousContentDTO.setLeadDocumentID(previousLeadDocDTO.getLeadDocumentID());
            previousContentDTO.setRemark(previousLeadDocDTO.getRemark());
            if (previousLeadDocDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousLeadDocDTO.getStatus().toString());
            }
            if (previousLeadDocDTO.getSupportingDocDTO() != null) {
                previousContentDTO.setSupportingDocID(previousLeadDocDTO.getSupportingDocDTO().getSupportingDocID());
                previousContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(previousLeadDocDTO.getSupportingDocDTO().getSupportingDocID()));
            }
            previousContentDTO.setLeadID(previousLeadDocDTO.getLeadID());
            if (previousLeadDocDTO.getLeadID() != null) {
                previousContentDTO.setLeadRefNumber(webAuditJDBCDao.getLeadRefNumberByLeadID(previousLeadDocDTO.getLeadID()));
            }
        }

        updateContentDTO.setLeadDocumentID(updateLeadDocDTO.getLeadDocumentID());
        updateContentDTO.setRemark(updateLeadDocDTO.getRemark());
        if (updateLeadDocDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateLeadDocDTO.getStatus().toString());
        }
        if (updateLeadDocDTO.getSupportingDocDTO() != null) {
            updateContentDTO.setSupportingDocID(updateLeadDocDTO.getSupportingDocDTO().getSupportingDocID());
            updateContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(updateLeadDocDTO.getSupportingDocDTO().getSupportingDocID()));
        }
        updateContentDTO.setLeadID(updateLeadDocDTO.getLeadID());
        if (updateContentDTO.getLeadID() != null) {
            updateContentDTO.setLeadRefNumber(webAuditJDBCDao.getLeadRefNumberByLeadID(updateLeadDocDTO.getLeadID()));
        }

        if (isLeadDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.LEAD, updateLeadDocDTO.getLeadID(), DomainConstants.WebAuditMainCategory.LEAD,
                    DomainConstants.WebAuditSubCategory.LEAD_DOC_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.LEAD, updateLeadDocDTO.getLeadID(), DomainConstants.WebAuditMainCategory.LEAD,
                    DomainConstants.WebAuditSubCategory.LEAD_DOC_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructDocStorageAudit(DocStorageDTO updateDocStorageDTO,
                                                       DocStorageDTO previousDocStorageDTO,
                                                       CredentialsDTO credentialsDTO,
                                                       Date date, boolean isStorageDocCreation) {

        WebAuditDTO webAuditDTO;

        DocStorageContentDTO previousContentDTO = new DocStorageContentDTO();
        DocStorageContentDTO updateContentDTO = new DocStorageContentDTO();

        if (previousDocStorageDTO != null) {
            previousContentDTO.setDocStorageID(previousDocStorageDTO.getDocStorageID());
            previousContentDTO.setFileName(previousDocStorageDTO.getFileName());
            previousContentDTO.setDescription(previousDocStorageDTO.getDescription());
            previousContentDTO.setLastUpdatedDateStr(previousDocStorageDTO.getLastUpdatedDateStr());
        }

        updateContentDTO.setDocStorageID(updateDocStorageDTO.getDocStorageID());
        updateContentDTO.setFileName(updateDocStorageDTO.getFileName());
        updateContentDTO.setDescription(updateDocStorageDTO.getDescription());
        updateContentDTO.setLastUpdatedDateStr(updateDocStorageDTO.getLastUpdatedDateStr());

        if (isStorageDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.DOCUMENT, updateDocStorageDTO.getDocStorageID(), DomainConstants.WebAuditMainCategory.DOCUMENT,
                    DomainConstants.WebAuditSubCategory.DOC_STORAGE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.DOCUMENT, updateDocStorageDTO.getDocStorageID(), DomainConstants.WebAuditMainCategory.DOCUMENT,
                    DomainConstants.WebAuditSubCategory.DOC_STORAGE_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }

    public static WebAuditDTO constructFacilityPaperAudit(FacilityPaperDTO updateFacilityPaperDTO,
                                                          FacilityPaperDTO previousFacilityPaperDTO,
                                                          CredentialsDTO credentialsDTO,
                                                          Date date, boolean isCreation,
                                                          WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FacilityPaperContentDTO previousContentDTO = new FacilityPaperContentDTO();
        FacilityPaperContentDTO updateContentDTO = new FacilityPaperContentDTO();

        if (previousFacilityPaperDTO != null) {
            previousContentDTO.setFacilityPaperID(previousFacilityPaperDTO.getFacilityPaperID());
            previousContentDTO.setUpcTemplateID(previousFacilityPaperDTO.getUpcTemplateID());
            if (previousFacilityPaperDTO.getUpcTemplateID() != null) {
                previousContentDTO.setUpcTemplateName(webAuditJDBCDao.getUpcTemplateNameByID(previousFacilityPaperDTO.getUpcTemplateID()));
            }
            previousContentDTO.setBranchCode(previousFacilityPaperDTO.getBranchCode());
            previousContentDTO.setFpRefNumber(previousFacilityPaperDTO.getFpRefNumber());
            if (previousFacilityPaperDTO.getIsCooperate() != null) {
                previousContentDTO.setIsCooperate(previousFacilityPaperDTO.getIsCooperate().toString());
            }
            previousContentDTO.setFacilityPaperNumber(previousFacilityPaperDTO.getFacilityPaperNumber());
            previousContentDTO.setBankAccountID(previousFacilityPaperDTO.getBankAccountID());
            previousContentDTO.setFpApprovingAuthorityLevel(previousFacilityPaperDTO.getFpApprovingAuthorityLevel());
            previousContentDTO.setCurrentFacilityPaperStatus(previousFacilityPaperDTO.getCurrentAuthorityLevel());
            previousContentDTO.setCurrentAssignUser(previousFacilityPaperDTO.getCurrentAssignUser());
            previousContentDTO.setExistingFacilitiesROA(previousFacilityPaperDTO.getExistingFacilitiesROA());
            previousContentDTO.setProposedFacilitiesROA(previousFacilityPaperDTO.getProposedFacilitiesROA());
            previousContentDTO.setCurrentAuthorityLevel(previousFacilityPaperDTO.getCurrentAuthorityLevel());

            previousContentDTO.setTotalDirectExposureNew(previousFacilityPaperDTO.getTotalDirectExposureNew());
            previousContentDTO.setTotalDirectExposurePrevious(previousFacilityPaperDTO.getGroupTotalDirectExposurePrevious());
            previousContentDTO.setTotalIndirectExposureNew(previousFacilityPaperDTO.getTotalIndirectExposureNew());
            previousContentDTO.setTotalIndirectExposurePrevious(previousFacilityPaperDTO.getTotalIndirectExposurePrevious());
            previousContentDTO.setTotalExposureNew(previousFacilityPaperDTO.getTotalExposureNew());
            previousContentDTO.setTotalExposurePrevious(previousFacilityPaperDTO.getTotalExposurePrevious());
            if (previousFacilityPaperDTO.getAddTotalExposureToGroup() != null) {
                previousContentDTO.setAddTotalExposureToGroup(previousFacilityPaperDTO.getAddTotalExposureToGroup().getStrVal());
            }
            previousContentDTO.setGroupTotalDirectExposurePrevious(previousFacilityPaperDTO.getGroupTotalDirectExposurePrevious());
            previousContentDTO.setGroupTotalDirectExposureNew(previousFacilityPaperDTO.getGroupTotalDirectExposureNew());
            previousContentDTO.setGroupTotalIndirectExposurePrevious(previousFacilityPaperDTO.getGroupTotalIndirectExposurePrevious());
            previousContentDTO.setGroupTotalIndirectExposureNew(previousFacilityPaperDTO.getGroupTotalIndirectExposureNew());
            previousContentDTO.setGroupTotalExposurePrevious(previousFacilityPaperDTO.getGroupTotalExposurePrevious());
            previousContentDTO.setGroupTotalExposureNew(previousFacilityPaperDTO.getGroupTotalExposureNew());

        }

        updateContentDTO.setFacilityPaperID(updateFacilityPaperDTO.getFacilityPaperID());
        updateContentDTO.setUpcTemplateID(updateFacilityPaperDTO.getUpcTemplateID());
        if (updateFacilityPaperDTO.getUpcTemplateID() != null) {
            updateContentDTO.setUpcTemplateName(webAuditJDBCDao.getUpcTemplateNameByID(updateFacilityPaperDTO.getUpcTemplateID()));
        }
        updateContentDTO.setBranchCode(updateFacilityPaperDTO.getBranchCode());
        updateContentDTO.setFpRefNumber(updateFacilityPaperDTO.getFpRefNumber());
        if (updateFacilityPaperDTO.getIsCooperate() != null) {
            updateContentDTO.setIsCooperate(updateFacilityPaperDTO.getIsCooperate().toString());
        }
        updateContentDTO.setFacilityPaperNumber(updateFacilityPaperDTO.getFacilityPaperNumber());
        updateContentDTO.setBankAccountID(updateFacilityPaperDTO.getBankAccountID());
        updateContentDTO.setFpApprovingAuthorityLevel(updateFacilityPaperDTO.getFpApprovingAuthorityLevel());
        updateContentDTO.setCurrentFacilityPaperStatus(updateFacilityPaperDTO.getCurrentAuthorityLevel());
        updateContentDTO.setCurrentAssignUser(updateFacilityPaperDTO.getCurrentAssignUser());
        updateContentDTO.setExistingFacilitiesROA(updateFacilityPaperDTO.getExistingFacilitiesROA());
        updateContentDTO.setProposedFacilitiesROA(updateFacilityPaperDTO.getProposedFacilitiesROA());
        updateContentDTO.setCurrentAuthorityLevel(updateFacilityPaperDTO.getCurrentAuthorityLevel());
        updateContentDTO.setLeadRefNumber(updateFacilityPaperDTO.getLeadRefNumber());
        if (updateFacilityPaperDTO.getWorkflowTemplateID() != null) {
            updateContentDTO.setWorkFlowTemplateID(updateFacilityPaperDTO.getWorkflowTemplateID());
            updateContentDTO.setWorkFlowTemplateName(webAuditJDBCDao.getWorkFlowTemplateNameByID(updateFacilityPaperDTO.getWorkflowTemplateID()));
        }
        updateContentDTO.setTotalDirectExposureNew(updateFacilityPaperDTO.getTotalDirectExposureNew());
        updateContentDTO.setTotalDirectExposurePrevious(updateContentDTO.getGroupTotalDirectExposurePrevious());
        updateContentDTO.setTotalIndirectExposureNew(updateFacilityPaperDTO.getTotalIndirectExposureNew());
        updateContentDTO.setTotalIndirectExposurePrevious(updateFacilityPaperDTO.getTotalIndirectExposurePrevious());
        updateContentDTO.setTotalExposureNew(updateFacilityPaperDTO.getTotalExposureNew());
        updateContentDTO.setTotalExposurePrevious(updateFacilityPaperDTO.getTotalExposurePrevious());
        if (updateFacilityPaperDTO.getAddTotalExposureToGroup() != null) {
            updateContentDTO.setAddTotalExposureToGroup(updateFacilityPaperDTO.getAddTotalExposureToGroup().getStrVal());
        }
        updateContentDTO.setGroupTotalDirectExposurePrevious(updateFacilityPaperDTO.getGroupTotalDirectExposurePrevious());
        updateContentDTO.setGroupTotalDirectExposureNew(updateFacilityPaperDTO.getGroupTotalDirectExposureNew());
        updateContentDTO.setGroupTotalIndirectExposurePrevious(updateFacilityPaperDTO.getGroupTotalIndirectExposurePrevious());
        updateContentDTO.setGroupTotalIndirectExposureNew(updateFacilityPaperDTO.getGroupTotalIndirectExposureNew());
        updateContentDTO.setGroupTotalExposurePrevious(updateFacilityPaperDTO.getGroupTotalExposurePrevious());
        updateContentDTO.setGroupTotalExposureNew(updateFacilityPaperDTO.getGroupTotalExposureNew());


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateFacilityPaperDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateFacilityPaperDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPDocumentAudit(FPDocumentDTO updateFPDocumentDTO,
                                                       FPDocumentDTO previousFPDocumentDTO,
                                                       CredentialsDTO credentialsDTO,
                                                       Date date, boolean isDocCreation,
                                                       WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FPDocumentContentDTO previousContentDTO = new FPDocumentContentDTO();
        FPDocumentContentDTO updateContentDTO = new FPDocumentContentDTO();

        if (previousFPDocumentDTO != null) {
            previousContentDTO.setFpDocumentID(previousFPDocumentDTO.getSupportingDocID());
            if (previousFPDocumentDTO.getFacilityPaperID() != null) {
                previousContentDTO.setFacilityPaperID(previousFPDocumentDTO.getFacilityPaperID());
                previousContentDTO.setFpRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousFPDocumentDTO.getFacilityPaperID()));
            }
            if (previousFPDocumentDTO.getSupportingDocID() != null) {
                previousContentDTO.setSupportingDocID(previousFPDocumentDTO.getSupportingDocID());
                previousContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(previousFPDocumentDTO.getSupportingDocID()));
            }
            previousContentDTO.setFacilityPaperID(previousFPDocumentDTO.getFacilityPaperID());
            if (previousFPDocumentDTO.getDocStorageDTO() != null) {
                previousContentDTO.setDocStorageID(previousFPDocumentDTO.getDocStorageDTO().getDocStorageID());
                previousContentDTO.setDocStorageName(previousFPDocumentDTO.getDocStorageDTO().getFileName());
            }
            previousContentDTO.setDescription(previousFPDocumentDTO.getDescription());
            if (previousFPDocumentDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousFPDocumentDTO.getStatus().toString());
            }
        }

        updateContentDTO.setFpDocumentID(updateFPDocumentDTO.getSupportingDocID());
        if (updateFPDocumentDTO.getFacilityPaperID() != null) {
            updateContentDTO.setFacilityPaperID(updateFPDocumentDTO.getFacilityPaperID());
            updateContentDTO.setFpRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(updateFPDocumentDTO.getFacilityPaperID()));
        }
        if (updateFPDocumentDTO.getSupportingDocID() != null) {
            updateContentDTO.setSupportingDocID(updateFPDocumentDTO.getSupportingDocID());
            updateContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(updateFPDocumentDTO.getSupportingDocID()));
        }
        updateContentDTO.setFacilityPaperID(updateFPDocumentDTO.getFacilityPaperID());
        if (updateFPDocumentDTO.getDocStorageDTO() != null) {
            updateContentDTO.setDocStorageID(updateFPDocumentDTO.getDocStorageDTO().getDocStorageID());
            updateContentDTO.setDocStorageName(updateFPDocumentDTO.getDocStorageDTO().getFileName());
        }
        updateContentDTO.setDescription(updateFPDocumentDTO.getDescription());
        if (updateFPDocumentDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateFPDocumentDTO.getStatus().toString());
        }


        if (isDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateFPDocumentDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_DOC_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateFPDocumentDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_DOC_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPCustomerDocAudit(CASCustomerDocDTO updateCASCustomerDocDTO,
                                                          CASCustomerDocDTO previousCASCustomerDocDTO,
                                                          CredentialsDTO credentialsDTO,
                                                          Date date, boolean isDocCreation,
                                                          WebAuditJDBCDao webAuditJDBCDao, Integer facilityPaperID) {

        WebAuditDTO webAuditDTO;

        FPCustomerDocContentDTO previousContentDTO = new FPCustomerDocContentDTO();
        FPCustomerDocContentDTO updateContentDTO = new FPCustomerDocContentDTO();

        if (previousCASCustomerDocDTO != null) {
            previousContentDTO.setFpCustomerDocID(previousCASCustomerDocDTO.getFpCustomerDocID());
            if (previousCASCustomerDocDTO.getSupportingDocID() != null) {
                previousContentDTO.setSupportingDocID(previousCASCustomerDocDTO.getSupportingDocID());
                previousContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(previousCASCustomerDocDTO.getSupportingDocID()));
            }
            if (previousCASCustomerDocDTO.getCasCustomerID() != null) {
                previousContentDTO.setFpCustomerID(previousCASCustomerDocDTO.getCasCustomerID());
                previousContentDTO.setFpCustomerName(webAuditJDBCDao.getCustomerNameByCASCustomerID(previousCASCustomerDocDTO.getCasCustomerID()));
            }
            if (previousCASCustomerDocDTO.getDocStorageDTO() != null) {
                previousContentDTO.setDocStorageID(previousCASCustomerDocDTO.getDocStorageDTO().getDocStorageID());
                previousContentDTO.setDocStorageName(previousCASCustomerDocDTO.getDocStorageDTO().getFileName());
            }
            previousContentDTO.setDescription(previousCASCustomerDocDTO.getDescription());
        }

        updateContentDTO.setFpCustomerDocID(updateCASCustomerDocDTO.getFpCustomerDocID());
        if (updateCASCustomerDocDTO.getSupportingDocID() != null) {
            updateContentDTO.setSupportingDocID(updateCASCustomerDocDTO.getSupportingDocID());
            updateContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(updateCASCustomerDocDTO.getSupportingDocID()));
        }
        if (updateCASCustomerDocDTO.getCasCustomerID() != null) {
            updateContentDTO.setFpCustomerID(updateCASCustomerDocDTO.getCasCustomerID());
            updateContentDTO.setFpCustomerName(webAuditJDBCDao.getCustomerNameByCASCustomerID(updateCASCustomerDocDTO.getCasCustomerID()));
        }
        if (updateCASCustomerDocDTO.getDocStorageDTO() != null) {
            updateContentDTO.setDocStorageID(updateCASCustomerDocDTO.getDocStorageDTO().getDocStorageID());
            updateContentDTO.setDocStorageName(updateCASCustomerDocDTO.getDocStorageDTO().getFileName());
        }
        updateContentDTO.setDescription(updateCASCustomerDocDTO.getDescription());


        if (isDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, facilityPaperID, DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_CUSTOMER_DOC_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, facilityPaperID, DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_CUSTOMER_DOC_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPCustomerCribDetailAudit(CASCustomerCribDetailDTO updateCribDTO,
                                                                 CASCustomerCribDetailDTO previousCribDTO,
                                                                 CredentialsDTO credentialsDTO,
                                                                 Date date, boolean isDocCreation,
                                                                 WebAuditJDBCDao webAuditJDBCDao, Integer facilityPaperID) {

        WebAuditDTO webAuditDTO;

        FPCustomerCribDetailContentDTO previousContentDTO = new FPCustomerCribDetailContentDTO();
        FPCustomerCribDetailContentDTO updateContentDTO = new FPCustomerCribDetailContentDTO();

        if (previousCribDTO != null) {
            previousContentDTO.setFpCustomerCribDetailID(previousCribDTO.getCasCustomerCribDetailsID());
            if (previousCribDTO.getSupportingDocID() != null) {
                previousContentDTO.setSupportingDocID(previousCribDTO.getSupportingDocID());
                previousContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(previousCribDTO.getSupportingDocID()));
            }
            previousContentDTO.setFpCustomerID(previousCribDTO.getCasCustomerID());
            if (previousCribDTO.getCasCustomerID() != null) {
                previousContentDTO.setFpCustomerName(webAuditJDBCDao.getCustomerNameByCASCustomerID(previousCribDTO.getCasCustomerID()));
            }
            if (previousCribDTO.getDocStorageDTO() != null) {
                previousContentDTO.setDocStorageID(previousCribDTO.getDocStorageDTO().getDocStorageID());
                previousContentDTO.setDocStorageName(previousCribDTO.getDocStorageDTO().getFileName());
            }
            previousContentDTO.setRemark(previousCribDTO.getRemark());
            if (previousCribDTO.getCribIssueDate() != null) {
                previousContentDTO.setCribIssueDate(previousCribDTO.getCribIssueDate().toString());
            }
            if (previousCribDTO.getCribStatus() != null) {
                previousContentDTO.setCribStatus(previousCribDTO.getCribStatus().toString());
            }
            if (previousCribDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousCribDTO.getStatus().toString());
            }
        }

        updateContentDTO.setFpCustomerCribDetailID(updateCribDTO.getCasCustomerCribDetailsID());
        if (updateCribDTO.getSupportingDocID() != null) {
            updateContentDTO.setSupportingDocID(updateCribDTO.getSupportingDocID());
            updateContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(updateCribDTO.getSupportingDocID()));
        }
        updateContentDTO.setFpCustomerID(updateCribDTO.getCasCustomerID());
        if (updateCribDTO.getCasCustomerID() != null) {
            updateContentDTO.setFpCustomerName(webAuditJDBCDao.getCustomerNameByCASCustomerID(updateCribDTO.getCasCustomerID()));
        }
        if (updateCribDTO.getDocStorageDTO() != null) {
            updateContentDTO.setDocStorageID(updateCribDTO.getDocStorageDTO().getDocStorageID());
            updateContentDTO.setDocStorageName(updateCribDTO.getDocStorageDTO().getFileName());
        }
        updateContentDTO.setRemark(updateCribDTO.getRemark());
        if (updateCribDTO.getCribIssueDate() != null) {
            updateContentDTO.setCribIssueDate(updateCribDTO.getCribIssueDate().toString());
        }
        if (updateCribDTO.getCribStatus() != null) {
            updateContentDTO.setCribStatus(updateCribDTO.getCribStatus().toString());
        }
        if (updateCribDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateCribDTO.getStatus().toString());
        }


        if (isDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, facilityPaperID, DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_CRIB_DETAIL_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, facilityPaperID, DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_CRIB_DETAIL_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPDirectorDetailAudit(FPDirectorDetailDTO updateDirectorDTO,
                                                             FPDirectorDetailDTO previousDirectorDTO,
                                                             CredentialsDTO credentialsDTO,
                                                             Date date, boolean isDocCreation,
                                                             WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FPDirectorDetailContentDTO previousContentDTO = new FPDirectorDetailContentDTO();
        FPDirectorDetailContentDTO updateContentDTO = new FPDirectorDetailContentDTO();

        if (previousDirectorDTO != null) {
            previousContentDTO.setFpDirectorDetailID(previousDirectorDTO.getFpDirectorDetailID());
            if (previousDirectorDTO.getFacilityPaperID() != null) {
                previousContentDTO.setFacilityPaperID(previousDirectorDTO.getFacilityPaperID());
                previousContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousDirectorDTO.getFacilityPaperID()));
            }
            previousContentDTO.setDirectorName(previousDirectorDTO.getDirectorName());
            previousContentDTO.setFullName(previousDirectorDTO.getFullName());
            if (previousDirectorDTO.getCivilStatus() != null) {
                previousContentDTO.setCivilStatus(previousDirectorDTO.getCivilStatus().toString());
            }
            previousContentDTO.setAddress(previousDirectorDTO.getAddress());
            if (previousDirectorDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDirectorDTO.getStatus().toString());
            }
            previousContentDTO.setDateOfBirth(previousDirectorDTO.getDateOfBirthStr());
        }

        updateContentDTO.setFpDirectorDetailID(updateDirectorDTO.getFpDirectorDetailID());
        if (updateDirectorDTO.getFacilityPaperID() != null) {
            updateContentDTO.setFacilityPaperID(updateDirectorDTO.getFacilityPaperID());
            updateContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(updateDirectorDTO.getFacilityPaperID()));
        }
        updateContentDTO.setFacilityPaperID(updateDirectorDTO.getFacilityPaperID());
        updateContentDTO.setDirectorName(updateDirectorDTO.getDirectorName());
        updateContentDTO.setFullName(updateDirectorDTO.getFullName());
        if (updateDirectorDTO.getCivilStatus() != null) {
            updateContentDTO.setCivilStatus(updateDirectorDTO.getCivilStatus().toString());
        }
        updateContentDTO.setAddress(updateDirectorDTO.getAddress());
        if (updateDirectorDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDirectorDTO.getStatus().toString());
        }
        updateContentDTO.setDateOfBirth(updateDirectorDTO.getDateOfBirthStr());


        if (isDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDirectorDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_DIRECTOR_DETAIL_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDirectorDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_DIRECTOR_DETAIL_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPCompanyRoaContentAudit(FPCompanyRoaDTO updateDirectorDTO,
                                                                FPCompanyRoaDTO previousDirectorDTO,
                                                                CredentialsDTO credentialsDTO,
                                                                Date date, boolean isDocCreation,
                                                                WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FPCompanyRoaContentDTO previousContentDTO = new FPCompanyRoaContentDTO();
        FPCompanyRoaContentDTO updateContentDTO = new FPCompanyRoaContentDTO();

        if (previousDirectorDTO != null) {
            previousContentDTO.setFpCompanyRoaID(previousDirectorDTO.getFpCompanyRoaID());
            if (previousDirectorDTO.getFacilityPaperID() != null) {
                previousContentDTO.setFacilityPaperID(previousDirectorDTO.getFacilityPaperID());
                previousContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousDirectorDTO.getFacilityPaperID()));
            }
            previousContentDTO.setDescription(previousDirectorDTO.getDescription());
            if (previousDirectorDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDirectorDTO.getStatus().toString());
            }
        }
        updateContentDTO.setFpCompanyRoaID(updateDirectorDTO.getFpCompanyRoaID());
        if (updateDirectorDTO.getFacilityPaperID() != null) {
            updateContentDTO.setFacilityPaperID(updateDirectorDTO.getFacilityPaperID());
            updateContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(updateDirectorDTO.getFacilityPaperID()));
        }
        updateContentDTO.setDescription(updateDirectorDTO.getDescription());
        if (updateDirectorDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDirectorDTO.getStatus().toString());
        }

        if (isDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDirectorDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_DIRECTOR_DETAIL_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDirectorDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_DIRECTOR_DETAIL_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPCommentAudit(FPCommentDTO updateCommentDTO,
                                                      FPCommentDTO previousCommentDTO,
                                                      CredentialsDTO credentialsDTO,
                                                      Date date, boolean isDocCreation,
                                                      WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FPCommentContetDTO previousContentDTO = new FPCommentContetDTO();
        FPCommentContetDTO updateContentDTO = new FPCommentContetDTO();

        if (previousCommentDTO != null) {
            previousContentDTO.setFpCommentID(previousCommentDTO.getFpCommentID());
            if (previousCommentDTO.getFacilityPaperID() != null) {
                previousContentDTO.setFacilityPaperID(previousCommentDTO.getFacilityPaperID());
                previousContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousCommentDTO.getFacilityPaperID()));
            }
            previousContentDTO.setComment(previousCommentDTO.getComment());
            previousContentDTO.setCommentedTimeStr(previousCommentDTO.getCreatedDateStr());
            if (previousCommentDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousCommentDTO.getStatus().toString());
            }
        }
        updateContentDTO.setFpCommentID(updateCommentDTO.getFpCommentID());
        if (updateCommentDTO.getFacilityPaperID() != null) {
            updateContentDTO.setFacilityPaperID(updateCommentDTO.getFacilityPaperID());
            updateContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(updateCommentDTO.getFacilityPaperID()));
        }
        updateContentDTO.setComment(updateCommentDTO.getComment());
        updateContentDTO.setCommentedTimeStr(updateCommentDTO.getCreatedDateStr());
        if (updateCommentDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateCommentDTO.getStatus().toString());
        }

        if (isDocCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateCommentDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_COMMENT_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateCommentDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_COMMENT_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPCustomerOtherBankFacilityAudit(CASCustomerOtherBankFacilityDTO updateDTO,
                                                                        CASCustomerOtherBankFacilityDTO previousDTO,
                                                                        CredentialsDTO credentialsDTO,
                                                                        Date date, boolean isOtherBankFacilityCreation,
                                                                        WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FPCustomerOtherBankFacilityContentDTO previousContentDTO = new FPCustomerOtherBankFacilityContentDTO();
        FPCustomerOtherBankFacilityContentDTO updateContentDTO = new FPCustomerOtherBankFacilityContentDTO();
        Integer customerID = null;

        if (previousDTO != null) {
            previousContentDTO.setFpCusOtherBankFacilityID(previousDTO.getCasCustomerOtherBankFacilityID());
            previousContentDTO.setFpCustomerID(previousDTO.getCasCustomerID());
            if (previousDTO.getCasCustomerID() != null) {
                previousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByCASCustomerID(previousDTO.getCasCustomerID()));
            }
            previousContentDTO.setBankName(previousDTO.getBankName());
            previousContentDTO.setBranchName(previousDTO.getBranchName());
            previousContentDTO.setCurrency(previousDTO.getCurrency());
            previousContentDTO.setFacilityAmount(previousDTO.getFacilityAmount());
            previousContentDTO.setExistingAmount(previousDTO.getExistingAmount());
            previousContentDTO.setOriginalAmount(previousDTO.getOriginalAmount());
            previousContentDTO.setOutstandingAmount(previousDTO.getOutstandingAmount());
            previousContentDTO.setFacilityType(previousDTO.getFacilityType());
            previousContentDTO.setDisbursementDate(previousDTO.getDisbursementDateStr());
            previousContentDTO.setMaturityDate(previousDTO.getMaturityDateStr());
            previousContentDTO.setSecurities(previousDTO.getSecurities());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }
        updateContentDTO.setFpCusOtherBankFacilityID(updateDTO.getCasCustomerOtherBankFacilityID());
        updateContentDTO.setFpCustomerID(updateDTO.getCasCustomerID());
        if (updateDTO.getCasCustomerID() != null) {
            updateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByCASCustomerID(updateDTO.getCasCustomerID()));
            customerID = webAuditJDBCDao.getCustomerIDByCASCustomerID(updateDTO.getCasCustomerID());
        }
        updateContentDTO.setBankName(updateDTO.getBankName());
        updateContentDTO.setBranchName(updateDTO.getBranchName());
        updateContentDTO.setCurrency(updateDTO.getCurrency());
        updateContentDTO.setFacilityAmount(updateDTO.getFacilityAmount());
        updateContentDTO.setExistingAmount(updateDTO.getExistingAmount());
        updateContentDTO.setOriginalAmount(updateDTO.getOriginalAmount());
        updateContentDTO.setOutstandingAmount(updateDTO.getOutstandingAmount());
        updateContentDTO.setFacilityType(updateDTO.getFacilityType());
        updateContentDTO.setDisbursementDate(updateDTO.getDisbursementDateStr());
        updateContentDTO.setMaturityDate(updateDTO.getMaturityDateStr());
        updateContentDTO.setSecurities(updateDTO.getSecurities());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }

        if (isOtherBankFacilityCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, customerID, DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_OTHER_BANK_FACILITY_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, customerID, DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_OTHER_BANK_FACILITY_EDIT, date, updateContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPCustomerAudit(CASCustomerDTO updateDTO,
                                                       CASCustomerDTO previousDTO,
                                                       CredentialsDTO credentialsDTO,
                                                       Date date, boolean isFPCustomerCreation,
                                                       WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FPCustomerContentDTO previousContentDTO = new FPCustomerContentDTO();
        FPCustomerContentDTO updateContentDTO = new FPCustomerContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setFpCustomerID(previousDTO.getCasCustomerID());
            previousContentDTO.setCustomerId(previousDTO.getCustomerID());
            if (previousDTO.getCustomerID() != null) {
                previousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(previousDTO.getCustomerID()));
            }
            previousContentDTO.setDisplayOrder(previousDTO.getDisplayOrder());
            if (previousDTO.getFacilityPaperID() != null) {
                previousContentDTO.setFacilityPaperID(previousDTO.getFacilityPaperID());
                previousContentDTO.setFpRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousDTO.getFacilityPaperID()));
            }
            previousContentDTO.setPrimary(previousDTO.getIsPrimary());
            previousContentDTO.setStatus(previousDTO.getStatus().toString());
        }
        updateContentDTO.setFpCustomerID(updateDTO.getCasCustomerID());
        updateContentDTO.setCustomerId(updateDTO.getCustomerID());
        if (updateDTO.getCustomerID() != null) {
            updateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(updateDTO.getCustomerID()));
        }
        updateContentDTO.setDisplayOrder(updateDTO.getDisplayOrder());
        if (updateDTO.getFacilityPaperID() != null) {
            updateContentDTO.setFacilityPaperID(updateDTO.getFacilityPaperID());
            updateContentDTO.setFpRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(updateDTO.getFacilityPaperID()));
        }
        updateContentDTO.setPrimary(updateDTO.getIsPrimary());
        updateContentDTO.setStatus(updateDTO.getStatus().toString());


        if (isFPCustomerCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_CUSTOMER_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_CUSTOMER_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFPUpcSectionDataAudit(FPUpcSectionDataDTO updateDTO,
                                                             FPUpcSectionDataDTO previousDTO,
                                                             CredentialsDTO credentialsDTO,
                                                             Date date, boolean isFPCustomerCreation,
                                                             WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FPUpcSectionDataContentDTO previousContentDTO = new FPUpcSectionDataContentDTO();
        FPUpcSectionDataContentDTO updateContentDTO = new FPUpcSectionDataContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setFpUpcSectionDataID(previousDTO.getFpUpcSectionDataID());
            if (previousDTO.getFacilityPaperID() != null) {
                previousContentDTO.setFacilityPaperID(previousDTO.getFacilityPaperID());
                previousContentDTO.setFpRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousDTO.getFacilityPaperID()));
            }
            if (previousContentDTO.getUpcSectionID() != null) {
                previousContentDTO.setUpcSectionID(previousDTO.getUpcSectionID());
                previousContentDTO.setUpcSectionName(webAuditJDBCDao.getUpcSectionNameBySectionID(previousDTO.getUpcSectionID()));
            }
            previousContentDTO.setParentSectionID(previousDTO.getParentSectionID());
            previousContentDTO.setSectionLevel(previousDTO.getSectionLevel());
            previousContentDTO.setDisplayOrder(previousDTO.getDisplayOrder());
            previousContentDTO.setData(previousDTO.getData());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }
        updateContentDTO.setFpUpcSectionDataID(updateDTO.getFpUpcSectionDataID());
        if (updateDTO.getFacilityPaperID() != null) {
            updateContentDTO.setFacilityPaperID(updateDTO.getFacilityPaperID());
            updateContentDTO.setFpRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(updateDTO.getFacilityPaperID()));
        }
        if (updateDTO.getUpcSectionID() != null) {
            updateContentDTO.setUpcSectionID(updateDTO.getUpcSectionID());
            updateContentDTO.setUpcSectionName(webAuditJDBCDao.getUpcSectionNameBySectionID(updateDTO.getUpcSectionID()));
        }
        updateContentDTO.setParentSectionID(updateDTO.getParentSectionID());
        updateContentDTO.setSectionLevel(updateDTO.getSectionLevel());
        updateContentDTO.setDisplayOrder(updateDTO.getDisplayOrder());
        updateContentDTO.setData(updateDTO.getData());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }


        if (isFPCustomerCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_UPC_SECTION_DATA_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PAPER_UPC_SECTION_DATA_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructCreditFacilityTypeAudit(CreditFacilityTypeDTO updateCreditFacilityTypeDTO,
                                                               CreditFacilityTypeDTO previousCreditFacilityTypeDTO,
                                                               CredentialsDTO credentialsDTO,
                                                               Date date, boolean isCreation) {

        WebAuditDTO webAuditDTO;

        CreditFacilityTypeContentDTO creditFacilityTypePreviousContentDTO = new CreditFacilityTypeContentDTO();
        CreditFacilityTypeContentDTO creditFacilityTypeUpdateContentDTO = new CreditFacilityTypeContentDTO();

        if (previousCreditFacilityTypeDTO != null) {
            creditFacilityTypePreviousContentDTO.setCreditFacilityTypeID(previousCreditFacilityTypeDTO.getCreditFacilityTypeID());
            creditFacilityTypePreviousContentDTO.setFacilityTypeName(previousCreditFacilityTypeDTO.getFacilityTypeName());
            creditFacilityTypePreviousContentDTO.setDescription(previousCreditFacilityTypeDTO.getDescription());
            if (previousCreditFacilityTypeDTO.getApproveStatus() != null) {
                creditFacilityTypePreviousContentDTO.setApproveStatus(previousCreditFacilityTypeDTO.getApproveStatus().toString());
            }
            if (previousCreditFacilityTypeDTO.getStatus() != null) {
                creditFacilityTypePreviousContentDTO.setStatus(previousCreditFacilityTypeDTO.getStatus().toString());
            }
            creditFacilityTypePreviousContentDTO.setApprovedDateStr(previousCreditFacilityTypeDTO.getApprovedDateStr());
        }

        creditFacilityTypeUpdateContentDTO.setCreditFacilityTypeID(updateCreditFacilityTypeDTO.getCreditFacilityTypeID());
        creditFacilityTypeUpdateContentDTO.setFacilityTypeName(updateCreditFacilityTypeDTO.getFacilityTypeName());
        creditFacilityTypeUpdateContentDTO.setDescription(updateCreditFacilityTypeDTO.getDescription());
        if (updateCreditFacilityTypeDTO.getApproveStatus() != null) {
            creditFacilityTypeUpdateContentDTO.setApproveStatus(updateCreditFacilityTypeDTO.getApproveStatus().toString());
        }
        if (updateCreditFacilityTypeDTO.getStatus() != null) {
            creditFacilityTypeUpdateContentDTO.setStatus(updateCreditFacilityTypeDTO.getStatus().toString());
        }
        creditFacilityTypeUpdateContentDTO.setApprovedDateStr(updateCreditFacilityTypeDTO.getApprovedDateStr());

        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_GROUP, updateCreditFacilityTypeDTO.getCreditFacilityTypeID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TYPE_ADD, date, null, creditFacilityTypeUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_GROUP, updateCreditFacilityTypeDTO.getCreditFacilityTypeID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TYPE_EDIT, date, creditFacilityTypePreviousContentDTO, creditFacilityTypeUpdateContentDTO);
        }

        return webAuditDTO;

    }


    public static WebAuditDTO constructUserDaAudit(UserDaDTO updateUserDaDTO,
                                                   UserDaDTO previousUserDaDTO,
                                                   CredentialsDTO credentialsDTO,
                                                   Date date, boolean isCreation) {

        WebAuditDTO webAuditDTO;

        UserDaContentDTO userDaPreviousContentDTO = new UserDaContentDTO();
        UserDaContentDTO userDaUpdateContentDTO = new UserDaContentDTO();

        if (previousUserDaDTO != null) {
            userDaPreviousContentDTO.setUserDaID(previousUserDaDTO.getUserDaID());
            userDaPreviousContentDTO.setUserName(previousUserDaDTO.getUserName());
            if (previousUserDaDTO.getMaxAmount() != null) {
                userDaPreviousContentDTO.setMaxAmount(previousUserDaDTO.getMaxAmount().toString());
            }
            userDaPreviousContentDTO.setDescription(previousUserDaDTO.getDescription());
            if (previousUserDaDTO.getApproveStatus() != null) {
                userDaPreviousContentDTO.setApproveStatus(previousUserDaDTO.getApproveStatus().toString());
            }
            if (previousUserDaDTO.getStatus() != null) {
                userDaPreviousContentDTO.setStatus(previousUserDaDTO.getStatus().toString());
            }
            userDaPreviousContentDTO.setApprovedDateStr(previousUserDaDTO.getApprovedDateStr());
        }

        userDaUpdateContentDTO.setUserDaID(updateUserDaDTO.getUserDaID());
        userDaUpdateContentDTO.setUserName(updateUserDaDTO.getUserName());
        if (updateUserDaDTO.getMaxAmount() != null) {
            userDaUpdateContentDTO.setMaxAmount(updateUserDaDTO.getMaxAmount().toString());
        }
        userDaUpdateContentDTO.setDescription(updateUserDaDTO.getDescription());
        if (updateUserDaDTO.getApproveStatus() != null) {
            userDaUpdateContentDTO.setApproveStatus(updateUserDaDTO.getApproveStatus().toString());
        }
        if (updateUserDaDTO.getStatus() != null) {
            userDaUpdateContentDTO.setStatus(updateUserDaDTO.getStatus().toString());
        }
        userDaUpdateContentDTO.setApprovedDateStr(updateUserDaDTO.getApprovedDateStr());

        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.USER_DA, updateUserDaDTO.getUserDaID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.USER_DA_ADD, date, null, userDaUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.USER_DA, updateUserDaDTO.getUserDaID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.USER_DA_EDIT, date, userDaPreviousContentDTO, userDaUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructCreditFacilityTemplateAudit(CreditFacilityTemplateDTO updateUserDaDTO,
                                                                   CreditFacilityTemplateDTO previousUserDaDTO,
                                                                   CredentialsDTO credentialsDTO,
                                                                   Date date, boolean isCreation) {

        WebAuditDTO webAuditDTO;

        CreditFacilityTemplateContentDTO creditFacilityTemplatePreviousContentDTO = new CreditFacilityTemplateContentDTO();
        CreditFacilityTemplateContentDTO creditFacilityTemplateUpdateContentDTO = new CreditFacilityTemplateContentDTO();

        if (previousUserDaDTO != null) {
            creditFacilityTemplatePreviousContentDTO.setCreditFacilityTemplateID(previousUserDaDTO.getCreditFacilityTemplateID());
            creditFacilityTemplatePreviousContentDTO.setCreditFacilityName(previousUserDaDTO.getCreditFacilityName());
            creditFacilityTemplatePreviousContentDTO.setCreditFacilityTypeID(previousUserDaDTO.getCreditFacilityTypeID());
            creditFacilityTemplatePreviousContentDTO.setFacilityTypeName(previousUserDaDTO.getFacilityTypeName());
            if (previousUserDaDTO.getMaxFacilityAmount() != null) {
                creditFacilityTemplatePreviousContentDTO.setMaxFacilityAmount(previousUserDaDTO.getMaxFacilityAmount().toString());
            }
            if (previousUserDaDTO.getMinFacilityAmount() != null) {
                creditFacilityTemplatePreviousContentDTO.setMinFacilityAmount(previousUserDaDTO.getMinFacilityAmount().toString());
            }
            creditFacilityTemplatePreviousContentDTO.setDescription(previousUserDaDTO.getDescription());
            if (previousUserDaDTO.getApproveStatus() != null) {
                creditFacilityTemplatePreviousContentDTO.setApproveStatus(previousUserDaDTO.getApproveStatus().toString());
            }
            if (previousUserDaDTO.getStatus() != null) {
                creditFacilityTemplatePreviousContentDTO.setStatus(previousUserDaDTO.getStatus().toString());
            }
            creditFacilityTemplatePreviousContentDTO.setApprovedDateStr(previousUserDaDTO.getApprovedDateStr());
        }

        creditFacilityTemplateUpdateContentDTO.setCreditFacilityTemplateID(updateUserDaDTO.getCreditFacilityTemplateID());
        creditFacilityTemplateUpdateContentDTO.setCreditFacilityName(updateUserDaDTO.getCreditFacilityName());
        creditFacilityTemplateUpdateContentDTO.setCreditFacilityTypeID(updateUserDaDTO.getCreditFacilityTypeID());
        creditFacilityTemplateUpdateContentDTO.setFacilityTypeName(updateUserDaDTO.getFacilityTypeName());
        if (updateUserDaDTO.getMaxFacilityAmount() != null) {
            creditFacilityTemplateUpdateContentDTO.setMaxFacilityAmount(updateUserDaDTO.getMaxFacilityAmount().toString());
        }
        if (updateUserDaDTO.getMinFacilityAmount() != null) {
            creditFacilityTemplateUpdateContentDTO.setMinFacilityAmount(updateUserDaDTO.getMinFacilityAmount().toString());
        }
        creditFacilityTemplateUpdateContentDTO.setDescription(updateUserDaDTO.getDescription());
        if (updateUserDaDTO.getApproveStatus() != null) {
            creditFacilityTemplateUpdateContentDTO.setApproveStatus(updateUserDaDTO.getApproveStatus().toString());
        }
        if (updateUserDaDTO.getStatus() != null) {
            creditFacilityTemplateUpdateContentDTO.setStatus(updateUserDaDTO.getStatus().toString());
        }
        creditFacilityTemplateUpdateContentDTO.setApprovedDateStr(updateUserDaDTO.getApprovedDateStr());


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_TEMPLATE, updateUserDaDTO.getCreditFacilityTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TEMPLATE_ADD, date, null, creditFacilityTemplateUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_TEMPLATE, updateUserDaDTO.getCreditFacilityTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TEMPLATE_EDIT, date, creditFacilityTemplatePreviousContentDTO, creditFacilityTemplateUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructUpmGroupAudit(UpmGroupDTO updateUpmGroupDTO,
                                                     UpmGroupDTO previousUpmGroupDTO,
                                                     CredentialsDTO credentialsDTO,
                                                     Date date, boolean isCreation) {

        WebAuditDTO webAuditDTO;

        UpmGroupContentDTO upmGroupPreviousContentDTO = new UpmGroupContentDTO();
        UpmGroupContentDTO upmGroupUpdateContentDTO = new UpmGroupContentDTO();

        if (previousUpmGroupDTO != null) {
            upmGroupPreviousContentDTO.setUpmGroupID(previousUpmGroupDTO.getUpmGroupID());
            upmGroupPreviousContentDTO.setGroupCode(previousUpmGroupDTO.getGroupCode());
            upmGroupPreviousContentDTO.setReferenceName(previousUpmGroupDTO.getReferenceName());
            upmGroupPreviousContentDTO.setWorkFlowLevel(previousUpmGroupDTO.getWorkFlowLevel());
            if (previousUpmGroupDTO.getApproveStatus() != null) {
                upmGroupPreviousContentDTO.setApproveStatus(previousUpmGroupDTO.getApproveStatus().toString());
            }
            if (previousUpmGroupDTO.getStatus() != null) {
                upmGroupPreviousContentDTO.setStatus(previousUpmGroupDTO.getStatus().toString());
            }
            upmGroupPreviousContentDTO.setApprovedDateStr(previousUpmGroupDTO.getApprovedDateStr());
        }

        upmGroupUpdateContentDTO.setUpmGroupID(updateUpmGroupDTO.getUpmGroupID());
        upmGroupUpdateContentDTO.setGroupCode(updateUpmGroupDTO.getGroupCode());
        upmGroupUpdateContentDTO.setReferenceName(updateUpmGroupDTO.getReferenceName());
        upmGroupUpdateContentDTO.setWorkFlowLevel(updateUpmGroupDTO.getWorkFlowLevel());
        if (updateUpmGroupDTO.getApproveStatus() != null) {
            upmGroupUpdateContentDTO.setApproveStatus(updateUpmGroupDTO.getApproveStatus().toString());
        }
        if (updateUpmGroupDTO.getStatus() != null) {
            upmGroupUpdateContentDTO.setStatus(updateUpmGroupDTO.getStatus().toString());
        }
        upmGroupUpdateContentDTO.setApprovedDateStr(updateUpmGroupDTO.getApprovedDateStr());


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPM_GROUP, updateUpmGroupDTO.getUpmGroupID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPM_GROUP_ADD, date, null, upmGroupUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPM_GROUP, updateUpmGroupDTO.getUpmGroupID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPM_GROUP_EDIT, date, upmGroupPreviousContentDTO, upmGroupUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructWorkFlowTemplateAudit(WorkFlowTemplateDTO updateDTO,
                                                             WorkFlowTemplateDTO previousDTO,
                                                             CredentialsDTO credentialsDTO,
                                                             Date date, boolean isCreation) {

        WebAuditDTO webAuditDTO;

        WorkFlowTemplateContentDTO previousContentDTO = new WorkFlowTemplateContentDTO();
        WorkFlowTemplateContentDTO updateContentDTO = new WorkFlowTemplateContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setWorkFlowTemplateID(previousDTO.getWorkFlowTemplateID());
            previousContentDTO.setWorkFlowTemplateName(previousDTO.getWorkFlowTemplateName());
            previousContentDTO.setCode(previousDTO.getCode());
            previousContentDTO.setDescription(previousDTO.getDescription());
            if (previousDTO.getApproveStatus() != null) {
                previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
            previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());
        }

        updateContentDTO.setWorkFlowTemplateID(updateDTO.getWorkFlowTemplateID());
        updateContentDTO.setWorkFlowTemplateName(updateDTO.getWorkFlowTemplateName());
        updateContentDTO.setCode(updateDTO.getCode());
        updateContentDTO.setDescription(updateDTO.getDescription());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.WORK_FLOW_TEMPLATE, updateContentDTO.getWorkFlowTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.WORK_FLOW_TEMPLATE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.WORK_FLOW_TEMPLATE, updateContentDTO.getWorkFlowTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.WORK_FLOW_TEMPLATE_EDIT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructUpcSectionDataAudit(UpcSectionDTO updateDTO,
                                                           UpcSectionDTO previousDTO,
                                                           CredentialsDTO credentialsDTO,
                                                           Date date, boolean isNew) {

        WebAuditDTO webAuditDTO;

        UpcSectionContentDTO previousContentDTO = new UpcSectionContentDTO();
        UpcSectionContentDTO updateContentDTO = new UpcSectionContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setUpcSectionID(previousDTO.getUpcSectionID());
            previousContentDTO.setUpcSectionName(previousDTO.getUpcSectionName());
            previousContentDTO.setUpcSectionDescription(previousDTO.getUpcSectionDescription());
            if (previousDTO.getApproveStatus() != null) {
                previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
            previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());
        }

        updateContentDTO.setUpcSectionID(updateDTO.getUpcSectionID());
        updateContentDTO.setUpcSectionName(updateDTO.getUpcSectionName());
        updateContentDTO.setUpcSectionDescription(updateDTO.getUpcSectionDescription());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());


        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_SECTION, updateDTO.getUpcSectionID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_SECTION_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_SECTION, updateDTO.getUpcSectionID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_SECTION_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructUpcTemplateAudit(UpcTemplateDTO updateDTO,
                                                        UpcTemplateDTO previousDTO,
                                                        CredentialsDTO credentialsDTO,
                                                        Date date, boolean isNew) {

        WebAuditDTO webAuditDTO;

        UpcTemplateContentDTO previousContentDTO = new UpcTemplateContentDTO();
        UpcTemplateContentDTO updateContentDTO = new UpcTemplateContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setUpcTemplateID(previousDTO.getUpcTemplateID());
            previousContentDTO.setTemplateName(previousDTO.getTemplateName());
            previousContentDTO.setDescription(previousDTO.getDescription());
            if (previousDTO.getApproveStatus() != null) {
                previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
            previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());
        }

        updateContentDTO.setUpcTemplateID(updateDTO.getUpcTemplateID());
        updateContentDTO.setTemplateName(updateDTO.getTemplateName());
        updateContentDTO.setDescription(updateDTO.getDescription());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());


        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_TEMPLATE, updateDTO.getUpcTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_TEMPLATE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_TEMPLATE, updateDTO.getUpcTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_TEMPLATE_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructCftInterestRateAudit(CftInterestRateDTO updateDTO,
                                                            CftInterestRateDTO previousDTO,
                                                            CredentialsDTO credentialsDTO,
                                                            Date date, boolean isNew,
                                                            WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CftInterestRateContentDTO previousContentDTO = new CftInterestRateContentDTO();
        CftInterestRateContentDTO updateContentDTO = new CftInterestRateContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCftInterestRateID(previousDTO.getCftInterestRateID());
            previousContentDTO.setRateName(previousDTO.getRateName());
            previousContentDTO.setRateCode(previousDTO.getRateCode());
            if (previousDTO.getValue() != null) {
                previousContentDTO.setValue(previousDTO.getValue().toString());
            }
            if (previousDTO.getIsDefault() != null) {
                previousContentDTO.setIsDefault(previousDTO.getIsDefault().getStrVal());
            }
            if (previousDTO.getCreditFacilityTemplateID() != null) {
                previousContentDTO.setCreditFacilityTemplateID(previousDTO.getCreditFacilityTemplateID());
                previousContentDTO.setCreditFacilityName(webAuditJDBCDao.getCreditFacilityNameByID(previousDTO.getCreditFacilityTemplateID()));
            }
            if (previousDTO.getApproveStatus() != null) {
                previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
            previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());
        }

        updateContentDTO.setCftInterestRateID(updateDTO.getCftInterestRateID());
        updateContentDTO.setRateName(updateDTO.getRateName());
        updateContentDTO.setRateCode(updateDTO.getRateCode());
        if (updateDTO.getValue() != null) {
            updateContentDTO.setValue(updateDTO.getValue().toString());
        }
        if (updateDTO.getIsDefault() != null) {
            updateContentDTO.setIsDefault(updateDTO.getIsDefault().getStrVal());
        }
        if (updateDTO.getCreditFacilityTemplateID() != null) {
            updateContentDTO.setCreditFacilityTemplateID(updateDTO.getCreditFacilityTemplateID());
            updateContentDTO.setCreditFacilityName(webAuditJDBCDao.getCreditFacilityNameByID(updateDTO.getCreditFacilityTemplateID()));
        }
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());


        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CFT_INTEREST_RATE, updateDTO.getCftInterestRateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CFT_INTEREST_RATE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CFT_INTEREST_RATE, updateDTO.getCftInterestRateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CFT_INTEREST_RATE_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructCftSupportingDocAudit(CftSupportingDocDTO updateDTO,
                                                             CftSupportingDocDTO previousDTO,
                                                             CredentialsDTO credentialsDTO,
                                                             Date date, boolean isNew,
                                                             WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CftSupportingDocContentDTO previousContentDTO = new CftSupportingDocContentDTO();
        CftSupportingDocContentDTO updateContentDTO = new CftSupportingDocContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCftSupportingDocID(previousDTO.getCftSupportingDocID());
            previousContentDTO.setCreditFacilityTemplateID(previousDTO.getCreditFacilityTemplateID());
            if (previousDTO.getMandatory() != null) {
                previousContentDTO.setMandatory(previousDTO.getMandatory().getStrVal());
            }
            if (previousDTO.getCreditFacilityTemplateID() != null) {
                previousContentDTO.setCreditFacilityTemplateID(previousDTO.getCreditFacilityTemplateID());
                previousContentDTO.setCreditFacilityName(webAuditJDBCDao.getCreditFacilityNameByID(previousDTO.getCreditFacilityTemplateID()));
            }
            if (previousDTO.getSupportingDocID() != null) {
                previousContentDTO.setSupportingDocID(previousDTO.getSupportingDocID());
                previousContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(previousDTO.getSupportingDocID()));
            }
            if (previousDTO.getApproveStatus() != null) {
                previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setCftSupportingDocID(updateDTO.getCftSupportingDocID());
        updateContentDTO.setCreditFacilityTemplateID(updateDTO.getCreditFacilityTemplateID());
        if (updateDTO.getMandatory() != null) {
            updateContentDTO.setMandatory(updateDTO.getMandatory().getStrVal());
        }
        if (updateDTO.getCreditFacilityTemplateID() != null) {
            updateContentDTO.setCreditFacilityTemplateID(updateDTO.getCreditFacilityTemplateID());
            updateContentDTO.setCreditFacilityName(webAuditJDBCDao.getCreditFacilityNameByID(updateDTO.getCreditFacilityTemplateID()));
        }
        if (updateDTO.getSupportingDocID() != null) {
            updateContentDTO.setSupportingDocID(updateDTO.getSupportingDocID());
            updateContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(updateDTO.getSupportingDocID()));
        }
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }

        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CFT_SUPPORTING_DOC, updateDTO.getCftSupportingDocID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CFT_SUPPORTING_DOC_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CFT_SUPPORTING_DOC, updateDTO.getCftSupportingDocID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CFT_SUPPORTING_DOC_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructFacilityAudit(FacilityDTO updateDTO,
                                                     FacilityDTO previousDTO,
                                                     CredentialsDTO credentialsDTO,
                                                     Date date, boolean isNew,
                                                     WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FacilityContentDTO previousContentDTO = new FacilityContentDTO();
        FacilityContentDTO updateContentDTO = new FacilityContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setFacilityID(previousDTO.getFacilityID());
            previousContentDTO.setFacilityRefCode(previousDTO.getFacilityRefCode());
            previousContentDTO.setCreditFacilityTemplateID(previousDTO.getCreditFacilityTemplateID());
            if (previousDTO.getCreditFacilityTemplateID() != null) {
                previousContentDTO.setCreditFacilityTemplateID(previousDTO.getCreditFacilityTemplateID());
                previousContentDTO.setCreditFacilityName(webAuditJDBCDao.getCreditFacilityNameByID(previousDTO.getCreditFacilityTemplateID()));
            }
            if (previousDTO.getFacilityPaperID() != null) {
                previousContentDTO.setFacilityPaperID(previousDTO.getFacilityPaperID());
                previousContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousDTO.getFacilityPaperID()));
            }
            previousContentDTO.setCreditFacilityTypeID(previousDTO.getCreditFacilityTypeID());
            previousContentDTO.setFacilityType(previousDTO.getFacilityType());
            previousContentDTO.setFacilityCurrency(previousDTO.getFacilityCurrency());
            previousContentDTO.setDisbursementAccNumber(previousDTO.getDisbursementAccNumber());
            previousContentDTO.setParentFacilityID(previousDTO.getParentFacilityID());
            if (previousDTO.getFacilityAmount() != null) {
                previousContentDTO.setFacilityAmount(previousDTO.getFacilityAmount().toString());
            }

            if (previousDTO.getExistingAmount() != null) {
                previousContentDTO.setExistingAmount(previousDTO.getExistingAmount().toString());
            }

            if (previousDTO.getOriginalAmount() != null) {
                previousContentDTO.setOriginalAmount(previousDTO.getOriginalAmount().toString());
            }
            if (previousDTO.getIsCooperate() != null) {
                previousContentDTO.setIsCooperate(previousDTO.getIsCooperate().getStrVal());
            }
            if (previousDTO.getOutstandingAmount() != null) {
                previousContentDTO.setOutstandingAmount(previousDTO.getOutstandingAmount().toString());
            }
            previousContentDTO.setSectorID(previousDTO.getSectorID());
            previousContentDTO.setSubSectorID(previousDTO.getSubSectorID());
            previousContentDTO.setPurposeOfAdvance(previousDTO.getPurposeOfAdvance());
            previousContentDTO.setPurpose(previousDTO.getPurpose());
            if (previousDTO.getIsOneOff() != null) {
                previousContentDTO.setIsOneOff(previousDTO.getIsOneOff().getStrVal());
            }
            if (previousDTO.getDirectFacility() != null) {
                previousContentDTO.setDirectFacility(previousDTO.getDirectFacility().getStrVal());
            }
            previousContentDTO.setRepayment(previousDTO.getRepayment());
            previousContentDTO.setCondition(previousDTO.getCondition());
            if (previousDTO.getIsNew() != null) {
                previousContentDTO.setIsNew(previousDTO.getIsNew().getStrVal());
            }
            if (previousDTO.getEnhancement() != null) {
                previousContentDTO.setIsEnhancement(previousDTO.getEnhancement().getStrVal());
            }
            if (previousDTO.getReduction() != null) {
                previousContentDTO.setIsReduction(previousDTO.getReduction().getStrVal());
            }
            previousContentDTO.setDisplayOrder(previousDTO.getDisplayOrder());
            previousContentDTO.setRemark(previousDTO.getRemark());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setFacilityID(updateDTO.getFacilityID());
        updateContentDTO.setFacilityRefCode(updateDTO.getFacilityRefCode());
        updateContentDTO.setCreditFacilityTemplateID(updateDTO.getCreditFacilityTemplateID());
        if (updateDTO.getCreditFacilityTemplateID() != null) {
            updateContentDTO.setCreditFacilityTemplateID(updateDTO.getCreditFacilityTemplateID());
            updateContentDTO.setCreditFacilityName(webAuditJDBCDao.getCreditFacilityNameByID(updateDTO.getCreditFacilityTemplateID()));
        }
        if (updateDTO.getFacilityPaperID() != null) {
            updateContentDTO.setFacilityPaperID(updateDTO.getFacilityPaperID());
            updateContentDTO.setFacilityPaperRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(updateDTO.getFacilityPaperID()));
        }
        updateContentDTO.setCreditFacilityTypeID(updateDTO.getCreditFacilityTypeID());
        updateContentDTO.setFacilityType(updateDTO.getFacilityType());
        updateContentDTO.setFacilityCurrency(updateDTO.getFacilityCurrency());
        updateContentDTO.setDisbursementAccNumber(updateDTO.getDisbursementAccNumber());
        updateContentDTO.setParentFacilityID(updateDTO.getParentFacilityID());
        if (updateDTO.getFacilityAmount() != null) {
            updateContentDTO.setFacilityAmount(updateDTO.getFacilityAmount().toString());
        }

        if (updateDTO.getOriginalAmount() != null) {
            updateContentDTO.setOriginalAmount(updateDTO.getOriginalAmount().toString());
        }

        if (updateDTO.getExistingAmount() != null) {
            updateContentDTO.setExistingAmount(updateDTO.getExistingAmount().toString());
        }
        if (updateDTO.getIsCooperate() != null) {
            updateContentDTO.setIsCooperate(updateDTO.getIsCooperate().getStrVal());
        }
        if (updateDTO.getOutstandingAmount() != null) {
            updateContentDTO.setOutstandingAmount(updateDTO.getOutstandingAmount().toString());
        }
        updateContentDTO.setSectorID(updateDTO.getSectorID());
        updateContentDTO.setSubSectorID(updateDTO.getSubSectorID());
        updateContentDTO.setPurposeOfAdvance(updateDTO.getPurposeOfAdvance());
        updateContentDTO.setPurpose(updateDTO.getPurpose());
        if (updateDTO.getIsOneOff() != null) {
            updateContentDTO.setIsOneOff(updateDTO.getIsOneOff().getStrVal());
        }
        if (updateDTO.getDirectFacility() != null) {
            updateContentDTO.setDirectFacility(updateDTO.getDirectFacility().getStrVal());
        }

        if (updateDTO.getReduction() != null) {
            updateContentDTO.setIsReduction(updateDTO.getReduction().getStrVal());
        }

        if (updateDTO.getEnhancement() != null) {
            updateContentDTO.setIsEnhancement(updateDTO.getEnhancement().getStrVal());
        }

        updateContentDTO.setRepayment(updateDTO.getRepayment());
        updateContentDTO.setCondition(updateDTO.getCondition());
        if (updateDTO.getIsNew() != null) {
            updateContentDTO.setIsNew(updateDTO.getIsNew().getStrVal());
        }
        updateContentDTO.setDisplayOrder(updateDTO.getDisplayOrder());
        updateContentDTO.setRemark(updateDTO.getRemark());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }


        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, updateDTO.getFacilityPaperID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructReplicateFacilityPaperAudit(FacilityPaperDTO newDTO,
                                                                   Integer previousFpID,
                                                                   CredentialsDTO credentialsDTO,
                                                                   Date date,
                                                                   WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FacilityPaperContentDTO previousContentDTO = new FacilityPaperContentDTO();
        FacilityPaperContentDTO newContentDTO = new FacilityPaperContentDTO();

        previousContentDTO.setFacilityPaperID(previousFpID);
        previousContentDTO.setFpRefNumber(webAuditJDBCDao.getFacilityPaperRefNumberByFpID(previousFpID));

        newContentDTO.setFacilityPaperID(newDTO.getFacilityPaperID());
        newContentDTO.setFpRefNumber(newDTO.getFpRefNumber());

        webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY_PAPER, newDTO.getFacilityPaperID(),
                DomainConstants.WebAuditMainCategory.FACILITY_PAPER, DomainConstants.WebAuditSubCategory.REPLICATE_FACILITY_PAPER,
                date, previousContentDTO, newContentDTO);

        return webAuditDTO;
    }


    public static WebAuditDTO constructRoleAudit(RoleDTO updateDTO,
                                                 RoleDTO previousDTO,
                                                 CredentialsDTO credentialsDTO,
                                                 Date date, boolean isCreation) {

        WebAuditDTO webAuditDTO;

        RoleContentDTO previousContentDTO = new RoleContentDTO();
        RoleContentDTO updateContentDTO = new RoleContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setRoleID(previousDTO.getRoleID());
            previousContentDTO.setRoleName(previousDTO.getRoleName());
            previousContentDTO.setUpmPrivilegeCode(previousDTO.getUpmPrivilegeCode());
            if (previousDTO.getApproveStatus() != null) {
                previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
            previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());
        }

        updateContentDTO.setRoleID(updateDTO.getRoleID());
        updateContentDTO.setRoleName(updateDTO.getRoleName());
        updateContentDTO.setUpmPrivilegeCode(updateDTO.getUpmPrivilegeCode());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.ROLE, updateDTO.getRoleID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.ROLE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.ROLE, updateDTO.getRoleID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.ROLE_EDIT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectCreditFacilityTypeAudit(CreditFacilityTypeDTO updateCreditFacilityTypeDTO,
                                                                            CreditFacilityTypeDTO previousCreditFacilityTypeDTO,
                                                                            CredentialsDTO credentialsDTO,
                                                                            Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        CreditFacilityTypeContentDTO creditFacilityTypePreviousContentDTO = new CreditFacilityTypeContentDTO();
        CreditFacilityTypeContentDTO creditFacilityTypeUpdateContentDTO = new CreditFacilityTypeContentDTO();


        creditFacilityTypePreviousContentDTO.setCreditFacilityTypeID(previousCreditFacilityTypeDTO.getCreditFacilityTypeID());
        creditFacilityTypePreviousContentDTO.setFacilityTypeName(previousCreditFacilityTypeDTO.getFacilityTypeName());
        creditFacilityTypePreviousContentDTO.setDescription(previousCreditFacilityTypeDTO.getDescription());
        if (previousCreditFacilityTypeDTO.getApproveStatus() != null) {
            creditFacilityTypePreviousContentDTO.setApproveStatus(previousCreditFacilityTypeDTO.getApproveStatus().toString());
        }
        if (previousCreditFacilityTypeDTO.getStatus() != null) {
            creditFacilityTypePreviousContentDTO.setStatus(previousCreditFacilityTypeDTO.getStatus().toString());
        }
        creditFacilityTypePreviousContentDTO.setApprovedDateStr(previousCreditFacilityTypeDTO.getApprovedDateStr());


        creditFacilityTypeUpdateContentDTO.setCreditFacilityTypeID(updateCreditFacilityTypeDTO.getCreditFacilityTypeID());
        creditFacilityTypeUpdateContentDTO.setFacilityTypeName(updateCreditFacilityTypeDTO.getFacilityTypeName());
        creditFacilityTypeUpdateContentDTO.setDescription(updateCreditFacilityTypeDTO.getDescription());
        if (updateCreditFacilityTypeDTO.getApproveStatus() != null) {
            creditFacilityTypeUpdateContentDTO.setApproveStatus(updateCreditFacilityTypeDTO.getApproveStatus().toString());
        }
        if (updateCreditFacilityTypeDTO.getStatus() != null) {
            creditFacilityTypeUpdateContentDTO.setStatus(updateCreditFacilityTypeDTO.getStatus().toString());
        }
        creditFacilityTypeUpdateContentDTO.setApprovedDateStr(updateCreditFacilityTypeDTO.getApprovedDateStr());

        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_GROUP, updateCreditFacilityTypeDTO.getCreditFacilityTypeID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TYPE_APPROVE, date, creditFacilityTypePreviousContentDTO, creditFacilityTypeUpdateContentDTO);
        } else {

            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_GROUP, updateCreditFacilityTypeDTO.getCreditFacilityTypeID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TYPE_REJECT, date, creditFacilityTypePreviousContentDTO, creditFacilityTypeUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectSupportingDocAudit(SupportingDocDTO updateSupportingDocDTO,
                                                                       SupportingDocDTO previousSupportingDocDTO,
                                                                       CredentialsDTO credentialsDTO,
                                                                       Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        SupportingDocContentDTO supportingDocPreviousContentDTO = new SupportingDocContentDTO();
        SupportingDocContentDTO supportingDocUpdateContentDTO = new SupportingDocContentDTO();

        supportingDocPreviousContentDTO.setSupportingDocID(previousSupportingDocDTO.getSupportingDocID());
        supportingDocPreviousContentDTO.setDocumentName(previousSupportingDocDTO.getDocumentName());
        supportingDocPreviousContentDTO.setDescription(previousSupportingDocDTO.getDescription());
        supportingDocPreviousContentDTO.setSupportDocumentType(previousSupportingDocDTO.getSupportDocumentType());
        if (previousSupportingDocDTO.getApproveStatus() != null) {
            supportingDocPreviousContentDTO.setApproveStatus(previousSupportingDocDTO.getApproveStatus().toString());
        }
        if (previousSupportingDocDTO.getStatus() != null) {
            supportingDocPreviousContentDTO.setStatus(previousSupportingDocDTO.getStatus().toString());
        }
        supportingDocPreviousContentDTO.setApprovedDateStr(previousSupportingDocDTO.getApprovedDateStr());


        supportingDocUpdateContentDTO.setSupportingDocID(updateSupportingDocDTO.getSupportingDocID());
        supportingDocUpdateContentDTO.setDocumentName(updateSupportingDocDTO.getDocumentName());
        supportingDocUpdateContentDTO.setDescription(updateSupportingDocDTO.getDescription());
        supportingDocUpdateContentDTO.setSupportDocumentType(updateSupportingDocDTO.getSupportDocumentType());
        if (updateSupportingDocDTO.getApproveStatus() != null) {
            supportingDocUpdateContentDTO.setApproveStatus(updateSupportingDocDTO.getApproveStatus().toString());
        }
        if (updateSupportingDocDTO.getStatus() != null) {
            supportingDocUpdateContentDTO.setStatus(updateSupportingDocDTO.getStatus().toString());
        }
        supportingDocUpdateContentDTO.setApprovedDateStr(updateSupportingDocDTO.getApprovedDateStr());

        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.SUPPORTING_DOC, updateSupportingDocDTO.getSupportingDocID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.SUPPORTING_DOC_APPROVE, date, supportingDocPreviousContentDTO, supportingDocUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.SUPPORTING_DOC, updateSupportingDocDTO.getSupportingDocID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.SUPPORTING_DOC_REJECT, date, supportingDocPreviousContentDTO, supportingDocUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectRoleAudit(RoleDTO updateDTO,
                                                              RoleDTO previousDTO,
                                                              CredentialsDTO credentialsDTO,
                                                              Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        RoleContentDTO previousContentDTO = new RoleContentDTO();
        RoleContentDTO updateContentDTO = new RoleContentDTO();

        previousContentDTO.setRoleID(previousDTO.getRoleID());
        previousContentDTO.setRoleName(previousDTO.getRoleName());
        previousContentDTO.setUpmPrivilegeCode(previousDTO.getUpmPrivilegeCode());
        if (previousDTO.getApproveStatus() != null) {
            previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
        }
        if (previousDTO.getStatus() != null) {
            previousContentDTO.setStatus(previousDTO.getStatus().toString());
        }
        previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());


        updateContentDTO.setRoleID(updateDTO.getRoleID());
        updateContentDTO.setRoleName(updateDTO.getRoleName());
        updateContentDTO.setUpmPrivilegeCode(updateDTO.getUpmPrivilegeCode());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());


        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.ROLE, updateDTO.getRoleID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.ROLE_APPROVE, date, previousContentDTO, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.ROLE, updateDTO.getRoleID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.ROLE_REJECT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectUpcTemplateAudit(UpcTemplateDTO updateDTO,
                                                                     UpcTemplateDTO previousDTO,
                                                                     CredentialsDTO credentialsDTO,
                                                                     Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        UpcTemplateContentDTO previousContentDTO = new UpcTemplateContentDTO();
        UpcTemplateContentDTO updateContentDTO = new UpcTemplateContentDTO();


        previousContentDTO.setUpcTemplateID(previousDTO.getUpcTemplateID());
        previousContentDTO.setTemplateName(previousDTO.getTemplateName());
        previousContentDTO.setDescription(previousDTO.getDescription());
        if (previousDTO.getApproveStatus() != null) {
            previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
        }
        if (previousDTO.getStatus() != null) {
            previousContentDTO.setStatus(previousDTO.getStatus().toString());
        }
        previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());


        updateContentDTO.setUpcTemplateID(updateDTO.getUpcTemplateID());
        updateContentDTO.setTemplateName(updateDTO.getTemplateName());
        updateContentDTO.setDescription(updateDTO.getDescription());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());

        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_TEMPLATE, updateDTO.getUpcTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_TEMPLATE_APPROVE, date, previousContentDTO, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_TEMPLATE, updateDTO.getUpcTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_TEMPLATE_REJECT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectUpcSectionAudit(UpcSectionDTO updateDTO,
                                                                    UpcSectionDTO previousDTO,
                                                                    CredentialsDTO credentialsDTO,
                                                                    Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        UpcSectionContentDTO previousContentDTO = new UpcSectionContentDTO();
        UpcSectionContentDTO updateContentDTO = new UpcSectionContentDTO();

        previousContentDTO.setUpcSectionID(previousDTO.getUpcSectionID());
        previousContentDTO.setUpcSectionName(previousDTO.getUpcSectionName());
        previousContentDTO.setUpcSectionDescription(previousDTO.getUpcSectionDescription());
        if (previousDTO.getApproveStatus() != null) {
            previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
        }
        if (previousDTO.getStatus() != null) {
            previousContentDTO.setStatus(previousDTO.getStatus().toString());
        }
        previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());


        updateContentDTO.setUpcSectionID(updateDTO.getUpcSectionID());
        updateContentDTO.setUpcSectionName(updateDTO.getUpcSectionName());
        updateContentDTO.setUpcSectionDescription(updateDTO.getUpcSectionDescription());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());

        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_SECTION, updateDTO.getUpcSectionID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_SECTION_APPROVE, date, previousContentDTO, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPC_SECTION, updateDTO.getUpcSectionID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPC_SECTION_REJECT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectWorkFlowTemplateAudit(WorkFlowTemplateDTO updateDTO,
                                                                          WorkFlowTemplateDTO previousDTO,
                                                                          CredentialsDTO credentialsDTO,
                                                                          Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        WorkFlowTemplateContentDTO previousContentDTO = new WorkFlowTemplateContentDTO();
        WorkFlowTemplateContentDTO updateContentDTO = new WorkFlowTemplateContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setWorkFlowTemplateID(previousDTO.getWorkFlowTemplateID());
            previousContentDTO.setWorkFlowTemplateName(previousDTO.getWorkFlowTemplateName());
            previousContentDTO.setCode(previousDTO.getCode());
            previousContentDTO.setDescription(previousDTO.getDescription());
            if (previousDTO.getApproveStatus() != null) {
                previousContentDTO.setApproveStatus(previousDTO.getApproveStatus().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
            previousContentDTO.setApprovedDateStr(previousDTO.getApprovedDateStr());
        }

        updateContentDTO.setWorkFlowTemplateID(updateDTO.getWorkFlowTemplateID());
        updateContentDTO.setWorkFlowTemplateName(updateDTO.getWorkFlowTemplateName());
        updateContentDTO.setCode(updateDTO.getCode());
        updateContentDTO.setDescription(updateDTO.getDescription());
        if (updateDTO.getApproveStatus() != null) {
            updateContentDTO.setApproveStatus(updateDTO.getApproveStatus().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }
        updateContentDTO.setApprovedDateStr(updateDTO.getApprovedDateStr());


        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.WORK_FLOW_TEMPLATE, updateContentDTO.getWorkFlowTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.WORK_FLOW_TEMPLATE_APPROVE, date, previousContentDTO, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.WORK_FLOW_TEMPLATE, updateContentDTO.getWorkFlowTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.WORK_FLOW_TEMPLATE_REJECT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectUserDaAudit(UserDaDTO updateUserDaDTO,
                                                                UserDaDTO previousUserDaDTO,
                                                                CredentialsDTO credentialsDTO,
                                                                Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        UserDaContentDTO userDaPreviousContentDTO = new UserDaContentDTO();
        UserDaContentDTO userDaUpdateContentDTO = new UserDaContentDTO();

        userDaPreviousContentDTO.setUserDaID(previousUserDaDTO.getUserDaID());
        userDaPreviousContentDTO.setUserName(previousUserDaDTO.getUserName());
        if (previousUserDaDTO.getMaxAmount() != null) {
            userDaPreviousContentDTO.setMaxAmount(previousUserDaDTO.getMaxAmount().toString());
        }
        userDaPreviousContentDTO.setDescription(previousUserDaDTO.getDescription());
        if (previousUserDaDTO.getApproveStatus() != null) {
            userDaPreviousContentDTO.setApproveStatus(previousUserDaDTO.getApproveStatus().toString());
        }
        if (previousUserDaDTO.getStatus() != null) {
            userDaPreviousContentDTO.setStatus(previousUserDaDTO.getStatus().toString());
        }
        userDaPreviousContentDTO.setApprovedDateStr(previousUserDaDTO.getApprovedDateStr());


        userDaUpdateContentDTO.setUserDaID(updateUserDaDTO.getUserDaID());
        userDaUpdateContentDTO.setUserName(updateUserDaDTO.getUserName());
        if (updateUserDaDTO.getMaxAmount() != null) {
            userDaUpdateContentDTO.setMaxAmount(updateUserDaDTO.getMaxAmount().toString());
        }
        userDaUpdateContentDTO.setDescription(updateUserDaDTO.getDescription());
        if (updateUserDaDTO.getApproveStatus() != null) {
            userDaUpdateContentDTO.setApproveStatus(updateUserDaDTO.getApproveStatus().toString());
        }
        if (updateUserDaDTO.getStatus() != null) {
            userDaUpdateContentDTO.setStatus(updateUserDaDTO.getStatus().toString());
        }
        userDaUpdateContentDTO.setApprovedDateStr(updateUserDaDTO.getApprovedDateStr());

        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.USER_DA, updateUserDaDTO.getUserDaID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.USER_DA_APPROVE, date, userDaPreviousContentDTO, userDaUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.USER_DA, updateUserDaDTO.getUserDaID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.USER_DA_REJECT, date, userDaPreviousContentDTO, userDaUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectUpmGroupAudit(UpmGroupDTO updateUpmGroupDTO,
                                                                  UpmGroupDTO previousUpmGroupDTO,
                                                                  CredentialsDTO credentialsDTO,
                                                                  Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        UpmGroupContentDTO upmGroupPreviousContentDTO = new UpmGroupContentDTO();
        UpmGroupContentDTO upmGroupUpdateContentDTO = new UpmGroupContentDTO();

        upmGroupPreviousContentDTO.setUpmGroupID(previousUpmGroupDTO.getUpmGroupID());
        upmGroupPreviousContentDTO.setGroupCode(previousUpmGroupDTO.getGroupCode());
        upmGroupPreviousContentDTO.setReferenceName(previousUpmGroupDTO.getReferenceName());
        upmGroupPreviousContentDTO.setWorkFlowLevel(previousUpmGroupDTO.getWorkFlowLevel());
        if (previousUpmGroupDTO.getApproveStatus() != null) {
            upmGroupPreviousContentDTO.setApproveStatus(previousUpmGroupDTO.getApproveStatus().toString());
        }
        if (previousUpmGroupDTO.getStatus() != null) {
            upmGroupPreviousContentDTO.setStatus(previousUpmGroupDTO.getStatus().toString());
        }
        upmGroupPreviousContentDTO.setApprovedDateStr(previousUpmGroupDTO.getApprovedDateStr());


        upmGroupUpdateContentDTO.setUpmGroupID(updateUpmGroupDTO.getUpmGroupID());
        upmGroupUpdateContentDTO.setGroupCode(updateUpmGroupDTO.getGroupCode());
        upmGroupUpdateContentDTO.setReferenceName(updateUpmGroupDTO.getReferenceName());
        upmGroupUpdateContentDTO.setWorkFlowLevel(updateUpmGroupDTO.getWorkFlowLevel());
        if (updateUpmGroupDTO.getApproveStatus() != null) {
            upmGroupUpdateContentDTO.setApproveStatus(updateUpmGroupDTO.getApproveStatus().toString());
        }
        if (updateUpmGroupDTO.getStatus() != null) {
            upmGroupUpdateContentDTO.setStatus(updateUpmGroupDTO.getStatus().toString());
        }
        upmGroupUpdateContentDTO.setApprovedDateStr(updateUpmGroupDTO.getApprovedDateStr());


        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPM_GROUP, updateUpmGroupDTO.getUpmGroupID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPM_GROUP_APPROVE, date, upmGroupPreviousContentDTO, upmGroupUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.UPM_GROUP, updateUpmGroupDTO.getUpmGroupID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.UPM_GROUP_REJECT, date, upmGroupPreviousContentDTO, upmGroupUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructApproveRejectCreditFacilityTemplateAudit(CreditFacilityTemplateDTO updateUserDaDTO,
                                                                                CreditFacilityTemplateDTO previousUserDaDTO,
                                                                                CredentialsDTO credentialsDTO,
                                                                                Date date, DomainConstants.MasterDataApproveStatus approveStatus) {

        WebAuditDTO webAuditDTO;

        CreditFacilityTemplateContentDTO creditFacilityTemplatePreviousContentDTO = new CreditFacilityTemplateContentDTO();
        CreditFacilityTemplateContentDTO creditFacilityTemplateUpdateContentDTO = new CreditFacilityTemplateContentDTO();

        creditFacilityTemplatePreviousContentDTO.setCreditFacilityTemplateID(previousUserDaDTO.getCreditFacilityTemplateID());
        creditFacilityTemplatePreviousContentDTO.setCreditFacilityName(previousUserDaDTO.getCreditFacilityName());
        creditFacilityTemplatePreviousContentDTO.setCreditFacilityTypeID(previousUserDaDTO.getCreditFacilityTypeID());
        creditFacilityTemplatePreviousContentDTO.setFacilityTypeName(previousUserDaDTO.getFacilityTypeName());
        if (previousUserDaDTO.getMaxFacilityAmount() != null) {
            creditFacilityTemplatePreviousContentDTO.setMaxFacilityAmount(previousUserDaDTO.getMaxFacilityAmount().toString());
        }
        if (previousUserDaDTO.getMinFacilityAmount() != null) {
            creditFacilityTemplatePreviousContentDTO.setMinFacilityAmount(previousUserDaDTO.getMinFacilityAmount().toString());
        }
        creditFacilityTemplatePreviousContentDTO.setDescription(previousUserDaDTO.getDescription());
        if (previousUserDaDTO.getApproveStatus() != null) {
            creditFacilityTemplatePreviousContentDTO.setApproveStatus(previousUserDaDTO.getApproveStatus().toString());
        }
        if (previousUserDaDTO.getStatus() != null) {
            creditFacilityTemplatePreviousContentDTO.setStatus(previousUserDaDTO.getStatus().toString());
        }
        creditFacilityTemplatePreviousContentDTO.setApprovedDateStr(previousUserDaDTO.getApprovedDateStr());


        creditFacilityTemplateUpdateContentDTO.setCreditFacilityTemplateID(updateUserDaDTO.getCreditFacilityTemplateID());
        creditFacilityTemplateUpdateContentDTO.setCreditFacilityName(updateUserDaDTO.getCreditFacilityName());
        creditFacilityTemplateUpdateContentDTO.setCreditFacilityTypeID(updateUserDaDTO.getCreditFacilityTypeID());
        creditFacilityTemplateUpdateContentDTO.setFacilityTypeName(updateUserDaDTO.getFacilityTypeName());
        if (updateUserDaDTO.getMaxFacilityAmount() != null) {
            creditFacilityTemplateUpdateContentDTO.setMaxFacilityAmount(updateUserDaDTO.getMaxFacilityAmount().toString());
        }
        if (updateUserDaDTO.getMinFacilityAmount() != null) {
            creditFacilityTemplateUpdateContentDTO.setMinFacilityAmount(updateUserDaDTO.getMinFacilityAmount().toString());
        }
        creditFacilityTemplateUpdateContentDTO.setDescription(updateUserDaDTO.getDescription());
        if (updateUserDaDTO.getApproveStatus() != null) {
            creditFacilityTemplateUpdateContentDTO.setApproveStatus(updateUserDaDTO.getApproveStatus().toString());
        }
        if (updateUserDaDTO.getStatus() != null) {
            creditFacilityTemplateUpdateContentDTO.setStatus(updateUserDaDTO.getStatus().toString());
        }
        creditFacilityTemplateUpdateContentDTO.setApprovedDateStr(updateUserDaDTO.getApprovedDateStr());


        if (approveStatus == DomainConstants.MasterDataApproveStatus.APPROVED) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_TEMPLATE, updateUserDaDTO.getCreditFacilityTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TEMPLATE_APPROVE, date, creditFacilityTemplatePreviousContentDTO, creditFacilityTemplateUpdateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CREDIT_FACILITY_TEMPLATE, updateUserDaDTO.getCreditFacilityTemplateID(), DomainConstants.WebAuditMainCategory.MASTER_DATA,
                    DomainConstants.WebAuditSubCategory.CREDIT_FACILITY_TEMPLATE_REJECT, date, creditFacilityTemplatePreviousContentDTO, creditFacilityTemplateUpdateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructCustomerAudit(CustomerDTO updateDTO,
                                                     CustomerDTO previousDTO,
                                                     CredentialsDTO credentialsDTO,
                                                     Date date, boolean isNew) {

        WebAuditDTO webAuditDTO;

        CustomerContentDTO previousContentDTO = new CustomerContentDTO();
        CustomerContentDTO updateContentDTO = new CustomerContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCustomerID(previousDTO.getCustomerID());
            previousContentDTO.setCustomerFinancialID(previousDTO.getCustomerFinancialID());
            previousContentDTO.setCustomerName(previousDTO.getCustomerName());
            previousContentDTO.setEmailAddress(previousDTO.getEmailAddress());
            previousContentDTO.setSecondaryEmailAddress(previousDTO.getSecondaryEmailAddress());
            previousContentDTO.setDateOfBirth(previousDTO.getDateOfBirth());
            previousContentDTO.setCivilStatus(previousDTO.getCivilStatus());
            if (previousDTO.getTitle() != null) {
                previousContentDTO.setCustomerTitle(previousDTO.getTitle().toString());
            }
            if (previousDTO.getIdentificationType() != null) {
                previousContentDTO.setIdentificationType(previousDTO.getIdentificationType().toString());
            }
        }

        updateContentDTO.setCustomerID(updateDTO.getCustomerID());
        updateContentDTO.setCustomerFinancialID(updateDTO.getCustomerFinancialID());
        updateContentDTO.setCustomerName(updateDTO.getCustomerName());
        updateContentDTO.setEmailAddress(updateDTO.getEmailAddress());
        updateContentDTO.setSecondaryEmailAddress(updateDTO.getSecondaryEmailAddress());
        updateContentDTO.setDateOfBirth(updateDTO.getDateOfBirth());
        updateContentDTO.setCivilStatus(updateDTO.getCivilStatus());
        if (updateDTO.getTitle() != null) {
            updateContentDTO.setCustomerTitle(updateDTO.getTitle().toString());
        }
        if (updateDTO.getIdentificationType() != null) {
            updateContentDTO.setIdentificationType(updateDTO.getIdentificationType().toString());
        }

        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructCustomer360SearchAudit(SearchCustomerRQ updateDTO,
                                                              CredentialsDTO credentialsDTO,
                                                              Date date) {

        WebAuditDTO webAuditDTO;

        Customer360SearchContentDTO contentSearchRQ = new Customer360SearchContentDTO();

        contentSearchRQ.setIdentificationType(updateDTO.getIdentificationType());
        contentSearchRQ.setIdentificationNumber(updateDTO.getIdentificationNumber());
        contentSearchRQ.setBankAccountNumber(updateDTO.getBankAccountNumber());
        contentSearchRQ.setCustomerFinancialID(updateDTO.getCustomerFinancialID());


        webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, null,
                DomainConstants.WebAuditMainCategory.MASTER_DATA, DomainConstants.WebAuditSubCategory.CUSTOMER_360_SEARCH, date,
                null, contentSearchRQ);


        return webAuditDTO;
    }


    public static WebAuditDTO constructCustomerAddressAudit(CustomerAddressDTO updateDTO,
                                                            CustomerAddressDTO previousDTO,
                                                            CredentialsDTO credentialsDTO,
                                                            Date date, boolean isNew,
                                                            WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CustomerAddressContentDTO previousContentDTO = new CustomerAddressContentDTO();
        CustomerAddressContentDTO updateContentDTO = new CustomerAddressContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCustomerAddressID(previousDTO.getCustomerAddressID());
            previousContentDTO.setCustomerID(previousDTO.getCustomerId());
            if (previousDTO.getCustomerId() != null) {
                previousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(previousDTO.getCustomerId()));
            }
            previousContentDTO.setAddress1(previousDTO.getAddress1());
            previousContentDTO.setAddress2(previousDTO.getAddress2());
            previousContentDTO.setCity(previousDTO.getCity());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setCustomerAddressID(updateDTO.getCustomerAddressID());
        updateContentDTO.setCustomerID(updateDTO.getCustomerId());
        if (updateDTO.getCustomerId() != null) {
            updateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(updateDTO.getCustomerId()));
        }
        updateContentDTO.setAddress1(updateDTO.getAddress1());
        updateContentDTO.setAddress2(updateDTO.getAddress2());
        updateContentDTO.setCity(updateDTO.getCity());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }

        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerId(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_ADDRESS_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerId(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_ADDRESS_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }

    public static WebAuditDTO constructCustomerTelephoneAudit(CustomerTelephoneDTO updateDTO,
                                                              CustomerTelephoneDTO previousDTO,
                                                              CredentialsDTO credentialsDTO,
                                                              Date date, boolean isNew,
                                                              WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CustomerTelephoneContentDTO previousContentDTO = new CustomerTelephoneContentDTO();
        CustomerTelephoneContentDTO updateContentDTO = new CustomerTelephoneContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCustomerTelephoneID(previousDTO.getCustomerTelephoneID());
            previousContentDTO.setCustomerID(previousDTO.getCustomerID());
            if (previousDTO.getCustomerID() != null) {
                previousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(previousDTO.getCustomerID()));
            }
            previousContentDTO.setContactNumber(previousDTO.getContactNumber());
            previousContentDTO.setDescription(previousDTO.getDescription());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setCustomerTelephoneID(updateDTO.getCustomerTelephoneID());
        updateContentDTO.setCustomerID(updateDTO.getCustomerID());
        if (updateDTO.getCustomerID() != null) {
            updateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(updateDTO.getCustomerID()));
        }
        updateContentDTO.setContactNumber(updateDTO.getContactNumber());
        updateContentDTO.setDescription(updateDTO.getDescription());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }

        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_TELEPHONE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_TELEPHONE_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructCustomerIdentificationAudit(CustomerIdentificationDTO updateDTO,
                                                                   CustomerIdentificationDTO previousDTO,
                                                                   CredentialsDTO credentialsDTO,
                                                                   Date date, boolean isNew,
                                                                   WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CustomerIdentificationContentDTO previousContentDTO = new CustomerIdentificationContentDTO();
        CustomerIdentificationContentDTO updateContentDTO = new CustomerIdentificationContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setIdentificationID(previousDTO.getIdentificationID());
            previousContentDTO.setCustomerID(previousDTO.getCustomerID());
            if (previousDTO.getCustomerID() != null) {
                previousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(previousDTO.getCustomerID()));
            }
            if (previousDTO.getIdentificationType() != null) {
                previousContentDTO.setIdentificationType(previousDTO.getIdentificationType().toString());
            }
            previousContentDTO.setIdentificationNumber(previousDTO.getIdentificationNumber());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setIdentificationID(updateDTO.getIdentificationID());
        updateContentDTO.setCustomerID(updateDTO.getCustomerID());
        if (updateDTO.getCustomerID() != null) {
            updateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(updateDTO.getCustomerID()));
        }
        if (updateDTO.getIdentificationType() != null) {
            updateContentDTO.setIdentificationType(updateDTO.getIdentificationType().toString());
        }
        updateContentDTO.setIdentificationNumber(updateDTO.getIdentificationNumber());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }

        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_IDENTIFICATION_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_IDENTIFICATION_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructCustomerBankDetailsAudit(CustomerBankDetailsDTO updateDTO,
                                                                CustomerBankDetailsDTO previousDTO,
                                                                CredentialsDTO credentialsDTO,
                                                                Date date, boolean isNew,
                                                                WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CustomerBankDetailsContentDTO previousContentDTO = new CustomerBankDetailsContentDTO();
        CustomerBankDetailsContentDTO updateContentDTO = new CustomerBankDetailsContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCustomerBankDetailsID(previousDTO.getCustomerBankDetailsID());
            previousContentDTO.setCustomerID(previousDTO.getCustomerID());
            if (previousDTO.getCustomerID() != null) {
                previousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(previousDTO.getCustomerID()));
            }
            previousContentDTO.setBankAccountNumber(previousDTO.getBankAccountNumber());
            previousContentDTO.setBankAccountType(previousDTO.getBankAccountType());
            previousContentDTO.setBranchCode(previousDTO.getBranchCode());
            previousContentDTO.setAccountCLSFlag(previousDTO.getAccountCLSFlag());
            previousContentDTO.setAccSince(previousDTO.getAccSince());
            previousContentDTO.setSchmCode(previousDTO.getSchmCode());
            previousContentDTO.setSchmType(previousDTO.getSchmType());
            previousContentDTO.setAccountCurrencyCode(previousDTO.getAccountCurrencyCode());
            previousContentDTO.setAccountStatus(previousDTO.getAccountStatus());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setCustomerBankDetailsID(updateDTO.getCustomerBankDetailsID());
        updateContentDTO.setCustomerID(updateDTO.getCustomerID());
        if (updateDTO.getCustomerID() != null) {
            updateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByID(updateDTO.getCustomerID()));
        }
        updateContentDTO.setBankAccountNumber(updateDTO.getBankAccountNumber());
        updateContentDTO.setBankAccountType(updateDTO.getBankAccountType());
        updateContentDTO.setBranchCode(updateDTO.getBranchCode());
        updateContentDTO.setAccountCLSFlag(updateDTO.getAccountCLSFlag());
        updateContentDTO.setAccSince(updateDTO.getAccSince());
        updateContentDTO.setSchmCode(updateDTO.getSchmCode());
        updateContentDTO.setSchmType(updateDTO.getSchmType());
        updateContentDTO.setAccountCurrencyCode(updateDTO.getAccountCurrencyCode());
        updateContentDTO.setAccountStatus(updateDTO.getAccountStatus());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }

        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_BANK_DETAIL_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_BANK_DETAIL_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }


    public static WebAuditDTO constructCusBankAccJoiningPartnerAudit(CusBankAccJoiningPartnerDto updateDTO,
                                                                     CusBankAccJoiningPartnerDto previousDTO,
                                                                     CredentialsDTO credentialsDTO,
                                                                     Date date, boolean isNew,
                                                                     WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CusBankAccJoiningPartnerContentDTO previousContentDTO = new CusBankAccJoiningPartnerContentDTO();
        CusBankAccJoiningPartnerContentDTO updateContentDTO = new CusBankAccJoiningPartnerContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCusBankAccJoiningPartnerID(previousDTO.getCusBankAccJoiningPartnerID());
            previousContentDTO.setCustomerBankDetailID(previousDTO.getCustomerBankDetailsID());
            previousContentDTO.setCustomerFinacleID(previousDTO.getCustomerFinacleID());
            previousContentDTO.setCustomerNICNumber(previousDTO.getCustomerNICNumber());
            if (previousDTO.getCustomerBankDetailsID() != null) {
                previousContentDTO.setCustomerID(webAuditJDBCDao.getCustomerIDByBankDetailID(previousDTO.getCustomerBankDetailsID()));
                previousContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByBankDetailID(previousDTO.getCustomerBankDetailsID()));
            }
            previousContentDTO.setPrimaryCustomer(Boolean.toString(previousDTO.isPrimaryCustomer()));
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setCusBankAccJoiningPartnerID(updateDTO.getCusBankAccJoiningPartnerID());
        updateContentDTO.setCustomerBankDetailID(updateDTO.getCustomerBankDetailsID());
        updateContentDTO.setCustomerFinacleID(updateDTO.getCustomerFinacleID());
        updateContentDTO.setCustomerNICNumber(updateDTO.getCustomerNICNumber());
        if (updateDTO.getCustomerBankDetailsID() != null) {
            updateContentDTO.setCustomerID(webAuditJDBCDao.getCustomerIDByBankDetailID(updateDTO.getCustomerBankDetailsID()));
            updateContentDTO.setCustomerName(webAuditJDBCDao.getCustomerNameByBankDetailID(updateDTO.getCustomerBankDetailsID()));
        }
        updateContentDTO.setPrimaryCustomer(Boolean.toString(updateDTO.isPrimaryCustomer()));
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }

        if (isNew) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateContentDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_JOINING_PARTNER_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER, updateContentDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_JOINING_PARTNER_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }

    public static WebAuditDTO constructLogInAudit(CredentialsDTO credentialsDTO, Date date) {

        WebAuditDTO webAuditDTO;

        LoginContentDTO loginContentDTO = new LoginContentDTO();

        loginContentDTO.setUserName(credentialsDTO.getUserName());
        loginContentDTO.setUserID(credentialsDTO.getUserID());

        webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.USER, credentialsDTO.getUserID(), DomainConstants.WebAuditMainCategory.USER,
                DomainConstants.WebAuditSubCategory.USER_LOGIN, date, null, loginContentDTO);

        return webAuditDTO;
    }


    public static WebAuditDTO constructFacilityDocumentAudit(FacilityDocumentDTO updateDTO,
                                                             FacilityDocumentDTO previousDTO,
                                                             CredentialsDTO credentialsDTO,
                                                             Date date, boolean isCreation,
                                                             WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FacilityDocumentContentDTO previousContentDTO = new FacilityDocumentContentDTO();
        FacilityDocumentContentDTO updateContentDTO = new FacilityDocumentContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setFacilityDocumentID(previousDTO.getFacilityDocumentID());
            previousContentDTO.setFacilityID(previousDTO.getFacilityID());
            if (previousDTO.getFacilityID() != null) {
                previousContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(previousDTO.getFacilityID()));
            }
            previousContentDTO.setSupportingDocID(previousDTO.getSupportingDocID());
            if (previousDTO.getSupportingDocID() != null) {
                previousContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(previousDTO.getSupportingDocID()));
            }
            if (previousDTO.getDocStorageDTO() != null) {
                previousContentDTO.setDocStorageID(previousDTO.getDocStorageDTO().getDocStorageID());
                previousContentDTO.setDocStorageName(webAuditJDBCDao.getDocumentNameByDocumentID(previousDTO.getDocStorageDTO().getDocStorageID()));
            }
            if (previousDTO.getMandatory() != null) {
                previousContentDTO.setMandatory(previousDTO.getMandatory().toString());
            }
            previousContentDTO.setRemark(previousDTO.getRemark());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setFacilityDocumentID(updateDTO.getFacilityDocumentID());
        updateContentDTO.setFacilityID(updateDTO.getFacilityID());
        if (updateDTO.getFacilityID() != null) {
            updateContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(updateDTO.getFacilityID()));
        }
        updateContentDTO.setSupportingDocID(updateDTO.getSupportingDocID());
        if (updateDTO.getSupportingDocID() != null) {
            updateContentDTO.setSupportingDocName(webAuditJDBCDao.getSupportDocNameByID(updateDTO.getSupportingDocID()));
        }
        if (updateDTO.getDocStorageDTO() != null) {
            updateContentDTO.setDocStorageID(updateDTO.getDocStorageDTO().getDocStorageID());
            updateContentDTO.setDocStorageName(webAuditJDBCDao.getDocumentNameByDocumentID(updateDTO.getDocStorageDTO().getDocStorageID()));
        }
        if (updateDTO.getMandatory() != null) {
            updateContentDTO.setMandatory(updateDTO.getMandatory().toString());
        }
        updateContentDTO.setRemark(updateDTO.getRemark());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_DOCUMENT_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_DOCUMENT_EDIT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructFacilityPurposeOfAdvanceAudit(FacilityPurposeOfAdvanceDTO updateDTO,
                                                                     FacilityPurposeOfAdvanceDTO previousDTO,
                                                                     CredentialsDTO credentialsDTO,
                                                                     Date date, boolean isCreation,
                                                                     WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FacilityPurposeOfAdvanceContentDTO previousContentDTO = new FacilityPurposeOfAdvanceContentDTO();
        FacilityPurposeOfAdvanceContentDTO updateContentDTO = new FacilityPurposeOfAdvanceContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setFacilityPurposeOfAdvanceID(previousDTO.getFacilityPurposeOfAdvanceID());
            previousContentDTO.setFacilityID(previousDTO.getFacilityID());
            if (previousDTO.getFacilityID() != null) {
                previousContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(previousDTO.getFacilityID()));
            }
            previousContentDTO.setPurposeOfAdvance(previousDTO.getPurposeOfAdvance());
            previousContentDTO.setReferenceDescription(previousDTO.getReferenceDescription());
            previousContentDTO.setReferenceCode(previousDTO.getReferenceCode());
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setFacilityPurposeOfAdvanceID(updateDTO.getFacilityPurposeOfAdvanceID());
        updateContentDTO.setFacilityID(updateDTO.getFacilityID());
        if (updateDTO.getFacilityID() != null) {
            updateContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(updateDTO.getFacilityID()));
        }
        updateContentDTO.setPurposeOfAdvance(updateDTO.getPurposeOfAdvance());
        updateContentDTO.setReferenceDescription(updateDTO.getReferenceDescription());
        updateContentDTO.setReferenceCode(updateDTO.getReferenceCode());
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PURPOSE_OF_ADVANCE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_PURPOSE_OF_ADVANCE_EDIT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructFacilityRepaymentAudit(FacilityRepaymentDTO updateDTO,
                                                              FacilityRepaymentDTO previousDTO,
                                                              CredentialsDTO credentialsDTO,
                                                              Date date, boolean isCreation,
                                                              WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FacilityRepaymentContentDTO previousContentDTO = new FacilityRepaymentContentDTO();
        FacilityRepaymentContentDTO updateContentDTO = new FacilityRepaymentContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setFacilityRepaymentID(previousDTO.getFacilityRepaymentID());
            previousContentDTO.setFacilityID(previousDTO.getFacilityID());
            if (previousDTO.getFacilityID() != null) {
                previousContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(previousDTO.getFacilityID()));
            }
            if (previousDTO.getRepaymentType() != null) {
                previousContentDTO.setRepaymentType(previousDTO.getRepaymentType().toString());
            }
            previousContentDTO.setLoanTerm(previousDTO.getLoanTerm());
            previousContentDTO.setPaymentDetail(previousDTO.getPaymentDetail());
            previousContentDTO.setDownPayment(previousDTO.getDownPayment());
            previousContentDTO.setRepaymentComment(previousDTO.getRepaymentComment());
            if (previousDTO.getPaymentFrequency() != null) {
                previousContentDTO.setPaymentFrequency(previousDTO.getPaymentFrequency().toString());
            }
        }

        updateContentDTO.setFacilityRepaymentID(updateDTO.getFacilityRepaymentID());
        updateContentDTO.setFacilityID(updateDTO.getFacilityID());
        if (updateDTO.getFacilityID() != null) {
            updateContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(updateDTO.getFacilityID()));
        }
        if (updateDTO.getRepaymentType() != null) {
            updateContentDTO.setRepaymentType(updateDTO.getRepaymentType().toString());
        }
        updateContentDTO.setLoanTerm(updateDTO.getLoanTerm());
        updateContentDTO.setPaymentDetail(updateDTO.getPaymentDetail());
        updateContentDTO.setDownPayment(updateDTO.getDownPayment());
        updateContentDTO.setRepaymentComment(updateDTO.getRepaymentComment());
        if (updateDTO.getPaymentFrequency() != null) {
            updateContentDTO.setPaymentFrequency(updateDTO.getPaymentFrequency().toString());
        }


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_REPAYMENT_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_REPAYMENT_EDIT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }


    public static WebAuditDTO constructFacilityInterestRateAudit(FacilityInterestRateDTO updateDTO,
                                                                 FacilityInterestRateDTO previousDTO,
                                                                 CredentialsDTO credentialsDTO,
                                                                 Date date, boolean isCreation,
                                                                 WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        FacilityInterestRateContentDTO previousContentDTO = new FacilityInterestRateContentDTO();
        FacilityInterestRateContentDTO updateContentDTO = new FacilityInterestRateContentDTO();

        if (previousDTO != null) {
            previousContentDTO.setCftInterestRateID(previousDTO.getCftInterestRateID());
            previousContentDTO.setFacilityID(previousDTO.getFacilityID());
            if (previousDTO.getFacilityID() != null) {
                previousContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(previousDTO.getFacilityID()));
            }
            if (previousDTO.getCftInterestRateID() != null) {
                previousContentDTO.setCftInterestRateID(previousDTO.getCftInterestRateID());
                previousContentDTO.setCftInterestRateName(webAuditJDBCDao.getCftInterestRateNameByID(previousDTO.getCftInterestRateID()));
            }
            previousContentDTO.setRateCode(previousDTO.getRateCode());
            previousContentDTO.setValue(previousDTO.getValue());
            if (previousDTO.getIsDefault() != null) {
                previousContentDTO.setIsDefault(previousDTO.getIsDefault().toString());
            }
            if (previousDTO.getStatus() != null) {
                previousContentDTO.setStatus(previousDTO.getStatus().toString());
            }
        }

        updateContentDTO.setCftInterestRateID(updateDTO.getCftInterestRateID());
        updateContentDTO.setFacilityID(updateDTO.getFacilityID());
        if (updateDTO.getFacilityID() != null) {
            updateContentDTO.setFacilityRefCode(webAuditJDBCDao.getFacilityRefNumberByFacilityID(updateDTO.getFacilityID()));
        }
        if (updateDTO.getCftInterestRateID() != null) {
            updateContentDTO.setCftInterestRateID(updateDTO.getCftInterestRateID());
            updateContentDTO.setCftInterestRateName(webAuditJDBCDao.getCftInterestRateNameByID(updateDTO.getCftInterestRateID()));
        }
        updateContentDTO.setRateCode(updateDTO.getRateCode());
        updateContentDTO.setValue(updateDTO.getValue());
        if (updateDTO.getIsDefault() != null) {
            updateContentDTO.setIsDefault(updateDTO.getIsDefault().toString());
        }
        if (updateDTO.getStatus() != null) {
            updateContentDTO.setStatus(updateDTO.getStatus().toString());
        }


        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_INTEREST_RATE_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.FACILITY, updateDTO.getFacilityID(), DomainConstants.WebAuditMainCategory.FACILITY_PAPER,
                    DomainConstants.WebAuditSubCategory.FACILITY_INTEREST_RATE_EDIT, date, previousContentDTO, updateContentDTO);
        }

        return webAuditDTO;
    }

    public static WebAuditDTO constructCustomerRatingsAudit(CustomerRatingsDTO updateCustomerRatingsDTO,
                                                            CustomerRatingsDTO previousCustomerRatingsDTO,
                                                            CredentialsDTO credentialsDTO,
                                                            Date date, boolean isCreation,
                                                            WebAuditJDBCDao webAuditJDBCDao) {

        WebAuditDTO webAuditDTO;

        CustomerRatingsContentDTO previousContentDTO = new CustomerRatingsContentDTO();
        CustomerRatingsContentDTO updateContentDTO = new CustomerRatingsContentDTO();

        if (previousCustomerRatingsDTO != null) {

            previousContentDTO.setCustomerID(previousCustomerRatingsDTO.getCustomerID());
            previousContentDTO.setExistingFacilitiesROA(previousCustomerRatingsDTO.getExistingFacilitiesROA());
            previousContentDTO.setProposedFacilitiesROA(previousCustomerRatingsDTO.getProposedFacilitiesROA());
            previousContentDTO.setRiskGrading(previousCustomerRatingsDTO.getRiskGrading());
            previousContentDTO.setRiskScore(previousCustomerRatingsDTO.getRiskScore());

        }

        updateContentDTO.setCustomerID(updateCustomerRatingsDTO.getCustomerID());
        updateContentDTO.setExistingFacilitiesROA(updateCustomerRatingsDTO.getExistingFacilitiesROA());
        updateContentDTO.setProposedFacilitiesROA(updateCustomerRatingsDTO.getProposedFacilitiesROA());
        updateContentDTO.setRiskGrading(updateCustomerRatingsDTO.getRiskGrading());
        updateContentDTO.setRiskScore(updateCustomerRatingsDTO.getRiskScore());

        if (isCreation) {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER_RATINGS, updateCustomerRatingsDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER_RATINGS,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_RATINGS_ADD, date, null, updateContentDTO);
        } else {
            webAuditDTO = new WebAuditDTO(credentialsDTO.getUserID(), DomainConstants.AuditType.CUSTOMER_RATINGS, updateCustomerRatingsDTO.getCustomerID(), DomainConstants.WebAuditMainCategory.CUSTOMER_RATINGS,
                    DomainConstants.WebAuditSubCategory.CUSTOMER_RATINGS_EDIT, date, previousContentDTO, updateContentDTO);
        }
        return webAuditDTO;
    }

}
