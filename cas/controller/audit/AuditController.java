package com.itechro.cas.controller.audit;

import com.itechro.cas.commons.constants.PrivilegeCode;
import com.itechro.cas.controller.BaseController;
import com.itechro.cas.exception.aop.ResponseExceptionHandler;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.audit.WebAuditSearchRQ;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "${api.prefix}/audit")
public class AuditController extends BaseController {

    private static final Logger LOG = LoggerFactory.getLogger(AuditController.class);

    @Autowired
    private WebAuditService webAuditService;

    @ResponseExceptionHandler
    @PreAuthorize("hasAuthority('" + PrivilegeCode.SYSTEM.ICAS_WEB_AUDIT_VIEW+ "')")
    @RequestMapping(value = "/getPagedAuditDetails",headers = "Accept=application/json", method = RequestMethod.POST)
    public ResponseEntity<Page<WebAuditDTO>> getPagedAuditDetails(@RequestBody WebAuditSearchRQ webAuditSearchRQ) throws AppsException{

        CredentialsDTO credentialsDTO = getCredentialsDTO();
        LOG.info("START : Search Audit Details : {} by user {}", webAuditSearchRQ, credentialsDTO);

        Page<WebAuditDTO> webAuditDTOPage = this.webAuditService.getPagedWebAudit(webAuditSearchRQ);

        LOG.info("END : Search Audit Details : {} by user {}", webAuditSearchRQ, credentialsDTO);

        return new ResponseEntity<>(webAuditDTOPage, HttpStatus.OK);
    }


}
