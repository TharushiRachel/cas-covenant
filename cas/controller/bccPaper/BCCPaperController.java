package com.itechro.cas.controller.bccPaper;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.bccpaper.*;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.SearchFacilityPaperRQ;
import com.itechro.cas.model.dto.finacle.request.CompReportingListReq;
import com.itechro.cas.model.dto.finacle.request.LoanAccCovenantReqDTO;
import com.itechro.cas.model.dto.finacle.response.LoanAccountCovenantDTO;
import com.itechro.cas.model.dto.integration.request.CommissionChargeRQ;
import com.itechro.cas.model.dto.integration.request.finacle.AccCollateralRequest;
import com.itechro.cas.model.dto.integration.request.finacle.CompReportingRequest;
import com.itechro.cas.model.dto.integration.request.finacle.LimitNodeRequest;
import com.itechro.cas.model.dto.integration.response.CommissionReportingResponse;
import com.itechro.cas.model.dto.integration.response.PrefentialChargeResponse;
import com.itechro.cas.model.dto.integration.response.finacle.AccCollateralResponse;
import com.itechro.cas.model.dto.integration.response.finacle.CompReportingResponse;
import com.itechro.cas.model.dto.integration.response.finacle.LimitNodeResponse;
import com.itechro.cas.model.dto.integration.response.finacle.RCTProductGroup;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.bccpaper.BCCPaperService;
import com.itechro.cas.service.integration.IntegrationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/bccPaper")
public class BCCPaperController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(BCCPaperController.class);

    @Autowired
    private BCCPaperService bccPaperService;

    @Autowired
    private IntegrationService integrationService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/createBCCPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> createBCCPaper(@RequestBody BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Create bcc paper Base Data : {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        FacilityPaperDTO result = this.bccPaperService.createBCCPaper(boardCreditCommitteePaperDTO, credentialsDTO);

        LOG.info("END : Create bcc paper Base Data: {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateBCCPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> updateBCCPaper(@RequestBody BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update bcc paper Base Data : {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.updateBCCPaper(boardCreditCommitteePaperDTO, credentialsDTO);

        LOG.info("END : Save or Update bcc paper Base Data: {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateBCCPDFReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> updateBCCPDFReport(@RequestBody BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update bcc paper PDF Report : {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.updateBCCPDFReport(boardCreditCommitteePaperDTO, credentialsDTO);

        LOG.info("END : Save or Update bcc paper PDF Report: {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getBCCPaperByFacilityPaperByID", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> getBCCPaperByFacilityPaperByID(@RequestBody BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get BCC paper by facility Paper ID : {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.getBCCPaperByFacilityPaperByID(boardCreditCommitteePaperDTO, credentialsDTO);

        LOG.info("END : Get BCC paper by facility Paper ID: {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCompanyDirectorDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> saveOrUpdateCompanyDirectorDetails(@RequestBody BccCompanyDirectorDTO bccCompanyDirectorDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Bcc Company Director Details : {}, user {}", bccCompanyDirectorDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.saveOrUpdateCompanyDirectorDetails(bccCompanyDirectorDTO, credentialsDTO);

        LOG.info("END :Save or Update Bcc Company Director Details: {}, user {}", bccCompanyDirectorDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateRiskRatingYear", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> saveOrUpdateRiskRatingYear(@RequestBody BccRiskRatingYearDTO bccRiskRatingYearDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Risk Rating Year : {}, user {}", bccRiskRatingYearDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.saveOrUpdateRiskRatingYear(bccRiskRatingYearDTO, credentialsDTO);

        LOG.info("END : Save or Update Risk Rating Year: {}, user {}", bccRiskRatingYearDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateBCCCustomerCribDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> saveOrUpdateBCCCustomerCribDetails(@RequestBody BccCustomerCribDetailDTO bccCustomerCribDetailDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update Risk Rating Year : {}, user {}", bccCustomerCribDetailDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.saveOrUpdateBCCCustomerCribDetails(bccCustomerCribDetailDTO, credentialsDTO);

        LOG.info("END : Save or Update Risk Rating Year: {}, user {}", bccCustomerCribDetailDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilityPaperDTOForBCC", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityPaperDTO>> getPagedFacilityPaperDTOForBCC(@RequestBody SearchFacilityPaperRQ facilityPaperSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Facility Papers for BCC Paper {}", credentialsDTO.getUserID());

        Page<FacilityPaperDTO> pageResponse = bccPaperService.getPagedFacilityPaperDTOForBCC(facilityPaperSearchRQ);

        LOG.info("END : Search Facility Papers fro Paper {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getBCCReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getBCCReport(@RequestBody BoardCreditCommitteePaperDTO searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get bcc Pdf report {} : by {}", searchRQ, credentialsDTO.getUserID());

        String bccReport = this.bccPaperService.getBCCReport(searchRQ, credentialsDTO);

        LOG.info("END : Get bcc Pdf report {} : by {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(bccReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateBccFacilities", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> saveOrUpdateBccFacilities(@RequestBody BccFacilityDTO bccFacilityDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save or Update bcc facility : {}, user {}", bccFacilityDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.saveOrUpdateBccFacilities(bccFacilityDTO, credentialsDTO);

        LOG.info("END : Save or Update bcc facility: {}, user {}", bccFacilityDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateBccPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deactivateBccPaper(@RequestBody BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the BCC Paper: {} by: {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.bccPaperService.deactivateBccPaper(boardCreditCommitteePaperDTO, credentialsDTO);

        LOG.info("END: Deactivate the BCC Paper: {} by: {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacilityPaperDTOForBCCForUserName", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityPaperDTO>> getPagedFacilityPaperDTOForBCCForUserName(@RequestBody SearchFacilityPaperRQ facilityPaperSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get Facility Papers for BCC Paper by Username {}", credentialsDTO.getUserID());

        Page<FacilityPaperDTO> pageResponse = bccPaperService.getPagedFacilityPaperDTOForBCCForUserName(facilityPaperSearchRQ, credentialsDTO);

        LOG.info("END : get Facility Papers for Paper by Username {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(pageResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/changeAssignUserBCCPaper", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> changeAssignUserBCCPaper(@RequestBody BoardCreditCommitteePaperDTO boardCreditCommitteePaperDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Change Assign User for bcc paper : {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.changeAssignUserBCCPaper(boardCreditCommitteePaperDTO, credentialsDTO);

        LOG.info("END : Change Assign User for bcc paper : {}, user {}", boardCreditCommitteePaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getComparableContent", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getComparableContent(@RequestBody CompReportingListReq compReportingListReq) throws Exception {

        String response = bccPaperService.getComparableContent(compReportingListReq);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/bCCPaperSubmission/{facilityPaperId}", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<BoardCreditCommitteePaperDTO> bCCPaperSubmission(@PathVariable Integer facilityPaperId) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : BCC Paper Submission : {}, user {}", facilityPaperId, credentialsDTO.getUserID());

        BoardCreditCommitteePaperDTO result = this.bccPaperService.bCCPaperSubmission(facilityPaperId, credentialsDTO);

        LOG.info("END : BCC Paper Submission : {}, user {}", facilityPaperId, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCompReportData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CompReportingResponse> getCompReportData(@RequestBody CompReportingRequest compReportingRequest) throws AppsException {

        CompReportingResponse result = this.bccPaperService.getReportingData(compReportingRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getLimitNodeData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LimitNodeResponse> getLimitNodeData(@RequestBody LimitNodeRequest limitNodeRequest) throws AppsException {

        LimitNodeResponse result = this.integrationService.getLimitNodeData(limitNodeRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getLoanAccountCovenants", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<LoanAccountCovenantDTO> getLoanAccountCovenants(@RequestBody LoanAccCovenantReqDTO loanAccCovenantReqDTO) throws AppsException {

        LoanAccountCovenantDTO result = this.integrationService.getLoanAccountCovenants(loanAccCovenantReqDTO);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getLoanAccountCollateral", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AccCollateralResponse> getLoanAccountCollateral(@RequestBody AccCollateralRequest accCollateralRequest) throws AppsException {

        AccCollateralResponse result = this.integrationService.getLoanAccountCollateral(accCollateralRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommissionData", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<PrefentialChargeResponse> getCommissionData(@RequestBody CommissionChargeRQ commissionChargeRQ) throws AppsException {

        PrefentialChargeResponse result = this.integrationService.getCommissionData(commissionChargeRQ);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCommissionLoanAccounts", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CommissionReportingResponse> getCommissionLoanAccounts(@RequestBody CompReportingRequest compReportingRequest) throws AppsException {

        CommissionReportingResponse result = this.bccPaperService.getCommissionReportingData(compReportingRequest);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getProductGroups", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<RCTProductGroup>> getProductGroups() {

        List<RCTProductGroup> result = this.integrationService.getRCTProductGroups();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
