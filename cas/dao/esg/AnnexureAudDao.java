package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureAud;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureAudDao extends JpaRepository<AnnexureAud, Integer> {
}
