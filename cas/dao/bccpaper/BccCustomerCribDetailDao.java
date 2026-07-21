package com.itechro.cas.dao.bccpaper;

import com.itechro.cas.model.domain.bccpaper.BccCustomerCribDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BccCustomerCribDetailDao extends JpaRepository<BccCustomerCribDetail, Integer> {
}
