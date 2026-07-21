package com.itechro.cas.dao.coveringApproval;

import com.itechro.cas.model.domain.coveringApproval.CovAppBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Repository
public interface CovAppBasinInfoDao extends JpaRepository<CovAppBasicInfo, Integer> {

    List<CovAppBasicInfo> findByAccountNumber(String accountNumber);

    List<CovAppBasicInfo> findByPartTranSRLAndChequeNumber(String partTranSRL, String chequeNumber);
}
