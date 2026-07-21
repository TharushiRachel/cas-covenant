package com.itechro.cas.controller.casmaster;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.applicationform.esg.AFAnnexRespDTO;
import com.itechro.cas.model.dto.esg.AnnexureDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casmaster.AnnexureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "${api.prefix}/annexes")
public class AnnexureController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AnnexureController.class);

    private final AnnexureService annexureService;

    public AnnexureController(AnnexureService annexureService) {
        this.annexureService = annexureService;
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getAnnexes", headers = "Accept=application/json")
    public ResponseEntity<AFAnnexRespDTO> getAnnexes() throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get annexes: user {}" , credentialsDTO.getUserID());

        AFAnnexRespDTO result = annexureService.getAnnexes();

        LOG.info("END : Get annexes: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getAnnexeById/{annexId}", headers = "Accept=application/json")
    public ResponseEntity<AnnexureDTO> getAnnexeById(@PathVariable Integer annexId) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get annexes by Id: user {}" , credentialsDTO.getUserID());

        AnnexureDTO result = annexureService.getAnnexeById(annexId);

        LOG.info("END : Get annexes by Id: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @GetMapping(value = "/getTempAnnexeById/{annexId}", headers = "Accept=application/json")
    public ResponseEntity<AnnexureDTO> getTempAnnexeById(@PathVariable Integer annexId) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Get annexes by Id: user {}" , credentialsDTO.getUserID());

        AnnexureDTO result = annexureService.getTempAnnexeById(annexId);

        LOG.info("END : Get annexes by Id: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/saveAnnex", headers = "Accept=application/json")
    public ResponseEntity<AFAnnexRespDTO> saveAnnex(@RequestBody AnnexureDTO annex) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Save annexes: annex {}, user {}" ,annex, credentialsDTO.getUserID());

        AFAnnexRespDTO result = annexureService.saveAnnex(annex, credentialsDTO);

        LOG.info("END : Save annexes: annex {}, user {}",annex, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/approveRejectAnnex", headers = "Accept=application/json")
    public ResponseEntity<AFAnnexRespDTO> approveRejectAnnex(@RequestBody AnnexureDTO annex) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Approve reject annexes: annex {}, user {}" ,annex, credentialsDTO.getUserID());

        AFAnnexRespDTO result = annexureService.approveRejectAnnex(annex, credentialsDTO);

        LOG.info("END : Approve reject annexes: annex {}, user {}",annex, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/deleteTempAnnexeById/{annexId}", headers = "Accept=application/json")
    public ResponseEntity<AFAnnexRespDTO> deleteTempAnnexeById(@PathVariable Integer annexId) throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Delete annexes by Id: user {}" , credentialsDTO.getUserID());

        AFAnnexRespDTO result = annexureService.deleteTempAnnexure(annexId);

        LOG.info("END : Delete annexes by Id: user {}",credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
