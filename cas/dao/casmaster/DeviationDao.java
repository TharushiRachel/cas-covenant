package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.facilitypaper.Deviation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviationDao extends JpaRepository<Deviation, Integer> {
}
