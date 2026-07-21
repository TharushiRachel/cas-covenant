package com.itechro.cas.dao.customer;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.model.domain.customer.CustomerBankDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerBankDetailsDao extends JpaRepository<CustomerBankDetail, Integer> {

    public List<CustomerBankDetail> findAllByBankAccountNumberAndStatus(
            String bankAccountNo, AppsConstants.Status status
    );

}
