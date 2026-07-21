package com.itechro.cas.dao.applicationform.esg;

import com.itechro.cas.model.domain.applicationform.esg.AFAnnexureAnswerData;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AFAnnexureAnswerDataDao extends JpaRepository<AFAnnexureAnswerData, Integer> {
    List<AFAnnexureAnswerData> findByAfAnnexureQuestionDataQuestionDataId(Integer questionDataId);
}
