package com.itechro.cas.dao.advanceAnalytics;

import com.itechro.cas.model.domain.advanceAnalytics.AnalyticsDecision;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdvanceAnalyticsDao extends JpaRepository<AnalyticsDecision, Integer> {


    AnalyticsDecision findByLeadId(Integer leadId);
}
