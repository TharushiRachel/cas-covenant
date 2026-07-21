package com.itechro.cas.service.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.dao.bcc.FPBccDao;
import com.itechro.cas.dao.bcc.FPBccDocumentDao;
import com.itechro.cas.dao.facilitypaper.FacilityPaperDao;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.bcc.FPBcc;
import com.itechro.cas.model.domain.bcc.FPBccDocument;
import com.itechro.cas.model.domain.facilitypaper.FPDocument;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import com.itechro.cas.model.domain.storage.DocStorage;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.bcc.FPBccDTO;
import com.itechro.cas.model.dto.bcc.FPBccDocDeleteRQ;
import com.itechro.cas.model.dto.bcc.FPBccDocUploadRQ;
import com.itechro.cas.model.dto.bcc.FPBccDocumentDTO;
import com.itechro.cas.model.dto.facilitypaper.FPDocumentDTO;
import com.itechro.cas.model.dto.facilitypaper.FPDocumentUploadRQ;
import com.itechro.cas.model.dto.facilitypaper.FPLoadOptionDTO;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.faclititypaper.FacilityPaperService;
import com.itechro.cas.service.storage.StorageService;
import com.itechro.cas.util.WebAuditUtils;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class FPBccService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperService.class);
    @Autowired
    private Environment environment;

    @Autowired
    private FacilityPaperDao facilityPaperDao;

    @Autowired
    private FPBccDocumentDao fpBccDocumentDao;

    @Autowired
    private FPBccDao fpBccDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private DocStorageDao docStorageDao;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Autowired
    private ModelMapper modelMapper;



    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO uploadFpBccDocument(FPBccDocUploadRQ uploadRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Upload FpBCC document :{} by :{}", uploadRQ, credentialsDTO.getUserName());
        Date date = new Date();

        FacilityPaper facilityPaper = this.facilityPaperDao.findById(uploadRQ.getFacilityPaperID()).get();
        FPBccDocument fpBccDocument = new FPBccDocument();
        FPBcc fpBcc = new FPBcc();
        FPBccDocumentDTO fpBccDocumentDTO = new FPBccDocumentDTO();
        DomainConstants.MasterDataApproveStatus approveStatus = DomainConstants.MasterDataApproveStatus.DRAFT;
        AppsConstants.Status fileStatus = AppsConstants.Status.ACT;

        Boolean isNewFpBcc = uploadRQ.getFpBccID() == 0;
        //LOG.info("isNewFpBcc:{}", isNewFpBcc);

        Integer newFpbccId;
        FPBccDocument tempNewFpBccDoc = new FPBccDocument();

        if (isNewFpBcc) {
            //insert new fpBcc
            //LOG.info("isNewFpBcc 222222:{}");
            fpBcc.setFacilityPaper(facilityPaper);
            fpBcc.setCreatedDate(date);
            fpBcc.setCreatedBy(credentialsDTO.getUserName());
            fpBcc.setApproveStatus(approveStatus);
            fpBcc.setCreatedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
            fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.DRAFT);

            FPBcc newFpBcc = fpBccDao.saveAndFlush(fpBcc);
            newFpbccId = newFpBcc.getFpBccId();
            //LOG.info("newFpBcc:{}", newFpBcc);

            //file upload
            if (uploadRQ.getDocStorageDTO() != null) {
                DocStorage uploadedDoc = UploadFpBccDocs(uploadRQ, newFpBcc, date);

                //insert fpBccDoc
                FPBccDocument newFpBccDoc = CreateFpBccDocument(newFpBcc, uploadedDoc, uploadRQ, fileStatus, credentialsDTO, date);
                tempNewFpBccDoc = newFpBccDoc;

            } else {
                LOG.error("FP Bcc paper data null:{}", uploadRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_DOCUMENT_NULL_SUPPORT_DOC);
            }
        } else {
            //update - only doc upload
            FPBcc existFpBcc = fpBccDao.findById(uploadRQ.getFpBccID()).get();
            newFpbccId = existFpBcc.getFpBccId();

            if (existFpBcc != null){
                String meetingMinute = "Meeting Minute";
                String coverPage = "Cover Page";

                if(uploadRQ.getDocumentName().equals(meetingMinute) || uploadRQ.getDocumentName().equals(coverPage)){
                    FPBccDocument oldFpBccDocument = fpBccDocumentDao.findFpBccByFpBccId(existFpBcc.getFpBccId(), uploadRQ.getDocumentName(), "ACT");

                    if(oldFpBccDocument != null){
                        oldFpBccDocument.setStatus(AppsConstants.Status.INA);
                        FPBccDocument deletedDoc = fpBccDocumentDao.saveAndFlush(oldFpBccDocument);
                    }
                }

                DocStorage uploadedDoc = UploadFpBccDocs(uploadRQ, existFpBcc, date);
                FPBccDocument newFpBccDoc = CreateFpBccDocument(existFpBcc, uploadedDoc, uploadRQ, fileStatus, credentialsDTO, date);
                tempNewFpBccDoc = newFpBccDoc;

                if (existFpBcc.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED){
                    existFpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.DRAFT);
                    FPBcc newFpBcc = fpBccDao.saveAndFlush(existFpBcc);
                }

            }else {
                LOG.error("No FP Bcc paper:{}", uploadRQ);
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FP_FP_DOCUMENT_NULL_SUPPORT_DOC);
            }

        }
        LOG.info("END: Upload facility paper document :{} by :{}", uploadRQ, credentialsDTO.getUserName());

        facilityPaper = this.facilityPaperDao.findById(uploadRQ.getFacilityPaperID()).get();

        FPBcc currentFpBcc = fpBccDao.findById(newFpbccId).get();
        currentFpBcc.getFpBccDocumentSet().add(tempNewFpBccDoc);
        facilityPaper.getFpBccSet().add(currentFpBcc);

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadDocument();
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);

    }

    private DocStorage UploadFpBccDocs(FPBccDocUploadRQ uploadRQ, FPBcc newFpBcc, Date date){
        DocStorage docStorage = new DocStorage();
        docStorage.setDescription("FP Bcc paper: " + newFpBcc.getFpBccId() + "");
        docStorage.setFileName(uploadRQ.getDocStorageDTO().getFileName());
        docStorage.setDocument(uploadRQ.getDocStorageDTO().getDocument());
        docStorage.setLastUpdatedDate(date);

        DocStorage newDocStorage = docStorageDao.saveAndFlush(docStorage);
        return newDocStorage;
    }

    private FPBccDocument CreateFpBccDocument( FPBcc fpBcc, DocStorage uploadedDoc, FPBccDocUploadRQ uploadRQ, AppsConstants.Status fileStatus,
                                               CredentialsDTO credentialsDTO, Date date){
        FPBccDocument fpBccDocument = new FPBccDocument();

        fpBccDocument.setFpBcc(fpBcc);
        fpBccDocument.setDocStorage(uploadedDoc);
        fpBccDocument.setDocumentName(uploadRQ.getDocumentName());
        fpBccDocument.setRemark(uploadRQ.getRemark());
        fpBccDocument.setUploadedUserDisplayName(uploadRQ.getUploadedUserDisplayName());
        fpBccDocument.setStatus(fileStatus);
        fpBccDocument.setCreatedBy(credentialsDTO.getUserName());
        fpBccDocument.setCreatedDate(date);
        fpBccDocument.setIsSendEmail(uploadRQ.getIsSendEmail());
        fpBccDocument.setFileSize(uploadRQ.getFileSize());
        fpBccDocument.setFileType(uploadRQ.getFileType());
        fpBccDocument.setApproveStatus(DomainConstants.MasterDataApproveStatus.PENDING);

        FPBccDocument newFpBccDoc = fpBccDocumentDao.saveAndFlush(fpBccDocument);
        return newFpBccDoc;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FacilityPaperDTO deactivateFpBccDoc(FPBccDocDeleteRQ fpBccDocDeleteRQ, CredentialsDTO credentialsDTO) throws AppsException {

        LOG.info("START: Deactivate the Fp Bcc document: {} by: {}", fpBccDocDeleteRQ, credentialsDTO.getUserName());

        FPBccDocument fpBccDocument = fpBccDocumentDao.findById(fpBccDocDeleteRQ.getFpBccDocumentID()).get();

        if(!fpBccDocument.getApproveStatus().equals("APPROVED")){
            fpBccDocument.setStatus(fpBccDocDeleteRQ.getStatus());

            FPBccDocument newFpBccDoc = fpBccDocumentDao.saveAndFlush(fpBccDocument);

        }
        FacilityPaper facilityPaper = this.facilityPaperDao.findById(fpBccDocDeleteRQ.getFacilityPaperID()).get();

        LOG.info("END: Deactivate the Fp Bcc document: {} by: {}", fpBccDocDeleteRQ, credentialsDTO.getUserName());

        FPLoadOptionDTO loadOptionDTO = new FPLoadOptionDTO();
        loadOptionDTO.loadDocument();
        return new FacilityPaperDTO(facilityPaper, loadOptionDTO);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FPBccDTO authorizeBCCDocs(FPBccDocumentDTO fpBccDocumentDTO , CredentialsDTO credentialsDTO) throws AppsException, IOException {

        LOG.info("START: authorizeBCCDocs {}, by {}", fpBccDocumentDTO, credentialsDTO.getUserName());

        Date date = new Date();
        FPBcc fpBcc = fpBccDao.findById(fpBccDocumentDTO.getFpBccID()).get();
        AppsConstants.BccStatus bccStatus = null;

//        DomainConstants.MasterDataApproveStatus approveStatus = null;
//        facilityPaper = facilityPaperDao.findById(fpBccDTO.getFacilityPaperID()).get();

        if (fpBcc != null){
            AppsConstants.Status fileStatus = AppsConstants.Status.ACT;
            List<FPBccDocument> fpBccDocument = fpBccDocumentDao.findByFpBccAndStatus(fpBccDocumentDTO.getFpBccID(), fileStatus.name());

            DomainConstants.MasterDataApproveStatus approveStatus = null;
            if(fpBccDocumentDTO.getApproveStatus() == DomainConstants.MasterDataApproveStatus.APPROVED){
                approveStatus = DomainConstants.MasterDataApproveStatus.APPROVED;
                fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.APPROVED);
            }
            else if(fpBccDocumentDTO.getApproveStatus() == DomainConstants.MasterDataApproveStatus.REJECTED){
                approveStatus = DomainConstants.MasterDataApproveStatus.REJECTED;
                fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.REJECTED);
            }

            for (FPBccDocument fpBccDocument1 : fpBccDocument) {
                if (fpBccDocument1.getApproveStatus() == DomainConstants.MasterDataApproveStatus.PENDING){
                    fpBccDocument1.setApproveStatus(approveStatus);
                    FPBccDocument newFbDoc = fpBccDocumentDao.saveAndFlush(fpBccDocument1);
                }

            }

//            facilityPaper = facilityPaperDao.saveAndFlush(facilityPaper);
        }


        //fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.);

//        fpBcc.setFacilityPaper(facilityPaper);
        fpBcc = fpBccDao.saveAndFlush(fpBcc);
        FPBccDTO fpBccDTO = new FPBccDTO(fpBcc);

        LOG.info("END: authorizeBCCDocs {}, by {}", fpBccDTO, credentialsDTO.getUserName());
        return fpBccDTO;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public FPBccDTO forwardBCCDocs(Integer fpBccID , CredentialsDTO credentialsDTO) throws AppsException, IOException {

        LOG.info("START: forwardBCCDocs {}, by {}", fpBccID, credentialsDTO.getUserName());

        Date date = new Date();
        FPBcc fpBcc = fpBccDao.findById(fpBccID).get();
        DomainConstants.MasterDataApproveStatus approveStatus = null;
        AppsConstants.Status fileStatus = AppsConstants.Status.ACT;

        //facilityPaper = facilityPaperDao.findById(fpBccDTO.getFacilityPaperID()).get();

        if (fpBcc.getApproveStatus() != DomainConstants.MasterDataApproveStatus.PENDING){
            fpBcc.setDocsApproveStatus(DomainConstants.BccDocsApproveStatus.PENDING);
            fpBcc = fpBccDao.saveAndFlush(fpBcc);

            List<FPBccDocument> fpBccDocumentList = fpBccDocumentDao.findByFpBccAndStatus(fpBccID, fileStatus.name());

            for (FPBccDocument fpBccDocument : fpBccDocumentList) {
                if (fpBccDocument.getApproveStatus() == DomainConstants.MasterDataApproveStatus.PENDING ||
                        fpBccDocument.getApproveStatus() == DomainConstants.MasterDataApproveStatus.REJECTED){

                    fpBccDocument.setApproveStatus(DomainConstants.MasterDataApproveStatus.PENDING);
                    FPBccDocument newFbDoc = fpBccDocumentDao.saveAndFlush(fpBccDocument);
                }

            }

        }

        FPBccDTO fpBccDTO = new FPBccDTO(fpBcc);

        LOG.info("END: forwardBCCDocs {}, by {}", fpBccDTO, credentialsDTO.getUserName());
        return fpBccDTO;
    }
}

