package com.itechro.cas.service.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.dao.casmaster.SupportingDocDao;
import com.itechro.cas.dao.facility.*;
import com.itechro.cas.dao.facility.jdbc.FacilityJdbcDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.facilitypaper.facility.*;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.facility.*;
import com.itechro.cas.model.dto.facilitypaper.FPLoadOptionDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.service.storage.StorageService;
import com.itechro.cas.util.WebAuditUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FacilityService {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityService.class);

    @Autowired
    private FacilityDao facilityDao;

    @Autowired
    private FacilityJdbcDao facilityJdbcDao;

    @Autowired
    private FacilityDocumentDao facilityDocumentDao;

    @Autowired
    private FacilityInterestRateDao facilityInterestRateDao;

    @Autowired
    private FacilityPurposeOfAdvanceDao facilityPurposeOfAdvanceDao;

    @Autowired
    private FacilityPaperDao facilityPaperDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DocStorageDao docStorageDao;

    @Autowired
    private SupportingDocDao supportingDocDao;

    @Autowired
    private WebAuditService webAuditService;

    @Autowired
    private WebAuditJDBCDao webAuditJDBCDao;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityDTO getFacilityByID(Integer facilityID) {

        Facility facility = facilityDao.getOne(facilityID);
        FacilityLoadOptionDTO loadOptionDTO = new FacilityLoadOptionDTO();
        loadOptionDTO.loadFacilityDocument();
        loadOptionDTO.loadFacilityInterestRate();
        loadOptionDTO.loadFacilityPurposeOfAdvance();

        return new FacilityDTO(facility, loadOptionDTO);
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public FacilityDTO saveOrUpdateFacility(FacilityDTO facilityDTO, CredentialsDTO credentialsDTO) throws AppsException {
//
//        LOG.info("START : Facility save update : {} : {}", facilityDTO, credentialsDTO.getUserID());
//        FacilityDTO previousDTO = null;
//        boolean isNewFacility = (facilityDTO.getFacilityID() == null);
//        if (!isNewFacility) {
//            previousDTO = this.getFacilityByID(facilityDTO.getFacilityID());
//        }
//
//        FacilityBuilder facilityBuilder = new FacilityBuilder(facilityDTO, credentialsDTO);
//        facilityBuilder.setCreditFacilityTemplateDao(creditFacilityTemplateDao);
//        facilityBuilder.setFacilityDao(facilityDao);
//        facilityBuilder.setFacilityPaperDao(facilityPaperDao);
//        facilityBuilder.setFacilityRepaymentDao(facilityRepaymentDao);
//
//        Facility facility = facilityBuilder.loadFacility()
//                .buildBaseFacility()
//                .buildFacilityDocument()
//                .buildAdvanceOfPurpose()
//                .buildInterestRate()
//                .buildVitalInfo()
//                .getFacility();
//
//        facility = facilityDao.saveAndFlush(facility);
//
//        //audit
//        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityAudit(new FacilityDTO(facility), previousDTO, credentialsDTO, new Date(), isNewFacility, webAuditJDBCDao);
//        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);
//
//        LOG.info("END : Facility save update : {} : {}", facilityDTO, credentialsDTO.getUserID());
//        FacilityLoadOptionDTO loadOptionDTO = new FacilityLoadOptionDTO();
//        loadOptionDTO.loadFacilityPurposeOfAdvance();
//        loadOptionDTO.loadFacilityInterestRate();
//        loadOptionDTO.loadFacilityDocument();
//        loadOptionDTO.loadSecurities();
//
//        return new FacilityDTO(facility, loadOptionDTO);
//    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<FacilityDTO> getPagedFacilityDTO(FacilitySearchRQ facilitySearchRQ) {
        return facilityJdbcDao.getPagedFacilityDTO(facilitySearchRQ);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityDocumentDTO getFacilityDocumentByID(Integer facilityDocID) {

        FacilityDocument facilityDoc = facilityDocumentDao.getOne(facilityDocID);
        return new FacilityDocumentDTO(facilityDoc);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityDocumentDTO saveOrUpdateFacilityDocument(FacilityDocumentDTO facilityDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Document save update : {} : {}", facilityDocumentDTO, credentialsDTO.getUserID());

        FacilityDocument facilityDocument = null;
        FacilityDocumentDTO previousDTO = null;
        Date date = new Date();
        boolean isNewFacilityDocument = facilityDocumentDTO.getFacilityDocumentID() == null;

        if (isNewFacilityDocument) {
            facilityDocument = new FacilityDocument();
            facilityDocument.setCreatedBy(credentialsDTO.getUserName());
            facilityDocument.setCreatedDate(date);
        } else {
            facilityDocument = facilityDocumentDao.getOne(facilityDocumentDTO.getFacilityDocumentID());
            previousDTO = new FacilityDocumentDTO(facilityDocument);
            facilityDocument.setModifiedBy(credentialsDTO.getUserName());
            facilityDocument.setModifiedDate(date);

        }
        facilityDocument.setCftSupportDocID(facilityDocumentDTO.getCftSupportDocID());
        facilityDocument.setMandatory(facilityDocumentDTO.getMandatory());
        facilityDocument.setDisplayOrder(facilityDocumentDTO.getDisplayOrder());
        //facilityDocument.setDisplayOrderByStatus(facilityDocumentDTO.getDisplayOrder(), facilityDocumentDTO.toString());
        facilityDocument.setRemark(facilityDocumentDTO.getRemark());
        facilityDocument.setStatus(facilityDocumentDTO.getStatus());

        facilityDocument = facilityDocumentDao.saveAndFlush(facilityDocument);

        //Audit
//        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityDocumentAudit(new FacilityDocumentDTO(facilityDocument), previousDTO, credentialsDTO, new Date(), isNewFacilityDocument, webAuditJDBCDao);
//        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility Document save update : {} : {}", facilityDocumentDTO, credentialsDTO.getUserID());

        return new FacilityDocumentDTO(facilityDocument);

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<FacilityDocumentDTO> getPagedFacilityDocumentDTO(FacilityDocumentSearchRQ searchRQ) {
        return facilityJdbcDao.getPagedFacilityDocDTO(searchRQ);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO uploadFacilityDocument(FacilityDocumentUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {
        LOG.info("START: Upload facility document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();
        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(uploadRQ.getFacilityPaperID());
        Facility facility = facilityPaper.getFacilityByID(uploadRQ.getFacilityID());

        FacilityDocument facilityDocument = null;
        if (uploadRQ.getFacilityDocumentID() == null) {
            facilityDocument = new FacilityDocument();
            facilityDocument.setCreatedBy(credentialsDTO.getUserName());
            facilityDocument.setCreatedDate(date);
            facility.addFacilityDocuments(facilityDocument);
        } else {
            facilityDocument = facility.getFacilityDocumentsByID(uploadRQ.getFacilityDocumentID());
            facilityDocument.setModifiedBy(credentialsDTO.getUserName());
            facilityDocument.setModifiedDate(date);
        }
        if (uploadRQ.getDocStorageDTO() != null) {
            uploadRQ.getDocStorageDTO().setDescription("FACILITY DOCUMENT: ");
            DocStorageDTO docStorageDTO = this.storageService.saveUpdateDocument(uploadRQ.getDocStorageDTO(), credentialsDTO);
            facilityDocument.setDocStorage(this.docStorageDao.getOne(docStorageDTO.getDocStorageID()));
        } else {
            LOG.error("Facility Document data null:{}", uploadRQ);
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FACILITY_DOCUMENT_NULL_FACILITY_DOC);
        }

        facilityDocument.setRemark(uploadRQ.getRemark());
        facilityDocument.setSupportingDoc(this.supportingDocDao.getOne(uploadRQ.getSupportingDocID()));
        facilityDocument.setStatus(uploadRQ.getStatus());
        ;
        facilityDocument.setCftSupportDocID(uploadRQ.getCftSupportingDocID());
        facilityDocument.setMandatory(uploadRQ.getMandatory());

        facility = facilityDao.saveAndFlush(facility);
        LOG.info("END: Upload facility document :{} by :{}", uploadRQ, credentialsDTO.getUserName());


        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadFacilities();
        FacilityLoadOptionDTO facilityLoadOption = new FacilityLoadOptionDTO();
        facilityLoadOption.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOption);
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityInterestRateDTO getFacilityInterestRateByID(Integer rateID) {

        FacilityInterestRate facilityInterestRate = facilityInterestRateDao.getOne(rateID);
        return new FacilityInterestRateDTO(facilityInterestRate);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityInterestRateDTO saveOrUpdateFacilityInterestRate(FacilityInterestRateDTO facilityInterestRateDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Interest Rate save update : {} : {}", facilityInterestRateDTO, credentialsDTO.getUserID());

        FacilityInterestRate facilityInterestRate = null;
        FacilityInterestRateDTO previousDTO = null;
        Date date = new Date();
        boolean isNewFacilityIR = facilityInterestRateDTO.getFacilityInterestRateID() == null;

        if (isNewFacilityIR) {
            facilityInterestRate = new FacilityInterestRate();
            facilityInterestRate.setCreatedBy(credentialsDTO.getUserName());
            facilityInterestRate.setCreatedDate(date);
        } else {
            facilityInterestRate = facilityInterestRateDao.getOne(facilityInterestRateDTO.getFacilityInterestRateID());
            previousDTO = new FacilityInterestRateDTO(facilityInterestRate);
            facilityInterestRate.setModifiedBy(credentialsDTO.getUserName());
            facilityInterestRate.setModifiedDate(date);

        }
        facilityInterestRate.setRateCode(facilityInterestRateDTO.getRateCode());
        facilityInterestRate.setUserComment(facilityInterestRateDTO.getUserComment());
        facilityInterestRate.setValue(facilityInterestRateDTO.getValue());
        facilityInterestRate.setIsDefault(facilityInterestRateDTO.getIsDefault());
        facilityInterestRate.setStatus(facilityInterestRateDTO.getStatus());

        facilityInterestRate = facilityInterestRateDao.saveAndFlush(facilityInterestRate);

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityInterestRateAudit(facilityInterestRateDTO, previousDTO, credentialsDTO, new Date(), isNewFacilityIR, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility Interest Rate save update : {} : {}", facilityInterestRateDTO, credentialsDTO.getUserID());

        return new FacilityInterestRateDTO(facilityInterestRate);

    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPurposeOfAdvanceDTO getFacilityPurposeOfAdvanceByID(Integer facilityPurposeOfAdvanceID) {

        FacilityPurposeOfAdvance facilityPurposeOfAdvance = facilityPurposeOfAdvanceDao.getOne(facilityPurposeOfAdvanceID);
        return new FacilityPurposeOfAdvanceDTO(facilityPurposeOfAdvance);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPurposeOfAdvanceDTO saveOrUpdateFacilityPurposeOfAdvance(FacilityPurposeOfAdvanceDTO facilityPurposeOfAdvanceDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility Purpose Of Advance save update : {} : {}", facilityPurposeOfAdvanceDTO, credentialsDTO.getUserID());

        FacilityPurposeOfAdvance facilityPurposeOfAdvance = null;
        FacilityPurposeOfAdvanceDTO previousDTO = null;
        Date date = new Date();
        boolean isNewFacilityPoA = facilityPurposeOfAdvanceDTO.getFacilityPurposeOfAdvanceID() == null;

        if (isNewFacilityPoA) {
            facilityPurposeOfAdvance = new FacilityPurposeOfAdvance();
            facilityPurposeOfAdvance.setCreatedBy(credentialsDTO.getUserName());
            facilityPurposeOfAdvance.setCreatedDate(date);
        } else {
            facilityPurposeOfAdvance = facilityPurposeOfAdvanceDao.getOne(facilityPurposeOfAdvanceDTO.getFacilityPurposeOfAdvanceID());
            previousDTO = new FacilityPurposeOfAdvanceDTO(facilityPurposeOfAdvance);
            facilityPurposeOfAdvance.setModifiedBy(credentialsDTO.getUserName());
            facilityPurposeOfAdvance.setModifiedDate(date);
        }
        facilityPurposeOfAdvance.setReferenceDescription(facilityPurposeOfAdvanceDTO.getReferenceDescription());
        facilityPurposeOfAdvance.setReferenceCode(facilityPurposeOfAdvanceDTO.getReferenceCode());
        facilityPurposeOfAdvance.setPurposeOfAdvance(facilityPurposeOfAdvanceDTO.getPurposeOfAdvance());
        facilityPurposeOfAdvance.setStatus(facilityPurposeOfAdvanceDTO.getStatus());

        facilityPurposeOfAdvance = facilityPurposeOfAdvanceDao.saveAndFlush(facilityPurposeOfAdvance);

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityPurposeOfAdvanceAudit(facilityPurposeOfAdvanceDTO, previousDTO, credentialsDTO, new Date(), isNewFacilityPoA, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility Purpose Of Advance save update : {} : {}", facilityPurposeOfAdvanceDTO, credentialsDTO.getUserID());

        return new FacilityPurposeOfAdvanceDTO(facilityPurposeOfAdvance);

    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO deactivateFacilitySupportingDoc(FacilityDocumentDTO facilityDocumentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Facility documents deactivate : {} : {}", facilityDocumentDTO, credentialsDTO.getUserID());

        FacilityDocumentDTO previousDTO = null;
        FacilityPaper facilityPaper = facilityPaperDao.getOne(facilityDocumentDTO.getFacilityPaperID());
        Facility facility = facilityDao.getOne(facilityDocumentDTO.getFacilityID());

        FacilityLoadOptionDTO facilityLoadOptionDTO = new FacilityLoadOptionDTO();
        FacilityDocument facilityDocument = facilityDocumentDao.getOne(facilityDocumentDTO.getFacilityDocumentID());
        previousDTO = new FacilityDocumentDTO(facilityDocument);
        facilityDocument.setStatus(AppsConstants.Status.INA);
        facilityDocument = facilityDocumentDao.saveAndFlush(facilityDocument);
        facilityLoadOptionDTO.loadFacilityDocument();

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityDocumentAudit(new FacilityDocumentDTO(facilityDocument), previousDTO, credentialsDTO, new Date(), false, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Facility documents deactivate : {} : {}", facilityDocumentDTO, credentialsDTO.getUserID());

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadFacilities();
        FacilityLoadOptionDTO facilityLoadOption = new FacilityLoadOptionDTO();
        facilityLoadOption.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOption);

        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO saveOrUpdateFacilityRepayment(FacilityRepaymentDTO facilityRepaymentDTO, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START : Save facility repayment : {} : {}", facilityRepaymentDTO, credentialsDTO.getUserID());

        FacilityRepayment facilityRepayment = null;
        FacilityRepaymentDTO previousDTO = null;
        Date date = new Date();
        boolean isNewRepayment = facilityRepaymentDTO.getFacilityRepaymentID() == null;

        FacilityPaper facilityPaper = this.facilityPaperDao.getOne(facilityRepaymentDTO.getFacilityPaperID());
        Facility facility = facilityPaper.getFacilityByID(facilityRepaymentDTO.getFacilityID());
        if (isNewRepayment) {
            facilityRepayment = new FacilityRepayment();
            facilityRepayment.setCreatedBy(credentialsDTO.getUserName());
            facilityRepayment.setCreatedDate(date);
            facilityRepayment.setFacility(facility);
            facility.setFacilityRepayment(facilityRepayment);
        } else {
            facilityRepayment = facility.getFacilityRepayment();
            previousDTO = new FacilityRepaymentDTO(facilityRepayment);
            facilityRepayment.setModifiedBy(credentialsDTO.getUserName());
            facilityRepayment.setModifiedDate(date);
        }
        facilityRepayment.setRepaymentType(facilityRepaymentDTO.getRepaymentType());
        facilityRepayment.setDownPayment(facilityRepaymentDTO.getDownPayment());
        facilityRepayment.setLoanTerm(facilityRepaymentDTO.getLoanTerm());
        facilityRepayment.setRepaymentComment(facilityRepaymentDTO.getRepaymentComment());
        facilityRepayment.setPaymentDetail(facilityRepaymentDTO.getPaymentDetail());
        facilityRepayment.setPaymentFrequency(facilityRepaymentDTO.getPaymentFrequency());
        facilityPaper = this.facilityPaperDao.saveAndFlush(facilityPaper);

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructFacilityRepaymentAudit(new FacilityRepaymentDTO(facilityRepayment), previousDTO, credentialsDTO, new Date(), false, webAuditJDBCDao);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END : Save facility repayment : {} : {}", facilityRepaymentDTO, credentialsDTO.getUserID());

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadFacilities();
        FacilityLoadOptionDTO facilityLoadOption = new FacilityLoadOptionDTO();
        facilityLoadOption.loadAllData();
        loadOptionDTO.setFacilityLoadOptionDTO(facilityLoadOption);

        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public List<FacilityDTO> getFacilities(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {
//
//        return facilityDao.findByFacilityPaperFacilityPaperID(facilityPaperID);
//
//    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public List<FacilityListForCovenantDTO> getFacilities(Integer facilityPaperID, CredentialsDTO credentialsDTO) throws AppsException {
        List<FacilityDTO> facilityDTOList = facilityDao.findByFacilityPaperFacilityPaperID(facilityPaperID);

        // Transform FacilityDTO objects into SimplifiedFacilityDTO objects
        List<FacilityListForCovenantDTO> simplifiedFacilityDTOList = facilityDTOList.stream()
                .map(facilityDTO -> {
                    FacilityListForCovenantDTO simplifiedFacilityDTO = new FacilityListForCovenantDTO();
                    simplifiedFacilityDTO.setFacilityID(facilityDTO.getFacilityID());
                    simplifiedFacilityDTO.setFacilityRefCode(facilityDTO.getFacilityRefCode());
                    simplifiedFacilityDTO.setCreditFacilityTemplateID(facilityDTO.getCreditFacilityTemplateID());
                    simplifiedFacilityDTO.setCreditFacilityName(facilityDTO.getCreditFacilityTemplateDTO().getCreditFacilityName());
                    simplifiedFacilityDTO.setFacilityAmount(facilityDTO.getFacilityAmount());
                    simplifiedFacilityDTO.setFacilityCurrency(facilityDTO.getFacilityCurrency());
                    simplifiedFacilityDTO.setDisplayOrder(facilityDTO.getDisplayOrder());
                    //simplifiedFacilityDTO.setDisplayOrder(newFacilityMap.get(facilityDTOList.size()).getDisplayOrder());
                    simplifiedFacilityDTO.setStatus(facilityDTO.getStatus());
                    return simplifiedFacilityDTO;
                })
                .filter(dto -> dto.getStatus() != AppsConstants.Status.INA)
                .collect(Collectors.toList());

        simplifiedFacilityDTOList.sort(Comparator.comparingInt(FacilityListForCovenantDTO::getDisplayOrder));
        for (int i = 0; i < simplifiedFacilityDTOList.size(); i++) {
            simplifiedFacilityDTOList.get(i).setDisplayOrder(i + 1);
        }

        return simplifiedFacilityDTOList;
    }


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public Facility getFacilityByIDForCovenants(Integer facilityID) {

        Facility facility = facilityDao.getOne(facilityID);

        return facility;
    }

    public Integer getDeviationCount(Integer facilityPaperID, CredentialsDTO credentialsDTO) {
        Integer count = 0;
        try {
            LOG.info("START : Get Deviation Count : {} : {}", facilityPaperID, credentialsDTO.getUserID());
            count = facilityPaperDao.getDeviationCount(facilityPaperID);
            LOG.info("END : Get Deviation Count : {} : {}", facilityPaperID, credentialsDTO.getUserID());
        } catch (Exception e) {
            LOG.error("Error : Get Deviation Count : {}", e.getMessage());
        }
        return count;
    }

//    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
//    public FacilityDTO getFacilityByID(Integer facilityID) {
//
//        Facility facility = facilityDao.getOne(facilityID);
//        FacilityLoadOptionDTO loadOptionDTO = new FacilityLoadOptionDTO();
//        loadOptionDTO.loadFacilityDocument();
//        loadOptionDTO.loadFacilityInterestRate();
//        loadOptionDTO.loadFacilityPurposeOfAdvance();
//
//        return new FacilityDTO(facility, loadOptionDTO);
//    }

}
