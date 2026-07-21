package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.FacilityCustomInfoData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomFacilityDao extends JpaRepository<FacilityCustomInfoData, Integer> {
}
