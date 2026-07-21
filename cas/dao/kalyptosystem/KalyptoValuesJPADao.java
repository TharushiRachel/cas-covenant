package com.itechro.cas.dao.kalyptosystem;

import com.itechro.cas.model.domain.kalyptosystem.kalyptoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KalyptoValuesJPADao extends JpaRepository<kalyptoData,Integer> {

    List<kalyptoData> findAllByIsAddedNew(Integer isNewAdded);

    @Query(value = "select * from T_KALYPTO_PARAMS where IS_NEW_ADDED = ?1 and PARAMETER_ID = ?2 and PERIOD_ID = ?3", nativeQuery = true)
    kalyptoData findByKalyptoIdAndParamterIdAndPeriodId (Integer isNewAdded , Integer parameterId, Integer periodId );

    @Query(value = "select * from T_KALYPTO_PARAMS where CUSTOMER_ID = ?1 and FACILITY_ID =?2", nativeQuery = true)
    List<kalyptoData> getKalyptoValuesForCustomer (String customerId ,String facilityId);

}
