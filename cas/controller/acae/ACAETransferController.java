package com.itechro.cas.controller.acae;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.acae.request.ACAERecordsForTransferRQ;
import com.itechro.cas.model.dto.acae.request.ACAETransferOptionRQ;
import com.itechro.cas.model.dto.acae.response.ACAERecordsForTransferDTO;
import com.itechro.cas.model.dto.casmaster.LoggedInUserWorkFlowByStatusRQ;
import com.itechro.cas.model.dto.casmaster.UpmGroupDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.acae.ACAETransferService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${api.prefix}/acae/transfer")
public class ACAETransferController extends BaseController {
    private static final Logger LOG = LoggerFactory.getLogger(ACAETransferController.class);

    private final ACAETransferService acaeTransferService;

    @Autowired
    public ACAETransferController(ACAETransferService acaeTransferService) {
        this.acaeTransferService = acaeTransferService;
    }

    @ResponseExceptionHandler
    @PostMapping(value = "/getACAERecordsForTransfer", headers = "Accept=application/json")
    public ResponseEntity<List<ACAERecordsForTransferDTO>> getACAERecordsForTransferController(
                @RequestBody ACAERecordsForTransferRQ acaeRecordsForTransferRQ) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : ACAE Records For Transfer : {} , user {}", acaeRecordsForTransferRQ, credentialsDTO);

        List<ACAERecordsForTransferDTO> acaeRecordsForTransferDTO =
                acaeTransferService.getACAERecordsForTransferService(acaeRecordsForTransferRQ, credentialsDTO);

        LOG.info("END :  ACAE Records For Transfer  : {} , user {}", acaeRecordsForTransferDTO, credentialsDTO.getUserID());

        return new ResponseEntity<>(acaeRecordsForTransferDTO, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value ="/forwardTransferOption", headers = "Accept=application/json")
    public ResponseEntity<Boolean> forwardTransferOptionController(@RequestBody ACAETransferOptionRQ acaeTransferOptionRQ)throws AppsException {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Forward Transfer Option : {} , user {}", acaeTransferOptionRQ, credentialsDTO);

        Boolean result =
                acaeTransferService.forwardTransferOptionService(acaeTransferOptionRQ, credentialsDTO);

        LOG.info("END : Forward Transfer Option  : {} , user {}", acaeTransferOptionRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @PostMapping(value ="/getTransferUserList", headers = "Accept=application/json")
    public ResponseEntity<List<UpmGroupDTO>> getTransferUserListController(@RequestBody LoggedInUserWorkFlowByStatusRQ loggedInUserWorkFlowByStatusRQ) {
        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START : Forward Transfer Option : {} , user {}", loggedInUserWorkFlowByStatusRQ, credentialsDTO);

        List<UpmGroupDTO> result =
                acaeTransferService.getTransferUserListService(loggedInUserWorkFlowByStatusRQ);

        LOG.info("END : Forward Transfer Option  : {} , user {}", loggedInUserWorkFlowByStatusRQ, credentialsDTO.getUserID());

        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
