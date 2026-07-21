package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.TempDeviation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempDeviationDao extends JpaRepository<TempDeviation, Integer> {
}
