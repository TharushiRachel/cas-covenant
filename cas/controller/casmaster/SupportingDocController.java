package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.dto.casmaster.GlobalSupportingDocDTO;
import com.itechro.cas.model.dto.casmaster.SupportingDocDTO;
import com.itechro.cas.model.dto.casmaster.SupportingDocSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/supportDoc")
public class SupportingDocController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SupportingDocController.class);
    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_SUPPORTING_DOC_VIEW+ "')")
    @RequestMapping(value = "getPagedSupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<SupportingDocDTO>> getPagedSupportDoc(@RequestBody SupportingDocSearchRQ supportingDocSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Supporting Doc : {} , user {}", supportingDocSearchRQ, credentialsDTO.getUserID());

        Page<SupportingDocDTO> pagedSupportingDocuments = masterDataService.getPagedSupportingDocDTO(supportingDocSearchRQ);

        LOG.info("END : Search Supporting Doc : {} , user {}", supportingDocSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedSupportingDocuments, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSupportingDocUpdateDTO/{supportingDocID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<SupportingDocDTO> getSupportingDocUpdateDTO(@PathVariable Integer supportingDocID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get Supporting Doc : {} , user {}", supportingDocID, credentialsDTO.getUserID());

        SupportingDocDTO supportingDocUpadateDTO = masterDataService.getSupportingDocByID(supportingDocID);

        LOG.info("END : get Supporting Doc : {} , user {}", supportingDocID, credentialsDTO.getUserID());

        return new ResponseEntity<>(supportingDocUpadateDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYgetApprovedSupportingDocListSTEM.ICAS_SETTING_SUPPORTING_DOC_ADD_EDIT+ "')")
    @RequestMapping(value = "/saveOrUpdateSupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<SupportingDocDTO> saveOrUpdateSupportingDoc(@RequestBody SupportingDocDTO supportingDocDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : save and update Supporting Doc : {} , user {}", supportingDocDTO, credentialsDTO.getUserID());

        SupportingDocDTO supportingDocUpadateDTOResponse = masterDataService.saveOrUpdateSupportingDocDTO(supportingDocDTO, credentialsDTO);

        LOG.info("END : save and update Supporting Doc : {} , user {}", supportingDocDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(supportingDocUpadateDTOResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_SUPPORTING_DOC_VIEW+ "')")
    @RequestMapping(value = "/getApprovedSupportingDocList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<SupportingDocDTO>> getApprovedSupportingDocList() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get approved Supporting Doc List  user {}", credentialsDTO.getUserID());

        List<SupportingDocDTO> approvedSupportingDocList = masterDataService.getApprovedSupportingDoc();

        LOG.info("END : get approved Supporting Doc List : {} , user {}", approvedSupportingDocList.size(), credentialsDTO.getUserID());

        return new ResponseEntity<>(approvedSupportingDocList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApprovedGlobalSupportingDocList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<GlobalSupportingDocDTO>> getApprovedGlobalSupportingDocList() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get approved Global Supporting Doc List  user {}", credentialsDTO.getUserID());

        List<GlobalSupportingDocDTO> approvedGlobalSupportingDocList = masterDataService.getApprovedGlobalSupportingDoc();

        LOG.info("END : get approved Global Supporting Doc List : {} , user {}", approvedGlobalSupportingDocList.size(), credentialsDTO.getUserID());

        return new ResponseEntity<>(approvedGlobalSupportingDocList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_SUPPORTING_DOC_APPROVE+ "')")
    @RequestMapping(value = "/approveOrRejectSupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<SupportingDocDTO> approveOrRejectSupportingDoc(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject SupportingDoc  : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        SupportingDocDTO approveRejectResponse = masterDataService.approveSupportingDocDTO(approveRejectRQ, credentialsDTO);

        LOG.info("END : Aprrove or Reject SupportingDoc : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse, HttpStatus.OK);
    }

}
