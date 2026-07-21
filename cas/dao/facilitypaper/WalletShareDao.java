package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.WalletShare;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletShareDao extends JpaRepository<WalletShare, Integer> {

    List<WalletShare> findByFacilityPaper(FacilityPaper facilityPaper);

    void deleteByFacilityPaperAndIsSystem(FacilityPaper facilityPaper, Integer isSystem);
}
