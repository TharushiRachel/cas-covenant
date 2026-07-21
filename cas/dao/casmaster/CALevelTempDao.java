package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CALevel;
import com.itechro.cas.model.domain.casmaster.CALevelTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CALevelTempDao extends JpaRepository<CALevelTemp, Integer> {

    List<CALevelTemp> findByCaCommitteeTempCommitteeId(Integer committeeId);
}
