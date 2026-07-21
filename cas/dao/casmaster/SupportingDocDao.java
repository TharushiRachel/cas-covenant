package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.SupportingDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupportingDocDao extends JpaRepository<SupportingDoc, Integer> {

}
