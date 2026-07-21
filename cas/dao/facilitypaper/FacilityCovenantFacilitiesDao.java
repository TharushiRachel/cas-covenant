package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.facility.FacilityCovenantFacilities;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityCovenantFacilitiesDao extends JpaRepository<FacilityCovenantFacilities, Integer> {

    List<FacilityCovenantFacilities> findByApplicationLevelCovenantApplicationCovenantId(Integer applicationCovenantId);

    List<FacilityCovenantFacilities> deleteByApplicationLevelCovenantApplicationCovenantId(Integer applicationCovenantId);
}
