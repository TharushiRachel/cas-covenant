package com.itechro.cas.dao.coveringApproval;

import com.itechro.cas.model.domain.coveringApproval.CoveringApproval;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 * @author tharushi
 */

@Repository
public interface CoveringApprovalDao extends JpaRepository<CoveringApproval, Integer> {

    List<CoveringApproval> findByAccountNumber(String accountNumber);

}
