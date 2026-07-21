package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.EnvironmentalRiskAud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnvironmentalRiskAudDao extends JpaRepository<EnvironmentalRiskAud, Integer> {
}
