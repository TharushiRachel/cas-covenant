package com.itechro.cas.dao.acae.jpa;

import com.itechro.cas.model.domain.acae.ACAEMock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;

import java.util.Date;

public interface ACAEPaperJPADAO extends JpaRepository<ACAEMock, Integer> {

    @Procedure(procedureName ="ACAE_OPERATIONS.SAVE_REMARK")
    String saveACAECommentRepository(
            @Param("STRING_REFERENCE_ID") String referenceNumber,
            @Param("STRING_ACCT_NO") String accountNumber,
            @Param("STRING_REMARK") String activeComment,
            @Param("DATE_NEG_DATE") Date negDate,
            @Param("DATE_PRE_NEG_DATE")  Date previousNegDate
            );


}
