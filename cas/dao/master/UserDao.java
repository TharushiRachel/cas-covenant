package com.itechro.cas.dao.master;

import com.itechro.cas.commons.constants.AppsConstants;
import com.itechro.cas.commons.constants.DomainConstants;
import com.itechro.cas.model.domain.master.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    User getUserByUserName(String userName);

    User getUserByEmail(String email);

    public User getByUserRefID(Integer userRefID);

    public User findByEmail(String email);

    public User findByUserNameIgnoreCase(String userName);

    public User findByUserNameIgnoreCaseAndUserTypeAndStatus(String userName, DomainConstants.UserType userType, AppsConstants.Status status);

    public User getUserByUserRefIDAndUserType(Integer userRefID, DomainConstants.UserType userType);
}
