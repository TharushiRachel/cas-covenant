package com.itechro.cas.dao.lead;

import com.itechro.cas.model.domain.lead.ComprehensiveFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComprehensiveFacilityDao extends JpaRepository<ComprehensiveFacility, Long> {

    @Query(value = "SELECT * FROM T_COMP_FACILITIES WHERE COMP_LEAD_ID=:compLeadId AND STATUS='ACT'", nativeQuery = true)
    List<ComprehensiveFacility> findAllFacilities(@Param("compLeadId") Long compLeadId);
}
