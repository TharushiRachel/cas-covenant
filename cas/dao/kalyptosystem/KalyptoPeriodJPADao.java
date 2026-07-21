package com.itechro.cas.dao.kalyptosystem;

import com.itechro.cas.model.domain.kalyptosystem.KalyptoPeriod;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KalyptoPeriodJPADao extends JpaRepository<KalyptoPeriod,Integer> {

    @Query(value = "select * from T_KALYPTO_PERIOD where CUSTOMER_ID = ?1 and FACILITY_ID = ?2 ", nativeQuery = true)
    List<KalyptoPeriod> getPeriodsforCustomer (String customerId  , String facilityId);
}
