package com.itechro.cas.dao.coveringApproval;

import com.itechro.cas.model.domain.coveringApproval.CovAppComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *
 *
 * @author tharushi
 */
@Repository
public interface CovAppCommentDao extends JpaRepository<CovAppComment, Integer> {

    List<CovAppComment> findByCoveringApprovalCovAppId(Integer covAppId);
}
