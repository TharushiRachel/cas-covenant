package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CACommitteeTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCommitteeTempDao extends JpaRepository<CACommitteeTemp,Integer> {
}
