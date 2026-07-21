package com.itechro.cas.dao.sme;

import com.itechro.cas.model.domain.sme.SmeQuestion;
import com.itechro.cas.model.domain.sme.SmeQuestionConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SmeQuestionConfigDao extends JpaRepository<SmeQuestionConfig, Integer> {

    //List<SmeQuestionConfig> findBySmeQuestion_SmeQuestionId(Integer smeQuestionId);
    List<SmeQuestionConfig> findBySmeQuestion(SmeQuestion smeQuestion);

}
