package com.itechro.cas.dao.casmaster;

import com.itechro.cas.commons.constants.AppsConstants.Status;
import com.itechro.cas.commons.constants.DomainConstants.MasterDataApproveStatus;
import com.itechro.cas.model.domain.casmaster.CftSupportingDoc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CftSupportingDocDao extends JpaRepository<CftSupportingDoc, Integer> {

    List<CftSupportingDoc> findAllBySupportingDoc_SupportingDocIDAndApproveStatusAndStatus(Integer supportDocID, MasterDataApproveStatus approveStatus, Status status);
}
