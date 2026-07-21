package com.itechro.cas.dao.sme;

import com.itechro.cas.model.domain.sme.SmeAnswerDescription;
import com.itechro.cas.model.domain.sme.SmeQuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmeAnswerDescriptionDao extends JpaRepository<SmeAnswerDescription, Integer>{
    List<SmeAnswerDescription> findBySmeQuestionAnswer_SmeAnswerId(Integer smeAnswerId);

    List<SmeAnswerDescription> findBySmeQuestionAnswer(SmeQuestionAnswer smeQuestionAnswer);
}
