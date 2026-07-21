package com.itechro.cas.dao.sme;

import com.itechro.cas.model.domain.sme.SmeQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmeQuestionAnswerDao extends JpaRepository<SmeQuestionAnswer, Integer>{
    List<SmeQuestionAnswer> findBySmeQuestion_SmeQuestionId(Integer smeQuestionId);
}
