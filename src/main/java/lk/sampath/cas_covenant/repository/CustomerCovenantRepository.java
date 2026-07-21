package lk.sampath.cas_covenant.repository;

import lk.sampath.cas_covenant.entity.CustomerCovenant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCovenantRepository extends JpaRepository<CustomerCovenant, Integer> {

  @Query(
      "SELECT MAX(c.displayOrder) FROM CustomerCovenant c WHERE c.facilityPaper.facilityPaperID = :facilityPaperID")
  Integer findMaxDisplayOrderByFacilityPaperID(@Param("facilityPaperID") Integer facilityPaperID);

  @Query("SELECT c FROM CustomerCovenant c WHERE c.facilityPaper.facilityPaperID = :facilityPaperID")
  List<CustomerCovenant> findCustomerCovenantsByFacilityPaperID(@Param("facilityPaperID") Integer facilityPaperID);

  @Query(value = "SELECT t.CUSTOMER_FINANCIAL_ID " +
          "FROM t_customer t " +
          "JOIN t_cas_customer c ON t.customer_id = c.customer_id " +
          "WHERE c.facility_paper_id = :facilityPaperId " +
          "AND c.is_primary = 'Y' " +
          "AND t.status = 'ACT' " +
          "AND c.status = 'ACT'",
          nativeQuery = true)
  String findCustomerFinancialId(@Param("facilityPaperId") Integer facilityPaperId);

  List<CustomerCovenant> findByFacilityPaperFpRefNumber(String fpRefNumber);

}
