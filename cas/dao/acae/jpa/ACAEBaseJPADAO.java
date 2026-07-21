package com.itechro.cas.dao.acae.jpa;

import com.itechro.cas.model.domain.acae.ACAEMock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ACAEBaseJPADAO extends JpaRepository<ACAEMock, Integer> {

    @Procedure("ACAE_OPERATIONS.UPDATE_ACAE_COMPLETE")
    String updateACAECompleteRepository(@Param("IN_SEARCH_REF_NUM") String referenceNumber);
}
