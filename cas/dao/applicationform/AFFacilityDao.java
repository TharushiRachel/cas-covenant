package com.itechro.cas.dao.applicationform;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.applicationform.AFFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AFFacilityDao extends JpaRepository<AFFacility, Integer> {
    List<AFFacility> findAllByParentFacilityIDAndStatusAndParentFacilityIDIsNotNull(Integer parentFacilityID, AppsConstants.Status status);

}
