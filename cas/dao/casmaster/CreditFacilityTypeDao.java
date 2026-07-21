package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.casmaster.CreditFacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditFacilityTypeDao extends JpaRepository<CreditFacilityType, Integer> {

    List<CreditFacilityType> findByApproveStatusAndStatus(DomainConstants.MasterDataApproveStatus approveStatus, AppsConstants.Status status);

}
