package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.SecuritySummaryTopic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecuritySummaryTopicDao extends JpaRepository<SecuritySummaryTopic, Integer> {

    List<SecuritySummaryTopic> findAllByStatus(AppsConstants.Status status);
}
