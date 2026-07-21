package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CACommitteeComment;
import com.itechro.cas.model.domain.casmaster.CAUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CACommitteeCommentDao extends JpaRepository<CACommitteeComment, Integer> {
    List<CACommitteeComment> findByCaCommitteeCommitteeId(Integer committeeId);
}
