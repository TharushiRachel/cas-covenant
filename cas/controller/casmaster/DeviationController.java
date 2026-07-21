package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.casmaster.DeviationTypeDTO;
import com.itechro.cas.model.dto.facilitypaper.DeviationDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.MasterDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/deviation")
public class DeviationController extends BaseController {


    private static final Logger LOG = LoggerFactory.getLogger(DeviationController.class);

    private final MasterDataService masterDataService;

    public DeviationController(MasterDataService masterDataService) {
        this.masterDataService = masterDataService;
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getAllDeviationTypes", headers = "Accept=application/json")
    public ResponseEntity<List<DeviationTypeDTO>> getAllDeviationTypes() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get Deviation Types: user {}" , credentialsDTO.getUserID());

        List<DeviationTypeDTO> result = masterDataService.getAllDeviationTypes();

        LOG.info("END : Get Deviation Types: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveDeviationType", headers = "Accept=application/json")
    public ResponseEntity<List<DeviationTypeDTO>> getAllDeviationTypes(@RequestBody DeviationTypeDTO deviationTypeDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Save Deviation Type: user {}" , credentialsDTO.getUserID());

        List<DeviationTypeDTO> result = masterDataService.saveDeviationType(deviationTypeDTO, credentialsDTO);

        LOG.info("END : Save Deviation Type: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @ResponseExceptionHandler
    @GetMapping(value = "/getAllDeviations", headers = "Accept=application/json")
    public ResponseEntity<List<DeviationDTO>> getAllDeviations() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get Deviations: user {}" , credentialsDTO.getUserID());

        List<DeviationDTO> result = masterDataService.getAllDeviations();

        LOG.info("END : Get Deviations: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveDeviation", headers = "Accept=application/json")
    public ResponseEntity<List<DeviationDTO>> saveDeviation(@RequestBody DeviationDTO deviationDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Save Deviation: user {}" , credentialsDTO.getUserID());

        List<DeviationDTO> result = masterDataService.saveDeviation(deviationDTO, credentialsDTO);

        LOG.info("END : Save Deviation: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/authorizedDeviation", headers = "Accept=application/json")
    public ResponseEntity<List<DeviationDTO>> authorizedDeviation(@RequestBody DeviationDTO deviationDTO) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Authorized Deviation: user {}" , credentialsDTO.getUserID());

        List<DeviationDTO> result = masterDataService.authorizedDeviation(deviationDTO, credentialsDTO);

        LOG.info("END : Authorized Deviation: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
