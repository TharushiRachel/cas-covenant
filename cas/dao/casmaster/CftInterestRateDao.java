package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CftInterestRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CftInterestRateDao extends JpaRepository<CftInterestRate, Integer> {
}
