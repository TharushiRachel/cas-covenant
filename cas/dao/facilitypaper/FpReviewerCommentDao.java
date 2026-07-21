package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.FPReviewerComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FpReviewerCommentDao extends JpaRepository<FPReviewerComment, Integer> {
}
