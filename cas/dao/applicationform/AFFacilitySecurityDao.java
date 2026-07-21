package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.AFFacilitySecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFFacilitySecurityDao extends JpaRepository<AFFacilitySecurity, Integer> {
}
