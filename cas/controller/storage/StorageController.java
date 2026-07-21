package com.itechro.cas.controller.storage;

import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.prefix}/storage")
public class StorageController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(StorageController.class);

    @Autowired
    private StorageService storageService;

    @RequestMapping(value = "/downloadDoc/{storageDocID}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getDocumentDataByID(@PathVariable Integer storageDocID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START :Download doc ID : {}, user {} ", storageDocID, credentialsDTO.getUserID());

        HttpHeaders httpHeaders = new HttpHeaders();
        byte[] bytes = this.downloadFile(storageDocID,  httpHeaders, credentialsDTO);

        LOG.info("END : Download doc ID : {}, user {} ", storageDocID, credentialsDTO.getUserID());

        return new ResponseEntity<>(bytes,httpHeaders, HttpStatus.OK);
    }

    @ResponseExceptionHandler
    @RequestMapping(value = "/getDocumentDTO/{storageDocID}", headers = "Accept=application/json", method = RequestMethod.GET)
    public ResponseEntity<DocStorageDTO> getDocStorageDTOByID(@PathVariable Integer storageDocID) throws Exception {

        CredentialsDTO credentialsDTO = getCredentialsDTO();

        LOG.info("START :Download doc ID : {}, user {} ", storageDocID, credentialsDTO.getUserID());

        DocStorageDTO documentData = this.storageService.downloadDocumentDTOByStorageID(storageDocID);

        LOG.info("END : Download doc ID : {}, user {} ", storageDocID, credentialsDTO.getUserID());

        return new ResponseEntity<>(documentData, HttpStatus.OK);
    }

    private byte[] downloadFile(Integer storageDocID,
                                HttpHeaders httpHeaders,
                                CredentialsDTO credentialsDTO) {

        httpHeaders.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        byte[] bytes = null;


        DocStorageDTO documentData = this.storageService.downloadDocumentDTOByStorageID(storageDocID);
        bytes = documentData.getDocument();
        httpHeaders.setContentLength(documentData.getDocument().length);
        httpHeaders.setContentDispositionFormData("attachment", documentData.getFileName());
        LOG.info("END : downloading document {} for user : {}, ", storageDocID, credentialsDTO.getUserID());

        return bytes;
    }

}
