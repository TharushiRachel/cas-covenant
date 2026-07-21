package com.itechro.cas.controller.casmaster;

import com.google.gson.Gson;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.applicationform.AFTTopicConfigDTO;
import com.itechro.cas.model.dto.applicationform.AFTopicConfigUploadRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/applicationTopicConfig")
public class ApplicationTopicConfigController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationFormTopicController.class);

    @Autowired
    private MasterDataService masterDataService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/uploadApplicationTopicConfigFile", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<AFTTopicConfigDTO>> uploadedApplicationTopicConfigFile(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                                                      @RequestParam("uploadRequestData") String uploadRQData) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        AFTopicConfigUploadRQ updateRQ = gson.fromJson(uploadRQData, AFTopicConfigUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.setMultipartFile(uploadFile);
        LOG.info("START : Upload Application From Topic Configurations : {} by user {}", null, credentialsDTO.getUserID());

        List<AFTTopicConfigDTO> result = this.masterDataService.uploadApplicationTopicConfigFile(updateRQ, credentialsDTO);

        LOG.info("END : Upload Application From Topic Configurations : {} by user {}", null, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @RequestMapping(value = "/getApplicationTopicConfigs", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<AFTTopicConfigDTO>> getApplicationTopicConfigs() throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Application Topic Configs user {} ", credentialsDTO.getUserID());

        List<AFTTopicConfigDTO> result = this.masterDataService.getApplicationTopicConfigs();

        LOG.info("END : Get Application Topic Configs user {} ", credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
