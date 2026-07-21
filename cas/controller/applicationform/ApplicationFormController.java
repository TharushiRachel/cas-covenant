package com.itechro.cas.controller.applicationform;

import com.google.gson.Gson;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.applicationform.AFStatusHistoryDTO;
import com.itechro.cas.model.dto.applicationform.*;
import com.itechro.cas.model.dto.applicationform.applicationFormCustomer.SearchApplicationFormRQ;
import com.itechro.cas.model.dto.applicationform.esg.*;
import com.itechro.cas.model.dto.esg.*;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.applicationform.ApplicationFormService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "${api.prefix}/applicationForm")
public class ApplicationFormController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormController.class);

    @Autowired
    private ApplicationFormService applicationFormService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/draftApplicationForm", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> draftApplicationForm(@RequestBody DraftApplicationFormRQ draftApplicationFormRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft Application Form base information : {}, user {}", draftApplicationFormRQ, credentialsDTO.getUserID());

        ApplicationFormDTO result = this.applicationFormService.draftApplicationForm(draftApplicationFormRQ, credentialsDTO);

        LOG.info("END : Draft Application Form base information : {}, user {}", draftApplicationFormRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateApplicationForm", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateApplicationForm(@RequestBody ApplicationFormDTO applicationFormDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Application Form base information : {}, user {}", applicationFormDTO, credentialsDTO.getUserID());

        ApplicationFormDTO result = this.applicationFormService.saveOrUpdateApplicationForm(applicationFormDTO, credentialsDTO);

        LOG.info("END : Save or Update Application Form base information : {}, user {}", applicationFormDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/replicateApplicationForm", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> replicateApplicationForm(@RequestBody ReplicateApplicationFormRQ applicationFormDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Replicate Application Form : {}, user {}", applicationFormDTO, credentialsDTO.getUserID());

        ApplicationFormDTO result = this.applicationFormService.replicateApplicationForm(applicationFormDTO, credentialsDTO);

        LOG.info("END : Replicate Application Form : {}, user {}", applicationFormDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedApplicationForm", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<ApplicationFormDTO>> getPagedApplicationForm(@RequestBody ApplicationFormSearchRQ applicationFormSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        Page<ApplicationFormDTO> pageResponse = applicationFormService.getPagedApplicationForm(applicationFormSearchRQ);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedBranchApplicationForm", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<ApplicationFormPageRSDTO>> getPagedBranchApplicationForm(@RequestBody ApplicationFormSearchRQ applicationFormSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        Page<ApplicationFormPageRSDTO> pageResponse = applicationFormService.getPagedBranchApplicationForm(applicationFormSearchRQ);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getInboxPagedApplicationForms", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<ApplicationFormPageRSDTO>> getInboxPagedApplicationForms(@RequestBody ApplicationFormSearchRQ applicationFormSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Inbox Application Forms {}", credentialsDTO.getUserID());

        Page<ApplicationFormPageRSDTO> pageResponse = applicationFormService.getInboxPagedApplicationForms(applicationFormSearchRQ);

        LOG.info("END : Search Inbox Application Forms {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCopyPagedApplicationForms", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<ApplicationFormCopyRSDTO>> getCopyPagedApplicationForms(@RequestBody ApplicationFormSearchRQ applicationFormSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Forms for Copy {}", credentialsDTO.getUserID());

        Page<ApplicationFormCopyRSDTO> pageResponse = applicationFormService.getCopyPagedApplicationForms(applicationFormSearchRQ);

        LOG.info("END : Search Application Forms for Copy {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationFormByID/{applicationFormID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<ApplicationFormDTO> getApplicationFormByID(@PathVariable Integer applicationFormID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Application Form By ID : {}, user {} ", applicationFormID, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.getApplicationFormByID(applicationFormID);

        LOG.info("END : Get Application Form By ID : {}, user {} ", applicationFormID, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateAFBasicDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateAFBasicDetails(@RequestBody BasicInformationDTO basicInformationDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Application Form Basic Detail Save Or Update : {} : {}", basicInformationDTO, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = applicationFormService.saveOrUpdateAFBasicDetails(basicInformationDTO, credentialsDTO);

        LOG.info("END : Application Form Basic Detail Save Or Update : {} : {}", basicInformationDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/uploadApplicationFormDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> uploadApplicationFormDocument(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                                            @RequestParam("uploadRequestData") String uploadRQData) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        AFDocumentUploadRQ updateRQ = gson.fromJson(uploadRQData, AFDocumentUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload Application Form document : {} by user {}", updateRQ, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.uploadApplicationFormDocument(updateRQ, credentialsDTO);

        LOG.info("END : Upload Application Form document : {} by user {}", applicationFormDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateApplicationFormSupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> deactivateApplicationFormSupportingDoc(@RequestBody AFDocumentDTO afDocumentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Application Form supporting document: {} by: {}", afDocumentDTO, credentialsDTO.getUserName());

        ApplicationFormDTO response = this.applicationFormService.deactivateApplicationFormSupportingDoc(afDocumentDTO, credentialsDTO);

        LOG.info("END: Deactivate the Application Form supporting document: {} by: {}", afDocumentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateOwnershipDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateOwnershipDetails(@RequestBody OwnershipDetailsDTO ownershipDetailsDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Application Form Ownership Details Save Or Update : {} : {}", ownershipDetailsDTO, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = applicationFormService.saveOrUpdateOwnershipDetails(ownershipDetailsDTO, credentialsDTO);

        LOG.info("END : Application Form Ownership Details Save Or Update : {} : {}", ownershipDetailsDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateFinancialObligations", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateFinancialObligations(@RequestBody AFFinancialObligationDTO afFinancialObligationDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Application Form Financial Obligation Save Or Update : {} : {}", afFinancialObligationDTO, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = applicationFormService.saveOrUpdateFinancialObligations(afFinancialObligationDTO, credentialsDTO);

        LOG.info("END : Application Form Financial Obligation Save Or Update : {} : {}", afFinancialObligationDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateAFFacility", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateFacilityPaperFacility(@RequestBody AFFacilityDTO afFacilityDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update Application Form facility data : {}, user {}", afFacilityDTO, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.saveOrUpdateAFFacility(afFacilityDTO, credentialsDTO);

        LOG.info("END : Update Application Form facility data: {}, user {}", afFacilityDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateApplicationFormTopics", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateApplicationFormTopics(@RequestBody AFTopicDataDTO afTopicDataDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Application Form Topics Save Or Update : {} : {}", afTopicDataDTO, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = applicationFormService.saveOrUpdateApplicationFormTopics(afTopicDataDTO, credentialsDTO);

        LOG.info("END : Application Form Topics Save Or Update : {} : {}", afTopicDataDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateApplicationFormTopic", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> deactivateApplicationFormTopic(@RequestBody AFTopicDataDTO afTopicDataDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Application Form Topic: {} by: {}", afTopicDataDTO, credentialsDTO.getUserName());

        ApplicationFormDTO response = this.applicationFormService.deactivateApplicationFormTopic(afTopicDataDTO, credentialsDTO);

        LOG.info("END: Deactivate the Application Form Topic: {} by: {}", afTopicDataDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveUpdateFacilitySecurity", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveUpdateFacilitySecurity(@RequestBody AFSecurityDTO afSecurityDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Application Form facility security data  : {}, user {}", afSecurityDTO, credentialsDTO.getUserID());

        ApplicationFormDTO updatedApplicationForm = this.applicationFormService.saveUpdateFacilitySecurity(afSecurityDTO, credentialsDTO);

        LOG.info("END :Save or Update Application Form facility security data: {}, user {}", afSecurityDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedApplicationForm, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCribReports", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateCribReports(@RequestBody AFCribReportUpdateRQ afCribReportUpdateRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Crib Reports data  : {}, user {}", afCribReportUpdateRQ, credentialsDTO.getUserID());

        ApplicationFormDTO updatedApplicationForm = this.applicationFormService.saveOrUpdateCribReports(afCribReportUpdateRQ, credentialsDTO);

        LOG.info("END :Save or Update Crib Reports data: {}, user {}", afCribReportUpdateRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedApplicationForm, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateOptionalCribReports", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateOptionalCribReports(@RequestBody AFCribReportUpdateRQ afCribReportUpdateRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Optional Crib Reports data  : {}, user {}", afCribReportUpdateRQ, credentialsDTO.getUserID());

        ApplicationFormDTO resultDTO = this.applicationFormService.saveOrUpdateOptionalCribReports(afCribReportUpdateRQ, credentialsDTO);

        LOG.info("END :Save or Update Optional Crib Reports data: {}, user {}", afCribReportUpdateRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(resultDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCribAttachments", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateCribAttachments(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                                          @RequestParam("uploadRequestData") String uploadRQData) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        AFCribAttachmentUploadRQ updateRQ = gson.fromJson(uploadRQData, AFCribAttachmentUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload Crib Attachments : {} by user {}", updateRQ, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.saveOrUpdateCribAttachments(updateRQ, credentialsDTO);

        LOG.info("END : Upload Crib Attachments : {} by user {}", applicationFormDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateAFCribAttachment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> deactivateAFCribAttachment(@RequestBody AFCribAttachmentUploadRQ afCribAttachmentUploadRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Application Form Crib Attachments : {} by: {}", afCribAttachmentUploadRQ, credentialsDTO.getUserName());

        ApplicationFormDTO response = this.applicationFormService.deactivateAFCribAttachment(afCribAttachmentUploadRQ, credentialsDTO);

        LOG.info("END: Deactivate the Application Form Crib Attachments: {} by: {}", afCribAttachmentUploadRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateOtherBankDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateOtherBankDetails(@RequestBody AFOtherBankDetailsDTO otherBankDetailsDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Other Bank Details  : {}, user {}", otherBankDetailsDTO, credentialsDTO.getUserID());

        ApplicationFormDTO updatedApplicationForm = this.applicationFormService.saveOrUpdateOtherBankDetails(otherBankDetailsDTO, credentialsDTO);

        LOG.info("END :Save or Update Other Bank Details: {}, user {}", otherBankDetailsDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedApplicationForm, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateBorrowerGuarantor", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateBorrowerGuarantor(@RequestBody AFBorrowerGuarantorDTO borrowerGuarantorDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Borrower Guarantor Details : {}, user {}", borrowerGuarantorDTO, credentialsDTO.getUserID());

        ApplicationFormDTO updatedApplicationForm = this.applicationFormService.saveOrUpdateBorrowerGuarantor(borrowerGuarantorDTO, credentialsDTO);

        LOG.info("END : Save or Update Borrower Guarantor Details: {}, user {}", borrowerGuarantorDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedApplicationForm, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/removeBorrowerGuarantorDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> removeBorrowerGuarantorDetails(@RequestBody AFBorrowerGuarantorDTO borrowerGuarantorDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Remove Borrower Guarantor details : {} by: {}", borrowerGuarantorDTO, credentialsDTO.getUserName());

        ApplicationFormDTO response = this.applicationFormService.removeBorrowerGuarantorDetails(borrowerGuarantorDTO, credentialsDTO);

        LOG.info("END: Remove Borrower Guarantor details : {} by: {}", borrowerGuarantorDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/removeOtherBankDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> removeOtherBankDetails(@RequestBody AFOtherBankDetailsDTO otherBankDetailsDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Remove other bank details : {} by: {}", otherBankDetailsDTO, credentialsDTO.getUserName());

        ApplicationFormDTO response = this.applicationFormService.removeOtherBankDetails(otherBankDetailsDTO, credentialsDTO);

        LOG.info("END: Remove other bank details : {} by: {}", otherBankDetailsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateRiskRate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateRiskRate(@RequestBody AFRiskRateDTO afRiskRateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Risk Rate : {}, user {}", afRiskRateDTO, credentialsDTO.getUserID());

        ApplicationFormDTO updatedApplicationForm = this.applicationFormService.saveOrUpdateRiskRate(afRiskRateDTO, credentialsDTO);

        LOG.info("END : Save or Update Risk Rate : {}, user {}", afRiskRateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedApplicationForm, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/draftFacilityPaperByApplicationForm", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> draftFacilityPaperByApplicationForm(@RequestBody FacilityPaperGenerateRQ facilityPaperGenerateRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft Facility Paper from Application Form : {}, user {}", facilityPaperGenerateRQ, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.applicationFormService.draftFacilityPaperByApplicationForm(facilityPaperGenerateRQ, credentialsDTO);

        LOG.info("END : Draft Facility Paper from Application Form : {}, user {}", facilityPaperGenerateRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateApplicationFormStatus", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> updateApplicationFormStatus(@RequestBody ApplicationFormStatusChangeRQ applicationFormStatusChangeRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update Application Form : {}, user {}", applicationFormStatusChangeRQ, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.updateApplicationFormStatus(applicationFormStatusChangeRQ, credentialsDTO);

        LOG.info("END : Update Application Form : {}, user {}", applicationFormStatusChangeRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAFReturnUsersList", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<AFStatusHistoryDTO>> getAFReturnUsersList(@RequestBody ApplicationFormDTO applicationFormDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Application Form Return Users List : {} , user {}", applicationFormDTO, credentialsDTO.getUserID());

        List<AFStatusHistoryDTO> response = applicationFormService.getAFReturnUsersList(applicationFormDTO, credentialsDTO);

        LOG.info("END : Get Application Return Users List: {} , user {}", applicationFormDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateAFFacilityDisplayOrderAndStatus", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> updateAFFacilityDisplayOrderAndStatus(@RequestBody ApplicationFormFacilitiesDTO applicationFormFacilitiesDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update Application Form facility display order and status : {}, user {}", applicationFormFacilitiesDTO, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.updateAFFacilityDisplayOrderAndStatus(applicationFormFacilitiesDTO, credentialsDTO);

        LOG.info("END : Update Application Form facility display order and status: {}, user {}", applicationFormFacilitiesDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/uploadAFFacilityDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> uploadAFFacilityDocument(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                                       @RequestParam("uploadRequestData") String uploadRQData) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        AFFacilityDocumentUploadRQ updateRQ = gson.fromJson(uploadRQData, AFFacilityDocumentUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload Application Form Facility Document : {} by user {}", updateRQ, credentialsDTO.getUserID());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.uploadAFFacilityDocument(updateRQ, credentialsDTO);

        LOG.info("END : Upload Application Form Facility Document : {} by user {}", applicationFormDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateAFFacilityDocuments", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> deactivateAFFacilityDocuments(@RequestBody AFFacilityDocumentDTO afFacilityDocumentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Application From Facility document: {} by: {}", afFacilityDocumentDTO, credentialsDTO.getUserName());

        ApplicationFormDTO applicationFormDTO = this.applicationFormService.deactivateAFFacilityDocuments(afFacilityDocumentDTO, credentialsDTO);

        LOG.info("END: Deactivate the Application Form Facility document: {} by: {}", afFacilityDocumentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(applicationFormDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationFormsForTransfer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Set<ApplicationFormPageRSDTO>> getApplicationFormsForTransfer(@RequestBody ApplicationFormTransferRQ applicationFormDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form for transfer {}", credentialsDTO.getUserID());

        Set<ApplicationFormPageRSDTO> pageResponse = applicationFormService.getApplicationFormsForTransfer(applicationFormDTO);

        LOG.info("END : Search Application Form for transfer {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateAFComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDTO> saveOrUpdateAFComment(@RequestBody AFCommentDTO afCommentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Application Form User Comment : {}, user {}", afCommentDTO, credentialsDTO.getUserID());

        ApplicationFormDTO updatedApplicationForm = this.applicationFormService.saveOrUpdateAFComment(afCommentDTO, credentialsDTO);

        LOG.info("END : Save or Update Application Form User Comment : {}, user {}", afCommentDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedApplicationForm, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationFormDashboardCount", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationFormDashboardCountDTO> getApplicationFormDashboardCount(@RequestBody ApplicationFormDashboardRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        ApplicationFormDashboardCountDTO response = new ApplicationFormDashboardCountDTO();

        LOG.info("START : getApplicationFormDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        response = this.applicationFormService.getApplicationFormDashboardCount(searchRQ);

        LOG.info("END : getApplicationFormDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedApplicationFormDashboard", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<ApplicationFormDashboardDTO>> getPagedApplicationFormDashboard(@RequestBody ApplicationFormDashboardRQ applicationFormDashboardRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        Page<ApplicationFormDashboardDTO> pageResponse = new Page<>();

        LOG.info("START : getPagedApplicationFormDashboard {} : {}", credentialsDTO.getUserID(), applicationFormDashboardRQ);

        pageResponse = this.applicationFormService.getPagedApplicationFormDashboard(applicationFormDashboardRQ);

        LOG.info("END : getPagedApplicationFormDashboard {} ", credentialsDTO.getUserID(), applicationFormDashboardRQ);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedSearchApplicationForm", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<ApplicationFormPageRSDTO>> getPagedSearchApplicationForm(@RequestBody SearchApplicationFormRQ applicationFormSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        Page<ApplicationFormPageRSDTO> pageResponse = applicationFormService.getPagedSearchApplicationForm(applicationFormSearchRQ);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnnexureByApplicationFormID/{applicationFormID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<AFAnnexureDataDTO>> getAnnexureByApplicationFormID(@PathVariable Integer applicationFormID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        List<AFAnnexureDataDTO> afAnnexureDataList = applicationFormService.getAnnexureByApplicationFormID(applicationFormID, credentialsDTO);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(afAnnexureDataList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnnexureByAnnexureId/{annexureId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<AnnexureDTO> getAnnexureByAnnexureId(@PathVariable Integer annexureId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        AnnexureDTO afAnnexureDTO = applicationFormService.getAnnexureByAnnexureId(annexureId, credentialsDTO);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(afAnnexureDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnnexureByAnnexureDataId/{annexureDataId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<AnnexureDTO> getAnnexureByAnnexureDataId(@PathVariable Integer annexureDataId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        AnnexureDTO afAnnexureDTO = applicationFormService.getAnnexureByAnnexureDataId(annexureDataId, credentialsDTO);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(afAnnexureDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveAnnexureAnswer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AFAnnexureDataDTO> saveDataToAnnexure(@RequestBody AFAnnexureDataDTO afAnnexureDataDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        AFAnnexureDataDTO response = applicationFormService.saveDataToAnnexure(afAnnexureDataDTO, credentialsDTO);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateDataToAnnexure/{annexureDataId}", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AFAnnexureDataDTO> updateDataToAnnexure(@PathVariable Integer annexureDataId, @RequestBody AFAnnexureDataDTO afAnnexureDataDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        AFAnnexureDataDTO response = applicationFormService.updateDataToAnnexure(annexureDataId, afAnnexureDataDTO, credentialsDTO);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnnexureList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<AFAnnexureListDTO>> getAnnexureList() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        List<AFAnnexureListDTO> afAnnexureDTO = applicationFormService.getAnnexureList(credentialsDTO);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(afAnnexureDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getEnvironmentalRiskTree", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<EnvironmentalRiskDTO>> getEnvironmentalRiskTree() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Application Form {}", credentialsDTO.getUserID());

        List<EnvironmentalRiskDTO> riskDTOS = applicationFormService.getEnvironmentalRiskTree(credentialsDTO);

        LOG.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(riskDTOS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveEnvironmentalRiskCategory", headers = "Accept=application/json")
    public ResponseEntity<List<EnvironmentalRiskDataDTO>> saveEnvironmentalRiskCategory(@RequestBody EnvironmentalRiskDataReq environmentalRiskReq) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Environmental Risk Category {}", credentialsDTO.getUserID());

        List<EnvironmentalRiskDataDTO> result = applicationFormService.saveAFEnvironmentalRisk(environmentalRiskReq, credentialsDTO);

        LOG.info("END : Save Environmental Risk Category {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/removeAFEnvironmentalRisk/{applicationFormId}", headers = "Accept=application/json")
    public ResponseEntity<List<EnvironmentalRiskDataDTO>> removeAFEnvironmentalRisk(@PathVariable Integer applicationFormId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Remove Environmental Risk Categories {}", credentialsDTO.getUserID());

        List<EnvironmentalRiskDataDTO> result = applicationFormService.removeAFEnvironmentalRisk(applicationFormId, credentialsDTO);

        LOG.info("END : Remove Environmental Risk Categories {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/approveAFEnvironmentalRisk", headers = "Accept=application/json")
    public ResponseEntity<ApplicationFormDTO> approveAFEnvironmentalRisk(@RequestBody ApproveRiskScoreDTO approveRiskScoreDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Approved Environmental Risk Categories {}", credentialsDTO.getUserID());

        ApplicationFormDTO result = applicationFormService.approveAFEnvironmentalRisk(approveRiskScoreDTO, credentialsDTO);

        LOG.info("END : Approved Environmental Risk Categories {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getAFEnvironmentalRiskOpinion/{applicationFormId}", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> getAFEnvironmentalRiskOpinion(@PathVariable Integer applicationFormId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Environmental Risk Opinions {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = applicationFormService.getAFEnvironmentalRiskOpinion(applicationFormId);

        LOG.info("END : Get Environmental Risk Opinions {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveAFEnvironmentalRiskOpinion", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> saveAFEnvironmentalRiskOpinion(@RequestBody RiskOpinionDTO riskOpinionDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Environmental Risk Opinion {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = applicationFormService.saveAFEnvironmentalRiskOpinion(riskOpinionDTO, credentialsDTO);

        LOG.info("END : Save Environmental Risk Opinion {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveAFEnvironmentalRiskOpinionReply", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> saveAFEnvironmentalRiskOpinionReply(@RequestBody RiskOpinionReplyDTO riskOpinionReplyDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Environmental Risk Opinion Reply {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = applicationFormService.saveAFEnvironmentalRiskOpinionReply(riskOpinionReplyDTO, credentialsDTO);

        LOG.info("END : Save Environmental Risk Opinion Reply {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/removeAFEnvironmentalRiskOpinion", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> removeAFEnvironmentalRiskOpinion(@RequestBody RiskOpinionDTO riskOpinionDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Remove Environmental Risk Opinion {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = applicationFormService.removeAFEnvironmentalRiskOpinion(riskOpinionDTO);

        LOG.info("END : Remove Environmental Risk Opinion {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
