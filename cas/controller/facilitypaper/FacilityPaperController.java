package com.itechro.cas.controller.facilitypaper;

import com.google.gson.Gson;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import com.itechro.cas.model.domain.facilitypaper.facility.FPCustomerEvaluation;
import com.itechro.cas.model.domain.facilitypaper.facility.FPUPCTemplateComparisonDateDTO;
import com.itechro.cas.model.domain.facilitypaper.facility.FusTraceDTO;
import com.itechro.cas.model.dto.bcc.FPBccDTO;
import com.itechro.cas.model.dto.casmaster.CAUserDTO;
import com.itechro.cas.model.dto.casmaster.UpcTemplateDTO;
import com.itechro.cas.model.dto.casmaster.UserDaDTO;
import com.itechro.cas.model.dto.covenants.CusApplicableCovenantDTO;
import com.itechro.cas.model.dto.customer.CASCustomerBankDetailsDTO;
import com.itechro.cas.model.dto.customer.CustomerEvaluationDTO;
import com.itechro.cas.model.dto.customer.request.CustomerEvaluationDeleteRequestDTO;
import com.itechro.cas.model.dto.customer.request.CustomerEvaluationIdRequest;
import com.itechro.cas.model.dto.customer.response.CustomerEvaluationDeleteResponse;
import com.itechro.cas.model.dto.customer.response.CustomerEvaluationIdResponse;
import com.itechro.cas.model.dto.email.CAEmailDTO;
import com.itechro.cas.model.dto.esg.*;
import com.itechro.cas.model.dto.facility.FacilityAndSecurityCopyDTO;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import com.itechro.cas.model.dto.facility.FacilityPaperFacilityDTO;
import com.itechro.cas.model.dto.facility.FacilitySecurityDTO;
import com.itechro.cas.model.dto.facility.facilityreview.FacilityReviewDTO;
import com.itechro.cas.model.dto.facility.facilityreview.FacilityReviewSearchRQ;
import com.itechro.cas.model.dto.facilitypaper.*;
import com.itechro.cas.model.dto.facilitypaper.committee.CommitteeInquiryMasterDTO;
import com.itechro.cas.model.dto.facilitypaper.creditcalculator.CreditScheduleESBResponseDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummaryDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewSummarySearchRQ;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewerCommentDTO;
import com.itechro.cas.model.dto.facilitypaper.facilitypaperreview.FPReviewerCommentSearchRQ;
import com.itechro.cas.model.dto.facilitypaper.request.*;
import com.itechro.cas.model.dto.facilitypaper.response.ApplicationCovenantDeleteResponse;
import com.itechro.cas.model.dto.facilitypaper.response.ApplicationCovenantListResponseDTO;
import com.itechro.cas.model.dto.facilitypaper.response.ApplicationCovenantUpdateResponseDTO;
import com.itechro.cas.model.dto.facilitypaper.securityDocument.*;
import com.itechro.cas.model.dto.integration.request.finacle.GroupExposureRequest;
import com.itechro.cas.model.dto.facilitypaper.response.*;
import com.itechro.cas.model.dto.integration.response.BranchLoadResponseListDTO;
import com.itechro.cas.model.dto.lead.DigitalApplicationDTO;
import com.itechro.cas.model.dto.lead.DigitalApplicationReq;
import com.itechro.cas.model.dto.lead.LeadFacilityPaperStatusDetailDTO;
import com.itechro.cas.model.dto.master.BooleanValueDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.faclititypaper.CommitteeInqService;
import com.itechro.cas.service.faclititypaper.FacilityPaperService;
import com.itechro.cas.service.faclititypaper.SecurityDocumentService;
import com.itechro.cas.service.integration.IntegrationService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "${api.prefix}/facilityPaper")
public class FacilityPaperController extends BaseController {


    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperController.class);

    @Autowired
    private FacilityPaperService facilityPaperService;

    @Autowired
    private IntegrationService integrationService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private CommitteeInqService committeeInqService;

    @Autowired
    private SecurityDocumentService securityDocumentService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/uploadFacilityPaperDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> uploadLeadDocument(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                               @RequestParam("uploadRequestData") String uploadRQData) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        FPDocumentUploadRQ updateRQ = gson.fromJson(uploadRQData, FPDocumentUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload facility paper document : {} by user {}", updateRQ, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.uploadFacilityPaperDocument(updateRQ, credentialsDTO);

        LOG.info("END : Upload facility paper document : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/draftFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> draftFacilityPaper(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft facility paper : {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedDraft = this.facilityPaperService.draftFacilityPaper(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Draft facility paper: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDraft, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/draftFacilityPaperWithNonFinacleCustomer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> draftFacilityPaperWithNonFinacleCustomer(@RequestBody DraftFPWithNoneFincaleCustomerRQ draftFPWithNoneFincaleCustomerRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft facility paper with Non Finacle Customer : {}, user {}", draftFPWithNoneFincaleCustomerRQ, credentialsDTO.getUserID());

        FacilityPaperDTO updatedDraft = this.facilityPaperService.draftFacilityPaperWithNonFinacleCustomer(draftFPWithNoneFincaleCustomerRQ, credentialsDTO);

        LOG.info("END : Draft facility paper with Non Finacle Customer : {}, user {}", draftFPWithNoneFincaleCustomerRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDraft, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/draftFacilityPaperByLead", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> draftFacilityPaperByLead(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft facility paper by lead: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedDraft = this.facilityPaperService.draftFacilityPaperByLead(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Draft facility paper by lead: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDraft, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/draftAgentFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> draftAgentFacilityPaper(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft agent facility paper : {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedDraft = this.facilityPaperService.draftAgentFacilityPaper(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Draft agent facility paper: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDraft, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> saveOrUpdateFacilityPaper(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update facility paper Base Data : {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedFacilityPaper = this.facilityPaperService.saveOrUpdateFacilityPaper(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Update facility paper Base Data: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedFacilityPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFacilityPaperExposure", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateFacilityPaperExposure(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update facility paper exposure Data : {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedFacilityPaper = this.facilityPaperService.updateFacilityPaperExposure(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Update facility paper exposure Data: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedFacilityPaper, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/calculateFacilityPaperExposure", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> calculateFacilityPaperExposure(@RequestBody CalculateExposureRQ calculateExposureRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Calculate facility paper exposure Data : {}, user {}", calculateExposureRQ, credentialsDTO.getUserID());

        FacilityPaperDTO updatedFacilityPaper = this.facilityPaperService.calculateFacilityPaperExposure(calculateExposureRQ, credentialsDTO);

        LOG.info("END : Calculate facility paper exposure Data: {}, user {}", calculateExposureRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedFacilityPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateFPFacility", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> saveOrUpdateFacilityPaperFacility(@RequestBody FacilityDTO facilityDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update facility paper facility data : {}, user {}", facilityDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedFacilityPaper = this.facilityPaperService.saveOrUpdateFacility(facilityDTO, credentialsDTO);

        LOG.info("END : Update facility paper facility data: {}, user {}", facilityDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedFacilityPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFPFacilityDisplayOrderAndStatus", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateFPFacilityDisplayOrderAndStatus(@RequestBody FacilityPaperFacilityDTO facilityPaperFacilityDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update facility paper facility display order and status : {}, user {}", facilityPaperFacilityDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedFacilityPaper = this.facilityPaperService.updateFacilityDisplayOrderAndStatus(facilityPaperFacilityDTO, credentialsDTO);

        LOG.info("END : Update facility paper facility display order and status: {}, user {}", facilityPaperFacilityDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedFacilityPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/replicateFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> replicateFacilityPaper(@RequestBody FacilityPaperReplicationRQ replicationRQ) throws Exception {
        LOG.info("replicate method :controller");

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Replicate Facility paper : {}, user {}", replicationRQ, credentialsDTO.getUserID());

        FacilityPaperDTO replicated = this.facilityPaperService.replicateFacilityPaper(replicationRQ, credentialsDTO);
        LOG.info("END : Replicate Facility paper : {}, user {}", replicationRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(replicated, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityPaperDTO>> getPagedFacilityPaper(@RequestBody FacilityPaperSearchRQ facilityPaperSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Facility Paper {}", credentialsDTO.getUserID());

        if (credentialsDTO.isAgent()) {
            facilityPaperSearchRQ.setIntiatedUserName(credentialsDTO.getUserName());
        }

        Page<FacilityPaperDTO> pageResponse = facilityPaperService.getPagedFacilityPaperDTO(facilityPaperSearchRQ);

        LOG.info("END : Search Facility Paper {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedMyFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityPaperDTO>> getPagedMyFacilityPaper(@RequestBody FacilityPaperSearchRQ facilityPaperSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Page<FacilityPaperDTO> pageResponse = new Page<>();

        LOG.info("START : Search Facility Paper {}", credentialsDTO.getUserID());

        if (credentialsDTO.isAgent()) {
            pageResponse = facilityPaperService.getAgentPagedMyFacilityPaper(facilityPaperSearchRQ);
        } else {
            pageResponse = facilityPaperService.getPagedMyFacilityPaper(facilityPaperSearchRQ);
            if (facilityPaperSearchRQ.getIsInboxOnly() == 1) {
                pageResponse.getPageData().clear();
            }
        }


        LOG.info("END : Search Facility Paper {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getDashboardCount", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DashboardCountDTO> getDashboardCount(@RequestBody DashboardCountSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        DashboardCountDTO response = new DashboardCountDTO();

        LOG.info("START : Get dashboard count {} : {}", credentialsDTO.getUserID(), searchRQ);

        if (credentialsDTO.isAgent()) {
            response = facilityPaperService.getAgentDashboardCount(searchRQ);
        } else {
            response = facilityPaperService.getDashboardCount(searchRQ);
        }

        LOG.info("START : Get dashboard count {} : {}", credentialsDTO.getUserID(), searchRQ);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityPaperByID/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<FacilityPaperDTO> getFacilityPaperDTO(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get facility paper By ID : {}, user {} ", facilityPaperID, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.getFacilityPaperDTOByID(facilityPaperID, credentialsDTO);

        LOG.info("END : Get facility paper By ID : {}, user {} ", facilityPaperID, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/removeFacilityPaperByID/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<FacilityPaperRemoveRS> removeFacilityPaper(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Remove facility paper By ID : {}, user {} ", facilityPaperID, credentialsDTO.getUserID());

        FacilityPaperRemoveRS facilityPaperRemoveRS = this.facilityPaperService.removeFacilityPaper(facilityPaperID, credentialsDTO);

        LOG.info("END : Remove facility paper By ID : {}, user {} ", facilityPaperID, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperRemoveRS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditCasCustomer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditCasCustomer(@RequestBody CASCustomerUpdateDTO CASCustomerUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Add Edit Joining Party to the Facility Paper : {}, user {} ", CASCustomerUpdateDTO, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.addEditCasCustomer(CASCustomerUpdateDTO, credentialsDTO);

        LOG.info("END : Add Edit Joining Party to the Facility Paper : {}, user {} ", CASCustomerUpdateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditNonFinacleCasCustomer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditNonFinacleFPCustomer(@RequestBody JoinNonFinacleCustomerRQ joinNonFinacleCustomerRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Add Edit Non Finacle Joining Party to the Facility Paper : {}, user {} ", joinNonFinacleCustomerRQ, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.addEditNonFinacleCasCustomer(joinNonFinacleCustomerRQ, credentialsDTO);

        LOG.info("END : Add Edit Non Finacle Joining Party to the Facility Paper : {}, user {} ", joinNonFinacleCustomerRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/removeFPJoningParties", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> removeFPJoningParties(@RequestBody CASCustomerUpdateDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Remove Joining Party from the Facility Paper : {}, user {} ", updateDTO, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.removeFPJoningParties(updateDTO, credentialsDTO);

        LOG.info("END : Remove Joining Party from the Facility Paper : {}, user {} ", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditDirectorDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditDirectorDetails(@RequestBody DirectorDetailUpdateDTO directorDetailUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Add/Edit Directors of facility paper By ID : {}, user {} ", directorDetailUpdateDTO, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.addEditDirectorDetails(directorDetailUpdateDTO, credentialsDTO);

        LOG.info("END : Add/Edit Directors of facility paper By ID : {}, user {} ", directorDetailUpdateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditCompanyRao", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditCompanyRao(@RequestBody CompanyRoaUpdateDTO companyRoaUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Add/Edit Company ROA of facility paper By ID : {}, user {} ", companyRoaUpdateDTO, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.addEditCompanyRao(companyRoaUpdateDTO, credentialsDTO);

        LOG.info("END : Add/Edit Company ROA of facility paper By ID : {}, user {} ", companyRoaUpdateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateFpFacilitySupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deactivateFpFacilitySupportingDoc(@RequestBody FPDocumentDTO fpDocumentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Fp supporting document: {} by: {}", fpDocumentDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.deactivateFpFacilitySupportingDoc(fpDocumentDTO, credentialsDTO);

        LOG.info("END: Deactivate the Fp supporting document: {} by: {}", fpDocumentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateFpCreditRiskDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deactivateFpCreditRiskDoc(@RequestBody FPCreditRiskDocumentDTO fpCreditRiskDocumentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Fp Credit Risk document: {} by: {}", fpCreditRiskDocumentDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.deactivateFpCreditRiskDoc(fpCreditRiskDocumentDTO, credentialsDTO);

        LOG.info("END: Deactivate the Fp Credit Risk document: {} by: {}", fpCreditRiskDocumentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditCustomerOtherBankDetail", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditCustomerOtherBankDetail(@RequestBody CustomerOtherBankFacilityDetailUpdateDTO otherBankFacilityDetailUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Add/Edit Company other bank details : {}, user {} ", otherBankFacilityDetailUpdateDTO, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.addEditCustomerOtherBankDetail(otherBankFacilityDetailUpdateDTO, credentialsDTO);

        LOG.info("END : Add/Edit Company other bank details : {}, user {} ", otherBankFacilityDetailUpdateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/uploadFPCustomerCribDetail", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> uploadFPCustomerCribDetail(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                                       @RequestParam("uploadRequestData") String uploadRQData) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        CASCustomerCribDetailUploadRQ updateRQ = gson.fromJson(uploadRQData, CASCustomerCribDetailUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload crib document : {} by user {}", updateRQ, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.uploadFPCustomerCribDetail(updateRQ, credentialsDTO);

        LOG.info("END : Upload crib document : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateCribSupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deactivateCribSupportingDoc(@RequestBody CASCustomerCribDetailDTO CASCustomerCribDetailDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the crib supporting document: {} by: {}", CASCustomerCribDetailDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.deactivateFpCribSupportingDoc(CASCustomerCribDetailDTO, credentialsDTO);

        LOG.info("END: Deactivate the crib supporting document: {} by: {}", CASCustomerCribDetailDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateFacilityPaper(@RequestBody FacilityPaperUpdateDTO facilityPaperUpdateDTO) throws Exception {

        LOG.info("START: Update facility paper data: {} ", facilityPaperUpdateDTO);

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Update facility paper: {} by: {}", facilityPaperUpdateDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.updateFacilityPaper(facilityPaperUpdateDTO, credentialsDTO);

        LOG.info("END: Update facility paper: {} by: {}", facilityPaperUpdateDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditComment(@RequestBody FPCommentDTO fpCommentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Update Facility Paper Comment: {} by: {}", fpCommentDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.addEditComment(fpCommentDTO, credentialsDTO);

        LOG.info("END: Update Facility Paper Comment: {} by: {}", fpCommentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/approveOrRejectFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> approveOrRejectFacilityPaper(@RequestBody FPReviewerCommentDTO fpReviewerCommentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Update facility paper with Reviewer comment: {} by: {}", fpReviewerCommentDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.approveOrRejectFacilityPaper(fpReviewerCommentDTO, credentialsDTO);

        LOG.info("END: Update facility paper with Reviewer comment: {} by: {}", fpReviewerCommentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditCreditRiskComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditCreditRiskComment(@RequestBody CreditRiskCommentUpdateDTO riskCommentUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Add Or Update credit risk comment Facility Paper ID: {} by: {}", riskCommentUpdateDTO.getFacilityPaperID(), credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.addEditCreditRiskComment(riskCommentUpdateDTO, credentialsDTO);

        LOG.info("END: Add Or Update credit risk comment Facility Paper ID: {} by: {}", riskCommentUpdateDTO.getFacilityPaperID(), credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditCreditRiskReply", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditCreditRiskReply(@RequestBody FPCreditRiskReplyDTO fpCreditRiskReplyDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("Facility Paper Credit Risk Reply Save Or Update : {} by: {}", fpCreditRiskReplyDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.addEditCreditRiskReply(fpCreditRiskReplyDTO, credentialsDTO);

        LOG.info("Facility Paper Credit Risk Reply Save Or Update : {} by: {}", fpCreditRiskReplyDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateSecuritySummery", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> saveOrUpdateSecuritySummery(@RequestBody FPSecuritySummeryDTO fpSecuritySummeryDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save Or Update facility paper security summery: {} by: {}", fpSecuritySummeryDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.saveOrUpdateSecuritySummery(fpSecuritySummeryDTO, credentialsDTO);

        LOG.info("END: Save Or Update facility paper security summery: {} by: {}", fpSecuritySummeryDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getReviewCommentFromFpID", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FPReviewerCommentDTO>> getReviewCommentFromFpID(@RequestBody FPReviewerCommentSearchRQ fpReviewerCommentSearchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: getting FP review comments for : {} by: {}", fpReviewerCommentSearchRQ, credentialsDTO.getUserName());

        Page<FPReviewerCommentDTO> response = this.facilityPaperService.getReviewCommentFromFpID(fpReviewerCommentSearchRQ);

        LOG.info("END: getting FP review comments for : {} by: {}", fpReviewerCommentSearchRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getReviewCommentFromFPIDAndUpmID", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FPReviewerCommentDTO> getReviewCommentFromFPIDAndUpmID(@RequestBody FPReviewerCommentSearchRQ fpReviewerCommentSearchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: getting FP review comments for : {} by: {}", fpReviewerCommentSearchRQ, credentialsDTO.getUserName());

        FPReviewerCommentDTO response = this.facilityPaperService.getReviewCommentFromFPIDAndUpmID(fpReviewerCommentSearchRQ);

        LOG.info("END: getting FP review comments for : {} by: {}", fpReviewerCommentSearchRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //
    @ResponseExceptionHandler
    @RequestMapping(value = "/getRemarkDtoList/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<RemarkDTO>> getRemarkDtoList(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get remark list: {} by: {}", facilityPaperID, credentialsDTO.getUserName());

        List<RemarkDTO> response = this.facilityPaperService.getRemarkDtoList(facilityPaperID, credentialsDTO);
//        Collections.sort(response, new Comparator<RemarkDTO>() {
//            @Override
//            public int compare(RemarkDTO o1, RemarkDTO o2) {
//                return o2.getDateStr().compareTo(o1.getDateStr());
//            }
//        });

        if (response != null && !response.isEmpty()) {
            Collections.sort(response, Collections.reverseOrder());
        }

        LOG.info("END: get remark list: {} by: {}", facilityPaperID, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

/*
    @ResponseExceptionHandler
    @RequestMapping(value = "/getReviewCommentFromFpID", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPReviewerCommentDTO>> getReviewCommentFromFpID(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get review comment list: {} by: {}", facilityPaperID, credentialsDTO.getUserName());

//        List<>

        List<RemarkDTO> response = this.facilityPaperService.getRemarkDtoList(facilityPaperID, credentialsDTO);
//        Collections.sort(response, new Comparator<RemarkDTO>() {
//            @Override
//            public int compare(RemarkDTO o1, RemarkDTO o2) {
//                return o2.getDateStr().compareTo(o1.getDateStr());
//            }
//        });

        if (response != null && !response.isEmpty()) {
            Collections.sort(response, Collections.reverseOrder());
        }

        LOG.info("END: get remark list: {} by: {}", facilityPaperID, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
*/


    @ResponseExceptionHandler
    @RequestMapping(value = "/getUserDAByLoggedInUser/{userName}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<UserDaDTO> getUserDAByLoggedInUser(@PathVariable String userName) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get UseDa by logged user: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        UserDaDTO response = this.facilityPaperService.getUserDAByLoggedInUser(userName, credentialsDTO);

        LOG.info("START: get UseDa by logged user: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getActiveApprovedUpcTemplateDtoList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UpcTemplateDTO>> getActiveApprovedUpcTemplateDtoList() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get active approved UpcTemplates: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        List<UpcTemplateDTO> responseList = this.facilityPaperService.getActiveApprovedUpcTemplateDtoList();

        LOG.info("START: get active approved UpcTemplates: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditUPCSectionData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditUPCSectionData(@RequestBody UpcTemplateAddRQ upcTemplateAddRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Add Edit Upc Template : {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.addEditUPCSectionData(upcTemplateAddRQ, credentialsDTO);

        LOG.info("START: Add Edit Upc Template : {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAssignedFacilityPaperCount", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Long> getAssignedFacilityPaperCount(@RequestBody FacilityPaperSearchRQ facilityPaperSearchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get assigned facility paper count: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        Long response = this.facilityPaperService.getAssignedFacilityPaperCount(facilityPaperSearchRQ);

        LOG.info("END: get assigned facility paper count: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/removeUpcSectionData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> removeUpcSectionData(@RequestBody UpcTemplateAddRQ upcTemplateAddRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: remove upc section data  : {} by : {}", credentialsDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.removeUpcSectionData(upcTemplateAddRQ, credentialsDTO);

        LOG.info("START: remove upc section data: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveUpdateFacilitySecurity", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> saveUpdateFacilitySecurity(@RequestBody FacilitySecurityDTO facilitySecurityDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update facility paper facility security data  : {}, user {}", facilitySecurityDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedFacilityPaper = this.facilityPaperService.saveUpdateFacilitySecurity(facilitySecurityDTO, credentialsDTO);

        LOG.info("END :Save or Update facility paper facility security data: {}, user {}", facilitySecurityDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedFacilityPaper, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilityPaperReviewSummary", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FPReviewSummaryDTO>> getPagedFacilityPaperReviewSummary(@RequestBody FPReviewSummarySearchRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get paged Facility Paper Summary: {} by user {}", searchRQ, credentialsDTO.getUserID());

        Page<FPReviewSummaryDTO> fpSummaryReportDTOS = this.facilityPaperService.getPagedFacilityPaperReviewSummary(searchRQ);

        LOG.info("END : Get paged Facility Paper Summary: {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(fpSummaryReportDTOS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilitiesForReview", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityReviewDTO>> getPagedFacilitiesForReview(@RequestBody FacilityReviewSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Paged Facilities for facility review : {} , user {}", searchRQ, credentialsDTO.getUserID());

        Page<FacilityReviewDTO> Response = facilityPaperService.getPagedFacilitiesForReview(searchRQ);

        LOG.info("END : Get Paged Facilities for facility review : {} , user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(Response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getOwnApprovedPagedFacilityPapers", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityReviewDTO>> getOwnApprovedPagedFacilityPapers(@RequestBody FacilityReviewSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Paged Own Facilities for facility review : {} , user {}", searchRQ, credentialsDTO.getUserID());

        Page<FacilityReviewDTO> Response = facilityPaperService.getPagedFacilitiesForReview(searchRQ);

        LOG.info("END : Get Paged Own Facilities for facility review : {} , user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(Response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateReviewComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FPReviewerCommentDTO> saveOrUpdateReviewComment(@RequestBody FPReviewerCommentDTO fpReviewerCommentDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update review comment : {} , user {}", fpReviewerCommentDTO.getFpReviewerCommentID(), credentialsDTO.getUserID());

        FPReviewerCommentDTO Response = facilityPaperService.saveOrUpdateReviewComment(fpReviewerCommentDTO, credentialsDTO);

        LOG.info("END : Save or Update review comment : {} , user {}", fpReviewerCommentDTO.getFpReviewerCommentID(), credentialsDTO.getUserID());

        return new ResponseEntity<>(Response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/isAbleToReturnFacilityPaperToAgent", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BooleanValueDTO> isAbleToReturnFacilityPaperToAgent(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get is Able To Return Facility Paper To Agent : {} , user {}", facilityPaperDTO, credentialsDTO.getUserID());

        BooleanValueDTO response = facilityPaperService.isAbleToReturnFacilityPaperToAgent(facilityPaperDTO, credentialsDTO);

        LOG.info("END :  Get is Able To Return Facility Paper To Agent: {} , user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFPDirectReturnUsersList", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<FPStatusHistoryDTO>> getFPDirectReturnUsersList(@RequestBody FacilityPaperDTO facilityPaperDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Facility Paper Direct Return Users List : {} , user {}", facilityPaperDTO, credentialsDTO.getUserID());

        List<FPStatusHistoryDTO> response = facilityPaperService.getFPDirectReturnUsersList(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Get Facility Paper Direct Return Users List: {} , user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilityPaperForTransfer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> getPagedFacilityPaperForTransfer(@RequestBody FacilityPaperSearchRQ facilityPaperSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Facility Paper for transfer {}", credentialsDTO.getUserID());

        FacilityPaperDTO response = facilityPaperService.getPagedFacilityPaperForTransfer(facilityPaperSearchRQ);

        LOG.info("END : Search Facility Paper for transfer {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFacilityPaperOutstandingDate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateFacilityPaperOutstandingDate(@RequestBody FacilityPaperUpdateDTO facilityPaperUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Facility Paper update Outstanding Date: {} : {}", facilityPaperUpdateDTO, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.updateFacilityPaperOutstandingDate(facilityPaperUpdateDTO, credentialsDTO);

        LOG.info("END: Facility Paper update Outstanding Date : {} by: {}", facilityPaperUpdateDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityPaperHistory", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<LeadFacilityPaperStatusDetailDTO>> getFacilityPaperHistory(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Facility paper status history : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        List<LeadFacilityPaperStatusDetailDTO> result = this.facilityPaperService.getFacilityPaperHistory(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Facility Paper status history : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateCasCustomerDTO", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateCasCustomerDTO(@RequestBody CASCustomerUpdateRQ casCustomerUpdateRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("Facility Paper Update Cas Customer Details : {} by: {}", casCustomerUpdateRQ, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.updateCasCustomerDTO(casCustomerUpdateRQ, credentialsDTO);

        LOG.info("Facility Paper Update Cas Customer Details : {} by: {}", casCustomerUpdateRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilityPaperHistoryWithUPCTemplateDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FPPreviousUPCTemplateRS>> getPagedFacilityPaperHistoryWithUPCTemplateDetails(@RequestBody FPPreviousUPCTemplateRQ fpPreviousUPCTemplateRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("Facility Paper Get Previous UPC Templates : {} by: {}", fpPreviousUPCTemplateRQ, credentialsDTO.getUserName());

        Page<FPPreviousUPCTemplateRS> response = this.facilityPaperService.getPagedFacilityPaperHistoryWithUPCTemplateDetails(fpPreviousUPCTemplateRQ, credentialsDTO);

        LOG.info("Facility Paper Get Previous UPC Templates : {} by: {}", fpPreviousUPCTemplateRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/copyUPCSectionData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> copyUPCSectionData(@RequestBody UpcTemplateAddRQ upcTemplateAddRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get active approved UpcTemplates: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.copyUPCSectionData(upcTemplateAddRQ, credentialsDTO);

        LOG.info("START: get active approved UpcTemplates: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCribReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> saveOrUpdateCribReport(@RequestBody CASCustomerCribReportDTO cribReportDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save Crib Report: {} by: {}", cribReportDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.saveOrUpdateCribReport(cribReportDTO, credentialsDTO);

        LOG.info("END: Save Crib Report: {} by: {}", cribReportDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCreditCalculatedFacilitiesESBResponseStatus", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<CreditScheduleESBResponseDTO>> getCreditCalculatedFacilitiesESBResponseStatus(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Credit Calculated Facilities ESB Response Status : {} by: {}", facilityPaperDTO, credentialsDTO.getUserName());

        List<CreditScheduleESBResponseDTO> response = this.facilityPaperService.getCreditCalculatedFacilitiesESBResponseStatus(facilityPaperDTO, credentialsDTO);

        LOG.info("END: Get Credit Calculated Facilities ESB Response Status : {} by: {}", facilityPaperDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSearchedFacilityPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityPaperDTO>> getSearchedFacilityPaper(@RequestBody SearchFacilityPaperRQ searchFacilityPaperRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Facility Paper {}", credentialsDTO.getUserID());

        if (credentialsDTO.isAgent()) {
            searchFacilityPaperRQ.setIntiatedUserName(credentialsDTO.getUserName());
        }

        Page<FacilityPaperDTO> pageResponse = facilityPaperService.getSearchedFacilityPaperDTO(searchFacilityPaperRQ);

        LOG.info("END : Search Facility Paper {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    //get the risk department code
    @ResponseExceptionHandler
    @RequestMapping(value = "/getRiskDivCode", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getRiskDivCode() throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        String value = facilityPaperService.getRiskDivCode();
        LOG.info("START : risk code: {}", value);

        return new ResponseEntity<>(value, HttpStatus.OK);
    }


    //return the risk opinion history
    //develop
    @ResponseExceptionHandler
    @RequestMapping(value = "/getRiskCommentList/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPCreditRiskCommentHistoryDTO>> getRiskOpinionHistory(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: getting FP risk comments for : {} by: {}", facilityPaperID, credentialsDTO.getUserName());

        List<FPCreditRiskCommentHistoryDTO> response = this.facilityPaperService.getRiskCommentList(facilityPaperID, credentialsDTO);

        if (response != null && !response.isEmpty()) {
            Collections.sort(response, Collections.reverseOrder(Comparator.comparing(FPCreditRiskCommentHistoryDTO::getModifiedDateStr)));
        }

        LOG.info("END: getting FP risk comments for : {} by: {}", facilityPaperID, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCIFDetails/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPReviewCIFDetailsDTO>> getCIFDEtails(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: getting FP risk comments for : {} by: {}", facilityPaperID, credentialsDTO.getUserName());

        List<FPReviewCIFDetailsDTO> response = this.facilityPaperService.getCIFDetails(facilityPaperID, credentialsDTO);


        if (response != null && !response.isEmpty()) {
            Collections.sort(response);
        }

        LOG.info("END: getting FP risk comments for : {} by: {}", facilityPaperID, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCIFID", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Object> saveOrUpdateCIFID(@Validated @RequestBody CustomerEvaluationIdRequest request) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: getting FP customer evaluation for : {} by: {}", credentialsDTO.getUserName());
        LOG.info("REQUEST+++++++++++ {}", request);

        FPCustomerEvaluation fpCustomerEvaluation = modelMapper.map(request, FPCustomerEvaluation.class);
        FPCustomerEvaluation saveCIFId = facilityPaperService.saveAndUpdateCIFId(fpCustomerEvaluation, credentialsDTO);
        CustomerEvaluationIdResponse customerEvaluationIdResponse = modelMapper.map(saveCIFId, CustomerEvaluationIdResponse.class);
        LOG.info("FPCustomerEvaluation+++++++++++ {}", customerEvaluationIdResponse);
        //fpCustomerEvaluationDao.save(fpCustomerEvaluation);
        return new ResponseEntity<>(customerEvaluationIdResponse, HttpStatus.CREATED);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/findEvaluationById/{facilityPaperId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<CustomerEvaluationIdRequest> findEvaluationById(@PathVariable Integer facilityPaperId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        CustomerEvaluationIdRequest customerEvaluationIdRequest = this.facilityPaperService.findEvaluationById(facilityPaperId, credentialsDTO);

        LOG.info("FPCustomerEvaluation+++++++++++ {}", customerEvaluationIdRequest);

        return new ResponseEntity<>(customerEvaluationIdRequest, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/findEvaluationById2/{facilityPaperId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerEvaluationDTO>> findEvaluationById2(@PathVariable Integer facilityPaperId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<CustomerEvaluationDTO> customerEvaluationIdRequest = this.facilityPaperService.findEvaluationById2(facilityPaperId, credentialsDTO);

        LOG.info("FPCustomerEvaluation+++++++++++ {}", customerEvaluationIdRequest);

        return new ResponseEntity<>(customerEvaluationIdRequest, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deleteCustomerEvaluation", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CustomerEvaluationDeleteResponse> deleteEvaluation(@RequestBody CustomerEvaluationDeleteRequestDTO customerEvaluationDeleteRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        Integer deleteEvaluationId = facilityPaperService.deleteEvaluationForm(customerEvaluationDeleteRequestDTO);

        CustomerEvaluationDeleteResponse customerEvaluationDeleteResponse = new CustomerEvaluationDeleteResponse();

        customerEvaluationDeleteResponse.setId(deleteEvaluationId);

        return new ResponseEntity<>(customerEvaluationDeleteResponse, HttpStatus.OK);

    }

//    @RequestMapping(value = "/saveApplicationCovenantDetails", headers = "Accept=application/json", method = RequestMethod.POST)
//    public ResponseEntity<Object> saveApplicationCovenant(@Validated @RequestBody ApplicationCovenantReqDTO request) throws AppsException {
//
//        CredentialsDTO credentialsDTO = getCredentialsDTO();
//        Date date = new Date();
//
//        LOG.info("START: Save or update customer: {} by user {}", request, credentialsDTO.getUserName());
//
//        List<ApplicationLevelCovenant> savedCovenants = facilityPaperService.saveOrUpdateApplicationCovenant(request, credentialsDTO);
//
//        List<ApplicationCovenantDetailsDTO> applicationCovenantDetailsDTOs = new ArrayList<>();
//
//        ApplicationCovenantPostDTO applicationCovenantSaveResponseDTOS = new ApplicationCovenantPostDTO();
//
//        for (ApplicationLevelCovenant savedCovenant : savedCovenants) {
//            ApplicationCovenantDetailsDTO applicationCovenantDetailsDTO = new ApplicationCovenantDetailsDTO();
//            applicationCovenantDetailsDTO.setCovenant_Code(savedCovenant.getCovenant_Code());
//            applicationCovenantDetailsDTO.setCovenant_Frequency(savedCovenant.getCovenant_Frequency());
//            applicationCovenantDetailsDTO.setCovenant_Description(savedCovenant.getCovenant_Description());
//            applicationCovenantDetailsDTO.setCovenant_Due_Date(savedCovenant.getCovenant_Due_Date());
//            applicationCovenantDetailsDTO.setDisbursementType(savedCovenant.getDisbursementType());
//            applicationCovenantDetailsDTO.setCustId(savedCovenant.getCustomerFinacleID());
//            applicationCovenantDetailsDTO.setCasReference(savedCovenant.getFacilityPaper().getFpRefNumber());
//            applicationCovenantDetailsDTO.setCreatedUserDisplayName(savedCovenant.getCreatedUserDisplayName());
//            applicationCovenantDetailsDTO.setCreatedBy(savedCovenant.getCreatedBy());
//            applicationCovenantDetailsDTO.setCreatedDate(savedCovenant.getCreatedDate());
//            applicationCovenantDetailsDTO.setApplicationCovenantId(savedCovenant.getApplicationCovenantId());
//
//
//            applicationCovenantSaveResponseDTOS.setApplicationCovenantId(savedCovenant.getApplicationCovenantId());
//
//            List<ApplicationCovenantFacilityDTO> applicationCovenantFacilityDTOS = new ArrayList<>();
//            for (FacilityCovenantFacilities facility : savedCovenant.getFacilityCovenantFacilitiesSet()) {
//                ApplicationCovenantFacilityDTO facilityDTO = new ApplicationCovenantFacilityDTO();
//                facilityDTO.setFacilityID(facility.getFacility().getFacilityID());
//                facilityDTO.setFacilityRefCode(facility.getFacilityRefCode());
//                facilityDTO.setCreditFacilityTemplateID(facility.getCreditFacilityTemplateID());
//                facilityDTO.setCreditFacilityName(facility.getCreditFacilityName());
//                facilityDTO.setFacilityCurrency(facility.getFacilityCurrency());
//                facilityDTO.setFacilityAmount(facility.getFacilityAmount());
//                facilityDTO.setDisplayOrder(facility.getDisplayOrder());
//                applicationCovenantFacilityDTOS.add(facilityDTO);
//            }
//
//            applicationCovenantDetailsDTO.setApplicationCovenantFacilityDTOS(applicationCovenantFacilityDTOS);
//            applicationCovenantDetailsDTOs.add(applicationCovenantDetailsDTO);
//        }
//
//
//        applicationCovenantSaveResponseDTOS.setRequestUUID(request.getRequestUUID());
//        applicationCovenantSaveResponseDTOS.setCustId(request.getCustId());
//        applicationCovenantSaveResponseDTOS.setCasReference(request.getCasReference());
//        applicationCovenantSaveResponseDTOS.setCreatedUserDisplayName(request.getCreatedUserDisplayName());
//        applicationCovenantSaveResponseDTOS.setCreatedBy(request.getCreatedBy());
//        applicationCovenantSaveResponseDTOS.setCreatedDate(date);
//        applicationCovenantSaveResponseDTOS.setCovValue(applicationCovenantDetailsDTOs);
//
//        return new ResponseEntity<>(applicationCovenantSaveResponseDTOS, HttpStatus.CREATED);
//    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationCovenantListByFpRefNumber/{fpRefNumber}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<ApplicationCovenantListResponseDTO>> getApplicationCovenantListByFpRefNumber(@PathVariable String fpRefNumber) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<ApplicationCovenantListResponseDTO> covenants = facilityPaperService.getApplicationCovenantList(fpRefNumber, credentialsDTO);

        List<ApplicationCovenantListResponseDTO> applicationLevelCovenants = covenants.stream().map(facilityCovenants -> modelMapper.map(facilityCovenants, ApplicationCovenantListResponseDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(applicationLevelCovenants, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/deleteApplicationCovenant/{applicationCovenantId}/{createdUserDisplayName}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<ApplicationCovenantDeleteResponse> deleteApplicationCovenant(@PathVariable Integer applicationCovenantId, @PathVariable String createdUserDisplayName) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        Integer deleteApplicationCovenantId = facilityPaperService.deleteApplicationCovenant(applicationCovenantId, createdUserDisplayName, credentialsDTO);

        ApplicationCovenantDeleteResponse applicationCovenantDeleteResponse = new ApplicationCovenantDeleteResponse();

        applicationCovenantDeleteResponse.setApplicationCovenantId(deleteApplicationCovenantId);

        return new ResponseEntity<>(applicationCovenantDeleteResponse, HttpStatus.OK);
    }

    @RequestMapping(value = "/uploadCreditRiskDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> uploadCreditRiskDocument(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                                     @RequestParam("uploadRequestData") String uploadRQData) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        FPCreditRiskDocumentUploadRQ updateRQ = gson.fromJson(uploadRQData, FPCreditRiskDocumentUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload facility paper Credit Risk document : {} by user {}", updateRQ, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.uploadCreditRiskDocument(updateRQ, credentialsDTO);

        LOG.info("END : Upload facility paper Credit Risk document : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addEditShareHolderDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addEditShareHolderDetails(@RequestBody ShareHolderDetailUpdateDTO shareHolderDetailUpdateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Add/Edit Shareholders of facility paper By ID : {}, user {} ", shareHolderDetailUpdateDTO, credentialsDTO.getUserID());

        FacilityPaperDTO response = this.facilityPaperService.addEditShareHolderDetails(shareHolderDetailUpdateDTO, credentialsDTO);

        LOG.info("END : Add/Edit Shareholders of facility paper By ID : {}, user {} ", shareHolderDetailUpdateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addNewCreditRiskComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> addNewCreditRiskComment(@RequestBody CreditRiskCommentAddDTO creditRiskCommentAddDTO) throws Exception {


        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Add facility paper credit risk comment: {} by: {}", creditRiskCommentAddDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.addNewCreditRiskComment(creditRiskCommentAddDTO, credentialsDTO);

        LOG.info("END: Add facility paper credit risk comment: {} by: {}", creditRiskCommentAddDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityDocumentElementList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPDocumentElementDTO>> getFacilityDocumentElementList() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get facility document element list , user {}", credentialsDTO.getUserID());

        List<FPDocumentElementDTO> facilityDocumentElementeList = facilityPaperService.getFacilityDocumentElements();

        LOG.info("END : get facility document element list data : {} , size {}", facilityDocumentElementeList, facilityDocumentElementeList.size());

        return new ResponseEntity<>(facilityDocumentElementeList, HttpStatus.OK);


    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateSecurityDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FPSecurityDocumentDTO> saveOrUpdateSecurityDocument(@RequestBody FPSecurityDocumentDTO fpSecurityDocumentDTO) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("fpSecurityDocument:  user {}", credentialsDTO.getUserID());

        FPSecurityDocumentDTO updateFpSecurityDocumentDTO = facilityPaperService.saveOrUpdateSecurityDocument(fpSecurityDocumentDTO, credentialsDTO);

        LOG.info("END : saveOrUpdateSecurityDocument:  user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(updateFpSecurityDocumentDTO, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getSecurityDocumentHistory/{securityDocumentID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPSecurityDocumentDTO>> getSecurityDocumentHistory(@PathVariable Integer securityDocumentID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("getSecurityDocumentHistory:  user {}", credentialsDTO.getUserID());

        LOG.info("getSecurityDocumentHistory:  securityDocumentID {}", securityDocumentID);

        List<FPSecurityDocumentDTO> response = this.facilityPaperService.getSecurityDocumentHistory(securityDocumentID);

        LOG.info("END : getSecurityDocumentHistory:  user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSecurityDocumentElements/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPDocumentElementDTO>> getSecurityDocumentElements(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("getSecurityDocumentElements:  user {}", credentialsDTO.getUserID());
        LOG.info("getSecurityDocumentElements:  facilityPaperID {}", facilityPaperID);

        List<FPDocumentElementDTO> response = this.facilityPaperService.getSecurityDocumentElements(facilityPaperID);

        LOG.info("END : getSecurityDocumentElements:  user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/deactivateFpCreditRiskComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deactivateFpCreditRiskComment(@RequestBody FPCreditRiskCommentDTO fpCreditRiskCommentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Fp Credit Risk Comment: {} by: {}", fpCreditRiskCommentDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.deactivateFpCreditRiskComment(fpCreditRiskCommentDTO, credentialsDTO);

        LOG.info("END: Deactivate the Fp Credit Risk Comment: {} by: {}", fpCreditRiskCommentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFacilityCovenant", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ApplicationCovenantUpdateResponseDTO> updateApplicationCovenant(@Validated @RequestBody ApplicationCovenantUpdateDTO request) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : update facility covenant: {} by user {}", request, credentialsDTO.getUserID());

        ApplicationLevelCovenant applicationLevelCovenant = modelMapper.map(request, ApplicationLevelCovenant.class);

        ApplicationLevelCovenant updateApplicationCovenant = facilityPaperService.updateApplicationLevelCovenant(request);

        ApplicationCovenantUpdateResponseDTO applicationCovenantSaveResponseDTOS = modelMapper.map(updateApplicationCovenant, ApplicationCovenantUpdateResponseDTO.class);

        return new ResponseEntity<>(applicationCovenantSaveResponseDTOS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationCovenantByID/{applicationCovenantId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<ApplicationCovenantUpdateResponseDTO> viewApplicationCovenantById(@PathVariable int applicationCovenantId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        ApplicationCovenantListResponseDTO applicationLevelCovenant = facilityPaperService.findApplicationCovenantById(applicationCovenantId, credentialsDTO);

        ApplicationCovenantUpdateResponseDTO applicationCovenantUpdateResponseDTO = modelMapper.map(applicationLevelCovenant, ApplicationCovenantUpdateResponseDTO.class);

        return new ResponseEntity<>(applicationCovenantUpdateResponseDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationCovenantByFacilityID/{facilityID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FacilityCovenantDetailsDTO>> getFacilityCovenantsByFacilityId(@PathVariable int facilityID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<FacilityCovenantDetailsDTO> applicationCovenantDetailsDTO = facilityPaperService.getFacilityCovenantsByFacilityId(facilityID, credentialsDTO);

        return new ResponseEntity<>(applicationCovenantDetailsDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityCovenantList/{fpRefNumber}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FinalDTO>> getFacilityCovenantList(@PathVariable String fpRefNumber) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<FinalDTO> covenants = facilityPaperService.getFacilityCovenantList(fpRefNumber, credentialsDTO);

        //List<FacilityCovenantReqDTO> applicationLevelCovenants = covenants.stream().map(facilityCovenants -> modelMapper.map(facilityCovenants, FacilityCovenantReqDTO.class)).collect(Collectors.toList());

        return new ResponseEntity<>(covenants, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getDocumentSectionContent/{facilityPaperID}/{secId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<String> getDocumentContent(@PathVariable int facilityPaperID, @PathVariable int secId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.getFacilityPaperDTOByID(facilityPaperID, credentialsDTO);
        BranchLoadResponseListDTO responseListDTO = integrationService.getBranchList(credentialsDTO);

        String branchName = "";
        if (!facilityPaperDTO.getBranchCode().isEmpty()) {
            branchName = responseListDTO.getBranchResponse(facilityPaperDTO.getBranchCode()).getBranchName();
        }
        String response = facilityPaperService.getDocumentContent(facilityPaperDTO, secId, branchName);
        LOG.info("content response", response);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/copyFacilities", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> copyFacilities(@RequestBody FacilityAndSecurityCopyDTO facilityDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : copy facilities for : {}, user {}", facilityDTO, credentialsDTO.getUserID());

        FacilityPaperDTO copyFacilityDTO = this.facilityPaperService.copyFacilities(facilityDTO, credentialsDTO);

        LOG.info("END : copy facilities for: {}, user {}", facilityDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(copyFacilityDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFacilityPaperType", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateFacilityPaperType(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft facility paper : {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedDraft = this.facilityPaperService.updateFacilityPaperType(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Draft facility paper: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDraft, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCommitteePaperDashboardCount", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CommitteePaperDashboardCountDTO> getCommitteePaperDashboardCount(@RequestBody CommitteePaperDashboardRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        CommitteePaperDashboardCountDTO response = new CommitteePaperDashboardCountDTO();

        LOG.info("START : getCommitteePaperDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        response = this.facilityPaperService.getCommitteePaperDashboardCount(searchRQ);

        LOG.info("END : getCommitteePaperDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedCommitteePaperDashboard", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<CommitteePaperDashboardDTO>> getPagedCommitteePaperDashboard(@RequestBody CommitteePaperDashboardRQ committeePaperDashboardRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        Page<CommitteePaperDashboardDTO> pageResponse = new Page<>();

        LOG.info("START : getPagedCommitteePaperDashboard {} : {}", credentialsDTO.getUserID(), committeePaperDashboardRQ);

        pageResponse = this.facilityPaperService.getPagedCommitteePaperDashboard(committeePaperDashboardRQ);

        LOG.info("Response: {} ", pageResponse);
        LOG.info("END : getPagedCommitteePaperDashboard {} ", credentialsDTO.getUserID(), committeePaperDashboardRQ);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateCommitteeStatusHistory", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CommitteeStatusHistoryDTO> updateCommitteeStatusHistory(@RequestBody CommitteeStatusHistoryDTO committeeStatusHistoryDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: updateCommitteeStatusHistory: {} by: {}", committeeStatusHistoryDTO, credentialsDTO.getUserName());

        CommitteeStatusHistoryDTO response = this.facilityPaperService.updateCommitteeStatusHistory(committeeStatusHistoryDTO, credentialsDTO);

        LOG.info("END: updateCommitteeStatusHistory: {} by: {}", response, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getBCCPaperDashboardCount", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BCCPaperDashboardCountDTO> getBCCPaperDashboardCount(@RequestBody CommitteePaperDashboardRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        BCCPaperDashboardCountDTO response = new BCCPaperDashboardCountDTO();

        LOG.info("START : getBCCPaperDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        response = this.facilityPaperService.getBCCPaperDashboardCount(searchRQ);

        LOG.info("END : getBCCPaperDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedBCCPaperDashboard", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<CommitteePaperDashboardDTO>> getPagedBCCPaperDashboard(@RequestBody CommitteePaperDashboardRQ committeePaperDashboardRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        Page<CommitteePaperDashboardDTO> pageResponse = new Page<>();

        LOG.info("START : getPagedBCCPaperDashboard {} : {}", credentialsDTO.getUserID(), committeePaperDashboardRQ);

        pageResponse = this.facilityPaperService.getPagedBCCPaperDashboard(committeePaperDashboardRQ);

        LOG.info("Response: {} ", pageResponse);
        LOG.info("END : getPagedCommitteePaperDashboard {} ", credentialsDTO.getUserID(), committeePaperDashboardRQ);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    //submit BCC decision and authorize BCC Paper
    @ResponseExceptionHandler
    @RequestMapping(value = "/updateBccDTO", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FPBccDTO> updateBccDTO(@RequestBody FPBccDTO fpBccDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: updateBccDTO: {} by: {}", fpBccDTO, credentialsDTO.getUserName());

        FPBccDTO response = this.facilityPaperService.updateBccDTO(fpBccDTO, credentialsDTO);

        LOG.info("END: updateBccDTO: {} by: {}", response, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFPUsersInvolved/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPStatusHistoryDTO>> getFPUsersInvolved(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("getFPUsersInvolved:  user {}", credentialsDTO.getUserID());

        LOG.info("getFPUsersInvolved:  facilityPaperID {}", facilityPaperID);

        List<FPStatusHistoryDTO> response = this.facilityPaperService.getFPUsersInvolved(facilityPaperID);

        LOG.info("END : getFPUsersInvolved:  user list {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFPCommitteeSignatureList/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPCommitteeSignatureDTO>> getFPCommitteeSignatureList(@PathVariable Integer facilityPaperID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("getFPCommitteeSignatureList:  user {}", credentialsDTO.getUserID());

        LOG.info("getFPCommitteeSignatureList:  facilityPaperID {}", facilityPaperID);

        List<FPCommitteeSignatureDTO> response = this.facilityPaperService.getFPCommitteeSignatureList(facilityPaperID);

        LOG.info("END : getFPCommitteeSignatureList:  user list {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveViewFusTrace", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveFusTraceView(@RequestBody FusTraceDTO fusTraceDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Fus Trace View: {} , user {} ", fusTraceDTO, credentialsDTO);

        Boolean result = facilityPaperService.saveFusTraceView(fusTraceDTO, credentialsDTO);

        LOG.info("END : Save Fus Trace View: {} , user {} ", result, credentialsDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFusTraceDataById", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<UPCTemplateCommentHistoryDTO>> getFusTraceDataById(@RequestBody FusTraceDTO fusTraceDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET Active Version By UPC Template Comparison Repository: {} , user {} ", fusTraceDTO, credentialsDTO);

        List<UPCTemplateCommentHistoryDTO> upcTemplateCommentHistoryDTOList =
                facilityPaperService.getFusTraceDataService(fusTraceDTO, credentialsDTO);

        LOG.info("END : Get UPC Template Comparison Comment History Repository: {} , user {} ", upcTemplateCommentHistoryDTOList, credentialsDTO);

        return new ResponseEntity<>(upcTemplateCommentHistoryDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/upcTemplateComparison/getUPCTemplateComparisonCommentHistory", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<UPCTemplateCommentHistoryDTO>> getFusTraceDataController(@RequestBody FusTraceDTO fusTraceDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : GET Active Version By UPC Template Comparison Repository: {} , user {} ", fusTraceDTO, credentialsDTO);

        List<UPCTemplateCommentHistoryDTO> upcTemplateCommentHistoryDTOList =
                facilityPaperService.getFusTraceDataService(fusTraceDTO, credentialsDTO);

        LOG.info("END : Get UPC Template Comparison Comment History Repository: {} , user {} ", upcTemplateCommentHistoryDTOList, credentialsDTO);

        return new ResponseEntity<>(upcTemplateCommentHistoryDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/upcTemplateComparison/getUPCTemplateComparisonByDate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<FPUPCTemplateComparisonDateDTO>> getUPCTemplateComparisonByDateController(@RequestBody FusTraceDTO fusTraceDTO)
            throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get UPC Template Comparison By Date: {} by: {}", fusTraceDTO, credentialsDTO.getUserName());

        List<FPUPCTemplateComparisonDateDTO> fpUPCTemplateComparisonDateDTOList = facilityPaperService.getUPCTemplateComparisonByDateService(
                fusTraceDTO, credentialsDTO);

        LOG.info("END: Get UPC Template Comparison By Date: {} by: {} ", fpUPCTemplateComparisonDateDTOList, credentialsDTO);

        return new ResponseEntity<>(fpUPCTemplateComparisonDateDTOList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveFusTrace", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> saveFusTraceController(@RequestBody FusTraceDTO fusTraceDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : SAVE Fus trace data : {} , user {} ", fusTraceDTO, credentialsDTO);

        Boolean result = facilityPaperService.saveFusTraceService(fusTraceDTO, credentialsDTO);

        LOG.info("END : SAVE Fus trace data : {} , user {} ", result, credentialsDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/upcTemplateComparison/getUPCSectionHistoryDataById", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<ArrayList<Integer>> getUPCSectionHistoryDataByIdController(@RequestBody FusTraceDTO fusTraceDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get UPC Section History Data By Id : {} , user {} ", fusTraceDTO, credentialsDTO);

        ArrayList<Integer> resultSet = facilityPaperService.getUPCSectionHistoryDataByIdService(fusTraceDTO, credentialsDTO);

        LOG.info("END : Get UPC Section History Data By Id : {} , user {} ", resultSet, credentialsDTO);

        return new ResponseEntity<>(resultSet, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUPCSectionsDataByFPId/{fpId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPUpcSectionDataDTO>> getUPCSectionsDataByFPId(@PathVariable Integer fpId) throws AppsException {
        LOG.info("START : Get UPC Sections Data By Facility Paper Id : {} ", fpId);

        List<FPUpcSectionDataDTO> results = facilityPaperService.getUPCSectionsDataByFacilityPaperId(fpId);

        LOG.info("END : Get UPC Sections Data By Facility Paper Id : {}", results);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getUPCSectionDataBySectionId/{fpId}/{sectionId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<FPUpcSectionDataDTO> getUPCSectionDataBySectionId(@PathVariable Integer fpId, @PathVariable Integer sectionId) throws AppsException {
        LOG.info("START : Get UPC Section Data By Section Id : {} ", sectionId);

        FPUpcSectionDataDTO result = facilityPaperService.getUPCSectionDataBySectionId(fpId, sectionId);

        LOG.info("END : Get UPC Section Data By Section Id : {}", result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFusTracesByFacilityPaper/{fpId}/{flag}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<UPCTemplateCommentHistoryDTO>> getFusTracesByFacilityPaper(@PathVariable Integer fpId, @PathVariable String flag) throws AppsException {
        LOG.info("START : Get UPC Sections Data By Facility Paper Id : {} ", fpId);

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<UPCTemplateCommentHistoryDTO> results = facilityPaperService.getFusTracesByFacilityPaper(fpId, flag, credentialsDTO);

        LOG.info("END : Get UPC Sections Data By Facility Paper Id : {}", results);

        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deleteFusTrace", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Boolean> removeFusTrace(@RequestBody FusTraceDTO fusTraceDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : REMOVE Fus trace data by id : {} , user {} ", fusTraceDTO, credentialsDTO);

        Boolean result = facilityPaperService.removeFusTrace(fusTraceDTO);

        LOG.info("END : REMOVE Fus trace data by id : {} , user {} ", result, credentialsDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommitteeButtonEnableData/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Integer> getCommitteeButtonEnableData(@PathVariable Integer facilityPaperID) throws AppsException {
        LOG.info("START : getCommitteeButtonEnableData by facilityPaperID: {} ", facilityPaperID);
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        Integer result = facilityPaperService.getCommitteeButtonEnableData(facilityPaperID, credentialsDTO);

        LOG.info("END : getCommitteeButtonEnableData count : {}", result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/sendCAEmail", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Void> sendCAEmail(@RequestBody CAEmailDTO caEmailDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : SEND CA EMAIL : {} , caEmailDTO {} ", caEmailDTO, credentialsDTO);

        this.facilityPaperService.sendCAPaperEmail(caEmailDTO, credentialsDTO);

        LOG.info("END : SEND CA EMAIL : {} , caEmailDTO {} ", caEmailDTO, credentialsDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateFacilityPaperYearType", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateFacilityPaperYearType(@RequestBody FacilityPaperDTO facilityPaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Draft facility paper : {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO updatedDraft = this.facilityPaperService.updateFacilityPaperYearType(facilityPaperDTO, credentialsDTO);

        LOG.info("END : Draft facility paper: {}, user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDraft, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/calculateGroupExposure", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> calculateGroupExposure(@RequestBody CalculateGrpExposureReq calculateExposureRQ) throws AppsException, IOException, SAXException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Calculate Group Exposure : {}, user {}", calculateExposureRQ, credentialsDTO.getUserID());

        FacilityPaperDTO result = this.facilityPaperService.calculateGroupExposure(calculateExposureRQ, credentialsDTO);

        LOG.info("START : Calculate Group Exposure : {}, user {}", calculateExposureRQ, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getGroupExposure", headers = "Accept=application/json")
    public ResponseEntity<List<GroupExposureDetailDTO>> getAndSaveGroupExposureDetails(@RequestBody GroupExposureRequest groupExposureRequest) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Group Exposure Detail:{}, user {}", groupExposureRequest, credentialsDTO.getUserID());

        List<GroupExposureDetailDTO> result = this.integrationService.getGroupExposure(groupExposureRequest);

        LOG.info("END : Get Group Exposure Detail: {}, user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getGroupExposureDetails", headers = "Accept=application/json")
    public ResponseEntity<List<GroupExposureDetailDTO>> getGroupExposureByCustomerID(@RequestBody FPCustomerFacilityRequest request) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Group Exposure Detail:{}, user {}", request, credentialsDTO.getUserID());

        List<GroupExposureDetailDTO> result = facilityPaperService.getGroupExposureDetailsByCustomerID(
                request.getCustomerID(), request.getFacilityID());

        LOG.info("END : Get Group Exposure Detail By CustomerID and FacilityID: {}, user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/uploadFPCustomerCribDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> uploadFPCustomerCribDetails(@RequestParam("uploadingFiles") MultipartFile[] uploadFiles,
                                                                        @RequestParam("uploadRequestData") String uploadRQData) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        Type listType = new TypeToken<List<CribDetailRQ>>() {
        }.getType();
        List<CribDetailRQ> updateRQList = gson.fromJson(uploadRQData, listType);

        LOG.info("START : Upload crib documents : {} by user {}", updateRQList, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.uploadFPCustomerCribDetails(updateRQList, uploadFiles, credentialsDTO);

        LOG.info("END : Upload crib document : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateCribReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateCribReport(@RequestParam("uploadingFile") MultipartFile[] uploadFiles, @RequestParam("uploadRequestData") String uploadRQData) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        CribDetailRQ updateRQ = gson.fromJson(uploadRQData, CribDetailRQ.class);

        //set file if edited.
        if (updateRQ.isReportUpdated()) {
            MultipartFile uploadFile = uploadFiles[0];
            updateRQ.getDocStorageDTO().setFileName(uploadFile.getOriginalFilename());
            updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());
        }

        LOG.info("START: Save Crib Report: {} by: {}", updateRQ, credentialsDTO.getUserName());

        FacilityPaperDTO facilityPaperDTO = this.facilityPaperService.updateCribReport(updateRQ, credentialsDTO);

        LOG.info("END: Save Crib Report: {} by: {}", updateRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deleteCribReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deleteCribReport(@RequestBody CribDetailRQ cribReportDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save Crib Report: {} by: {}", cribReportDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityPaperService.deleteCribReport(cribReportDTO, credentialsDTO);

        LOG.info("END: Save Crib Report: {} by: {}", cribReportDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCribHistoryByCustomer/{facilityPaperId}/{identificationNo}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CribDetailsResponse>> getCribHistoryByCustomer(@PathVariable Integer facilityPaperId, @PathVariable String identificationNo) throws AppsException {
        LOG.info("START : getCribHistoryByCustomer by identificationNo: {} ", identificationNo);

        List<CribDetailsResponse> response = new ArrayList<>();
        response = facilityPaperService.getCribHistoryByCustomer(facilityPaperId, identificationNo);

        LOG.info("END : getCribHistoryByCustomer : {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateGroupExposure", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> updateGroupExposure(@RequestBody CalculateGrpExposureReq calculateExposureRQ) throws AppsException, IOException, SAXException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update Group Exposure : {}, user {}", calculateExposureRQ, credentialsDTO.getUserID());

        FacilityPaperDTO result = this.facilityPaperService.updateGroupExposure(calculateExposureRQ, credentialsDTO);

        LOG.info("START : Update Group Exposure : {}, user {}", calculateExposureRQ, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/supporting-docs-zip/{fpId}")
    public void downloadSupportingDocsZipFile(HttpServletResponse response, @PathVariable Integer fpId) throws IOException {
        byte[] zipFileContent = facilityPaperService.SupportingDocsZipFile(fpId);

        response.setContentType("application/zip");
        response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = documents.zip");
        response.setHeader(HttpHeaders.TRANSFER_ENCODING, "binary");

        try (ServletOutputStream outputStream = response.getOutputStream()) {
            outputStream.write(zipFileContent);
            outputStream.flush();
            response.flushBuffer();
        } catch (IOException e) {
            LOG.error("Error while writing zip file to response output stream", e);
            throw new IOException("Error while writing zip file to response output stream", e);
        }
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveEnvironmentalRiskCategory", headers = "Accept=application/json")
    public ResponseEntity<List<EnvironmentalRiskDataDTO>> saveEnvironmentalRiskCategory(@RequestBody EnvironmentalRiskDataReq environmentalRiskReq) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Environmental Risk Category {}", credentialsDTO.getUserID());

        List<EnvironmentalRiskDataDTO> result = facilityPaperService.saveFPEnvironmentalRisk(environmentalRiskReq, credentialsDTO);

        LOG.info("END : Save Environmental Risk Category {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/saveUpdateCommitteeInquiry")
    public ResponseEntity<List<CommitteeInquiryMasterDTO>> saveUpdateCommitteeInquiry(@RequestBody CommitteeInquiryMasterDTO committeeInquiryMasterDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Committee Inquiry : {}, user {}", committeeInquiryMasterDTO, credentialsDTO.getUserID());

        List<CommitteeInquiryMasterDTO> result = committeeInqService.saveUpdateCommitteeInquiry(committeeInquiryMasterDTO, credentialsDTO);

        LOG.info("END : Save Committee Inquiry : {}, user {}", committeeInquiryMasterDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/getCommitteeInquiryByFacilityPaperId/{facilityPaperID}")
    public ResponseEntity<List<CommitteeInquiryMasterDTO>> getCommitteeInquiryByFacilityPaperId(@PathVariable Integer facilityPaperID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Committee Inquiry : {}, user {}", facilityPaperID, credentialsDTO.getUserID());

        List<CommitteeInquiryMasterDTO> result = committeeInqService.getCommitteeInquiryByFacilityPaperId(facilityPaperID);

        LOG.info("END : Save Committee Inquiry : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping(value = "/statusUpdateCommitteeInquiry")
    public ResponseEntity<CommitteeInquiryMasterDTO> statusUpdateCommitteeInquiry(@RequestBody CommitteeInquiryMasterDTO committeeInquiryMasterDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Committee Inquiry : {}, user {}", committeeInquiryMasterDTO, credentialsDTO.getUserID());

        CommitteeInquiryMasterDTO result = committeeInqService.statusUpdateCommitteeInquiry(committeeInquiryMasterDTO);

        LOG.info("END : Save Committee Inquiry : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping(value = "/getCommitteeUsers/{facilityPaperID}")
    public ResponseEntity<List<CAUserDTO>> getCommitteeUsers(@PathVariable Integer facilityPaperID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get Committee Users : {}, user {}", facilityPaperID, credentialsDTO.getUserID());

        List<CAUserDTO> result = committeeInqService.getCommitteeUsers(facilityPaperID);

        LOG.info("END : get Committee Users : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/removeFPEnvironmentalRisk/{facilityPaperId}", headers = "Accept=application/json")
    public ResponseEntity<List<EnvironmentalRiskDataDTO>> removeFPEnvironmentalRisk(@PathVariable Integer facilityPaperId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Remove Environmental Risk Categories {}", credentialsDTO.getUserID());

        List<EnvironmentalRiskDataDTO> result = facilityPaperService.removeFPEnvironmentalRisk(facilityPaperId, credentialsDTO);

        LOG.info("END : Remove Environmental Risk Categories {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/approvedFPEnvironmentalRisk", headers = "Accept=application/json")
    public ResponseEntity<FacilityPaperDTO> approvedFPEnvironmentalRisk(@RequestBody ApproveRiskScoreDTO approveRiskScoreDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Approved Environmental Risk Categories {}", credentialsDTO.getUserID());

        FacilityPaperDTO result = facilityPaperService.approvedFPEnvironmentalRisk(approveRiskScoreDTO, credentialsDTO);

        LOG.info("END : Approved Environmental Risk Categories {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getFPEnvironmentalRiskOpinion/{facilityPaperId}", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> getFPEnvironmentalRiskOpinion(@PathVariable Integer facilityPaperId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Environmental Risk Opinions {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = facilityPaperService.getFPEnvironmentalRiskOpinion(facilityPaperId);

        LOG.info("END : Get Environmental Risk Opinions {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveFPEnvironmentalRiskOpinion", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> saveFPEnvironmentalRiskOpinion(@RequestBody RiskOpinionDTO riskOpinionDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Environmental Risk Opinion {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = facilityPaperService.saveFPEnvironmentalRiskOpinion(riskOpinionDTO, credentialsDTO);

        LOG.info("END : Save Environmental Risk Opinion {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveFPEnvironmentalRiskOpinionReply", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> saveFPEnvironmentalRiskOpinionReply(@RequestBody RiskOpinionReplyDTO riskOpinionReplyDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Environmental Risk Opinion Reply {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = facilityPaperService.saveFPEnvironmentalRiskOpinionReply(riskOpinionReplyDTO, credentialsDTO);

        LOG.info("END : Save Environmental Risk Opinion Reply {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/removeFPEnvironmentalRiskOpinion", headers = "Accept=application/json")
    public ResponseEntity<List<RiskOpinionDTO>> removeFPEnvironmentalRiskOpinion(@RequestBody RiskOpinionDTO riskOpinionDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Remove Environmental Risk Opinion {}", credentialsDTO.getUserID());

        List<RiskOpinionDTO> result = facilityPaperService.removeFPEnvironmentalRiskOpinion(riskOpinionDTO);

        LOG.info("END : Remove Environmental Risk Opinion {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCusApplicableCovenantList/{fpRefNumber}/{facilityId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CusApplicableCovenantDTO>> getCusApplicableCovenantList(@PathVariable String fpRefNumber, @PathVariable Integer facilityId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<CusApplicableCovenantDTO> covenants = facilityPaperService.getCusApplicableCovenantList(fpRefNumber, facilityId, credentialsDTO);

        return new ResponseEntity<>(covenants, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getWalletShares/{facilityPaperId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<WalletShareDTO>> getWalletShares(@PathVariable Integer facilityPaperId) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get wallet share : {}, user {}", facilityPaperId, credentialsDTO.getUserID());

        List<WalletShareDTO> result = this.facilityPaperService.getWalletShare(facilityPaperId);

        LOG.info("END : Get wallet share : {}, user {}", facilityPaperId, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveWalletShare", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<WalletShareDTO>> saveWalletShares(@RequestBody WalletShareReq walletShareReq) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save wallet share : {}, user {}", walletShareReq, credentialsDTO.getUserID());

        List<WalletShareDTO> result = this.facilityPaperService.saveWalletShare(walletShareReq, credentialsDTO);

        LOG.info("END : Save wallet share : {}, user {}", walletShareReq, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityTemplateDocumentElements", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<FCTemplateElement>> getFacilityTemplateDocumentElements(@RequestBody SecurityDocumentContentDTO documentContentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : get Security Document Elements : {}, user {}", documentContentDTO, credentialsDTO.getUserID());

        List<FCTemplateElement> response = securityDocumentService.getSecurityDocumentElements(documentContentDTO);

        LOG.info("END : get Security Document Elements : {}, user {}", documentContentDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveSecurityDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<SecurityDocumentDTO> saveSecurityDocument(@RequestBody SecurityDocumentReq securityDocumentReq) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Save Security Document Content : {}, user {}", securityDocumentReq, credentialsDTO.getUserID());

        SecurityDocumentDTO response = securityDocumentService.saveOrUpdateSecurityDocument(securityDocumentReq, credentialsDTO);

        LOG.info("END : Save Security Document Content : {}, user {}", securityDocumentReq, credentialsDTO.getUserID());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSecurityDocumentContent", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getSecurityDocumentContent(@RequestBody SecurityDocumentContentDTO documentContentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : get Security Document Content : {}, user {}", documentContentDTO, credentialsDTO.getUserID());

        String response = securityDocumentService.getSecurityDocumentContent(documentContentDTO, credentialsDTO);

        LOG.info("END : get Security Document Content : {}, user {}", documentContentDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSecurityDocumentHistoryData/{securityDocumentID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<SecurityDocumentDTO>> getSecurityDocumentHistoryData(@PathVariable Integer securityDocumentID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get Security Document History : {}, user {}", securityDocumentID, credentialsDTO.getUserID());

        List<SecurityDocumentDTO> response = securityDocumentService.getSecurityDocumentHistory(securityDocumentID);

        LOG.info("END : get Security Document History: {}, user {}", securityDocumentID, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSDCovenantList/{fpRefNumber}/{facilityId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CusApplicableCovenantDTO>> getSDCovenantList(@PathVariable String fpRefNumber, @PathVariable Integer facilityId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        List<CusApplicableCovenantDTO> covenants = securityDocumentService.getCovenantList(fpRefNumber, facilityId, credentialsDTO);

        return new ResponseEntity<>(covenants, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getDocumentTags/{facilityPaperId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPSecurityDocumentTagDTO>> getDocumentTags(@PathVariable Integer facilityPaperId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : get Security Document Tags : {}, user {}", facilityPaperId, credentialsDTO.getUserID());

        List<FPSecurityDocumentTagDTO> response = securityDocumentService.getDocumentTags(facilityPaperId);

        LOG.info("END : get Security Document Tags: {}, user {}", facilityPaperId, credentialsDTO.getUserID());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/getUpcSectionData/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FPUpcSectionDataDTO>> getUpcSectionData(@PathVariable Integer facilityPaperID) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : getUpcSectionData : {}, user {}", facilityPaperID, credentialsDTO.getUserID());

        List<FPUpcSectionDataDTO> result = this.facilityPaperService.getUpcSectionData(facilityPaperID, credentialsDTO);

        LOG.info("END : getUpcSectionData : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDeviations", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<DeviationDTO>> getDeviations() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : getDeviations :  user {}", credentialsDTO.getUserID());

        List<DeviationDTO> result = this.facilityPaperService.getDeviations();

        LOG.info("END : getDeviations : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCompDeviations/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CompDeviationDTO>> getCompDeviations(@PathVariable Integer facilityPaperID) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : getCompDeviations :  user {}", credentialsDTO.getUserID());

        List<CompDeviationDTO> result = this.facilityPaperService.getCompDeviations(facilityPaperID, credentialsDTO);

        LOG.info("END : getCompDeviations : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveOrUpdateCompDeviations", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<CompDeviationDTO>> saveOrUpdateCompDeviations(@RequestBody List<CompDeviationDTO> compDeviationDTOList) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : saveOrUpdateCompDeviations :  user {}", credentialsDTO.getUserID());

        List<CompDeviationDTO> result = this.facilityPaperService.saveOrUpdateCompDeviations(compDeviationDTOList, credentialsDTO);

        LOG.info("END : saveOrUpdateCompDeviations : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getDigitalFormApplicationContent", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DigitalApplicationDTO> getDigitalFormApplicationContent(@RequestBody DigitalApplicationReq digitalApplicationReq) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : getDigitalFormApplicationContent :  user {}", credentialsDTO.getUserID());

        DigitalApplicationDTO result = this.facilityPaperService.getDigitalFormApplicationContent(digitalApplicationReq);

        LOG.info("END : getDigitalFormApplicationContent : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveMdAssistanceComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<FPMDReviewCommentDTO>> saveMdAssistanceComment(@RequestBody FPMDReviewCommentDTO fpmdReviewCommentDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : saveMdAssistanceComment : {}, user {}", fpmdReviewCommentDTO, credentialsDTO.getUserID());

        List<FPMDReviewCommentDTO> result = this.facilityPaperService.saveMDReviewComment(fpmdReviewCommentDTO, credentialsDTO);

        LOG.info("END : saveMdAssistanceComment : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/markAsViewMDComments", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<FPMDReviewCommentDTO>> markAsViewMDComments(@RequestBody FPMDReviewCommentDTO fpmdReviewCommentDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : saveMdAssistanceComment : {}, user {}", fpmdReviewCommentDTO, credentialsDTO.getUserID());

        List<FPMDReviewCommentDTO> result = this.facilityPaperService.markAsViewMDComments(fpmdReviewCommentDTO, credentialsDTO);

        LOG.info("END : saveMdAssistanceComment : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    

    @RequestMapping(value = "/getCustomerClassification", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<CustomerClassificationDTO>> getCustomerClassification(@RequestBody FPCustomerClassificationDTO dto) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : getCustomerClassification :  user {}", credentialsDTO.getUserID());

        List<CustomerClassificationDTO> result = this.facilityPaperService.getCustomerClassification(dto);

        LOG.info("END : getCustomerClassification : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/saveClassification", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<CustomerClassificationDTO>> saveCustomerClassification(@RequestBody FPCustomerClassificationDTO dto) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : saveCustomerClassification :  user {}", credentialsDTO.getUserID());

        List<CustomerClassificationDTO> result = this.facilityPaperService.saveCustomerClassification(dto);

        LOG.info("END : saveCustomerClassification : {}, user {}", result, credentialsDTO.getUserID());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/refreshCompDeviations/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CompDeviationDTO>> refreshCompDeviations(@PathVariable Integer facilityPaperID) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : refreshCompDeviations :  user {}", credentialsDTO.getUserID());

        List<CompDeviationDTO> result = this.facilityPaperService.refreshCompDeviations(facilityPaperID,credentialsDTO);

        LOG.info("END : refreshCompDeviations : {}, user {}", result, credentialsDTO.getUserID());
          return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getCustomerBankDetails/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CASCustomerBankDetailsDTO>> getCustomerBankDetails(@PathVariable Integer facilityPaperID) throws AppsException  {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : getCustomerBankDetails :  user {}", credentialsDTO.getUserID());

        List<CASCustomerBankDetailsDTO> result = this.facilityPaperService.getCustomerBankDetails(facilityPaperID, credentialsDTO);

        LOG.info("END : getCustomerBankDetails : {}, user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}

