package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.AFTopicData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFTopicDataDao extends JpaRepository<AFTopicData, Integer> {
}
