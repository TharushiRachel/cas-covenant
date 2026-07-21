package com.itechro.cas.controller.casv1;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.casv1.*;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.casv1.CasV1Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("${api.prefix}/casv1")
public class CasV1FacilityPaperController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(CasV1FacilityPaperController.class);

    @Autowired
    private CasV1Service casV1Service;

    @ResponseExceptionHandler
    @RequestMapping(value = "/getCustomerDetails/{accNo}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<List<CustomerDetailDTO>> getCustomerDetails(@PathVariable String accNo) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get customer data : {}, user {}", accNo, credentialsDTO.getUserID());

        List<CustomerDetailDTO> result = this.casV1Service.getCustomersDetailByAcc(accNo);

        LOG.info("END : Get customer data: {}, user {}", accNo, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getFacilityPaperDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<FacilityPaperDataDTO> getFacilityPaperDetails(@RequestBody FPRequestDTO fpRequestDTO) throws AppsException {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get facility paper data : {}, user {}", fpRequestDTO, credentialsDTO.getUserID());

        FacilityPaperDataDTO result = this.casV1Service.getFacilityPaperDetails(fpRequestDTO);

        LOG.info("END : Get facility paper data: {}, user {}", fpRequestDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value = "/getAttachmentDetails", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<AttachmentDetailDTO>> getAttachmentDetails(@RequestBody Map<String, Object> request) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get attachment data : {}, user {}", request, credentialsDTO.getUserID());

        String refNo = (String) request.get("refNo");
        String paperDate = (String) request.get("paperDate");
        String upcFormat = (String) request.get("upcFormat");

        LOG.info("END : Get attachment data: {}, user {}", request, credentialsDTO.getUserID());

        return new ResponseEntity<>(casV1Service.getAttachments(refNo, paperDate, upcFormat), HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getSections", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<SectionDetailDTO>> getSections(@RequestBody Map<String, String> request) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get attachment data : {}, user {}", credentialsDTO.getUserID());

        String upcFormatNum = request.get("upcFormatNum");

        LOG.info("END : Get attachment data: {}, user {}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(casV1Service.getDropdownSections(upcFormatNum), HttpStatus.OK);

    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getComments", headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<List<CommentTableDTO>> getComments(@RequestBody Map<String, String> request) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Get attachment data : {}, user {}", credentialsDTO.getUserID());

        String refNo = (String) request.get("refNo");
        String paperDate = (String) request.get("paperDate");

        LOG.info("END : Get attachment data: {}, user {}",  credentialsDTO.getUserID());

        return new ResponseEntity<>(casV1Service.getComments(refNo, paperDate), HttpStatus.OK);

    }
}
