package com.itechro.cas.dao.covenant;

import com.itechro.cas.model.domain.covenant.NoneComplianceCovenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoneComplianceCovenantDao extends JpaRepository<NoneComplianceCovenant, Integer> {
    List<NoneComplianceCovenant> findByFacilityPaperId(Integer facilityPaperId);
}
