package com.itechro.cas.controller.lead;

import com.google.gson.Gson;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.model.dto.advancedAnalytics.AnalyticsDecisionDTO;
import com.itechro.cas.model.dto.advancedAnalytics.IndividualLeaseRequestDTO;
import com.itechro.cas.model.dto.advancedAnalytics.IndividualLeaseResDTO;
import com.itechro.cas.model.dto.integration.response.StandardResponse;
import com.itechro.cas.model.dto.lead.DigitalApplicationDTO;
import com.itechro.cas.model.dto.lead.DigitalApplicationReq;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.CreditFacilityTemplateDTO;
import com.itechro.cas.model.dto.customer.CustomerDTO;
import com.itechro.cas.model.dto.lead.*;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import com.itechro.cas.service.customer.CustomerService;
import com.itechro.cas.service.integration.MicroIntegrationService;
import com.itechro.cas.service.lead.LeadService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.itechro.cas.exception.impl.AppsException;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/lead")
public class LeadController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(LeadController.class);

    @Autowired
    private LeadService leadService;

    @Autowired
    private MasterDataService masterDataService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MicroIntegrationService microIntegrationService;


    @ResponseExceptionHandler
    @RequestMapping(value = "/getLeadByID/{leadID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<LeadDTO> getLeadByID(@PathVariable Integer leadID) throws Exception {

        LOG.info("START : Get Lead by ID: {}", leadID);

        LeadDTO leadDTO = this.leadService.getLeadDTOByID(leadID);

        LOG.info("END : Get Lead By ID : {}", leadID);

        return new ResponseEntity<>(leadDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getLead/{leadRefNumber}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<LeadDTO> getLeadByRefNumber(@PathVariable String leadRefNumber) throws Exception {

        LOG.info("START : Get Lead by Ref Number: {}", leadRefNumber);

        LeadDTO leadDTO = this.leadService.getLeadDTOByRefNumber(leadRefNumber);

        LOG.info("END : Get Lead By Ref Number : {}", leadRefNumber);

        return new ResponseEntity<>(leadDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedLeadDetail", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<LeadDTO>> getPagedLeadData(@RequestBody LeadSearchRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search leads : {} by user {}", searchRQ, credentialsDTO.getUserID());

        Page<LeadDTO> leadDTOPage = this.leadService.getPagedLeadDTO(searchRQ, credentialsDTO);

        LOG.info("END : Search leads : {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTOPage, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerPagedLeadDTO", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<LeadDTO>> getCustomerPagedLeadDTO(@RequestBody LeadSearchRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search leads : {} by user {}", searchRQ, credentialsDTO.getUserID());

        Page<LeadDTO> leadDTOPage = this.leadService.getCustomerPagedLeadDTO(searchRQ, credentialsDTO);

        LOG.info("END : Search leads : {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTOPage, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getBranchPendingLeadCount", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Long> getBranchPendingLeadCount(@RequestBody LeadSearchRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        if (StringUtils.isBlank(searchRQ.getBranchCode())) {
            searchRQ.setBranchCode(credentialsDTO.getDivCode());
        }
        LOG.info("START : Load lead count: {} by user {}", searchRQ, credentialsDTO.getUserID());

        Long loadCount = this.leadService.getBranchPendingLeadCount(searchRQ);

        LOG.info("END : Load lead count: {} by user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(loadCount, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateLead", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LeadDTO> saveOrUpdateLead(@RequestBody LeadDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save/update lead : {} by user {}", updateDTO, credentialsDTO.getUserID());

        LeadDTO leadDTO = this.leadService.saveOrUpdateLeadDTO(updateDTO, credentialsDTO);

        LOG.info("END : Save/update lead : {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/submitLead", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LeadDTO> submitLead(@RequestBody LeadDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Submit lead : {} by user {}", updateDTO, credentialsDTO.getUserID());

        LeadDTO leadDTO = this.leadService.submitLead(updateDTO, credentialsDTO);

        LOG.info("END : Submit lead : {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateLeadStatusOrAssignee", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LeadDTO> updateLeadStatusOrAssignee(@RequestBody LeadStatusUpdateRQ updateRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Update lead status/assignee : {} by user {}", updateRQ, credentialsDTO.getUserID());

        LeadDTO leadDTO = this.leadService.updateLeadDTOStatus(updateRQ, credentialsDTO);

        LOG.info("END : Update lead status/assignee: {} by user {}", leadDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTO, HttpStatus.OK);
    }

    @PostMapping(value = "/uploadLeadDocument", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LeadDTO> uploadLeadDocument(@RequestParam(value = "uploadingFile", required = false) MultipartFile uploadFile, @RequestParam("uploadRequestData") String uploadRQData) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LeadDocumentUploadRQ request = new Gson().fromJson(uploadRQData, LeadDocumentUploadRQ.class);

        if (uploadFile != null) {
            request.getDocStorageDTO().setDocument(uploadFile.getBytes());
        }
        LOG.info("START: Upload/Update lead document : {} by user {}", request, credentialsDTO.getUserID());

        LeadDTO leadDTO = leadService.uploadLeadDocument(request, credentialsDTO);

        LOG.info("END: Upload/Update lead document : {} by user {}", leadDTO, credentialsDTO.getUserID());

        return ResponseEntity.ok(leadDTO);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getApprovedCreditFacilityTemplates", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CreditFacilityTemplateDTO>> getApprovedCreditFacilityTemplate() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get approved credit facility templates by user {}", credentialsDTO.getUserID());

        List<CreditFacilityTemplateDTO> templateResponseList = masterDataService.getApprovedCreditFacilityTemplates();

        LOG.info("END : get approved credit facility templates by user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(templateResponseList, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getActiveCustomers", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerDTO>> getActiveCustomers() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get active customers by user {}", credentialsDTO.getUserID());

        List<CustomerDTO> activeResponseList = customerService.getActiveCustomers();

        LOG.info("END : get active customers by user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(activeResponseList, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityPaperHistoryForLead", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LeadFacilityPaperStatusDetailRS> getFacilityPaperHistoryForLead(@RequestBody LeadDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Facility paper status history for Lead : {} by user {}", updateDTO, credentialsDTO.getUserID());

        LeadFacilityPaperStatusDetailRS leadFacilityPaperStatusDetailRS = this.leadService.getFacilityPaperHistoryForLead(updateDTO, credentialsDTO);

        LOG.info("END : Search Facility Paper status history for lead : {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadFacilityPaperStatusDetailRS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getLeadDashboardCount", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LeadDashboardCountDTO> getLeadDashboardCount(@RequestBody LeadDashboardRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LeadDashboardCountDTO response = new LeadDashboardCountDTO();

        LOG.info("START : getLeadDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        response = this.leadService.getLeadDashboardCount(searchRQ);

        LOG.info("END : getLeadDashboardCount {} : {}", credentialsDTO.getUserID(), searchRQ);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedLeadDashboard", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<LeadDashboardDTO>> getPagedLeadDashboard(@RequestBody LeadDashboardRQ leadDashboardRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        Page<LeadDashboardDTO> pageResponse = new Page<>();

        LOG.info("START : getPagedLeadDashboard {} : {}", credentialsDTO.getUserID(), leadDashboardRQ);

        pageResponse = this.leadService.getPagedLeadDashboard(leadDashboardRQ);

        LOG.info("END : getPagedLeadDashboard {} ", credentialsDTO.getUserID(), leadDashboardRQ);

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateLeadComment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LeadDTO> saveOrUpdateLeadComment(@RequestBody LeadCommentDTO leadCommentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Lead User Comment : {}, user {}", leadCommentDTO, credentialsDTO.getUserID());

        LeadDTO updatedLead = this.leadService.saveOrUpdateLeadComment(leadCommentDTO, credentialsDTO);

        LOG.info("END : Save or Update Lead Form User Comment : {}, user {}", leadCommentDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedLead, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateLeadFormSupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LeadDTO> deactivateLeadSupportingDoc(@RequestBody LeadDocumentDTO leadDocumentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Lead supporting document: {} by: {}", leadDocumentDTO, credentialsDTO.getUserName());

        LeadDTO response = this.leadService.deactivateLeadSupportingDoc(leadDocumentDTO, credentialsDTO);

        LOG.info("END: Deactivate the Application Form supporting document: {} by: {}", leadDocumentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

//    @ResponseExceptionHandler
//    @RequestMapping(value = "/getDigitalizedApplication/{compLeadId}", headers = "Accept=application/json", method = RequestMethod.GET)
//    public ResponseEntity<String> getDigitalizedApplication(@PathVariable Long compLeadId) throws Exception {
//
//        CredentialsDTO credentialsDTO = getCredentialsDTO();
//
//        LOG.info("START: Get Digitalized Application : {} by: {}", credentialsDTO.getUserName());
//
//        String response = leadService.getDigitalizedApplication(compLeadId);
//
//        LOG.info("content response", response);
//
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

    @ResponseExceptionHandler
    @PostMapping("/getDigitalizedApplication")
    public ResponseEntity<String> getDigitalizedApplication(@RequestBody DigitalizedApplicationRequestDTO req) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Get Digitalized Application By Req: {} by: {}", credentialsDTO.getUserName());

        String content = leadService.getDigitalizedApplication(req);

        LOG.info("END: Get Digitalized Application By Req");

        return new ResponseEntity<>(content, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/saveDigitalizedApplication", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<DigitalApplicationDTO> saveDigitalizedApplication(@RequestBody DigitalApplicationReq digitalApplicationReq) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Save Digital Application Content user {}",  credentialsDTO.getUserID());

        DigitalApplicationDTO response = leadService.saveOrUpdateDigitalApplication(digitalApplicationReq, credentialsDTO);

        LOG.info("END : Save Digital Application Content user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @RequestMapping(value = "/getCreditFacilityTemplates", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CreditFacilityTemplateDTO>> getApprovedActiveCreditFacilityTemplate() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get approved and active credit facility templates by user {}", credentialsDTO.getUserID());

        List<CreditFacilityTemplateDTO> templateResponseList = masterDataService.getApprovedActiveCreditFacilityTemplates();

        LOG.info("END : get approved and active credit facility templates by user {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(templateResponseList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getInPrincipleSanctionLetterContent", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getInPrincipleSanctionLetterContent(@RequestBody InPrincipalSanctionLetterRQ inPrincipalSanctionLetterRQ) throws AppsException {

        LOG.info("START : Get InPrincipleSanctionLetterContent by compFacilityId: {}", inPrincipalSanctionLetterRQ);

        String response = leadService.getInPrincipleSanctionLetterContent(inPrincipalSanctionLetterRQ);

        LOG.info("END : Get InPrincipleSanctionLetterContent By compFacilityId : {}", inPrincipalSanctionLetterRQ);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/getIndividualLeasingAA")
    public ResponseEntity<StandardResponse<AnalyticsDecisionDTO>> getIndividualLeasingAA(
            @RequestBody IndividualLeaseRequestDTO individualLeaseRequestDTO) throws AppsException, IOException {
        LOG.info(
                "START | searchIndividualCrib - AdvanceAnalyticsController | request : {}",
                individualLeaseRequestDTO);

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        ResponseEntity<StandardResponse<AnalyticsDecisionDTO>> response =
                microIntegrationService.getIndividualLeasingAA(individualLeaseRequestDTO, credentialsDTO);
        LOG.info("END | searchIndividualCrib - AdvanceAnalyticsController | response : {}", response);
        return ResponseEntity.ok().body(response.getBody());
    }

    @ResponseExceptionHandler
    @GetMapping("/deleteDigitalizedApplication/{applicationId}")
    public ResponseEntity<Boolean> deleteDigitalizedApplication(@PathVariable Integer applicationId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Soft Delete Digital Application : {}, user {}", applicationId, credentialsDTO.getUserID());

        boolean ok = leadService.deleteDigitalApplication(applicationId, credentialsDTO);

        LOG.info("END : Soft Delete Digital Application : {}, user {}", applicationId, credentialsDTO.getUserID());

        return new ResponseEntity<>(ok, HttpStatus.OK);
    }


}
