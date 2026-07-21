package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.casmaster.CALevel;
import org.jsmpp.bean.OptionalParameter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CALevelDao extends JpaRepository<CALevel, Integer> {

    List<CALevel> findByCaCommitteeCommitteeId(Integer committeeId);
    CALevel findByCaCommitteeCommitteeIdAndLevelIdAndPathType(Integer committeeID, Integer levelID, AppsConstants.CAPathType pathType);
}
