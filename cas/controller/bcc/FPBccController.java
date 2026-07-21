package com.itechro.cas.controller.bcc;

import com.google.gson.Gson;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.controller.facilitypaper.FacilityPaperController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.bcc.FPBccDTO;
import com.itechro.cas.model.dto.bcc.FPBccDocDeleteRQ;
import com.itechro.cas.model.dto.bcc.FPBccDocUploadRQ;
import com.itechro.cas.model.dto.bcc.FPBccDocumentDTO;
import com.itechro.cas.model.dto.facilitypaper.FPDocumentDTO;
import com.itechro.cas.model.dto.facilitypaper.FPDocumentUploadRQ;
import com.itechro.cas.model.dto.facilitypaper.FacilityPaperDTO;
import com.itechro.cas.model.dto.facilitypaper.RemarkDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.bcc.FPBccService;
import com.itechro.cas.service.faclititypaper.FacilityPaperService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/fbBcc")
public class FPBccController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(FacilityPaperController.class);

    @Autowired
    private FacilityPaperService facilityPaperService;

    @Autowired
    private FPBccService fpBccService;

    @Autowired
    private ModelMapper modelMapper;
    private com.itechro.cas.model.dto.bcc.FPBccDocumentDTO FPBccDocumentDTO;

    @ResponseExceptionHandler
    @RequestMapping(value = "/uploadFpBccDocument", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> uploadBccDocument(@RequestParam("uploadingFile") MultipartFile[] uploadFiles,
                                                              @RequestParam("uploadRequestData") String uploadRQData) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        FPBccDocUploadRQ uploadDocRQ = gson.fromJson(uploadRQData, FPBccDocUploadRQ.class);

        MultipartFile uploadFile = uploadFiles[0];
        uploadDocRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START : Upload facility paper document : {} by user {}", uploadDocRQ, credentialsDTO.getUserID());

        FacilityPaperDTO facilityPaperDTO = this.fpBccService.uploadFpBccDocument(uploadDocRQ, credentialsDTO);

        LOG.info("END : Upload facility paper document : {} by user {}", facilityPaperDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(facilityPaperDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/deactivateFpBccDoc", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDTO> deactivateFpBccDoc(@RequestBody FPBccDocDeleteRQ fpBccDocDeleteRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: Deactivate the Fp Bcc document: {} by: {}", fpBccDocDeleteRQ, credentialsDTO.getUserName());

        FacilityPaperDTO response = this.fpBccService.deactivateFpBccDoc(fpBccDocDeleteRQ, credentialsDTO);

        LOG.info("END: Deactivate the Fp Bcc document: {} by: {}", fpBccDocDeleteRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/authorizeBCCDocs", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FPBccDTO> authorizeBCCDocs(@RequestBody FPBccDocumentDTO fpBccDocumentDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: authorizeBCCDocs: {} by: {}", fpBccDocumentDTO, credentialsDTO.getUserName());

        FPBccDTO response = this.fpBccService.authorizeBCCDocs(fpBccDocumentDTO, credentialsDTO);

        LOG.info("END: authorizeBCCDocs: {} by: {}", response, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/forwardBCCDocs", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FPBccDTO> forwardBCCDocs(@RequestBody FPBccDTO fpBccDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START: forwardBCCDocs: {} by: {}", fpBccDTO, credentialsDTO.getUserName());

        FPBccDTO response = this.fpBccService.forwardBCCDocs(fpBccDTO.getFpBccId(), credentialsDTO);

        LOG.info("END: forwardBCCDocs: {} by: {}", response, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
