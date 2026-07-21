package lk.sampath.cas_covenant.repository;

import lk.sampath.cas_covenant.entity.NoneComplianceCovenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoneComplianceCovenantRepository extends JpaRepository<NoneComplianceCovenant, Integer> {

    List<NoneComplianceCovenant> findByFacilityPaperId(Integer facilityPaperId);
}
