package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.*;
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
@RequestMapping(value = "${api.prefix}/creditFacility")
public class CreditFacilityController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CreditFacilityController.class);

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedCreditFacilityTypes", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<CreditFacilityTypeDTO>> getPagedCreditFacilityTypes(@RequestBody CreditFacilityTypeSearchRQ creditFacilityTypeSearchRQ) throws AppsException {


        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search Credit facility types : {} , user {}", creditFacilityTypeSearchRQ, credentialsDTO.getUserID());

        Page<CreditFacilityTypeDTO> pagedCreditFacilityTypes = masterDataService.getPagedCreditFacilityTypeDTO(creditFacilityTypeSearchRQ);

        LOG.info("START : Search Credit facility types : {} , user {}", creditFacilityTypeSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedCreditFacilityTypes, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_CREDIT_FACILITY_TEMPLATE_VIEW+ "')")
    @RequestMapping(value = "/getPagedCreditFacilityTemplates", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<CreditFacilityTemplateDTO>> getPagedCreditFacilityTemplates(@RequestBody CreditFacilityTemplateSearchRQ creditFacilityTemplateSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search Credit facility templates : {} , user {}", creditFacilityTemplateSearchRQ, credentialsDTO.getUserID());

        Page<CreditFacilityTemplateDTO> pagedCreditFacilityTemplates = masterDataService.getPagedCreditFacilityTemplateDTO(creditFacilityTemplateSearchRQ);

        LOG.info("END : Search Credit facility templates : {} , user {}", creditFacilityTemplateSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedCreditFacilityTemplates, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCreditFacilityTypeUpdateDTO/{creditFacilityTypeID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<CreditFacilityTypeDTO> getCreditFacilityTypeUpdateDTO(@PathVariable Integer creditFacilityTypeID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get Credit facility types : {} , user {}", creditFacilityTypeID, credentialsDTO.getUserID());

        CreditFacilityTypeDTO creditFacilityTypeUpdateDTO = masterDataService.getCreditFacilityTypeByID(creditFacilityTypeID);

        LOG.info("END : get Credit facility type : {} , user {}", creditFacilityTypeID, credentialsDTO.getUserID());

        return new ResponseEntity<>(creditFacilityTypeUpdateDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateCreditFacilityType", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CreditFacilityTypeDTO> saveOrUpdateCreditFacilityType(@RequestBody CreditFacilityTypeDTO creditFacilityTypeDTO) throws AppsException {


        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : save Credit facility types : {} , user {}", creditFacilityTypeDTO, credentialsDTO.getUserID());

        CreditFacilityTypeDTO creditFacilityTypeUpdateDTOResponse = masterDataService.saveOrUpdateCreditFacilityTypeDTO(creditFacilityTypeDTO, credentialsDTO);

        LOG.info("END : save Credit facility types : {} , user {}", creditFacilityTypeDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(creditFacilityTypeUpdateDTOResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_CREDIT_FACILITY_TEMPLATE_VIEW+ "')")
    @RequestMapping(value = "/getCreditFacilityTemplateUpdateDTO/{creditFacilityTemplateID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<CreditFacilityTemplateDTO> getCreditFacilityTemplateUpdateDTO(@PathVariable Integer creditFacilityTemplateID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get Credit facility templates : {} , user {}", creditFacilityTemplateID, credentialsDTO.getUserID());

        CreditFacilityTemplateDTO creditFacilityTemplateUpdateDTO = masterDataService.getCreditFacilityTemplateByID(creditFacilityTemplateID);

        LOG.info("START : get Credit facility templates : {} , user {}", creditFacilityTemplateID, credentialsDTO.getUserID());

        return new ResponseEntity<>(creditFacilityTemplateUpdateDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_CREDIT_FACILITY_TEMPLATE_ADD_EDIT+ "')")
    @RequestMapping(value = "/saveOrUpdateCreditFacilityTemplate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CreditFacilityTemplateDTO> saveOrUpdateCreditFacilityTemplate(@RequestBody CreditFacilityTemplateDTO creditFacilityTemplateDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : save Credit facility templates : {} , user {}", creditFacilityTemplateDTO, credentialsDTO.getUserID());

        CreditFacilityTemplateDTO creditFacilityTemplateUpdateDTOResponse = masterDataService.saveOrUpdateCreditFacilityTemplateDTO(creditFacilityTemplateDTO, credentialsDTO);

        LOG.info("START : save Credit facility templates : {} , user {}", creditFacilityTemplateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(creditFacilityTemplateUpdateDTOResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getApprovedFacilityTypeList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CreditFacilityTypeDTO>> getApprovedFacilityTypeList() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get approved Credit facility types , user {}", credentialsDTO.getUserID());

        List<CreditFacilityTypeDTO> approvedCreditFacilityTypeList = masterDataService.getApprovedCreditFacilityType();

        LOG.info("END : get approved Credit facility types size : {} , user {}", approvedCreditFacilityTypeList.size(), credentialsDTO.getUserID());

        return new ResponseEntity<>(approvedCreditFacilityTypeList, HttpStatus.OK);


    }

    @ResponseExceptionHandler
    //@PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_SETTING_CREDIT_FACILITY_TEMPLATE_APPROVE+ "')")
    @RequestMapping(value = "/approveOrRejectCreditFacilityTemplate", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CreditFacilityTemplateDTO> approveOrRejectCreditFacilityTemplate(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject Credit facility Template : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        CreditFacilityTemplateDTO approveRejectResponse = masterDataService.approveCreditFacilityTemplateDTO(approveRejectRQ, credentialsDTO);

        LOG.info("END : Aprrove or Reject Credit facility Template : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/approveOrRejectCreditFacilityType", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CreditFacilityTypeDTO> approveOrRejectCreditFacilityType(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Aprrove or Reject Credit facility  : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        CreditFacilityTypeDTO approveRejectResponse = masterDataService.approveCreditFacilityTypeDTO(approveRejectRQ, credentialsDTO);

        LOG.info("END : Aprrove or Reject Credit facility type : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAllCftInterestRateDTOS", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CftInterestRateDTO>> getAllCftInterestRateDTOS() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get all cft intreest rates by user {}", credentialsDTO.getUserID());

        List<CftInterestRateDTO> responseListDTO = masterDataService.getAllCftInterestRateDTOS();

        LOG.info("END : Get all cft intreest rates : {} , by user {}", responseListDTO, credentialsDTO.getUserID());
        return new ResponseEntity<>(responseListDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityTypes", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<FacilityTypeDTO>> getFacilityTypes() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get all cft intreest rates by user {}", credentialsDTO.getUserID());

        List<FacilityTypeDTO> facilityTypeDTOList = masterDataService.getFacilityTypes();

        LOG.info("END : Get all cft intreest rates : {} , by user {}", facilityTypeDTOList, credentialsDTO.getUserID());
        return new ResponseEntity<>(facilityTypeDTOList, HttpStatus.OK);
    }

}
