package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.dto.casmaster.AFTopicDTO;
import com.itechro.cas.model.dto.casmaster.AFTopicSearchRQ;
import com.itechro.cas.model.dto.casmaster.ApproveRejectRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.prefix}/applicationFormTopic")
public class ApplicationFormTopicController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormTopicController.class);

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getPagedAFTopics", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<AFTopicDTO>> getPagedAFTopics(@RequestBody AFTopicSearchRQ afTopicSearchRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search Application Form Paged Topics : {} , user {}", afTopicSearchRQ, credentialsDTO.getUserID());

        Page<AFTopicDTO> afTopicDTOPage = masterDataService.getPagedAFTopics(afTopicSearchRQ);

        LOG.info("START : Search Application Form Topics : {} , user {}", afTopicSearchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(afTopicDTOPage, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/saveOrUpdateAFTopic", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AFTopicDTO> saveOrUpdateAFTopic(@RequestBody AFTopicDTO updateDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save/update Application Form Topic DTO : {} by user {}", updateDTO, credentialsDTO.getUserID());

        AFTopicDTO updatedDTO = this.masterDataService.saveOrUpdateAFTopic(updateDTO, credentialsDTO);

        LOG.info("END : Save/update Application Form Topic : {} by user {}", updateDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(updatedDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAFTopicByID/{afTopicID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<AFTopicDTO> getAFTopicByID(@PathVariable Integer afTopicID) throws Exception {

        LOG.info("START : Get Application Form Topic dto by ID: {}", afTopicID);

        AFTopicDTO dataDTO = this.masterDataService.getAFTopicByID(afTopicID);

        LOG.info("END : Get Application Form Topic dto By ID : {}", afTopicID);

        return new ResponseEntity<>(dataDTO, HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/approveAFTopic", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AFTopicDTO> approveAFTopic(@RequestBody ApproveRejectRQ approveRejectRQ) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Approve or Reject Application Form Topic  : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        AFTopicDTO approveRejectResponse = masterDataService.approveAFTopic(approveRejectRQ, credentialsDTO);

        LOG.info("END : Approve or Reject Application Form Topic : {} , user {}", approveRejectRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(approveRejectResponse, HttpStatus.OK);
    }


}
