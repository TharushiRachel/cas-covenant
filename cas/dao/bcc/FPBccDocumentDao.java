package com.itechro.cas.dao.bcc;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.bcc.FPBccDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FPBccDocumentDao extends JpaRepository<FPBccDocument, Integer> {

    @Query(value = "select d.* from T_FP_BCC_DOC d where d.FP_BCC_ID = ?1 and d.DOCUMENT_NAME = ?2 and d.STATUS = ?3", nativeQuery = true)
    FPBccDocument findFpBccByFpBccId(int fpBccId, String docName, String status);

    @Query(value = "select d.* from T_FP_BCC_DOC d where d.FP_BCC_ID = ?1 and d.STATUS = ?2", nativeQuery = true)
    List<FPBccDocument>  findByFpBccAndStatus(Integer fpBccId, String fileStatus);
}
//@Query(value = "select original_user_alias.* from SD_USER original_user_alias",
//        nativeQuery = true,
//        queryRewriter = MyRepository.class)

    //select d.* from T_FP_BCC_DOC d where d.FP_BCC_ID =131 and d.DOCUMENT_NAME = 'Cover Page' and d.STATUS = 'ACT';