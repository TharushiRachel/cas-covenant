package com.itechro.cas.dao.finacle;

import com.itechro.cas.model.domain.facilitypaper.GuaranteeVolumes;
import com.itechro.cas.model.domain.finacle.CollateralDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface GuaranteeVolumesDao extends JpaRepository<GuaranteeVolumes, Integer> {


//    @Query("SELECT c FROM CollateralDetails c WHERE c.facilityPaperId = :facilityPaperId AND c.customerFinacleId = :cusId")
//    List<CollateralDetails> findByFacilityPaperIdWithInsuranceDetails(Integer facilityPaperId, String cusId);
//
//    @Query("SELECT c FROM CollateralDetails c WHERE c.facilityPaperId = :facilityPaperId ")
//    List<CollateralDetails> findByFacilityPaperIdInsuranceDetials(Integer facilityPaperId);

    @Transactional
    @Modifying
    @Query("DELETE FROM GuaranteeVolumes c WHERE c.facilityPaperId = :facilityPaperId")
    void deleteByFacilityPaperId(Integer facilityPaperId);
}


