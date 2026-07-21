package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPCreditRiskDocument;
import com.itechro.cas.model.domain.facilitypaper.FPDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FPCreditRiskDocumentDao extends JpaRepository<FPCreditRiskDocument,Integer> {
}
