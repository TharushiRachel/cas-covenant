package lk.sampath.cas_covenant.repository;

import lk.sampath.cas_covenant.entity.facilityPaper.Facility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityRepository extends JpaRepository<Facility, Integer> {

    @Query("SELECT f FROM Facility f WHERE f.facilityPaper.facilityPaperID = :facilityPaperID")
    List<Facility> findFacilitiesByFacilityPaperID(@Param("facilityPaperID") Integer facilityPaperID);
}
