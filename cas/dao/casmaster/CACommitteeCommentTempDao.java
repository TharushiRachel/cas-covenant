package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CACommitteeCommentTemp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CACommitteeCommentTempDao extends JpaRepository<CACommitteeCommentTemp, Integer> {

    void deleteByCaCommitteeTempCommitteeId(Integer committeeId);

    List<CACommitteeCommentTemp> findByCaCommitteeTempCommitteeId(Integer committeeId);
}
