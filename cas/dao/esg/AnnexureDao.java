package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.Annexure;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureDao extends JpaRepository<Annexure, Integer> {
}
