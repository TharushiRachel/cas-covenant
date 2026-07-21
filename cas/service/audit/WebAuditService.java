package com.itechro.cas.service.audit;

import com.itechro.cas.dao.audit.WebAuditDao;
import com.itechro.cas.dao.audit.WebAuditJDBCDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.common.Page;
import com.itechro.cas.model.domain.audit.WebAudit;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.audit.WebAuditSearchRQ;
import com.itechro.cas.model.security.CredentialsDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WebAuditService {

    private static final Logger LOG = LoggerFactory.getLogger(WebAuditService.class);

    @Autowired
    private WebAuditDao webAuditDao;

    @Autowired
    private WebAuditJDBCDao webAuditJDBCDao;


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWebAudit(WebAuditDTO webAuditDTO, CredentialsDTO credentialsDTO) {

        try {
            LOG.info("START : saveWebAudit | WebAuditService | Web Audit {} by {}", webAuditDTO, credentialsDTO.getUserID());

            WebAudit webAudit = getWebAudit(webAuditDTO, credentialsDTO);
            webAuditDao.saveAndFlush(webAudit);

            LOG.info("END : saveWebAudit | WebAuditService | Web Audit {} by {}", webAuditDTO, credentialsDTO.getUserID());

        } catch (Exception e) {
            LOG.error("ERROR: saveWebAudit | WebAuditService |  Failed to save Web Audit for userID={} | data={} | error={}",
                    credentialsDTO.getUserID(), webAuditDTO, e.getMessage(), e);
        }

    }


    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveWebAudit(List<WebAuditDTO> webAuditDTOs, CredentialsDTO credentialsDTO) {

        try {
            LOG.info("START : saveWebAudit | WebAuditService | Web Audit {} by {}", webAuditDTOs, credentialsDTO.getUserID());

            for (WebAuditDTO webAuditDTO : webAuditDTOs) {
                try {
                    WebAudit webAudit = getWebAudit(webAuditDTO, credentialsDTO);
                    webAuditDao.saveAndFlush(webAudit);
                } catch (Exception e) {
                    LOG.error("ERROR: saveWebAudit | WebAuditService |  Failed to save individual WebAuditDTO={} by userID={} | error={}",
                            webAuditDTO, credentialsDTO.getUserID(), e.getMessage(), e);
                }
            }

            LOG.info("END : saveWebAudit | WebAuditService |  Web Audit {} by {}", webAuditDTOs, credentialsDTO.getUserID());

        } catch (Exception e) {
            LOG.error("ERROR: saveWebAudit | WebAuditService |  Bulk Web Audit failed for userID={} | error={}",
                    credentialsDTO.getUserID(), e.getMessage(), e);
        }

    }


    private WebAudit getWebAudit(WebAuditDTO webAuditDTO, CredentialsDTO credentialsDTO) {

        try {
            WebAudit webAudit = new WebAudit();

            webAudit.setUserID(credentialsDTO.getUserID());
            webAudit.setUserName(credentialsDTO.getUserName());
            webAudit.setAuditTypeID(webAuditDTO.getAuditTypeID());
            webAudit.setAuditType(webAuditDTO.getAuditType());
            webAudit.setWebAuditMainCategory(webAuditDTO.getWebAuditMainCategory());
            webAudit.setWebAuditSubCategory(webAuditDTO.getWebAuditSubCategory());
            webAudit.setAuditedDateTime(webAuditDTO.getAuditedDateTime());
            webAudit.setUpdateContent(webAuditDTO.getJsonUpdateContent());

            if (webAuditDTO.getJsonPreviousContent() != null) {
                webAudit.setPreviousContent(webAuditDTO.getJsonPreviousContent());
            }

            return webAudit;

        } catch (Exception e) {
            LOG.error("ERROR: getWebAudit | WebAuditService |  Failed to map WebAudit from DTOs | webAuditDTO={} | credentialsDTO={} | error={}",
                    webAuditDTO, credentialsDTO, e.getMessage(), e);
            throw new RuntimeException("Error while mapping WebAudit", e);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<WebAuditDTO> getPagedWebAudit(WebAuditSearchRQ searchRQ) throws AppsException {
        try {
            return webAuditJDBCDao.getPagedWebAudit(searchRQ);
        } catch (Exception e) {
            LOG.error("ERROR: getPagedWebAudit | WebAuditService | Failed to retrieve paged web audit for searchRQ={} | error={}",
                    searchRQ, e.getMessage(), e);
            throw new AppsException("  getPagedWebAudit | WebAuditService | Error retrieving paged WebAudit data", e);
        }
    }

}
