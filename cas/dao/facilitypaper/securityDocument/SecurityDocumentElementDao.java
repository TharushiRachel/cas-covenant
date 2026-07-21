package com.itechro.cas.dao.facilitypaper.securityDocument;

import com.itechro.cas.model.domain.facilitypaper.securityDocument.SecurityDocumentElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecurityDocumentElementDao extends JpaRepository<SecurityDocumentElement, Integer> {

    List<SecurityDocumentElement> findAllByCreditFacilityNameAndStatus(String creditFacilityName, String status);
}
