package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.dto.casmaster.UpcSectionDTO;
import com.itechro.cas.model.dto.casmaster.UpcTemplateDTO;
import com.itechro.cas.model.dto.master.UpcSectionSearchRQ;
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
@RequestMapping(value = "${api.prefix}/upcSection")
public class SectionController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(SectionController.class);

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    @RequestMapping(value = "getPagedUPCSections", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<UpcSectionDTO>> getPagedSupportDoc(@RequestBody UpcSectionSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search upc section : {} , user {}", searchRQ, credentialsDTO.getUserID());

        Page<UpcSectionDTO> pageDataResult = masterDataService.getPagedUpcSectionDTO(searchRQ);

        LOG.info("START : Search upc section : {} , user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pageDataResult, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getUPCSectionUpdateDTO/{upcSectionID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<UpcSectionDTO> getUPCSectionDTOByID(@PathVariable Integer upcSectionID) throws Exception {

        LOG.info("START : Get UPC section dto by ID: {}", upcSectionID);

        UpcSectionDTO upcSectionDTO = this.masterDataService.getUpcSectionDTOByID(upcSectionID);

        LOG.info("END : Get UPC section dto By ID : {}", upcSectionID);

        return new ResponseEntity<>(upcSectionDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateUPCSection", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<UpcSectionDTO> saveOrUpdateLead(@RequestBody UpcSectionDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save/update UpcSectionDTO : {} by user {}", updateDTO, credentialsDTO.getUserID());

        UpcSectionDTO leadDTO = this.masterDataService.saveOrUpdateUpcSectionDTO(updateDTO, credentialsDTO);

        LOG.info("END : Save/update UpcSectionDTO : {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(leadDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/approveOrRejectUPCSection",headers ="Accept=application/json",method = RequestMethod.POST)
    public ResponseEntity<UpcSectionDTO> approveOrRejectUPCSection(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject Upc section  : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        UpcSectionDTO approveRejectResponse = masterDataService.approveUpcSectionDTO(approveRejectRQ,credentialsDTO);

        LOG.info("END : Aprrove or Reject Upc section : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse,HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getActiveApprovedUpcSectionListByTemplateID", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<UpcSectionDTO>> getActiveApprovedUpcSectionListByTemplateID(@RequestBody UpcTemplateDTO upcTemplateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: get active approved Upc Sections by Template ID: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        List<UpcSectionDTO> responseList = this.masterDataService.getActiveApprovedUpcSectionListByTemplateID(upcTemplateDTO);

        LOG.info("START: get active approved Upc Sections by Template ID: {} by: {}", credentialsDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(responseList, HttpStatus.OK);
    }
}
