package com.itechro.cas.service.storage;

import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.exception.impl.ICasErrorCode;
import com.itechro.cas.model.domain.storage.DocStorage;
import com.itechro.cas.model.dto.audit.WebAuditDTO;
import com.itechro.cas.model.dto.storage.DocStorageDTO;
import com.itechro.cas.model.security.CredentialsDTO;
import com.itechro.cas.service.audit.WebAuditService;
import com.itechro.cas.util.WebAuditUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

@Service
public class StorageService implements EnvironmentAware {

    private static final Logger LOG = LoggerFactory.getLogger(StorageService.class);

    private Environment environment;

    @Autowired
    private DocStorageDao docStorageDao;

    @Autowired
    private WebAuditService webAuditService;

    private Path rootLocation;

    @PostConstruct
    private void setRootLocation() {
        this.rootLocation = Paths.get(this.environment.getProperty("apps.cas.file.upload.path"));
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public DocStorageDTO loadDocumentByStorageID(Integer docStorageID) {
        DocStorage docStorage = this.docStorageDao.getOne(docStorageID);
        return new DocStorageDTO(docStorage);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public byte[] downloadDocumentByStorageID(Integer docStorageID) {
        DocStorage docStorage = this.docStorageDao.getOne(docStorageID);
        return docStorage.getDocument();
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public DocStorageDTO downloadDocumentDTOByStorageID(Integer docStorageID) {
        DocStorage docStorage = this.docStorageDao.getOne(docStorageID);
        return new DocStorageDTO(docStorage);
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = AppsException.class)
    public DocStorageDTO saveUpdateDocument(DocStorageDTO updateDTO, CredentialsDTO credentialsDTO) {
        LOG.info("START: Save/Update document,{}", updateDTO);
        Date date = new Date();
        boolean isNewDocumentData = updateDTO.getDocStorageID() == null;
        DocStorage docStorage = null;
        DocStorageDTO updateDocStorageDTO = null;
        DocStorageDTO previousStorageDTO = null;

        if (isNewDocumentData) {
            docStorage = new DocStorage();
        } else {
            docStorage = this.docStorageDao.getOne(updateDTO.getDocStorageID());
            previousStorageDTO = new DocStorageDTO(docStorage);
        }

        docStorage.setDescription(updateDTO.getDescription());
        docStorage.setFileName(updateDTO.getFileName());
        docStorage.setDocument(updateDTO.getDocument());
        docStorage.setLastUpdatedDate(date);

        docStorage = this.docStorageDao.saveAndFlush(docStorage);
        updateDocStorageDTO = new DocStorageDTO(docStorage);

        //Audit
        WebAuditDTO webAuditDTO = WebAuditUtils.constructDocStorageAudit(updateDocStorageDTO, previousStorageDTO, credentialsDTO, date, isNewDocumentData);
        webAuditService.saveWebAudit(webAuditDTO, credentialsDTO);

        LOG.info("END: Save/Update document,{}", updateDTO);
        return updateDocStorageDTO;

    }

    public Resource loadAsResource(String filename) throws AppsException {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FILE_STORAGE_FAILED_TO_READ);

            }
        } catch (MalformedURLException e) {
            throw new AppsException(ICasErrorCode.APPS_EXCEPTION_FILE_STORAGE_FAILED_TO_READ, e);
        }
    }

    public Path load(String filename) {
        return rootLocation.resolve(filename);
    }


}
