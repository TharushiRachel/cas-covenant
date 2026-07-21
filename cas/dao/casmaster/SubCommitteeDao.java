package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CACommittee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubCommitteeDao extends JpaRepository<CACommittee, Integer> {
}
