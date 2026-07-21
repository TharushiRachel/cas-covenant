package com.itechro.cas.dao.finacle;

import com.itechro.cas.model.domain.finacle.CollateralDetails;
import com.itechro.cas.model.domain.finacle.ExportTurnovers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ExportTurnOversDao extends JpaRepository<ExportTurnovers, Integer> {


    @Query("SELECT c FROM ExportTurnovers c WHERE c.facilityPaperId = :facilityPaperId AND c.customerFinacleId = :cusId")
    List<ExportTurnovers> findByFacilityPaperIdAndCusId(Integer facilityPaperId, String cusId);


    @Transactional
    @Modifying
    @Query("DELETE FROM ExportTurnovers c WHERE c.facilityPaperId = :facilityPaperId AND c.customerFinacleId = :cusId")
    void deleteByFacilityPaperIdAndCusId(Integer facilityPaperId, String cusId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ExportTurnovers c WHERE c.facilityPaperId = :facilityPaperId")
    void deleteByFacilityPaperId(Integer facilityPaperId);
}


