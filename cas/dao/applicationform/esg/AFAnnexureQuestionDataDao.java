package com.itechro.cas.dao.applicationform.esg;

import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureQuestionData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AFAnnexureQuestionDataDao extends JpaRepository<AFAnnexureQuestionData, Integer> {
    List<AFAnnexureQuestionData> findByAfAnnexureDataAnnexureDataId(Integer annexureDataId);
}
