package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.CribDetailsHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CribDetailsHistoryDao extends JpaRepository<CribDetailsHistory, Integer> {
}