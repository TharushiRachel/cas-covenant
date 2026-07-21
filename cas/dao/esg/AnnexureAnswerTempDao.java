package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureAnswerTemp;
import com.itechro.cas.model.domain.esg.AnnexureQuestionTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface AnnexureAnswerTempDao extends JpaRepository<AnnexureAnswerTemp, Integer> {

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM T_ESG_ANNEX_ANSWERS_TEMP WHERE QUESTION_ID = :questionId", nativeQuery = true)
    void deleteByQuestionId(Integer questionId);
}
