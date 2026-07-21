package com.itechro.cas.dao.facility;

import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPurposeOfAdvance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityPurposeOfAdvanceDao extends JpaRepository<FacilityPurposeOfAdvance,Integer> {
}
