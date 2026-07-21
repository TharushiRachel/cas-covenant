package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.WalletShare;
import com.itechro.cas.model.domain.facilitypaper.WalletShareFacility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface WalletShareFacilityDao extends JpaRepository<WalletShareFacility, Integer> {

    @Modifying
    @Transactional
    void deleteByWalletShare(WalletShare walletShare);
}
