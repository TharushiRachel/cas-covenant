package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.AFDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFDocumentDao extends JpaRepository<AFDocument, Integer> {
}
