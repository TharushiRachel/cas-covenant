package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.AFSecurity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFSecurityDao extends JpaRepository<AFSecurity, Integer> {
}
