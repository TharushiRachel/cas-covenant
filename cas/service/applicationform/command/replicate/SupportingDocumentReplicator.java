package com.itechro.cas.service.applicationform.command.replicate;

import com.itechro.cas.commons.command.CommandExecutor;
import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.dao.storage.DocStorageDao;
import com.itechro.cas.exception.impl.AppsException;
import com.itechro.cas.model.domain.applicationform.AFDocument;
import com.itechro.cas.model.domain.storage.DocStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class SupportingDocumentReplicator extends CommandExecutor<ApplicationFormReplicateContext> {

    private static final Logger LOG = LoggerFactory.getLogger(SupportingDocumentReplicator.class);

    @Autowired
    private DocStorageDao docStorageDao;

    @Override
    public ApplicationFormReplicateContext execute(ApplicationFormReplicateContext context) throws AppsException {

        for (AFDocument afOriginalDocument : context.getCurrentApplicationForm().getOrderedAfDocument().stream().filter(afDocument ->
                afDocument.getStatus() == AppsConstants.Status.ACT).collect(Collectors.toList())) {

            AFDocument replicatedDocument = new AFDocument();
            replicatedDocument.setCreatedBy(context.getCredentialsDto().getUserName());
            replicatedDocument.setCreatedDate(context.getDate());
            if (afOriginalDocument.getDocStorage() != null) {
                DocStorage docStorage = new DocStorage();
                docStorage.setFileName(afOriginalDocument.getDocStorage().getFileName());
                docStorage.setDescription(afOriginalDocument.getDocStorage().getDescription());
                docStorage.setDocument(afOriginalDocument.getDocStorage().getDocument());
                docStorage.setLastUpdatedDate(context.getDate());
                replicatedDocument.setDocStorage(this.docStorageDao.save(docStorage));
            }
            replicatedDocument.setSupportingDoc(afOriginalDocument.getSupportingDoc());
            replicatedDocument.setStatus(afOriginalDocument.getStatus());
            replicatedDocument.setRemark(afOriginalDocument.getRemark());
            replicatedDocument.setUploadedDivCode(context.getReplicateApplicationFromFormRQ().getAssignUserDivCode());
            replicatedDocument.setUploadedUserDisplayName(context.getReplicateApplicationFromFormRQ().getAssignUserDisplayName());
            context.getNewApplicationForm().addAFDocument(replicatedDocument);
        }
        LOG.info("Application Form Document replicated");
        return context;
    }

}
