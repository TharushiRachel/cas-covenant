package com.itechro.cas.controller.casmaster;

import com.itechro.cas.commons.constants.PrivilegeCode;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.dto.casmaster.UpcTemplateDTO;
import com.itechro.cas.model.dto.casmaster.UpcTemplateSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "${api.prefix}/upcTemplate")
public class UPCTemplateController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(UPCTemplateController.class);

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_SUPPORTING_DOC_VIEW+ "')")
    @RequestMapping(value = "getPagedUPCTemplate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<UpcTemplateDTO>> getPagedSupportDoc(@RequestBody UpcTemplateSearchRQ searchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search upc template : {} , user {}", searchRQ, credentialsDTO.getUserID());

        Page<UpcTemplateDTO> pageDataResult = masterDataService.getPagedUpcTemplateDTO(searchRQ);

        LOG.info("START : Search upc template : {} , user {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pageDataResult, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getUPCTemplateUpdateDTO/{templateID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<UpcTemplateDTO> getUPCSectionDTOByID(@PathVariable Integer templateID) throws Exception {

        LOG.info("START : Get UPC template dto by ID: {}", templateID);

        UpcTemplateDTO upcSectionDTO = this.masterDataService.getUpcTemplateDTOByID(templateID);

        LOG.info("END : Get UPC template dto By ID : {}", templateID);

        return new ResponseEntity<>(upcSectionDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_LEAD_ADD_EDIT+ "')")
    @RequestMapping(value = "/saveOrUpdateUPCTemplate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<UpcTemplateDTO> saveOrUpdateLead(@RequestBody UpcTemplateDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save/update UPC template DTO : {} by user {}", updateDTO, credentialsDTO.getUserID());

        UpcTemplateDTO updatedDTO = this.masterDataService.saveOrUpdateUpcTemplateDTO(updateDTO, credentialsDTO);

        LOG.info("END : Save/update UPCTemplateDTO : {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_UPC_TEMPLATE_APPROVE+ "')")
    @RequestMapping(value = "/approveOrRejectUPCTemplate",headers ="Accept=application/json",method = RequestMethod.POST)
    public ResponseEntity<UpcTemplateDTO> approveOrRejectUPCTemplate(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject Upc   : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        UpcTemplateDTO approveRejectResponse = masterDataService.approveUpcTemplateDTO(approveRejectRQ,credentialsDTO);

        LOG.info("END : Aprrove or Reject Upc template : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse,HttpStatus.OK);
    }
}
