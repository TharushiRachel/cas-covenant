package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.WSFacilitySecurity;
import com.itechro.cas.model.domain.facilitypaper.WalletShare;
import com.itechro.cas.model.domain.facilitypaper.WalletShareFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WSFacilitySecurityDao extends JpaRepository<WSFacilitySecurity,Integer> {

    void deleteByWalletShareFacility(WalletShareFacility walletShareFacility);

    void deleteByWalletShare(WalletShare walletShare);
}
