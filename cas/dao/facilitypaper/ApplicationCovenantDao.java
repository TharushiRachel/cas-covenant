package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.covenant.ApplicationLevelCovenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationCovenantDao extends JpaRepository<ApplicationLevelCovenant, Integer> {

    List<ApplicationLevelCovenant> findByFacilityPaperFpRefNumber(String fpRefNumber);

    ApplicationLevelCovenant deleteByApplicationCovenantId(Integer applicationCovenantId);
}
