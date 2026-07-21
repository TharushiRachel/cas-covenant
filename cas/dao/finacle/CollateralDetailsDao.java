package com.itechro.cas.dao.finacle;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.das.DALimitTemp;
import com.itechro.cas.model.domain.finacle.CollateralDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CollateralDetailsDao extends JpaRepository<CollateralDetails, Integer> {


    @Query("SELECT c FROM CollateralDetails c WHERE c.facilityPaperId = :facilityPaperId AND c.customerFinacleId = :cusId")
    List<CollateralDetails> findByFacilityPaperIdWithInsuranceDetails(Integer facilityPaperId, String cusId);

    @Query("SELECT c FROM CollateralDetails c WHERE c.facilityPaperId = :facilityPaperId ")
    List<CollateralDetails> findByFacilityPaperIdInsuranceDetials(Integer facilityPaperId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CollateralDetails c WHERE c.facilityPaperId = :facilityPaperId AND c.customerFinacleId = :cusId")
    void deleteByFacilityPaperIdAndCusId(Integer facilityPaperId, String cusId);

    @Transactional
    @Modifying
    @Query("DELETE FROM CollateralDetails c WHERE c.facilityPaperId = :facilityPaperId")
    void deleteByFacilityPaperId(Integer facilityPaperId);

    @Transactional
    int deleteByFacilityPaperIdAndCustomerFinacleId(Integer facilityPaperId, String cusID);
}


