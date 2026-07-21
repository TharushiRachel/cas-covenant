package com.itechro.cas.dao.esg;

import com.itechro.cas.model.domain.esg.AnnexureTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnexureTempDao extends JpaRepository<AnnexureTemp, Integer> {
}
