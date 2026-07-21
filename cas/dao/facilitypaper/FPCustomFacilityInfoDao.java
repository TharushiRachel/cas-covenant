package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.casmaster.FacilityCustomInfoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FPCustomFacilityInfoDao extends JpaRepository<FacilityCustomInfoData,Integer> {

    @Query(value = "SELECT * FROM T_CUSTOM_FACILITY_INFO WHERE FACILITY_ID = :facilityId AND STATUS = 'ACT'", nativeQuery = true)
    List<FacilityCustomInfoData> getAllByFacilityId(@Param("facilityId") Integer facilityId);
}
