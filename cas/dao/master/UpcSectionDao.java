package com.itechro.cas.dao.master;

import com.itechro.cas.model.domain.casmaster.UpcSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpcSectionDao extends JpaRepository<UpcSection,Integer> {

}
