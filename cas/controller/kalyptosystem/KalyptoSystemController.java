package com.itechro.cas.controller.kalyptosystem;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.integration.request.LoadKalyptoDataRQ;
import com.itechro.cas.model.dto.integration.response.KalyptoRS;
import com.itechro.cas.model.dto.kalyptosystem.KalyptoValueNameRPDTO;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoRQ;
import com.itechro.cas.model.dto.kalyptosystem.request.KalyptoValuesRQ;
import com.itechro.cas.model.dto.kalyptosystem.response.KalyptoDTO;
import com.itechro.cas.model.dto.kalyptosystem.response.KalyptoIntegrationDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.kalyptosystem.KalyptoSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.prefix}/kalypto")
public class KalyptoSystemController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(KalyptoSystemController.class);
    private final KalyptoSystemService kalyptoSystemService;

    @Autowired
    public KalyptoSystemController(KalyptoSystemService kalyptoSystemService) {
        this.kalyptoSystemService = kalyptoSystemService;
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getKalyptoValues", headers = "Accept=application/json")
    public ResponseEntity<KalyptoValueNameRPDTO> getKalyptoValues(@RequestBody KalyptoRQ kalyptoRQ) throws AppsException{
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get KALYPTO VALUES  Controller : {} , user {}", kalyptoRQ, credentialsDTO);

        KalyptoValueNameRPDTO result = kalyptoSystemService.getKalyptoValues(kalyptoRQ, credentialsDTO);

        LOG.info("END : Get KALYPTO VALUES  : {} , user {}", kalyptoRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/isExistKalyptoData", headers = "Accept=application/json")
    public ResponseEntity<Boolean> isExistKalyptoDataController(@RequestBody KalyptoRQ kalyptoRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Is Exist KalyptoData Controller : {}", kalyptoRQ);

        Boolean isExist = kalyptoSystemService.isExistKalyptoDataService(kalyptoRQ);

        LOG.info("END  : Is Exist KalyptoData Controller: {} , user {}", isExist, credentialsDTO.getUserID());

        return new ResponseEntity<>(isExist, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/isAddedNewKalypto", headers = "Accept=application/json")
    public ResponseEntity<Boolean> isAddedNewKalyptoValueController(@RequestBody KalyptoRQ kalyptoRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Is Exist KalyptoData Controller : {} , user {}", kalyptoRQ, credentialsDTO);

        Boolean isExist = kalyptoSystemService.isAddedNewKalyptoValueService(kalyptoRQ, credentialsDTO);

        LOG.info("END : Is Exist KalyptoData Controller: {} , user {}", isExist, credentialsDTO.getUserID());

        return new ResponseEntity<>(isExist, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getDirectIntegrationService", headers = "Accept=application/json")
    public ResponseEntity<KalyptoRS> getKalyptoFromIntegrationService(@RequestBody LoadKalyptoDataRQ loadKalyptoDataRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get KALYPTO From Integration Service Controller : {} , user {}", loadKalyptoDataRQ, credentialsDTO);

        KalyptoRS kalyptoRS = kalyptoSystemService.getDirectIntegrationService(loadKalyptoDataRQ, credentialsDTO);

        LOG.info("END : Get KALYPTO From Integration Service Controller : {} , user {}", loadKalyptoDataRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(kalyptoRS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getKalyptoFromIntegrationService", headers = "Accept=application/json")
    public ResponseEntity<KalyptoIntegrationDTO> getKalyptoFromIntegrationService(@RequestBody KalyptoRQ kalyptoRQ)throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get KALYPTO From Integration Service Controller : {} , user {}", kalyptoRQ, credentialsDTO);

        KalyptoIntegrationDTO kalyptoIntegrationDTO = kalyptoSystemService.getKalyptoFromIntegrationService(kalyptoRQ, credentialsDTO);

        LOG.info("END : Get KALYPTO From Integration Service Controller : {} , user {}", kalyptoRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(kalyptoIntegrationDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getKalyptoDetails", headers = "Accept=application/json")
    public ResponseEntity<KalyptoDTO> getKalyptoDetails(@RequestBody KalyptoRQ kalyptoRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get KALYPTO Details Controller : {} , user {}", kalyptoRQ, credentialsDTO);

        KalyptoDTO result = kalyptoSystemService.getKalyptoDetails(kalyptoRQ, credentialsDTO);

        LOG.info("END : Get KALYPTO Details Controller : {} , user {}", result, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveEditedKalypto", headers = "Accept=application/json")
    public ResponseEntity<Boolean> saveEditedKalyptoController(@RequestBody KalyptoValuesRQ kalyptoValuesRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Save Edited KALYPTO Controller : {} , user {}", kalyptoValuesRQ, credentialsDTO);

        Boolean isSucess = kalyptoSystemService.saveEditedKALYPTOService(kalyptoValuesRQ, credentialsDTO);

        LOG.info("END : Save Edited KALYPTO Controller: {} , user {}", isSucess, credentialsDTO.getUserID());

        return new ResponseEntity<>(isSucess, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/refreshKalyptoValues", headers = "Accept=application/json")
    public ResponseEntity<KalyptoValueNameRPDTO> refreshKalyptoValues(@RequestBody KalyptoRQ kalyptoRQ) throws AppsException{
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get KALYPTO From Refresh Controller : {} , user {}", kalyptoRQ, credentialsDTO);

        KalyptoValueNameRPDTO result = kalyptoSystemService.refreshKalyptoValues(kalyptoRQ, credentialsDTO);

        LOG.info("END : Get KALYPTO From Refresh Controller : {} , user {}", kalyptoRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
