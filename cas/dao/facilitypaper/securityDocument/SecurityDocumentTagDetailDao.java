package com.itechro.cas.dao.facilitypaper.securityDocument;

import com.itechro.cas.model.domain.facilitypaper.securityDocument.FPSecurityDocumentTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityDocumentTagDetailDao extends JpaRepository<FPSecurityDocumentTag, Integer> {

    FPSecurityDocumentTag findByTagAndFacilityPaperId(String tag, Integer fpId);

    List<FPSecurityDocumentTag> findAllByFacilityPaperId(Integer fpId);
}
