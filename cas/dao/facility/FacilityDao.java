package com.itechro.cas.dao.facility;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.facilitypaper.facility.Facility;
import com.itechro.cas.model.dto.facility.FacilityDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FacilityDao extends JpaRepository<Facility, Integer> {

    List<Facility> findAllByParentFacilityIDAndStatusAndParentFacilityIDIsNotNull(Integer parentFacilityID, AppsConstants.Status status);

    List<FacilityDTO> findByFacilityPaperFacilityPaperID(Integer facilityPaperID);
}
