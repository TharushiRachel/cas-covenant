package com.itechro.cas.dao.casv1;

import com.itechro.cas.model.domain.casv1.CustomerDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CasV1CustomerDao extends JpaRepository<CustomerDetail,String> {

    List<CustomerDetail> findAllByAccNo(String accNo);
}
