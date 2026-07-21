package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.AppsConstants.Status;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.commons.constants.DomainConstants.MasterDataApproveStatus;
import com.itechro.cas.model.domain.casmaster.CreditFacilityTemplate;
import com.itechro.cas.model.domain.casmaster.CreditFacilityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditFacilityTemplateDao extends JpaRepository<CreditFacilityTemplate, Integer> {

    List<CreditFacilityTemplate> findAllByCreditFacilityType_CreditFacilityTypeIDAndApproveStatusAndStatus(Integer creditFacilityTypeID, MasterDataApproveStatus approveStatus, Status status);

    List<CreditFacilityTemplate> findAllByAndApproveStatus(DomainConstants.MasterDataApproveStatus masterDataApproveStatus);

    List<CreditFacilityTemplate> findAByApproveStatusAndStatus(DomainConstants.MasterDataApproveStatus approveStatus, AppsConstants.Status status);

    List<CreditFacilityTemplate> findAllByApproveStatusAndStatus(MasterDataApproveStatus masterDataApproveStatus, Status status);
}
