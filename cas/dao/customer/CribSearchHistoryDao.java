package com.itechro.cas.dao.customer;

import com.itechro.cas.model.domain.customer.CribSearchHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CribSearchHistoryDao extends JpaRepository<CribSearchHistory,Integer> {
}