package com.itechro.cas.dao.facilitypaper.securityDocument;

import com.itechro.cas.model.domain.facilitypaper.securityDocument.FPSecurityDocumentDetail;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface SecurityDocumentDetailDao extends JpaRepository<FPSecurityDocumentDetail, Integer> {

    @Query(value = "SELECT * FROM T_FP_SECURITY_DOCUMENT WHERE FACILITY_PAPER_ID = :fpId AND FACILITY_ID = :facilityId AND DOCUMENT_STATUS != 'DELETE'", nativeQuery = true)
    List<FPSecurityDocumentDetail> getDocumentsByFacilityPaperAndFacilityId(@Param("fpId") Integer fpId, @Param("facilityId") Integer facilityId);

    @Query(value = "SELECT * FROM T_FP_SECURITY_DOCUMENT WHERE FACILITY_PAPER_ID = :fpId AND DOCUMENT_STATUS = 'DRAFT'", nativeQuery = true)
    List<FPSecurityDocumentDetail> getDocumentStatusByFacilityPaper(@Param("fpId") Integer fpId);

    @Query(value = "SELECT COUNT(*) FROM T_FP_SECURITY_DOCUMENT WHERE FACILITY_PAPER_ID = :fpId AND DOCUMENT_STATUS = :status", nativeQuery = true)
    int getDocumentCountByPaperAndStatus(@Param("fpId") Integer fpId,@Param("status") String status);

    @Query(value = "SELECT * FROM T_FP_SECURITY_DOCUMENT WHERE FACILITY_ID = :facilityId", nativeQuery = true)
    List<FPSecurityDocumentDetail> getDocumentStatusByFacility(@Param("facilityId") Integer facilityId);
}
