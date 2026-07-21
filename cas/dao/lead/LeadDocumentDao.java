package com.itechro.cas.dao.lead;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.lead.LeadDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LeadDocumentDao extends JpaRepository<LeadDocument, Integer> {
    List<LeadDocument> findAllByLeadLeadIDAndStatus(Integer leadId, AppsConstants.Status status);
}
