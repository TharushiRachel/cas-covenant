package com.itechro.cas.controller.facility;

import com.google.gson.Gson;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.controller.facilitypaper.FacilityPaperController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.facility.*;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.facility.FacilityService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RestController
@RequestMapping(value = "${api.prefix}/facility")
public class facilityController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperController.class);

    @Autowired
    private FacilityService facilityService;

    @Autowired
    private ModelMapper modelMapper;

    @ResponseExceptionHandler
    @RequestMapping(value = "/uploadFacilityDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> uploadFacilityDocument(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                                   @RequestParam("uploadRequestData") String uploadRQData) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        FacilityDocumentUploadRQ updateRQ = gson.fromJson(uploadRQData, FacilityDocumentUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload facility document : {} by user {}", updateRQ, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.facilityService.uploadFacilityDocument(updateRQ, credentialsDTO);

        LOG.info("END : Upload facility document : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedFacility", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<FacilityDTO>> getPagedFacility(@RequestBody FacilitySearchRQ facilitySearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Facility : {} , user {}", facilitySearchRQ, credentialsDTO.getUserID());

        Page<FacilityDTO> pagedResponse = facilityService.getPagedFacilityDTO(facilitySearchRQ);

        LOG.info("END : Search Facility : {} , user {}", facilitySearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(pagedResponse, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityUpdateDto/{facilityID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<FacilityDTO> getFacilityUpdateDto(@PathVariable Integer facilityID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get  update facility : {} , user {}", facilityID, credentialsDTO.getUserID());

        FacilityDTO Response = facilityService.getFacilityByID(facilityID);

        LOG.info("END : get  update facility : {} , user {}", facilityID, credentialsDTO.getUserID());

        return new ResponseEntity<>(Response, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateFacilitySupportingDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deactivateFacilitySupportingDoc(@RequestBody FacilityDocumentDTO facilityDocumentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the facility supporting document: {} by: {}", facilityDocumentDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityService.deactivateFacilitySupportingDoc(facilityDocumentDTO, credentialsDTO);

        LOG.info("END: Deactivate the facility supporting document: {} by: {}", facilityDocumentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateFacilityRepayment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> saveOrUpdateFacilityRepayment(@RequestBody FacilityRepaymentDTO facilityRepaymentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Save facility repayment: {} by: {}", facilityRepaymentDTO, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.facilityService.saveOrUpdateFacilityRepayment(facilityRepaymentDTO, credentialsDTO);

        LOG.info("END: START: Save facility repayment: {} by: {}", facilityRepaymentDTO, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityList/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getApplicationCovenantListByFpRefNumber(@PathVariable Integer facilityPaperID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get  update facility : {} , user {}", facilityPaperID, credentialsDTO.getUserID());

        List<FacilityListForCovenantDTO> Response = facilityService.getFacilities(facilityPaperID, credentialsDTO);

        LOG.info("END : get  update facility : {} , user {}", facilityPaperID, credentialsDTO.getUserID());

        return new ResponseEntity<>(Response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getDeviationCount/{facilityPaperID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<Object> getDeviationCount(@PathVariable Integer facilityPaperID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : get Deviation Count : {} , user {}", facilityPaperID, credentialsDTO.getUserID());

        Integer Response = facilityService.getDeviationCount(facilityPaperID, credentialsDTO);

        LOG.info("END :get Deviation Count  : {} , user {}", facilityPaperID, credentialsDTO.getUserID());

        return new ResponseEntity<>(Response, HttpStatus.OK);
    }


}
