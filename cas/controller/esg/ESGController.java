package com.itechro.cas.controller.esg;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.esg.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.esg.ESGService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/esg")
@Slf4j
public class ESGController extends BaseController {

    private final ESGService esgService;

    public ESGController(ESGService esgService) {
        this.esgService = esgService;
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveAnnexureAnswer", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<AnswerDataDTO>> saveDataToAnnexure(@RequestBody List<AnswerDataDTO> answerDataDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | saveDataToAnnexure - ESGController | request  {}, by {}",answerDataDTO, credentialsDTO.getUserID());

        List<AnswerDataDTO> response = esgService.saveDataToAnnexure(answerDataDTO, credentialsDTO);

        log.info("END | saveDataToAnnexure - ESGController | response  {}",response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateDataToAnnexure/{annexureId}", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<AnswerDataDTO>> updateDataToAnnexure(
            @PathVariable Integer annexureId,
            @RequestParam (required = false) Integer applicationFormID,
            @RequestParam (required = false)  Integer facilityPaperID,
            @RequestBody List<AnswerDataDTO> answerDataDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | updateDataToAnnexure - ESGController | annexureId {}, applicationFormID {}, facilityPaperID {}, request  {}, by {}",annexureId, applicationFormID, facilityPaperID, answerDataDTO, credentialsDTO.getUserID());

        List<AnswerDataDTO> response = esgService.updateDataToAnnexure(annexureId, applicationFormID, facilityPaperID, answerDataDTO, credentialsDTO);

        log.info("END | updateDataToAnnexure - ESGController | response  {}",response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnnexureByAnnexureId/{annexureId}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<AnnexureDTO> getAnnexureByAnnexureId(@PathVariable Integer annexureId) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | getAnnexureByAnnexureId - ESGController | by {}", credentialsDTO.getUserID());

        AnnexureDTO afAnnexureDTO = esgService.getAnnexureByAnnexureId(annexureId, credentialsDTO);

        log.info("END | getAnnexureByAnnexureId - ESGController | response {}", afAnnexureDTO);

        return new ResponseEntity<>(afAnnexureDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnnexureList", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<AFAnnexureListDTO>> getAnnexureList() throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | getAnnexureList - ESGController | by {}", credentialsDTO.getUserID());

        List<AFAnnexureListDTO> afAnnexureDTO = esgService.getAnnexureList(credentialsDTO);

        log.info("END | getAnnexureList - ESGController | response {}", afAnnexureDTO);

        return new ResponseEntity<>(afAnnexureDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAnnexureByPaperID", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<AnnexureDTO>> getAnnexureDataByApplicationFormIdOrFacilityPaperId(@RequestBody AnswerDataRequestDTO answerDataRequestDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START : Search Application Form {}", credentialsDTO.getUserID());

        List<AnnexureDTO> afAnnexureDataList = esgService.getAnnexureDataByApplicationFormIdOrFacilityPaperId(answerDataRequestDTO, credentialsDTO);

        log.info("END : Search Application Form {}", credentialsDTO.getUserID());

        return new ResponseEntity<>(afAnnexureDataList, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deleteAnnexure", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AnswerDataDTO> deleteAnnexureByAnnexureId(@RequestBody DeleteAnnexureDTO deleteAnnexureDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | deleteAnnexureByAnnexureId - ESGController | by {}", credentialsDTO.getUserID());

        AnswerDataDTO response = esgService.deleteAnnexureByAnnexureId(deleteAnnexureDTO, credentialsDTO);

        log.info("END | deleteAnnexureByAnnexureId - ESGController | response {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/refreshAnnexure", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<AnnexureDTO> refreshAnnexure(@RequestBody DeleteAnnexureDTO deleteAnnexureDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | refreshAnnexure - ESGController | by {}", credentialsDTO.getUserID());

        AnnexureDTO response = esgService.refreshAnnexure(deleteAnnexureDTO, credentialsDTO);

        log.info("END | refreshAnnexure - ESGController | response {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/addAttachments", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<EsgDocStorageDTO>> addAttachments(@RequestBody EsgDocStorageDTO esgDocStorageDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();


        log.info("START | addAttachments - ESGController | name: {}, fileName: {}, user: {}",
                esgDocStorageDTO.getName(),
                esgDocStorageDTO.getFileName(),
                credentialsDTO.getUserID());

        List<EsgDocStorageDTO> response = esgService.addAttachments(esgDocStorageDTO, credentialsDTO);

        log.info("END | addAttachments - ESGController | response size {}", response.size());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAttachments", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<EsgDocStorageDTO>> getAttachments(@RequestBody DeleteAnnexureDTO deleteAnnexureDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | addAttachments - ESGController | by {}", credentialsDTO.getUserID());

        List<EsgDocStorageDTO> response = esgService.getAttachments(deleteAnnexureDTO, credentialsDTO);

        log.info("END | addAttachments - ESGController | response {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getAttachmentById/{esgStorageID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getAttachmentById(@PathVariable Integer esgStorageID) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | getAttachmentById - ESGController | by {}", credentialsDTO.getUserID());

        byte[] response = esgService.getAttachmentById(esgStorageID, credentialsDTO);

        //log.info("END | getAttachmentById - ESGController | response {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/updateAttachment/{esgStorageID}", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<EsgDocStorageDTO>> updateAttachment(@PathVariable Integer esgStorageID, @RequestBody EsgDocStorageDTO esgDocStorageDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | updateAttachment - ESGController | by {}", credentialsDTO.getUserID());

        List<EsgDocStorageDTO> response = esgService.updateAttachment(esgStorageID, esgDocStorageDTO, credentialsDTO);

        log.info("END | updateAttachment - ESGController | response {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deleteAttachment", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<EsgDocStorageDTO> deleteAttachment(@RequestBody EsgDocStorageDTO esgDocStorageDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        log.info("START | deleteAttachment - ESGController | by {}", credentialsDTO.getUserID());

        EsgDocStorageDTO response = esgService.deleteAttachment(esgDocStorageDTO.getEsgStorageID(), credentialsDTO);

        log.info("END | deleteAttachment - ESGController | response {}", response);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
