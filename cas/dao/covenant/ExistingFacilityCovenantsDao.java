package com.itechro.cas.dao.covenant;

import com.itechro.cas.model.domain.covenant.ExistingFacilityCovenants;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExistingFacilityCovenantsDao extends JpaRepository<ExistingFacilityCovenants, Long> {
    Optional<ExistingFacilityCovenants> findByFacilityId(Integer facilityId);

    List<ExistingFacilityCovenants> findByFacilityPaperId(Integer facilityPaperId);
}
