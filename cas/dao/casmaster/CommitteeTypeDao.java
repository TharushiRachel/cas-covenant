package com.itechro.cas.dao.casmaster;

import com.itechro.cas.model.domain.casmaster.CommitteeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommitteeTypeDao extends JpaRepository<CommitteeType, Integer> , QuerydslPredicateExecutor<CommitteeType>{
    List<CommitteeType> findAll();

}
