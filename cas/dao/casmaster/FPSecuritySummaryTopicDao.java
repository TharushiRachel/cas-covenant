package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.facilitypaper.FPSecuritySummaryTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FPSecuritySummaryTopicDao extends JpaRepository<FPSecuritySummaryTopic, Integer> {
}
