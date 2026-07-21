package lk.sampath.cas_covenant.repository;

import lk.sampath.cas_covenant.entity.facilityPaper.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FacilityPaperRepository extends JpaRepository<FacilityPaper, Integer> {}
