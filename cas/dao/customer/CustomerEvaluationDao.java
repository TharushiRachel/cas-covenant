package com.itechro.cas.dao.customer;

import com.itechro.cas.model.domain.customer.CustomerEvaluation;
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
public interface CustomerEvaluationDao extends JpaRepository<CustomerEvaluation, Integer> {


    // Define the method to find the maximum ID by customerId and customerEvaluationId
//    @Query("SELECT MAX(fe.ID) FROM T_CUSTOMER_EVALUATION fe WHERE fe.CUST_ID = :customerId AND fe.C_E_ID = :customerEvaluationId")
//    Integer findMaxIdByCustomerIdAndEvaluationId(@Param("customerId") String customerId,
//                                                 @Param("customerEvaluationId") Integer customerEvaluationId);

}
