package com.itechro.cas.dao.facilitypaper;

import com.itechro.cas.model.domain.facilitypaper.CustomerClassification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerClassificationDao extends JpaRepository<CustomerClassification, Long> {

    @Query(
            value =
                    "SELECT " +
                            " ID as id, " +
                            " PARENT_CODE as parentCode, " +
                            " DESCRIPTION as description, " +
                            " LEVEL as depth, " +
                            " SYS_CONNECT_BY_PATH(ID, '>') as path " +
                            "FROM COM_FAC_CODES " +
                            "START WITH PARENT_CODE = 0 " +
                            "CONNECT BY PRIOR ID = PARENT_CODE " +
                            "ORDER SIBLINGS BY ID",
            nativeQuery = true
    )
    List<CustomerClassification> findAllWithParent();
}
