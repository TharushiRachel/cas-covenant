package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFTopicConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AFTopicConfigDao extends JpaRepository<AFTopicConfig, Integer> {

    List<AFTopicConfig> findAllByStatus(AppsConstants.Status status);
}
