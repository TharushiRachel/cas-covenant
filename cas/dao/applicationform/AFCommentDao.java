package com.itechro.cas.dao.applicationform;

import com.itechro.cas.model.domain.applicationform.AFComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AFCommentDao extends JpaRepository<AFComment, Integer> {
}
