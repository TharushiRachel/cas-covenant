package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.AFFacilityDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFFacilityDocumentDao extends JpaRepository<AFFacilityDocument, Integer> {
}
