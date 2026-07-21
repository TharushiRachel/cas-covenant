package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.CASCustomerCribDetail;
import com.itechro.cas.model.domain.facilitypaper.CompDeviation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface CompDeviationsDao extends JpaRepository<CompDeviation,Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM T_COMP_DEVIATIONS d WHERE d.FACILITY_PAPER_ID = :facilityPaperId",nativeQuery = true)
    int deleteAllByFacilityPaperId(@Param("facilityPaperId") Integer facilityPaperId);

}
