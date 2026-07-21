package com.itechro.cas.dao.finacle;

import com.itechro.cas.model.domain.finacle.ExportTurnovers;
import com.itechro.cas.model.domain.finacle.ImportTurnovers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ImportTurnOversDao extends JpaRepository<ImportTurnovers, Integer> {


    @Query("SELECT c FROM ImportTurnovers c WHERE c.facilityPaperId = :facilityPaperId AND c.customerFinacleId = :cusId")
    List<ImportTurnovers> findByFacilityPaperIdAndCusId(Integer facilityPaperId, String cusId);


    @Transactional
    @Modifying
    @Query("DELETE FROM ImportTurnovers c WHERE c.facilityPaperId = :facilityPaperId AND c.customerFinacleId = :cusId")
    void deleteByFacilityPaperIdAndCusId(Integer facilityPaperId, String cusId);

    @Transactional
    @Modifying
    @Query("DELETE FROM ImportTurnovers c WHERE c.facilityPaperId = :facilityPaperId")
    void deleteByFacilityPaperId(Integer facilityPaperId);
}


