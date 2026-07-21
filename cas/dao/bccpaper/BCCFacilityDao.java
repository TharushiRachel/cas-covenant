package com.itechro.cas.dao.bccpaper;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.bccpaper.BccFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BCCFacilityDao extends JpaRepository<BccFacility, Integer> {

    BccFacility findByFacilityIDAndStatus(Integer facilityID, AppsConstants.Status status);
}
