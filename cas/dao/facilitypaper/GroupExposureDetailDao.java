package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.GroupExposureDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface GroupExposureDetailDao extends JpaRepository<GroupExposureDetail, Integer> {

    List<GroupExposureDetail> findByFacilityID(Integer fpId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM T_GROUP_EXPOSURE_DETAIL WHERE FACILITY_ID = :facilityID", nativeQuery = true)
    void deleteRecordsByFacilityID(@Param("facilityID") Integer facilityID);

    @Modifying
    @Transactional
    @Query(value = "UPDATE T_GROUP_EXPOSURE_DETAIL SET IS_SELECTED = :value WHERE GROUP_EXPOSURE_ID = :id", nativeQuery = true)
    void updateSelectedExposure(@Param("id") Integer id, @Param("value") Integer value);

    List<GroupExposureDetail> findByFacilityIDAndIsSelected(Integer id, Integer selected);
}


