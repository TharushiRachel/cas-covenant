package com.itechro.cas.dao.facility;

import com.itechro.cas.model.domain.customer.CustomerEvaluation;
import com.itechro.cas.model.domain.facilitypaper.facility.FPCustomerEvaluation;
import com.itechro.cas.model.domain.facilitypaper.facility.FacilityPaper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 *
 * @author tharushi
 */
@Repository
public interface FPCustomerEvaluationDao extends JpaRepository<FPCustomerEvaluation, Integer> {

    //FPCustomerEvaluation findByCustomerEvaluationCustomerEvaluationIdAndCustomerEvaluationCustomerIdAndFacilityPaperId(Integer customerEvaluationId, String customerId, Integer facilityPaperId);


}
