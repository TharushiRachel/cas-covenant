package lk.sampath.cas_covenant.repository;

import lk.sampath.cas_covenant.entity.FacilityCovenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityCovenantRepository extends JpaRepository<FacilityCovenant, Integer> {

    @Query(value = "SELECT * FROM APPLICATION_LEVEL_COVENANT WHERE FACILITY_PAPER_ID = :facilityPaperID", nativeQuery = true)
    List<FacilityCovenant> findFacilityCovenantsByFacilityPaperID(@Param("facilityPaperID") Integer facilityPaperID);
}
