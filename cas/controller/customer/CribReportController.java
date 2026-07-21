package com.itechro.cas.controller.customer;

import com.google.gson.Gson;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.model.dto.customer.CribSearchHistoryDTO;
import com.itechro.cas.model.dto.customer.CustomerCribResponseDTO;
import com.itechro.cas.model.dto.integration.request.CustomerCASCribRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribCorporateRQ;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRequestDTO;
import com.itechro.cas.model.dto.integration.request.cribreport.CribRetailReportRQ;
import com.itechro.cas.model.dto.integration.response.cribreport.CribResponseDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.cribReport.CribReportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/cribReport")
public class CribReportController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CribReportController.class);

    @Autowired
    private CribReportService cribReportService;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getRetailCribReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getRetailCribReport(@RequestBody CribRetailReportRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Retail Crib report {} : by {}", searchRQ, credentialsDTO.getUserID());

        String bccReport = this.cribReportService.getRetailCribReport(searchRQ, credentialsDTO);

        LOG.info("END : Get Retail Crib report {} : by {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(bccReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCorporateCribReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<String> getCorporateCribReport(@RequestBody CribCorporateRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Corporate Crib report {} : by {}", searchRQ, credentialsDTO.getUserID());

        String bccReport = this.cribReportService.getCorporateCribReport(searchRQ, credentialsDTO);

        LOG.info("END : Get Corporate Crib report {} : by {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(bccReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCribReportFromCasDB", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<CustomerCribResponseDTO>> getRetailCribReportFromCasDB(@RequestBody CustomerCASCribRQ searchRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Previous Retail Crib report from cas DB {} : by {}", searchRQ, credentialsDTO.getUserID());

        List<CustomerCribResponseDTO> customerCribResponseDTOS = this.cribReportService.getCribReportFromCasDB(searchRQ, credentialsDTO);

        LOG.info("END : Get Previous Retail Crib report {} : by {}", searchRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(customerCribResponseDTOS, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/searchIndividualCrib", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribResponseDTO> searchIndividualCrib(@RequestBody CribRequestDTO cribRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Individual Crib {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        CribResponseDTO cribReport = this.cribReportService.searchIndividualCrib(cribRequestDTO);

        LOG.info("END : Search Individual Crib {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(cribReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/searchIndividualCribContinue", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribResponseDTO> searchIndividualCribContinue(@RequestBody CribRequestDTO cribRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Individual Crib Continue {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        CribResponseDTO cribReport = this.cribReportService.searchIndividualCribContinue(cribRequestDTO);

        LOG.info("END : Search Individual Crib Continue {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(cribReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/searchCompanyCrib", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribResponseDTO> searchCompanyCrib(@RequestBody CribRequestDTO cribRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Company Crib {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        CribResponseDTO cribReport = this.cribReportService.searchCompanyCrib(cribRequestDTO);

        LOG.info("END : Search Company Crib {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(cribReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/searchCompanyCribContinue", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribResponseDTO> searchCompanyCribContinue(@RequestBody CribRequestDTO cribRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Search Company Crib Continue {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        CribResponseDTO cribReport = this.cribReportService.searchCompanyCribContinue(cribRequestDTO);

        LOG.info("END : Search Company Crib Continue {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(cribReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomReport", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribResponseDTO> getCustomReport(@RequestBody CribRequestDTO cribRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Custom Report Token {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        CribResponseDTO cribReport = this.cribReportService.getCustomReport(cribRequestDTO);

        LOG.info("END : Get Custom Report Token {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(cribReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCribReportPDF", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribResponseDTO> getCribReportPDF(@RequestBody CribRequestDTO cribRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get Crib report PDF {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        CribResponseDTO cribReport = this.cribReportService.getCustomReportPDF(cribRequestDTO);

        LOG.info("END : Get Crib report PDF {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(cribReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomReportByToken", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribResponseDTO> getCustomReportByToken(@RequestBody CribRequestDTO cribRequestDTO) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Check Crib report token {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        CribResponseDTO cribReport = this.cribReportService.getCustomReportByToken(cribRequestDTO);

        LOG.info("END : Check Crib report token {} : by {}", cribRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(cribReport, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/saveCribSearch", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<CribSearchHistoryDTO> saveCribSearch(@RequestParam("uploadingFile") MultipartFile[] uploadFiles, @RequestParam("uploadRequestData") String uploadRQData) throws Exception {
        CredentialsDTO credentialsDTO = getCredentialsDTO();
        Gson gson = new Gson();
        CribSearchHistoryDTO updateRQ = gson.fromJson(uploadRQData, CribSearchHistoryDTO.class);

        MultipartFile uploadFile = uploadFiles[0];
        updateRQ.getDocStorageDTO().setFileName(uploadFile.getOriginalFilename());
        updateRQ.getDocStorageDTO().setDocument(uploadFile.getBytes());

        LOG.info("START: Save Crib Search History: {} by: {}", updateRQ, credentialsDTO.getUserName());

        CribSearchHistoryDTO response = this.cribReportService.saveCribSearch(updateRQ, credentialsDTO);

        LOG.info("END: Save Crib Search History: {} by: {}", updateRQ, credentialsDTO.getUserName());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
