package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.UpcTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpcTemplateDao extends JpaRepository<UpcTemplate, Integer> {

}
